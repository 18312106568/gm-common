package gm.common.base.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SimpleCondition<T> implements Serializable {

    private String name;

    private T value;

    SimpleOperator operator;
}
