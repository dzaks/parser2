package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Test for {@link Uic301Utils}.
 */
public final class Uic301UtilsTest {

    @Test
    public void testIntegerOf() {

        assertThat(Uic301Utils.integerOf("a", null)).isNull();
        assertThat(Uic301Utils.integerOf("a", "" + Integer.MIN_VALUE))
                .isEqualTo(Integer.MIN_VALUE);
        assertThat(Uic301Utils.integerOf("a", "-1")).isEqualTo(-1);
        assertThat(Uic301Utils.integerOf("a", "0")).isEqualTo(0);
        assertThat(Uic301Utils.integerOf("a", "1")).isEqualTo(1);
        assertThat(Uic301Utils.integerOf("a", "" + Integer.MAX_VALUE))
                .isEqualTo(Integer.MAX_VALUE);

        try {
            Uic301Utils.integerOf("a", "");
            fail();
        } catch (final RuntimeException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "Failed to convert value for field 'a' into an integer: ''");
        }

        try {
            Uic301Utils.integerOf("a", "x");
            fail();
        } catch (final RuntimeException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "Failed to convert value for field 'a' into an integer: 'x'");
        }

    }

    @Test
    public void testBigDecimalOf() {

        assertThat(Uic301Utils.bigDecimalOf("a", null, 2)).isNull();
        assertThat(Uic301Utils.bigDecimalOf("a", "100", 2)).isEqualTo(
                BigDecimal.ONE.setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(Uic301Utils.bigDecimalOf("a", "1000", 3))
                .isEqualTo(BigDecimal.ONE.setScale(3));
        assertThat(Uic301Utils.bigDecimalOf("a", "000", 2)).isEqualTo(
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(Uic301Utils.bigDecimalOf("a", "123456789", 4))
                .isEqualTo(new BigDecimal(12345.6789).setScale(4,
                        BigDecimal.ROUND_HALF_UP));

        try {
            Uic301Utils.bigDecimalOf("a", "", 2);
            fail();
        } catch (final RuntimeException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "Failed to convert value for field 'a' into a big decimal: ''");
        }

        try {
            Uic301Utils.bigDecimalOf("a", "x", 2);
            fail();
        } catch (final RuntimeException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "Failed to convert value for field 'a' into a big decimal: 'x'");
        }

    }

}
