package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test for {@link Date}.
 */
public final class DateTest {

    @Test
    public void testInvalidConstructors() {

        try {
            new Date(-1, 12, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected year >=0 and <=99, but was: -1");
        }

        try {
            new Date(100, 12, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected year >=0 and <=99, but was: 100");
        }

        try {
            new Date(17, 0, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected month 1-12, but was: 0");
        }

        try {
            new Date(17, 13, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected month 1-12, but was: 13");
        }

        try {
            new Date(17, 12, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected day 1-31, but was: 0");
        }

        try {
            new Date(17, 12, 32);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected day 1-31, but was: 32");
        }

    }

    @Test
    public void testValueOf() {

        assertThat(Date.valueOf(null)).isNull();
        assertThat(Date.valueOf("171231")).isEqualTo(new Date(17, 12, 31));
        assertThat(Date.valueOf("170101")).isEqualTo(new Date(17, 1, 1));

        try {
            Date.valueOf("");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMDD, but was: ''");
        }

        try {
            Date.valueOf("1712");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMDD, but was: '1712'");
        }

        try {
            Date.valueOf("1712001");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMDD, but was: '1712001'");
        }

        try {
            Date.valueOf("17MM00");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMDD, but was: '17MM00'");
        }

        try {
            Date.valueOf("171232");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMDD, but was: '171232'");
        }

    }

    @Test
    public void testValid() {

        assertThat(Date.valid(null)).isTrue();
        assertThat(Date.valid("171231")).isTrue();
        assertThat(Date.valid("170101")).isTrue();
        assertThat(Date.valid("001231")).isTrue();

        assertThat(Date.valid("")).isFalse();
        assertThat(Date.valid("1712")).isFalse();
        assertThat(Date.valid("1712001")).isFalse();
        assertThat(Date.valid("17MM00")).isFalse();
        assertThat(Date.valid("171232")).isFalse();
        assertThat(Date.valid("170031")).isFalse();
        assertThat(Date.valid("171200")).isFalse();

    }

}
