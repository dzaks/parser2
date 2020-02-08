package ch.sbb.fss.uic301.parser.constraints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ClockProvider;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.junit.Test;

import ch.sbb.fss.uic301.parser.Uic301Document;
import ch.sbb.fss.uic301.parser.Uic301Exception;

/**
 * Test for the {@link AllSameHeaderValidator} class.
 */
public class AllSameHeaderValidatorTest {

    private static final String HEADER_LINE = "14111000011851180190100000049000001";

    private static final String DETAIL_HEADER = "14121000011851180190100";
                                                

    private static final String DETAIL_REST = "003010000011801337801EUR01002000004200000600019022000850033200085000100004525401176322259719010900001000000004200000000000118500629301000000000000000042000000000000000000420050000000000210000000000000DE0050";

    private static final String DETAIL_LINE = DETAIL_HEADER + DETAIL_REST;

    private static final String TOTAL_HEADER = "14131000011851180190100";

    private static final String TOTAL_REST = "0EUR0100000000042000000000000000000000000000000000211000000000399";

    private static final String TOTAL_LINE = TOTAL_HEADER + TOTAL_REST;

    @Test
    public void testIsValidTrue() throws Uic301Exception {

        // PREPARE
        final Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

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
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, "14121000011881180190100" + DETAIL_REST);
        doc.parseTotal(3, TOTAL_LINE);
        doc.seal();

        // TEST & VERIFY
        final Set<ConstraintViolation<Uic301Document>> violations = validator
                .validate(doc);
        assertThat(violations).hasSize(1);
        final ConstraintViolation<Uic301Document> violation = violations
                .iterator().next();
        assertThat(violation.getMessage()).isEqualTo(
                "Some lines do not contain the same information as the header (See log for all errors) - "
                        + "First occurence = Rail Union Compiling mismatch: line #2, header='1185', detail='1188'");

    }

    @Test
    public void testInvalidDetailIdentifier() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, "14221000000871185171100" + DETAIL_REST);
        doc.parseTotal(3, TOTAL_LINE);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Identifier mismatch: line #2, header='141110000', detail='142210000'"));

    }

    @Test
    public void testInvalidDetailRailUnionCompiling() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, "14121000099991180190100" + DETAIL_REST);
        doc.parseTotal(3, TOTAL_LINE);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Rail Union Compiling mismatch: line #2, header='1185', detail='9999'"));

    }

    @Test
    public void testInvalidDetailRailUnionReceiving() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, "14121000011859999171100" + DETAIL_REST);
        doc.parseTotal(3, TOTAL_LINE);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Rail Union Receiving mismatch: line #2, header='1180', detail='9999'"));

    }

    @Test
    public void testInvalidDetailPeriod() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, "14121000011851180190200" + DETAIL_REST);
        doc.parseTotal(3, TOTAL_LINE);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Period mismatch: line #2, header='190100', detail='190200'"));

    }

    @Test
    public void testInvalidTotalIdentifier() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, DETAIL_LINE);
        doc.parseTotal(3, "14231000000871185171100" + TOTAL_REST);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Identifier mismatch: line #3, header='141110000', total='142310000'"));

    }

    @Test
    public void testInvalidTotalRailUnionCompiling() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, DETAIL_LINE);
        doc.parseTotal(3, "14131000099991180190100" + TOTAL_REST);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Rail Union Compiling mismatch: line #3, header='1185', total='9999'"));

    }

    @Test
    public void testInvalidTotalRailUnionReceiving() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, DETAIL_LINE);
        doc.parseTotal(3, "14131000011859999171100" + TOTAL_REST);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Rail Union Receiving mismatch: line #3, header='1180', total='9999'"));

    }

    @Test
    public void testInvalidTotalPeriod() throws Uic301Exception {

        // PREPARE
        final AllSameHeaderValidator testee = new AllSameHeaderValidator();
        final AllSameHeader constraintAnnotation = createAnnotation();
        testee.initialize(constraintAnnotation);

        final Uic301Document doc = new Uic301Document();
        doc.parseHeader(1, HEADER_LINE);
        doc.parseDetail(2, DETAIL_LINE);
        doc.parseTotal(3, "14131000011851180190200" + TOTAL_REST);

        // TEST & VERIFY
        final TestContext ctx = new TestContext();
        assertThat(testee.isValid(doc, ctx)).isFalse();
        assertThat(ctx.getMessageParameters()).contains(entry(
                AllSameHeader.MSG_KEY,
                "Period mismatch: line #3, header='190100', total='190200'"));

    }

    private static AllSameHeader createAnnotation() {
        return new AllSameHeader() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return AllSameHeader.class;
            }

            @Override
            public String message() {
                return AllSameHeader.MESSAGE;
            }

            @Override
            public Class<?>[] groups() {
                return null;
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return null;
            }

            @Override
            public boolean block() {
                return true;
            }
        };
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
