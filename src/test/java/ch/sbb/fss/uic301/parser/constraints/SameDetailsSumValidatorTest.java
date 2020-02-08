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

    private static final String HEADER_LINE = "14111000011851180190100000049000001";

    private static final String DETAIL_LINE = "14121000011851180190100003010000011801337801EUR01002000004200000600019022000850033200085000100004525401176322259719010900001000000004200000000000118500629301000000000000000042000000000000000000420050000000000210000000000000DE0050";

    private static final String TOTAL_LINE = "141310000118511801901000EUR0100000000042000000000000000000000000000000000211000000000399";
    
    private static final String TOTAL_LINE_WITH_ERROR = "141310000118511801901000EUR0100000000043000000000000000000000000000000000211000000000399";

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
        doc.parseDetail(2, DETAIL_LINE);
        doc.parseTotal(3, TOTAL_LINE_WITH_ERROR);
        doc.seal();

        // TEST & VERIFY
        final Set<ConstraintViolation<Uic301Document>> violations = validator
                .validate(doc);
        assertThat(violations).hasSize(1);
        final ConstraintViolation<Uic301Document> violation = violations
                .iterator().next();
        System.out.println(violation.getMessage());
        final char decimalSeparator = new DecimalFormatSymbols(
                Locale.getDefault()).getDecimalSeparator();
        assertThat(violation.getMessage()).isEqualTo(
                "Some totals differ from the calculated details sum: Gross debit mismatch: total=000000000430, sum details=4.2"
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
