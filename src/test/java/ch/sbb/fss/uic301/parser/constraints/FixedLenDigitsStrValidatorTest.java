package ch.sbb.fss.uic301.parser.constraints;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.junit.Test;

import ch.sbb.fss.uic301.parser.Uic301Exception;

/**
 * Test for the {@link FixedLenDigitsStrValidator} class.
 */
public class FixedLenDigitsStrValidatorTest {

    @Test
    public void testIsValid() throws Uic301Exception {

        // PREPARE
        final FixedLenDigitsStrValidator testee = new FixedLenDigitsStrValidator();
        final FixedLenDigitsStr constraintAnnotation = createAnnotation(2);
        testee.initialize(constraintAnnotation);

        // TEST & VERIFY
        assertThat(testee.isValid(null, createContext())).isTrue();
        assertThat(testee.isValid("12", createContext())).isTrue();

        assertThat(testee.isValid("", createContext())).isFalse();
        assertThat(testee.isValid("1", createContext())).isFalse();
        assertThat(testee.isValid("123", createContext())).isFalse();
        assertThat(testee.isValid("ab", createContext())).isFalse();
        assertThat(testee.isValid("AB", createContext())).isFalse();

    }

    private static ConstraintValidatorContext createContext() {
        return new ConstraintValidatorContext() {
            @Override
            public <T> T unwrap(Class<T> type) {
                return null;
            }

            @Override
            public String getDefaultConstraintMessageTemplate() {
                return null;
            }

            @Override
            public ClockProvider getClockProvider() {
                return null;
            }

            @Override
            public void disableDefaultConstraintViolation() {
            }

            @Override
            public ConstraintViolationBuilder buildConstraintViolationWithTemplate(
                    String messageTemplate) {
                return null;
            }
        };
    }

    private static FixedLenDigitsStr createAnnotation(final int len) {
        return new FixedLenDigitsStr() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public int value() {
                return len;
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return null;
            }

            @Override
            public String message() {
                return FixedLenDigitsStr.MESSAGE;
            }

            @Override
            public Class<?>[] groups() {
                return null;
            }
        };
    }

}
