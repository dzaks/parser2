package ch.sbb.fss.uic301.parser;

/**
 * Objects that are the result from parsing a line.
 */
public interface ParsedLineItem {

    /**
     * Returns the parsed line number.
     * 
     * @return Number of parsed line or -1 if not constructed by parser.
     */
    public int getParsedLineNo();

}
