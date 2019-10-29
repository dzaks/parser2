package ch.sbb.fss.uic301.parser;

/**
 * Type of issue/allocation.
 */
public enum IssueAllocationIdentifier {

    /** Unknown/undefined. */
    UNDEFINED("0"),

    /** Hermes reference number. */
    HERMES_REFERENCE_NUMBER("1"),

    /** Electronic issue number. */
    ELECTRONIC_ISSUE_NUMBER("2"),

    /**
     * Physical number of manual transport document entered in the accounting
     * system.
     */
    PHYSICAL_NUMBER_DOCUMENT_ACCOUNTING_SYSTEM("3"),

    /**
     * Physical number of manual transport document, where shares are calculated
     * manually.
     */
    PHYSICAL_NUMBER_DOCUMENT_CALCULATED_MANUALLY("4"),

    /** Number of appended list. */
    NUMBER_APPENDED_LIST("5"),

    /**
     * Virtual number of the manual transport document entered in an accounting
     * system.
     */
    VIRTUAL_NUMBER_DOCUMENT_ACCOUNTING_SYSTEM("6");

    private final String code;

    private IssueAllocationIdentifier(final String code) {
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
        for (IssueAllocationIdentifier value : values()) {
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
    public static IssueAllocationIdentifier forCode(final String code) {
        for (IssueAllocationIdentifier value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
