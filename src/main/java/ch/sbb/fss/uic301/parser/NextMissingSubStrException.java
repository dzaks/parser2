package ch.sbb.fss.uic301.parser;

/**
 * The number of next characters is not available because the string is too
 * short.
 */
public final class NextMissingSubStrException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int pos;

    private final int len;

    private final String name;

    private final String trace;

    private final String line;

    /**
     * Constructor with mandatory data.
     * 
     * @param pos
     *            Current position.
     * @param len
     *            Length.
     * @param name
     *            Field name.
     * @param trace
     *            Information parsed so far.
     * @param line
     *            Current line.
     */
    public NextMissingSubStrException(final int pos, final int len,
            final String name, final String trace, final String line) {
        super("Cannot return " + len + " character" + (len > 1 ? "s" : "")
                + " for field '" + name + "' starting at position " + pos
                + ": trace=[" + trace + "], line='" + line + "'");

        this.pos = pos;
        this.len = len;
        this.name = name;
        this.trace = trace;
        this.line = line;

    }

    /**
     * Returns the current position.
     * 
     * @return Position.
     */
    public final int getPos() {
        return pos;
    }

    /**
     * Returns the length.
     * 
     * @return Requested length.
     */
    public final int getLen() {
        return len;
    }

    /**
     * Returns the name.
     * 
     * @return Field name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the trace information.
     * 
     * @return Information about what was parsed so far.
     */
    public final String getTrace() {
        return trace;
    }

    /**
     * Returns the line that lead to this exception.
     * 
     * @return Current line parsed.
     */
    public final String getLine() {
        return line;
    }

}
