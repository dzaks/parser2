package ch.sbb.fss.uic301.parser;

import static ch.sbb.fss.uic301.parser.Uic301Utils.integerOf;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ch.sbb.fss.uic301.parser.StatementPeriod.StatementPeriodStr;
import ch.sbb.fss.uic301.parser.constraints.FixedLenDigitsStr;
import ch.sbb.fss.uic301.parser.constraints.IdentifierStr;
import ch.sbb.fss.uic301.parser.constraints.RicsCodeStr;

/**
 * UIC 301 Header.<br>
 * <br>
 * This is a value object and so equals and has code are based on the values of
 * all fields.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Uic301Header.TAG)
public final class Uic301Header implements Sealable {

    public static final String TAG = "header";

    private static final String G4 = "141110000";

    private static final String G4_DB = "144110000";

    private static final String G5_ALLOCATION = "142110000";

    private static final String G5_ALLOCATION_DB = "145110000";

    private static final String G5_ISSUES = "143110000";

    private transient boolean sealed = false;

    @XmlAttribute(name = "errorCount")
    private Integer errorCount;

    @XmlElement(name = "field-error")
    private List<FieldError> errors;

    /**
     * Identifies the type of the header<br>
     * [#1, 9 characters, Mandatory].<br>
     * "141110000" = G4<br>
     * "142110000" = G5 / Allocations<br>
     * "143110000" = G5 / Issues<br>
     */
    @NotNull
    @IdentifierStr({ G4, G4_DB, G5_ALLOCATION, G5_ALLOCATION_DB, G5_ISSUES })
    @XmlAttribute(name = "identifier")
    private String identifier;

    /**
     * RU compiling the statement (RICS code)<br>
     * [#2, 4 characters, Mandatory].
     */
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "railUnionCompiling")
    private String railUnionCompiling;

    /**
     * RU receiving the statement (RICS code)<br>
     * [#3, 4 characters, Mandatory].
     */
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "railUnionReceiving")
    private String railUnionReceiving;

    /**
     * Statement period (YYMMPP). YY=Year, MM=Month, PP=Period in the month (00
     * default, other usage must be bilaterally agreed upon)<br>
     * [#4, 6 characters, Mandatory].
     */
    @NotNull
    @StatementPeriodStr
    @XmlAttribute(name = "period")
    private String period;

    /**
     * Number of detail phrases<br>
     * [#5, 6 characters, Mandatory].
     */
    @NotNull
    @FixedLenDigitsStr(6)
    @XmlAttribute(name = "noOfDetailPhrases")
    private String noOfDetailPhrases;

    /**
     * Number of total phrases<br>
     * [#6, 6 characters, Mandatory].
     */
    @NotNull
    @FixedLenDigitsStr(6)
    @XmlAttribute(name = "noOfTotalPhrases")
    private String noOfTotalPhrases;

    /** Number of parsed line or -1 if not constructed by parser. */
    @XmlAttribute(name = "lineNo")
    private int parsedLineNo;

    /**
     * Constructor for JAX-B.
     */
    protected Uic301Header() {
        super();
    }

    Uic301Header(final String identifier, final String railUnionCompiling, final String railUnionReceiving,
            final String period, final String noOfDetailPhrases, final String noOfTotalPhrases,
            final int parsedLineNo) {
        super();
        this.identifier = identifier;
        this.railUnionCompiling = railUnionCompiling;
        this.railUnionReceiving = railUnionReceiving;
        this.period = period;
        this.noOfDetailPhrases = noOfDetailPhrases;
        this.noOfTotalPhrases = noOfTotalPhrases;
        this.parsedLineNo = parsedLineNo;
    }

    Uic301Header(Uic301Header toCopy) {
        super();
        this.identifier = toCopy.getIdentifier();
        this.railUnionCompiling = toCopy.getRailUnionCompiling();
        this.railUnionReceiving = toCopy.getRailUnionReceiving();
        this.period = toCopy.getPeriod();
        this.noOfDetailPhrases = toCopy.getNoOfDetailPhrases();
        this.noOfTotalPhrases = toCopy.getNoOfTotalPhrases();
        this.parsedLineNo = toCopy.getParsedLineNo();
    }

    /**
     * Returns the type of the identifier.
     * 
     * @return Identifier type.
     */
    public Uic301Type getIdentifierType() {
        if (identifier == null) {
            return null;
        }
        if (G4.equals(identifier) || G4_DB.equals(identifier)) {
            return Uic301Type.G4;
        }
        if (G5_ALLOCATION.equals(identifier) || G5_ALLOCATION_DB.equals(identifier)) {
            return Uic301Type.G5_ALLOCATION;
        }
        if (G5_ISSUES.equals(identifier)) {
            return Uic301Type.G5_ISSUE;
        }
        throw new IllegalArgumentException("Unknown identifier type: '" + identifier + "'");
    }

    /**
     * Returns the formatted identifier. Returns the original value in case of an
     * exception.
     * 
     * @return Formatted value.
     */
    public String getIdentifierTypeFormatted() {
        try {
            final Uic301Type type = getIdentifierType();
            if (type == null) {
                return "";
            }
            return type.name();
        } catch (final RuntimeException ex) {
            return identifier;
        }
    }

    public String getPeriod() {
        return period;
    }

    /**
     * Returns the statement period.
     * 
     * @return Statement period.
     */
    public StatementPeriod getPeriodType() {
        if (period == null) {
            return null;
        }
        return StatementPeriod.valueOf(period);
    }

    /**
     * Returns the period type as formatted string. Returns the original value in
     * case of an exception.
     * 
     * @return Formatted string.
     */
    public String getPeriodTypeFormatted() {
        try {
            final StatementPeriod pt = getPeriodType();
            if (pt == null) {
                return "";
            }
            return pt.toString();
        } catch (final RuntimeException ex) {
            return period;
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getRailUnionCompiling() {
        return railUnionCompiling;
    }

    public String getRailUnionReceiving() {
        return railUnionReceiving;
    }

    public String getNoOfDetailPhrases() {
        return noOfDetailPhrases;
    }

    public Integer getNoOfDetailPhrasesValue() {
        if (noOfDetailPhrases == null) {
            return null;
        }
        return integerOf("noOfDetailPhrases", noOfDetailPhrases);
    }

    /**
     * Returns a formatted integer string. Returns in case of exceptions the
     * original value.
     * 
     * @return No of detail phrases as text.
     */
    public String getNoOfDetailPhrasesFormatted() {
        try {
            final Integer value = getNoOfDetailPhrasesValue();
            if (value == null) {
                return "";
            }
            return new DecimalFormat("###,###.###").format(value);
        } catch (final RuntimeException ex) {
            return noOfDetailPhrases;
        }
    }

    public String getNoOfTotalPhrases() {
        return noOfTotalPhrases;
    }

    public Integer getNoOfTotalPhrasesValue() {
        if (noOfTotalPhrases == null) {
            return null;
        }
        return integerOf("noOfTotalPhrases", noOfTotalPhrases);
    }

    /**
     * Returns a formatted integer string. Returns in case of exceptions the
     * original value.
     * 
     * @return No of total phrases as text.
     */
    public String getNoOfTotalPhrasesFormatted() {
        try {
            final Integer value = getNoOfTotalPhrasesValue();
            if (value == null) {
                return "";
            }
            return new DecimalFormat("###,###.###").format(value);
        } catch (final RuntimeException ex) {
            return noOfTotalPhrases;
        }
    }

    public int getParsedLineNo() {
        return parsedLineNo;
    }

    public void setRailUnionCompiling(String railUnionCompiling) {
        this.railUnionCompiling = railUnionCompiling;
    }


    public void setRailUnionReceiving(String railUnionReceiving) {
        this.railUnionReceiving = railUnionReceiving;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorCount == null) ? 0 : errorCount.hashCode());
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((noOfDetailPhrases == null) ? 0 : noOfDetailPhrases.hashCode());
        result = prime * result + ((noOfTotalPhrases == null) ? 0 : noOfTotalPhrases.hashCode());
        result = prime * result + parsedLineNo;
        result = prime * result + ((period == null) ? 0 : period.hashCode());
        result = prime * result + ((railUnionCompiling == null) ? 0 : railUnionCompiling.hashCode());
        result = prime * result + ((railUnionReceiving == null) ? 0 : railUnionReceiving.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Uic301Header other = (Uic301Header) obj;
        if (errorCount == null) {
            if (other.errorCount != null) {
                return false;
            }
        } else if (!errorCount.equals(other.errorCount)) {
            return false;
        }
        if (errors == null) {
            if (other.errors != null) {
                return false;
            }
        } else if (!errors.equals(other.errors)) {
            return false;
        }
        if (identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!identifier.equals(other.identifier)) {
            return false;
        }
        if (noOfDetailPhrases == null) {
            if (other.noOfDetailPhrases != null) {
                return false;
            }
        } else if (!noOfDetailPhrases.equals(other.noOfDetailPhrases)) {
            return false;
        }
        if (noOfTotalPhrases == null) {
            if (other.noOfTotalPhrases != null) {
                return false;
            }
        } else if (!noOfTotalPhrases.equals(other.noOfTotalPhrases)) {
            return false;
        }
        if (parsedLineNo != other.parsedLineNo) {
            return false;
        }
        if (period == null) {
            if (other.period != null) {
                return false;
            }
        } else if (!period.equals(other.period)) {
            return false;
        }
        if (railUnionCompiling == null) {
            if (other.railUnionCompiling != null) {
                return false;
            }
        } else if (!railUnionCompiling.equals(other.railUnionCompiling)) {
            return false;
        }
        if (railUnionReceiving == null) {
            if (other.railUnionReceiving != null) {
                return false;
            }
        } else if (!railUnionReceiving.equals(other.railUnionReceiving)) {
            return false;
        }
        return true;
    }

    /**
     * Validates this object and saves the violations as errors.
     */
    public void validate(@NotNull final Validator validator) {
        assertNotSealed();
        final List<FieldError> list = Uic301Utils.validate(validator, this);
        if (list.size() == 0) {
            errorCount = null;
        } else {
            errors = list;
            errorCount = errors.size();
        }
    }

    /**
     * Returns a number of errors.
     * 
     * @return Number of errors.
     */
    public int getErrorCount() {
        if (errorCount == null) {
            return 0;
        }
        return errorCount;
    }

    /**
     * Returns a list of errors.
     * 
     * @return Errors in the header.
     */
    @NotNull
    public List<FieldError> getErrors() {
        if (errors == null) {
            return Collections.emptyList();
        }
        return errors;
    }

    @Override
    public String toString() {
        return getIdentifierTypeFormatted() + " - " + getPeriodTypeFormatted();
    }

    @Override
    public void seal() {
        if (!sealed) {
            sealed = true;
        }
    }

    @Override
    public boolean isSealed() {
        return sealed;
    }

    /**
     * Executed after unmarshalling of this object.
     * 
     * @param unmarshaller
     *            Unmarshaller.
     * @param parent
     *            Parent oject.
     */
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        seal();
    }

    private void assertNotSealed() {
        if (sealed) {
            throw new IllegalStateException("The class is sealed. No more changes are allowed.");
        }
    }

    /**
     * Creates a new header by parsing a string.
     * 
     * @param no
     *            Line number.
     * @param line
     *            Line to parse.
     * 
     * @return Returns a new instance with the parsed data.
     * 
     * @throws Uic301Exception
     *             Parsing the header failed.
     */
    public static Uic301Header parse(final int no, final String line) throws Uic301Exception {
        if (!isHeader(line)) {
            throw new IllegalArgumentException("# " + no + " is no header line: '" + line + "'");
        }

        try {

            final SubString subStr = new SubString(line);
            final String identifier = subStr.next("identifier", 9);
            final String railUnionCompiling = subStr.next("railUnionCompiling", 4);
            final String railUnionReceiving = subStr.next("railUnionReceiving", 4);
            final String period = subStr.next("period", 6);
            final String noOfDetailPhrases = subStr.next("noOfDetailPhrases", 6);
            final String noOfTotalPhrases = subStr.next("noOfTotalPhrases", 6);

            return new Uic301Header(identifier, railUnionCompiling, railUnionReceiving, period, noOfDetailPhrases,
                    noOfTotalPhrases, no);

        } catch (final NextMissingSubStrException ex) {
            throw new Uic301Exception("Header line # " + no + " not parseable: " + ex.getMessage());
        }

    }

    /**
     * Determines if the given line is a header line.
     * 
     * @param line
     *            Line to analyze.
     * 
     * @return True if the line starts with a known header identifier.
     */
    public static boolean isHeader(final String line) {
        return line != null && (line.startsWith(G4) || line.startsWith(G4_DB) || line.startsWith(G5_ALLOCATION)
                || line.startsWith(G5_ALLOCATION_DB) || line.startsWith(G5_ISSUES));
    }

}
