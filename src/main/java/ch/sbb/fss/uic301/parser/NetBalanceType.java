package ch.sbb.fss.uic301.parser;

public enum NetBalanceType {

    /** Neither a debit nor a credit (The amount is zero). */
    NONE("0"),

    /** the net balance amount is a debit. */
    DEBIT("1"),

    /** the net balance amount is a credit. */
    CREDIT("2");

    private final String code;

    private NetBalanceType(final String code) {
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
        for (NetBalanceType value : values()) {
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
    public static NetBalanceType forCode(final String code) {
        for (NetBalanceType value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
