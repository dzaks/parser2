package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test for {@link StatementPeriod}.
 */
public final class StatementPeriodTest {

    @Test
    public void testInvalidConstructors() {

        try {
            new StatementPeriod(-1, 12, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected year >=0 and <=99, but was: -1");
        }

        try {
            new StatementPeriod(100, 12, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected year >=0 and <=99, but was: 100");
        }

        try {
            new StatementPeriod(17, 0, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected month 1-12, but was: 0");
        }

        try {
            new StatementPeriod(17, 13, 0);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected month 1-12, but was: 13");
        }

        try {
            new StatementPeriod(17, 12, -1);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected period > 0, but was: -1");
        }

        try {
            new StatementPeriod(17, 12, 100);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected period > 0, but was: 100");
        }

    }

    @Test
    public void testValueOf() {

        assertThat(StatementPeriod.valueOf(null)).isNull();
        assertThat(StatementPeriod.valueOf("171200"))
                .isEqualTo(new StatementPeriod(17, 12, 0));
        assertThat(StatementPeriod.valueOf("170100"))
                .isEqualTo(new StatementPeriod(17, 1, 0));
        assertThat(StatementPeriod.valueOf("001200"))
                .isEqualTo(new StatementPeriod(0, 12, 0));
        assertThat(StatementPeriod.valueOf("991299"))
                .isEqualTo(new StatementPeriod(99, 12, 99));

        try {
            StatementPeriod.valueOf("");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMPP, but was: ''");
        }

        try {
            StatementPeriod.valueOf("1712");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMPP, but was: '1712'");
        }

        try {
            StatementPeriod.valueOf("1712001");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMPP, but was: '1712001'");
        }

        try {
            StatementPeriod.valueOf("17MM00");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("Expected YYMMPP, but was: '17MM00'");
        }

    }

    @Test
    public void testValid() {

        assertThat(StatementPeriod.valid(null)).isTrue();
        assertThat(StatementPeriod.valid("171200")).isTrue();
        assertThat(StatementPeriod.valid("170100")).isTrue();
        assertThat(StatementPeriod.valid("001200")).isTrue();
        assertThat(StatementPeriod.valid("991299")).isTrue();

        assertThat(StatementPeriod.valid("")).isFalse();
        assertThat(StatementPeriod.valid("1712")).isFalse();
        assertThat(StatementPeriod.valid("1712001")).isFalse();
        assertThat(StatementPeriod.valid("17MM00")).isFalse();

    }

}
