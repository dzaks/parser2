package ch.sbb.fss.uic301.parser;

import javax.validation.constraints.NotEmpty;

/**
 * Combination of ISO 4207 code for currency and period code stipulated by BCC.
 */
public final class StatementCurrencyPeriod
        implements Comparable<StatementCurrencyPeriod> {

    private final String currency;

    private final String period;

    /**
     * Constructor with all data.
     * 
     * @param currency
     *            ISO 4207 currency code.
     * @param period
     *            Period code stipulated by BCC.
     */
    public StatementCurrencyPeriod(@NotEmpty final String currency,
            @NotEmpty final String period) {
        super();
        this.currency = currency;
        this.period = period;
    }

    /**
     * Returns the currency code.
     * 
     * @return ISO 4207 currency code
     */
    public final String getCurrency() {
        return currency;
    }

    /**
     * Returns the period code.
     * 
     * @return Period code stipulated by BCC.
     */
    public final String getPeriod() {
        return period;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((period == null) ? 0 : period.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        StatementCurrencyPeriod other = (StatementCurrencyPeriod) obj;
        if (currency == null) {
            if (other.currency != null) {
                return false;
            }
        } else if (!currency.equals(other.currency)) {
            return false;
        }
        if (period == null) {
            if (other.period != null) {
                return false;
            }
        } else if (!period.equals(other.period)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return currency + period;
    }

    @Override
    public int compareTo(final StatementCurrencyPeriod other) {
        int c = currency.compareTo(other.currency);
        if (c != 0) {
            return c;
        }
        c = period.compareTo(other.period);
        if (c != 0) {
            return c;
        }
        return 0;
    }

}
