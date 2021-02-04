package com.example.util;

import  com.querydsl.core.types.Predicate;
import org.springframework.expression.common.ExpressionUtils;

import java.util.ArrayList;

@NoArgsConsctructor
public class QPredicates {

    private List<Predicate> predicates = new ArrayList<>();
    
    public static QPredicates    builder() {
        return new QPredicates();
    }

    public Predicate buildAnd() {
        return ExpressionUtils.allof(predicates);
    }

    public Predicate buildOr() {
        return ExpressionUtils.anyof(predicates);
    }


    public <T> QPredicates add (T object, Function<T, Predicate> function) {
         if (object != null) {
             predicates.add(function.apply(object));
         }
         return this;
    }


}
