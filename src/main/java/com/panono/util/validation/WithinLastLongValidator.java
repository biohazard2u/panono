package com.panono.util.validation;

import java.time.Duration;
import java.util.function.Supplier;

import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.annotations.VisibleForTesting;

public class WithinLastLongValidator implements ConstraintValidator<WithinLast, Long> {

    @VisibleForTesting
    public static Supplier<Long> NOW = System::currentTimeMillis;

    private WithinLast annotation;

    @Override
    public void initialize(WithinLast annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(@Nullable Long value, ConstraintValidatorContext context) {
        Duration age = Duration.of(annotation.duration(), annotation.unit());
        return value == null || NOW.get() - value <= age.toMillis();
    }
}
