package ch.sbb.fss.uic301.parser;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ch.sbb.fss.uic301.parser.constraints.AllSameHeader;
import ch.sbb.fss.uic301.parser.constraints.SameDetailSums;

/**
 * A single UIC 301 document.
 */
@AllSameHeader(block=true)
@SameDetailSums(block=true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Uic301Document.TAG)
public final class Uic301Document implements Sealable {

    public static final String TAG = "document";

    private transient boolean sealed;
    
    @XmlAttribute(name = "ignore-block")
    private Boolean ignoreBlock;

    @XmlAttribute(name = "patched")
    private boolean patched;
    
    @XmlAttribute(name = "errorCount")
    private Integer errorCount;

    @XmlElement(name = "header")
    private Uic301Header header;

    @XmlElement(name = "details")
    private Uic301Details details;

    @XmlElement(name = "totals")
    private Uic301Totals totals;

    /**
     * Default constructor.
     */
    public Uic301Document() {
        super();
    }

    /**
     * Constructor with all data for testing.
     * 
     * @param header
     *            Header.
     * @param details
     *            Details.
     * @param totals
     *            Totals.
     */
    public Uic301Document(@NotNull final Uic301Header header, @NotNull final Uic301Details details,
            @NotNull final Uic301Totals totals) {
        super();
        this.header = header;
        this.details = details;
        this.totals = totals;
    }

    /**
     * Returns the document header.
     * 
     * @return Header or <code>null</code> if no header line has been parsed.
     */
    public final Uic301Header getHeader() {
        return header;
    }

    /**
     * Returns the details.
     * 
     * @return Details or <code>null</code> if no details line has been parsed.
     */
    public final Uic301Details getDetails() {
        return details;
    }

    /**
     * Returns the totals.
     * 
     * @return Totals or <code>null</code> if no total line has been parsed.
     */
    public final Uic301Totals getTotals() {
        return totals;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorCount == null) ? 0 : errorCount.hashCode());
        result = prime * result + ((details == null) ? 0 : details.hashCode());
        result = prime * result + ((header == null) ? 0 : header.hashCode());
        result = prime * result + ((totals == null) ? 0 : totals.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Uic301Document other = (Uic301Document) obj;
        if (errorCount == null) {
            if (other.errorCount != null) {
                return false;
            }
        } else if (!errorCount.equals(other.errorCount)) {
            return false;
        }
        if (details == null) {
            if (other.details != null) {
                return false;
            }
        } else if (!details.equals(other.details)) {
            return false;
        }
        if (header == null) {
            if (other.header != null) {
                return false;
            }
        } else if (!header.equals(other.header)) {
            return false;
        }
        if (totals == null) {
            if (other.totals != null) {
                return false;
            }
        } else if (!totals.equals(other.totals)) {
            return false;
        }
        return true;
    }

    /**
     * Parses a header line.
     * 
     * @param no
     *            Line number.
     * @param line
     *            Line to parse.
     * 
     * @throws Uic301Exception
     *             Parsing the header failed.
     */
    public final void parseHeader(final int no, final String line) throws Uic301Exception {

        assertNotSealed();

        if (header != null) {
            throw new IllegalStateException("The header was already parsed");
        }

        header = Uic301Header.parse(no, line);

    }

    /**
     * Parses a detail line.
     * 
     * @param no
     *            Line number.
     * @param line
     *            Line to parse.
     * 
     * @throws Uic301Exception
     *             Parsing the detail failed.
     */
    public final void parseDetail(final int no, final String line) throws Uic301Exception {

        assertNotSealed();

        if (details == null) {
            details = new Uic301Details();
        }
        if (Uic301G4Detail.isDetail(line)) {
            details.add(Uic301G4Detail.parse(no, line));
        } else if (Uic301G5Detail.isDetail(line)) {
            details.add(Uic301G5Detail.parse(no, line));
        } else {
            throw new IllegalArgumentException("Input was neither G4 nor G5: '" + line + "'");
        }

    }

    /**
     * Parses a total line.
     * 
     * @param no
     *            Line number.
     * @param line
     *            Line to parse.
     * 
     * @throws Uic301Exception
     *             Parsing the total failed.
     */
    public final void parseTotal(final int no, final String line) throws Uic301Exception {

        assertNotSealed();

        if (totals == null) {
            totals = new Uic301Totals();
        }
        totals.add(Uic301Total.parse(no, line));

    }


    @Override
    public String toString() {
        return  getHeader().getIdentifierTypeFormatted() 
                + " / " + getHeader().getRailUnionCompiling() 
                    + " / " + getHeader().getPeriodTypeFormatted() +" [errors=" + getErrorCount() + "]";
    }

    @Override
    public final void seal() {
        if (!isSealed()) {
            if (header != null) {
                header.seal();
            }
            if (details != null) {
                details.seal();
            }
            if (totals != null) {
                totals.seal();
            }
            sealed = true;
        }
    }

    @Override
    public boolean isSealed() {
        return sealed;
    }

    /**
     * Validates the instance (and child instances).
     * 
     * @param validator
     *            Validator to use.
     */
    public void validate(final Validator validator) {
        assertNotSealed();
        header.validate(validator);
        details.validate(validator);
        totals.validate(validator);
        // Calculate error count
        int count = 0;
        count = count + header.getErrorCount();
        count = count + details.getErrorCount();
        count = count + totals.getErrorCount();
        if (count > 0) {
            errorCount = count;
        }
    }

    /**
     * Returns a number of errors for all childs
     * 
     * @return Number of errors in the headers.
     */
    public int getErrorCount() {
        if (errorCount == null) {
            return 0;
        }
        return errorCount;
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

    public Boolean getIgnoreBlock() {
        return ignoreBlock;
    }

    public void setIgnoreBlock(Boolean ignoreBlock) {
        this.ignoreBlock = ignoreBlock;
    }

    public boolean getPatched() {
        return patched;
    }

    public void setPatched(boolean patched) {
        this.patched = patched;
    }

}
