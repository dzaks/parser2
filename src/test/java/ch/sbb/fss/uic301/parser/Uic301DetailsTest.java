package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Test for the {@link Uic301Details} class.
 */
public class Uic301DetailsTest {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301Details.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValidate() {

        // PREPARE
        final Uic301Details testee = createValidSample();

        // TEST
        testee.validate(VALIDATOR);
        testee.seal();

        // VERIFY
        assertThat(testee.getErrorCount()).isEqualTo(0);

    }

    @Test
    public void testInvalidIdentifier() {

        // PREPARE
        final Uic301Details testee = createInvalidSample();

        // TEST
        testee.validate(VALIDATOR);
        testee.seal();

        // VERIFY
        assertThat(testee.getErrorCount()).isEqualTo(2);

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301Details original = createValidSample();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Details.class);
        final Uic301Details copy = JaxbUtils.unmarshal(xml, Uic301Details.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301Details original = createInvalidSample();
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Details.class);
        final Uic301Details copy = JaxbUtils.unmarshal(xml, Uic301Details.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J.readAsString(this.getClass().getResource("/uic301-details.xml").toURI().toURL(),
                "utf-8", 1024);
        // TEST
        final Uic301Details details = JaxbUtils.unmarshal(xml, Uic301Details.class);

        // VALIDATE
        assertThat(details.getErrorCount()).isEqualTo(2);

    }

    static Uic301Details createValidSample() {
        final Uic301Detail g4 = Uic301G4DetailTest.createValidSample();
        final List<Uic301Detail> list = new ArrayList<>();
        list.add(g4);
        return new Uic301Details(list);
    }

    static Uic301Details createInvalidSample() {
        final Uic301Detail g4 = Uic301G4DetailTest.createInvalidSample();
        final List<Uic301Detail> list = new ArrayList<>();
        list.add(g4);
        return new Uic301Details(list);
    }

}
