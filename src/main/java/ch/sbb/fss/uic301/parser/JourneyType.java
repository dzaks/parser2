package ch.sbb.fss.uic301.parser;

/**
 * Type of journey.
 */
public enum JourneyType {

    /** not used. */
    NOT_USED("0"),

    /** single journey. */
    SINGLE_JOURNEY("1"),

    /** return, with out- and inbound trip on the same route. */
    RETURN_TRIP("2"),

    /** round trip, outbound leg, with price calculated as a single journey. */
    ROUND_TRIP_OUTBOUND_LEG_SINGLE_JOURNEY("3"),

    /** round trip, outbound leg, with price calculated as half return fare. */
    ROUND_TRIP_OUTBOUND_LEG_HALF_RETURN_FARE("4"),

    /** round trip, inbound leg, with price calculated as single journey. */
    ROUND_TRIP_INBOUND_LEG_SINGLE_JOURNEY("5"),

    /** round trip, inbound leg, with price calculated as half return fare. */
    ROUND_TRIP_INBOUND_LEG_HALF_RETURN_FARE("6"),

    /** pass. */
    PASS("9");

    private final String code;

    private JourneyType(final String code) {
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
        for (JourneyType value : values()) {
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
    public static JourneyType forCode(final String code) {
        for (JourneyType value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
