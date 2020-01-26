package ch.sbb.fss.uic301.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Validator;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Multiple UIC 301 documents.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Uic301Documents.TAG)
public final class Uic301Documents implements Sealable {

    public static final String TAG = "documents";

    private transient boolean sealed;

    private transient Uic301ParserState currentState;

    private transient Uic301Document currentDoc;

    @XmlAttribute(name = "errorCount")
    private Integer errorCount;

    @XmlElement(name = "document")
    private List<Uic301Document> documents;

    /**
     * Default constructor.
     */
    public Uic301Documents() {
        super();
        sealed = false;
        currentState = Uic301ParserState.TOTAL;
        documents = new ArrayList<>();
    }

    /**
     * Constructor for tests.
     * 
     * @param docs
     *            Documents.
     */
    public Uic301Documents(final Uic301Document... docs) {
        super();
        sealed = false;
        currentState = null;
        documents = new ArrayList<>();
        for (final Uic301Document doc : docs) {
            documents.add(doc);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorCount == null) ? 0 : errorCount.hashCode());
        result = prime * result + ((documents == null) ? 0 : documents.hashCode());
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
        Uic301Documents other = (Uic301Documents) obj;
        if (errorCount == null) {
            if (other.errorCount != null) {
                return false;
            }
        } else if (!errorCount.equals(other.errorCount)) {
            return false;
        }
        if (documents == null) {
            if (other.documents != null) {
                return false;
            }
        } else if (!documents.equals(other.documents)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the UIC 301 documents.
     * 
     * @return Immutable list.
     */
    public final List<Uic301Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Parses a single line.
     * 
     * @param lineNo
     *            Current line number (Used for error messages).
     * @param line
     *            Current line to parse.
     * 
     * @throws Uic301Exception
     *             The parse process failed.
     */
    public final void parse(final int lineNo, final String line) throws Uic301Exception {

        assertNotSealed();

        if (Uic301Header.isHeader(line)) {
            Uic301ParserState.verifyTransition(lineNo, currentState, Uic301ParserState.HEADER);
            currentState = Uic301ParserState.HEADER;
            currentDoc = new Uic301Document();
            documents.add(currentDoc);
            currentDoc.parseHeader(lineNo, line);
        } else if (Uic301G4Detail.isDetail(line) || Uic301G5Detail.isDetail(line)) {
            Uic301ParserState.verifyTransition(lineNo, currentState, Uic301ParserState.DETAIL);
            currentState = Uic301ParserState.DETAIL;
            currentDoc.parseDetail(lineNo, line);
        } else if (Uic301Total.isTotal(line)) {
            Uic301ParserState.verifyTransition(lineNo, currentState, Uic301ParserState.TOTAL);
            currentState = Uic301ParserState.TOTAL;
            currentDoc.parseTotal(lineNo, line);
        } else {
            throw new Uic301Exception("Unknown identifier in line # " + lineNo + ": " + line);
        }

    }

    @Override
    public final void seal() {
        if (!isSealed()) {
            if (documents != null) {
                for (final Uic301Document doc : documents) {
                    doc.seal();
                }
            }
            sealed = true;
        }
    }

    @Override
    public final boolean isSealed() {
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
        for (Uic301Document document : documents) {
            document.validate(validator);
        }
        // Calculate total errors
        int count = 0;
        for (Uic301Document document : documents) {
            count = count + document.getErrorCount();
        }
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

    @Override
    public String toString() {
        return "Uic301Documents [errorCount=" + errorCount + "]";
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

    public static Uic301Documents copy(Uic301Documents docsToCopy) {
        Uic301Documents copy = new Uic301Documents(copyDocuments(docsToCopy.getDocuments()));
        return copy;
    }

    public static Uic301Document[] copyDocuments(List<Uic301Document> originals) {
        List<Uic301Document> result = new ArrayList<Uic301Document>();
        originals.forEach(d -> result.add(copyDocument(d)));
        return result.toArray(new Uic301Document[result.size()]);
    }

    public static Uic301Document copyDocument(Uic301Document doc) {
        Uic301Document copy = new Uic301Document(copyHeader(doc.getHeader()), copyDetails(doc.getDetails()), copyTotals(doc.getTotals()));
        copy.setIgnoreBlock(doc.getIgnoreBlock());
        return copy;
    }

    public static Uic301Header copyHeader(Uic301Header header) {
        return new Uic301Header(header);
    }

    public static Uic301Details copyDetails(Uic301Details detailsToCopy) {
        Uic301Details details = new Uic301Details();
        detailsToCopy.getList().forEach(d -> details.add(copyDetail(d)));
        return details;
    }

    public static Uic301Detail copyDetail(Uic301Detail d) {
        if (d instanceof Uic301G4Detail) {
            return new Uic301G4Detail((Uic301G4Detail) d);
        } else {
            return new Uic301G5Detail((Uic301G5Detail) d);
        }
    }

    public static Uic301Totals copyTotals(Uic301Totals totals) {
        Uic301Totals copy = new Uic301Totals();
        totals.getList().forEach(t -> copy.add(new Uic301Total(t)));
        return copy;
    }
}
