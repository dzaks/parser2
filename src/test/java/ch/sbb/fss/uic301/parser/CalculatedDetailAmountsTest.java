package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Test for the {@link CalculatedDetailAmounts} class.
 */
public class CalculatedDetailAmountsTest {

    @Test
    public void testAddAndSeal() {

        // PREPARE
        final CalculatedDetailAmounts amounts = new CalculatedDetailAmounts();

        // TEST
        amounts.addAmounts(
                new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(2).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(3).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP));

        // VERIFY
        assertThat(amounts.getGrossAmountToBeCredited()).isEqualTo(
                new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getGrossAmountToBeDebited()).isEqualTo(
                new BigDecimal(2).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getAmountCommissionCredited()).isEqualTo(
                new BigDecimal(3).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getAmountCommissionDebited()).isEqualTo(
                new BigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP));

        // TEST
        amounts.addAmounts(
                new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(2).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(3).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP));

        // VERIFY
        assertThat(amounts.getGrossAmountToBeCredited()).isEqualTo(
                new BigDecimal(2).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getGrossAmountToBeDebited()).isEqualTo(
                new BigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getAmountCommissionCredited()).isEqualTo(
                new BigDecimal(6).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getAmountCommissionDebited()).isEqualTo(
                new BigDecimal(8).setScale(2, BigDecimal.ROUND_HALF_UP));

        // TEST
        amounts.addAmounts(
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));

        // VERIFY
        assertThat(amounts.getGrossAmountToBeCredited()).isEqualTo(
                new BigDecimal(2).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getGrossAmountToBeDebited()).isEqualTo(
                new BigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getAmountCommissionCredited()).isEqualTo(
                new BigDecimal(6).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(amounts.getAmountCommissionDebited()).isEqualTo(
                new BigDecimal(8).setScale(2, BigDecimal.ROUND_HALF_UP));

        // TEST
        amounts.seal();

        // VERIFY
        try {
            amounts.addAmounts(
                    BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                    BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                    BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                    BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
            fail();
        } catch (final UnsupportedOperationException ex) {
            // OK
        }

    }

}
