package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.fuin.utils4j.JaxbUtils;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Test for {@link FieldError}.
 */
public final class FieldErrorTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(FieldError.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testConstructors() {

        try {
            new FieldError(null, "WTF");
            fail();
        } catch (final NullPointerException ex) {
            assertThat(ex.getMessage()).isEqualTo("Argument 'field' must not be null");
        }

        try {
            new FieldError("abc", null);
            fail();
        } catch (final NullPointerException ex) {
            assertThat(ex.getMessage()).isEqualTo("Argument 'error' must not be null");
        }

        final FieldError testee = new FieldError("abc", "WTF");
        assertThat(testee.getField()).isEqualTo("abc");
        assertThat(testee.getError()).isEqualTo("WTF");

    }

    @Test
    public void testMarshalUnmarshal() {

        // PREPARE
        final FieldError original = new FieldError("abc", "WTF");

        // TEST
        final String xml = JaxbUtils.marshal(original, FieldError.class);
        final FieldError copy = JaxbUtils.unmarshal(xml, FieldError.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

}
