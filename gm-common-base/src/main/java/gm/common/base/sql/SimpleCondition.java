package gm.common.base.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleCondition<T> {

    private String name;

    private T value;

    SimpleOperator operator;
}
