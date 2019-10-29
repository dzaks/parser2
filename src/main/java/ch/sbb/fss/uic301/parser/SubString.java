package ch.sbb.fss.uic301.parser;

/**
 * Stateful substring helper.
 */
public final class SubString {

    private int pos;

    private String str;

    private StringBuilder trace;

    /**
     * Constructor with text to substring.
     * 
     * @param str
     *            Text - Never <code>null</code>.
     */
    public SubString(final String str) {
        super();
        if (str == null) {
            throw new IllegalArgumentException("Argument null is not allowed");
        }
        this.pos = 0;
        this.str = str;
        this.trace = new StringBuilder();
    }

    /**
     * Returns the next <code>len</code> character.
     * 
     * @param name
     *            Name of the field to parse (for error message and tracing) -
     *            Never <code>null</code> or an empty string.
     * @param len
     *            Number of characters to return (Value &gt; 0).
     * 
     * @return Next sub string.
     * 
     * @throws NextMissingSubStrException
     *             String too short.
     */
    public final String next(final String name, final int len)
            throws NextMissingSubStrException {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "Expected a non-null and non-empty name, but was: " + name);
        }
        if (len < 1) {
            throw new IllegalArgumentException(
                    "Expected a length greater than 0, but was: " + len);
        }
        if (pos + len > str.length()) {
            throw new NextMissingSubStrException(pos, len, name,
                    trace.toString(), str);
        }
        final String sub = str.substring(pos, pos + len);

        if (pos > 0) {
            trace.append(", ");
        }
        trace.append(name);
        trace.append("='");
        trace.append(sub);
        trace.append("'");

        pos = pos + len;
        return sub;
    }

    /**
     * Returns the trace information.
     * 
     * @return Trace information.
     */
    public final String getTrace() {
        return trace.toString();
    }

}
