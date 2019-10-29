package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

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
 * Test for the {@link Uic301Header} class.
 */
public class Uic301HeaderTest {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301Header.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValid() {

        // PREPARE
        final Uic301Header testee = createValidSample();

        // TEST
        final Set<ConstraintViolation<Uic301Header>> violations = VALIDATOR.validate(testee);

        // VERIFY
        assertThat(violations).hasSize(0);

    }

    @Test
    public void testInvalidIdentifier() {

        // TEST
        final Set<ConstraintViolation<Uic301Header>> violations = VALIDATOR
                .validate(new Uic301Header("1411A0000", "0087", "1185", "171100", "083955", "000001", -1));

        // VERIFY
        assertSingleViolation(violations, "identifier",
                "Expected one of [141110000, 144110000, 142110000, 145110000, 143110000], but was: 1411A0000");

    }

    @Test
    public void testInvalidRailUnionCompiling() {

        // TEST
        final Set<ConstraintViolation<Uic301Header>> violations = VALIDATOR
                .validate(new Uic301Header("141110000", "0X87", "1185", "171100", "083955", "000001", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionCompiling", "Wrong RICS code (0X87)");

    }

    @Test
    public void testInvalidRailUnionReceiving() {

        // TEST
        final Set<ConstraintViolation<Uic301Header>> violations = VALIDATOR
                .validate(new Uic301Header("141110000", "0087", "11850", "171100", "083955", "000001", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionReceiving", "Wrong RICS code (11850)");

    }

    @Test
    public void testInvalidPeriod() {

        // TEST
        final Set<ConstraintViolation<Uic301Header>> violations = VALIDATOR
                .validate(new Uic301Header("141110000", "0087", "1185", "x", "083955", "000001", -1));

        // VERIFY
        assertSingleViolation(violations, "period", "Expected 'YYMMPP', but was: 'x'");

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301Header original = createValidSample();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Header.class);
        final Uic301Header copy = JaxbUtils.unmarshal(xml, Uic301Header.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301Header original = createInvalidSample();
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Header.class);
        final Uic301Header copy = JaxbUtils.unmarshal(xml, Uic301Header.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J
                .readAsString(this.getClass().getResource("/uic301-header.xml").toURI().toURL(), "utf-8", 1024);
        // TEST
        final Uic301Header header = JaxbUtils.unmarshal(xml, Uic301Header.class);

        // VALIDATE
        assertThat(header.getErrors()).hasSize(6);
        assertThat(header.getErrors()).contains(new FieldError("railUnionReceiving", "Wrong RICS code (1X85)"));

    }

    private void assertSingleViolation(final Set<ConstraintViolation<Uic301Header>> violations, final String property,
            final String message) {
        assertThat(violations).hasSize(1);
        final ConstraintViolation<Uic301Header> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo(message);
        assertThat(violation.getPropertyPath().iterator().next().toString()).isEqualTo(property);
    }

    /**
     * Creates a sample with valid data.
     * 
     * @return Valid instance.
     */
    static Uic301Header createValidSample() {
        final String identifier = "141110000"; // G4
        final String railUnionCompiling = "0087"; // SNCF
        final String railUnionReceiving = "1185"; // SBB
        final String period = "171100"; // 17 Nov
        final String noOfDetailPhrases = "083955";
        final String noOfTotalPhrases = "000001";
        final Uic301Header testee = new Uic301Header(identifier, railUnionCompiling, railUnionReceiving, period,
                noOfDetailPhrases, noOfTotalPhrases, -1);
        return testee;
    }

    /**
     * Creates a sample with invalid data.
     * 
     * @return Invalid instance.
     */
    static Uic301Header createInvalidSample() {
        final String identifier = "1411A0000";
        final String railUnionCompiling = "0X87";
        final String railUnionReceiving = "1X85";
        final String period = "x";
        final String noOfDetailPhrases = "y";
        final String noOfTotalPhrases = "z";
        final Uic301Header testee = new Uic301Header(identifier, railUnionCompiling, railUnionReceiving, period,
                noOfDetailPhrases, noOfTotalPhrases, -1);
        return testee;
    }

}
