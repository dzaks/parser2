package ch.sbb.fss.uic301.parser;

public enum TransactionType {

    /** Reservation / Sales. */
    RESERVATION_SALES("0", "Reservation / Sales"),

    /** Cancellation. */
    CANCELLATION("1", "cancellation"),

    /** After Sales Fee. */
    AFTER_SALES_FEE("2", "After Sales Fee"),

    /** Electronic refund of ticket. */
    ELECTRONIC_REFUND_OF_TICKET("3", "Electronic refund of ticket"),

    /** Ticket exchange. */
    TICKET_EXCHANGE("4", "Ticket exchange"),

    /** Refund or exchange following a strike. */
    REFUND_OR_EXCHANGE_STRIKE("5", "Refund or exchange following a strike");

    private final String code;

    private String text;

    private TransactionType(final String code, final String text) {
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
        for (TransactionType value : values()) {
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
    public static TransactionType forCode(final String code) {
        for (TransactionType value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
