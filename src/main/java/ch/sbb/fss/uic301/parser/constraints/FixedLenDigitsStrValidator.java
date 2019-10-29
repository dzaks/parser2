package ch.sbb.fss.uic301.parser.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Verifies if a string has only digits and has exactly a given length. A
 * <code>null</code> string is considered a valid value.
 */
public final class FixedLenDigitsStrValidator
        implements ConstraintValidator<FixedLenDigitsStr, String> {

    private int expectedLen;

    @Override
    public void initialize(FixedLenDigitsStr constraintAnnotation) {
        this.expectedLen = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final String value,
            final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.length() != expectedLen) {
            return false;
        }
        return value.matches("[0-9]*");
    }

}
