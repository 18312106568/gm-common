package gm.common.base.sql;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractQuery<T> {

    /**
     * 拼装preditcate
     * @param from
     * @param simpleCondition 条件
     * @param criteriaBuilder
     * @return
     */
    protected  Predicate predicate(From from,SimpleCondition simpleCondition,CriteriaBuilder criteriaBuilder){
        switch(simpleCondition.getOperator()){
            case EQ:
                return criteriaBuilder.equal(
                        from.get(simpleCondition.getName()), simpleCondition.getValue());

            case GE:
                return criteriaBuilder.greaterThanOrEqualTo(
                        from.get(simpleCondition.getName()),(Comparable)simpleCondition.getValue());

            case LE:
                return criteriaBuilder.lessThanOrEqualTo(
                        from.get(simpleCondition.getName()),(Comparable)simpleCondition.getValue());

            case NE:
                return criteriaBuilder.notEqual(
                        from.get(simpleCondition.getName()),simpleCondition.getValue());

            case GT:
                return criteriaBuilder.greaterThan(
                        from.get(simpleCondition.getName()),(Comparable)simpleCondition.getValue());

            case LT:
                return criteriaBuilder.lessThan(
                        from.get(simpleCondition.getName()),(Comparable)simpleCondition.getValue());

            case IN:
                criteriaBuilder.in(
                        from.get(simpleCondition.getName())).value(simpleCondition.getValue());

            case LIKE:
                return criteriaBuilder.like(
                        from.get(simpleCondition.getName()),"%"+(simpleCondition.getValue()+"%"));

        }
        return null;
    }

    /**
     * 简单查询，无表连接
     * @param root
     * @param criteriaBuilder
     * @param booleanOperator
     * @return
     */
    protected Predicate where( Root<T> root,
                               CriteriaBuilder criteriaBuilder,
                               Predicate.BooleanOperator booleanOperator,
                               Collection<SimpleCondition> simpleConditions) {

        List<Predicate> list = new ArrayList<>();
        for(SimpleCondition simpleCondition:simpleConditions){
            Predicate predicate = predicate(root,simpleCondition,criteriaBuilder);
            if(predicate!=null) {
                list.add(predicate);
            }
        }
        Predicate[] p = new Predicate[list.size()];
        if(Predicate.BooleanOperator.AND.equals(booleanOperator)){
            return criteriaBuilder.and(list.toArray(p));
        }
        return criteriaBuilder.or(list.toArray(p));
    }


    /**
     *
     * @param childName
     * @param childClazz
     * @param root
     * @param criteriaBuilder
     * @param simpleConditionGroups 查询条件组
     * @return
     */
    protected Predicate joinPredicate(Root root,
                                    String childName,
                                    Class childClazz,
                                    CriteriaBuilder criteriaBuilder,
                                    Collection<Collection<SimpleCondition>> simpleConditionGroups,
                                    Boolean isList) {
        List<Predicate> list = new ArrayList<>();
        for(Collection<SimpleCondition> collection :simpleConditionGroups){
            Join join = null;
            if(isList){
                join = root.join(root.getModel().getList(childName,childClazz), JoinType.LEFT);
            }else{
                join = root.join(root.getModel().getSingularAttribute(childName,childClazz),JoinType.LEFT);
            }
            for(SimpleCondition simpleCondition :collection) {
                Predicate predicate = predicate(join, simpleCondition, criteriaBuilder);
                if (predicate != null) {
                    list.add(predicate);
                }
            }
        }
        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));
    }

    /**
     * 获取查询条件
     * @param root
     * @param criteriaQuery
     * @param cb
     * @param mainAndCondition
     * @param mainOrCondition
     * @return
     */
    protected Predicate getPredicate(Root<T> root
            , CriteriaQuery criteriaQuery
            , CriteriaBuilder cb
            , Collection<SimpleCondition> mainAndCondition
            , Collection<SimpleCondition> mainOrCondition) {
        List<Predicate> predicateList = new ArrayList<>();
        //主表and查询条件
        if (mainAndCondition != null && !mainAndCondition.isEmpty()) {
            Predicate mainAndPredicate = where(root, cb, Predicate.BooleanOperator.AND, mainAndCondition);
            predicateList.add(mainAndPredicate);
        }
        //主表or查询条件
        if (mainOrCondition != null && !mainOrCondition.isEmpty()) {
            Predicate mainOrPredicate = where(root, cb, Predicate.BooleanOperator.OR, mainOrCondition);
            predicateList.add(mainOrPredicate);
        }
        return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }

}
