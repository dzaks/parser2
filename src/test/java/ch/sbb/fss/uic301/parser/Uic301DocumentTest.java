package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;

import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Test for the {@link Uic301Document} class.
 */
public class Uic301DocumentTest {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301Document.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValidate() {

        // PREPARE
        final Uic301Document testee = createValidSample();

        // TEST
        testee.validate(VALIDATOR);
        testee.seal();

        // VERIFY
        assertThat(testee.getErrorCount()).isEqualTo(0);

    }

    @Test
    public void testInvalid() {

        // PREPARE
        final Uic301Document testee = createInvalidSample();

        // TEST
        testee.validate(VALIDATOR);
        testee.seal();

        // VERIFY
        assertThat(testee.getErrorCount()).isEqualTo(21);

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301Document original = createValidSample();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Document.class);
        final Uic301Document copy = JaxbUtils.unmarshal(xml, Uic301Document.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301Document original = createInvalidSample();
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Document.class);
        final Uic301Document copy = JaxbUtils.unmarshal(xml, Uic301Document.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J
                .readAsString(this.getClass().getResource("/uic301-document.xml").toURI().toURL(), "utf-8", 1024);
        // TEST
        final Uic301Document document = JaxbUtils.unmarshal(xml, Uic301Document.class);

        // VALIDATE
        assertThat(document.getErrorCount()).isEqualTo(21);

    }


    @Test
    public void testIgnoreBlock() {

        // PREPARE
        final Uic301Document original = createValidSample();
        original.setIgnoreBlock(Boolean.TRUE);

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Document.class);
        final Uic301Document copy = JaxbUtils.unmarshal(xml, Uic301Document.class);

        // VERIFY
        assertTrue(copy.getIgnoreBlock());

    }
    
    static Uic301Document createValidSample() {
        final Uic301Header header = Uic301HeaderTest.createValidSample();
        final Uic301Details details = Uic301DetailsTest.createValidSample();
        final Uic301Totals totals = Uic301TotalsTest.createValidSample();
        return new Uic301Document(header, details, totals);
    }

    static Uic301Document createInvalidSample() {
        final Uic301Header header = Uic301HeaderTest.createInvalidSample();
        final Uic301Details details = Uic301DetailsTest.createInvalidSample();
        final Uic301Totals totals = Uic301TotalsTest.createInvalidSample();
        return new Uic301Document(header, details, totals);
    }

}
