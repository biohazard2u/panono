package com.panono.util.validation;

public interface CastsUtils {

    @SuppressWarnings("unchecked")
    static <T> Class<T> cast(Class<?> target) {
        return (Class<T>) target;
    }
}
