package ch.sbb.fss.uic301.parser;

/**
 * Distribution channel.
 */
public enum DistributionChannel {

    /** Unknown. */
    UNKNOWN("0"),

    /** Station. */
    STATION("1"),

    /** Travel agency. */
    TRAVEL_AGENCY("2"),

    /** Call center RU. */
    CALL_CENTER_RU("3"),

    /** Sales on board. */
    SALES_ON_BOARD("4"),

    /** Ticket vending machine RU. */
    TICKET_VENDING_MACHINE_RU("5"),

    /** Not used 1. */
    RESERVED_1("6"),

    /** Not used 2. */
    RESERVED_2("7"),

    /** Website travel agency. */
    WEBSITE_TRAVEL_AGENCY("8"),

    /** Website RU. */
    WEBSITE_RU("9");

    private final String code;

    private DistributionChannel(final String code) {
        this.code = code;
    }

    /**
     * Returns the code.
     * 
     * @return Channel code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Determined if the given code is valid.
     * 
     * @param code
     *            Code to verify.
     * 
     * @return <code>true</code> if the code is known.
     */
    public static boolean valid(final String code) {
        for (DistributionChannel value : values()) {
            if (value.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the enumeration instance for the given code. Throws an
     * {@link IllegalArgumentException} if the code is unknown
     * ({@link #valid(String)} will return <code>false</code> in this case.
     * 
     * @param code
     *            Code to return an instance for.
     * 
     * @return Enumeration instance.
     */
    public static DistributionChannel forCode(final String code) {
        for (DistributionChannel value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
