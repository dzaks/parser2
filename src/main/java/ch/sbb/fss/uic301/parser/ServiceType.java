package ch.sbb.fss.uic301.parser;

public enum ServiceType {

    /** NRT domestic. */
    UNDEFINED("00", "Undefined"),

    /** NRT domestic. */
    NRT_DOMESTIC("01", "NRT domestic"),

    /** NRT international. */
    NRT_INTERNATIONAL("02", "NRT international"),

    /** Train supplements. */
    TRAIN_SUPPLEMENTS("03", "Train supplements"),

    /** Combined service. */
    COMBINED_SERVICE("04", "Combined service"),

    /** IRT domestic. */
    IRT_DOMESTIC("05", "IRT domestic"),

    /** Couchette (not IRT). */
    COUCHETTE_NOT_IRT("06", "Couchette (not IRT)"),

    /** IRT international. */
    IRT_INTERNATIONAL("07", "IRT international"),

    /** Sleeper (not IRT). */
    SLEEPER_NOT_IRT("40", "Sleeper (not IRT)");

    private final String code;

    private String text;

    private ServiceType(final String code, final String text) {
        this.code = code;
        this.text = text;
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
     * Returns the English text.
     * 
     * @return Text.
     */
    public String getText() {
        return text;
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
        for (ServiceType value : values()) {
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
    public static ServiceType forCode(final String code) {
        for (ServiceType value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
