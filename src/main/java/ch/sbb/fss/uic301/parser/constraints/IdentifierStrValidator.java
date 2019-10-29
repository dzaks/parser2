package ch.sbb.fss.uic301.parser.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Verifies if a string is one of the expected identifiers. A <code>null</code>
 * string is considered a valid value.
 */
public final class IdentifierStrValidator
        implements ConstraintValidator<IdentifierStr, String> {

    private String[] expectedValues;

    @Override
    public void initialize(IdentifierStr constraintAnnotation) {
        this.expectedValues = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final String value,
            final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (final String expectedValue : expectedValues) {
            if (expectedValue.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
