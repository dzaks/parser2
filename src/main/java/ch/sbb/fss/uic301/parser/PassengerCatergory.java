package ch.sbb.fss.uic301.parser;

/**
 * Passenger catergory.
 */
public enum PassengerCatergory {

    /** Unused or unknown. */
    UNKNOWN("00"),

    /* Adult. */
    ADULT("11"),

    /* Youth. */
    YOUTH("12"),

    /* Child. */
    CHILD("13"),

    /* Senior. */
    SENIOR("18"),

    /* Dog. */
    DOG("19");

    private final String code;

    private PassengerCatergory(final String code) {
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
        for (PassengerCatergory value : values()) {
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
    public static PassengerCatergory forCode(final String code) {
        for (PassengerCatergory value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
