package ch.sbb.fss.uic301.parser.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Verifies if a string has a fixed lengths, only zeroes and is not
 * <code>null</code>.
 */
public final class ReservedStrValidator
        implements ConstraintValidator<ReservedStr, String> {

    private int expectedLen;

    @Override
    public void initialize(ReservedStr constraintAnnotation) {
        this.expectedLen = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final String value,
            final ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.length() != expectedLen) {
            return false;
        }
        return value.matches("[0]*");
    }

}
