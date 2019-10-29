package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Test for the {@link Uic301Totals} class.
 */
public class Uic301TotalsTest {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301Totals.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValid() {

        // PREPARE
        final Uic301Totals testee = createValidSample();

        // TEST
        final Set<ConstraintViolation<Uic301Totals>> violations = VALIDATOR.validate(testee);

        // VERIFY
        assertThat(violations).hasSize(0);

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301Totals original = createValidSample();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Totals.class);
        final Uic301Totals copy = JaxbUtils.unmarshal(xml, Uic301Totals.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301Totals original = createInvalidSample();
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Totals.class);
        final Uic301Totals copy = JaxbUtils.unmarshal(xml, Uic301Totals.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J
                .readAsString(this.getClass().getResource("/uic301-totals.xml").toURI().toURL(), "utf-8", 1024);
        // TEST
        final Uic301Totals testee = JaxbUtils.unmarshal(xml, Uic301Totals.class);

        // VALIDATE
        assertThat(testee.getErrorCount()).isEqualTo(13);
        assertThat(testee.getList()).hasSize(2);
        assertThat(testee.getList().get(0).getErrors()).hasSize(13);
        assertThat(testee.getList().get(1).getErrors()).isEmpty();
        try {
            testee.validate(VALIDATOR);
            fail();
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo("The class is sealed. No more changes are allowed.");
        }

    }

    /**
     * Creates a sample with valid data.
     * 
     * @return Valid instance.
     */
    static Uic301Totals createValidSample() {
        return new Uic301Totals(Uic301TotalTest.createValidSample());
    }

    /**
     * Creates a sample with invalid data.
     * 
     * @return Invalid instance.
     */
    static Uic301Totals createInvalidSample() {
        return new Uic301Totals(Uic301TotalTest.createInvalidSample());
    }

}
