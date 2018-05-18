package com.purdynet.siqproduct.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T> List<T> asSingleton(T singleton) {
        List<T> list = new ArrayList<>();
        list.add(singleton);
        return list;
    }

    public static <T> List<T> asList(T... items) {
        List<T> list = new ArrayList<>();
        for (T item : items) {
            list.add(item);
        }
        return list;
    }
}
