package ch.sbb.fss.uic301.parser.constraints;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.ClockProvider;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.junit.Test;

import ch.sbb.fss.uic301.parser.Uic301Document;
import ch.sbb.fss.uic301.parser.Uic301Exception;

/**
 * Test for the {@link SameDetailsSumValidator} class.
 */
public class SameDetailsSumValidatorTest {

    private static final String HEADER_LINE = "14111000000871185171100083955000001";

    private static final String DETAIL_LINE = "14121000000871185171100007010000011850000000EUR01001000121000921300017112400877130401185000100008718980231420000017112200001000000121000000000000008700100011008700271110001210000000000000000012100100000000012100000000000000CH0000";

    private static final String TOTAL_LINE = "141310000008711851711000EUR0100000001210000000000000000000001210000000000001000000013310";

    @Test
    public void testIsValidTrue() throws Uic301Exception {

        // PREPARE
        final Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        final SameDetailsSumValidator testee = new SameDetailsSumValidator();

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, DETAIL_LINE);
        doc.parseTotal(3, TOTAL_LINE);
        doc.seal();

        // TEST & VERIFY
        final Set<ConstraintViolation<Uic301Document>> violations = validator
                .validate(doc);
        assertThat(violations).isEmpty();
        assertThat(testee.isValid(doc, new TestContext())).isTrue();

    }

    @Test
    public void testIsValidFalse() throws Uic301Exception {

        // PREPARE
        final Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        final SameDetailsSumValidator testee = new SameDetailsSumValidator();

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2,
                "14121000000871185171100007010000011850000000EUR01001000121000921300017112400877130401185000100008718980231420000017112200001000000121000000000000008700100011008700271110001210000000000000000012100100000000012300000000000000CH0000");
        doc.parseTotal(3, TOTAL_LINE);
        doc.seal();

        // TEST & VERIFY
        final Set<ConstraintViolation<Uic301Document>> violations = validator
                .validate(doc);
        assertThat(violations).hasSize(1);
        final ConstraintViolation<Uic301Document> violation = violations
                .iterator().next();
        final char decimalSeparator = new DecimalFormatSymbols(
                Locale.getDefault()).getDecimalSeparator();
        assertThat(violation.getMessage()).isEqualTo(
                "Some totals differ from the calculated details sum: Amount commission debited mismatch: total=12.1, sum details=12.3, Net balance amount mismatch: total=133.1, sum details=133.3"
                        .replace('.', decimalSeparator));
        assertThat(testee.isValid(doc, new TestContext())).isFalse();

    }

    private static class TestContext
            implements HibernateConstraintValidatorContext {

        private Map<String, Object> messageParameters = new HashMap<>();

        public Map<String, Object> getMessageParameters() {
            return messageParameters;
        }

        @Override
        public <T> T unwrap(Class<T> type) {
            if (HibernateConstraintValidatorContext.class
                    .isAssignableFrom(type)) {
                return (T) this;
            }
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

        @Override
        public HibernateConstraintValidatorContext addExpressionVariable(
                String key, Object value) {
            return this;
        }

        @Override
        public HibernateConstraintValidatorContext addMessageParameter(
                String key, Object value) {
            messageParameters.put(key, value);
            return this;
        }

        @Override
        public HibernateConstraintValidatorContext withDynamicPayload(
                Object arg0) {
            return this;
        }

    }

}
