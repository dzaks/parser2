package ch.sbb.fss.uic301.parser.constraints;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.junit.Test;

import ch.sbb.fss.uic301.parser.Uic301Exception;

/**
 * Test for the {@link IdentifierStrValidator} class.
 */
public class IdentifierStrValidatorTest {

    @Test
    public void testIsValid() throws Uic301Exception {

        // PREPARE
        final IdentifierStrValidator testee = new IdentifierStrValidator();
        final IdentifierStr constraintAnnotation = createAnnotation("1", "23");
        testee.initialize(constraintAnnotation);

        // TEST & VERIFY
        assertThat(testee.isValid(null, createContext())).isTrue();
        assertThat(testee.isValid("1", createContext())).isTrue();
        assertThat(testee.isValid("23", createContext())).isTrue();

        assertThat(testee.isValid("", createContext())).isFalse();
        assertThat(testee.isValid("2", createContext())).isFalse();
        assertThat(testee.isValid("123", createContext())).isFalse();
        assertThat(testee.isValid("ab", createContext())).isFalse();
        assertThat(testee.isValid("ABC", createContext())).isFalse();

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

    private static IdentifierStr createAnnotation(final String... values) {
        return new IdentifierStr() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String[] value() {
                return values;
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return null;
            }

            @Override
            public String message() {
                return IdentifierStr.MESSAGE;
            }

            @Override
            public Class<?>[] groups() {
                return null;
            }
        };
    }

}
