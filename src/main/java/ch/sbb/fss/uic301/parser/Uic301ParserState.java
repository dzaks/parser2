package ch.sbb.fss.uic301.parser;

/**
 * State of parsing a file that may contain multiple UIC 301 documents.
 */
public enum Uic301ParserState {

    /** The parsing just started. */
    INIT,

    /** Currently parsing a header. */
    HEADER,

    /** Currently parsing a detail line. */
    DETAIL,

    /** Currently parsing a total. */
    TOTAL;

    /**
     * Verifies if a transition from one state to another is valid.
     * 
     * @param lineNo
     *            Current line number.
     * @param current
     *            Current state (before parsing the next line).
     * @param next
     *            Next line to parse.
     * 
     * @throws Uic301Exception
     *             State transition failed.
     */
    public static void verifyTransition(final int lineNo,
            final Uic301ParserState current, final Uic301ParserState next)
            throws Uic301Exception {
        switch (current) {
            case INIT:
                if (next != HEADER) {
                    throw new Uic301Exception("State is " + current
                            + " and expected next is " + HEADER + ", but was: "
                            + next + " [Line # " + lineNo + "]");
                }
                break;
            case HEADER:
                if (next != DETAIL) {
                    throw new Uic301Exception("State is " + current
                            + " and expected next is " + DETAIL + ", but was: "
                            + next + " [Line # " + lineNo + "]");
                }
                break;
            case DETAIL:
                if (next != TOTAL && next != DETAIL) {
                    throw new Uic301Exception("State is " + current
                            + " and expected next is " + DETAIL + " or " + TOTAL
                            + ", but was: " + next + " [Line # " + lineNo + "]");
                }
                break;
            case TOTAL:
                if (next != HEADER && next != TOTAL) {
                    throw new Uic301Exception("State is " + current
                            + " and expected next is " + HEADER + " or " + TOTAL
                            + ", but was: " + next + " [Line # " + lineNo + "]");
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown state: " + current);
        }

    }

}