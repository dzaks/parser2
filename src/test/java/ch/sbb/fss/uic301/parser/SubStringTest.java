package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test for {@link SubString}.
 */
public final class SubStringTest {

    @Test
    public void testNullConstructor() {
        try {
            new SubString(null);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Argument null is not allowed");
        }
    }

    @Test
    public void testNextNoName() {
        final SubString testee = new SubString("");
        try {
            testee.next(null, 1);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "Expected a non-null and non-empty name, but was: null");
        }
        try {
            testee.next("", 1);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "Expected a non-null and non-empty name, but was: ");
        }
    }

    @Test
    public void testNextWrongLen() {
        final SubString testee = new SubString("");
        try {
            testee.next("a", -1);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected a length greater than 0, but was: -1");
        }
        try {
            testee.next("a", 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected a length greater than 0, but was: 0");
        }
    }

    @Test
    public void testNextMissing() {
        final SubString testee = new SubString("");
        assertThat(testee.next("a", 1)).isEqualTo(" ");
    }

    @Test
    public void testNextMissingSubStrException() {
        final SubString testee = new SubString("123ABC");
        
        // Existing
        assertThat(testee.next("a", 3)).isEqualTo("123");
        assertThat(testee.next("b", 3)).isEqualTo("ABC");
        
        // Missing
        assertThat(testee.next("c", 1)).isEqualTo(" ");
        assertThat(testee.next("d", 3)).isEqualTo("   ");
    }

    @Test
    public void testNextValid() {

        final SubString testee1 = new SubString("123ABC");
        assertThat(testee1.next("a", 3)).isEqualTo("123");
        assertThat(testee1.next("b", 3)).isEqualTo("ABC");

        final SubString testee2 = new SubString("123");
        assertThat(testee2.next("a", 1)).isEqualTo("1");
        assertThat(testee2.next("b", 2)).isEqualTo("23");

        final SubString testee3 = new SubString("123");
        assertThat(testee3.next("a", 3)).isEqualTo("123");

    }

}
