package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test for {@link StatementCurrencyPeriod}.
 */
public class StatementCurrencyPeriodTest {

    @Test
    public void testConstruction() {

        // PREPARE/TEST
        final StatementCurrencyPeriod testee = new StatementCurrencyPeriod(
                "EUR", "01");

        // VERIFY
        assertThat(testee.getCurrency()).isEqualTo("EUR");
        assertThat(testee.getPeriod()).isEqualTo("01");

    }

    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(StatementCurrencyPeriod.class).verify();
    }

}
