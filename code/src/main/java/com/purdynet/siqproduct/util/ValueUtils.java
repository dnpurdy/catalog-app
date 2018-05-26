package com.purdynet.siqproduct.util;

public class ValueUtils {
    public static <T> T nvl(T a, T b) {
        return (a == null)?b:a;
    }
}
