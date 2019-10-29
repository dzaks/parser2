package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;

import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Test for the {@link Uic301Documents} class.
 */
public class Uic301DocumentsTest {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301Documents.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValidate() {

        // PREPARE
        final Uic301Documents testee = new Uic301Documents(Uic301DocumentTest.createValidSample());

        // TEST
        testee.validate(VALIDATOR);
        testee.seal();

        // VERIFY
        assertThat(testee.getErrorCount()).isEqualTo(0);

    }

    @Test
    public void testInvalid() {

        // PREPARE
        final Uic301Documents testee = new Uic301Documents(Uic301DocumentTest.createInvalidSample());

        // TEST
        testee.validate(VALIDATOR);
        testee.seal();

        // VERIFY
        assertThat(testee.getErrorCount()).isEqualTo(21);

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301Documents original = new Uic301Documents(Uic301DocumentTest.createValidSample());

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Documents.class);
        final Uic301Documents copy = JaxbUtils.unmarshal(xml, Uic301Documents.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301Documents original = new Uic301Documents(Uic301DocumentTest.createInvalidSample());
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Documents.class);
        System.out.println(xml);
        final Uic301Documents copy = JaxbUtils.unmarshal(xml, Uic301Documents.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J
                .readAsString(this.getClass().getResource("/uic301-documents.xml").toURI().toURL(), "utf-8", 1024);
        // TEST
        final Uic301Documents documents = JaxbUtils.unmarshal(xml, Uic301Documents.class);

        // VALIDATE
        assertThat(documents.getErrorCount()).isEqualTo(21);

    }

    @Test
    public void testCopy() {
        // PREPARE
        final Uic301Documents testee = new Uic301Documents(Uic301DocumentTest.createValidSample());

        // TEST
        Uic301Documents copy = Uic301Documents.copy(testee);

        // VERIFY
        assertThat(copy.getDocuments().size()).isEqualTo(testee.getDocuments().size());
        assertThat(copy.getDocuments().get(0).getDetails().getList().size()).isEqualTo(testee.getDocuments().get(0).getDetails().getList().size());
    }


}
