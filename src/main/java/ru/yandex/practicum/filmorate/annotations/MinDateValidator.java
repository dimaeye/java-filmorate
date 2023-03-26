package ru.yandex.practicum.filmorate.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinDateValidator implements ConstraintValidator<MinDateConstraint, LocalDate> {
    private LocalDate minLocalDate;

    @Override
    public void initialize(MinDateConstraint constraintAnnotation) {
        minLocalDate = LocalDate.of(
                constraintAnnotation.year(), constraintAnnotation.month(), constraintAnnotation.day()
        );
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return minLocalDate.isBefore(localDate);
    }
}
