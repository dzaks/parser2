package ch.sbb.fss.uic301.parser;

/**
 * Some kind of error happened that couldn't be handled by the parser.
 */
public final class Uic301Exception extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor only with message.
     * 
     * @param message
     *            Error message.
     */
    public Uic301Exception(final String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message
     *            Error message.
     * @param cause
     *            Original error.
     */
    public Uic301Exception(final String message, final Throwable cause) {
        super(message, cause);
    }

}
