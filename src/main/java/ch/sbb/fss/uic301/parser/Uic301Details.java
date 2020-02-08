package ch.sbb.fss.uic301.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Multiple UIC 301 Details.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Uic301Details.TAG)
public final class Uic301Details implements Sealable {

    public static final String TAG = "details";

    private transient boolean sealed;

    @XmlAttribute(name = "errorCount")
    private Integer errorCount;

    @XmlElements({ @XmlElement(name = "detail-g4", type = Uic301G4Detail.class),
            @XmlElement(name = "detail-g5", type = Uic301G5Detail.class) })
    private List<Uic301Detail> list;

    private transient Map<StatementCurrencyPeriod, CalculatedDetailAmounts> amounts;

    /**
     * Default constructor.
     */
    public Uic301Details() {
        super();
        if (list == null) {
            list = new ArrayList<>();
        }
        sealed = false;
    }

    /**
     * Constructor with details.
     * 
     * @param details
     *            List of details.
     */
    public Uic301Details(@NotNull final List<Uic301Detail> details) {
        super();
        this.sealed = false;
        this.list = new ArrayList<>(details);
    }

    /**
     * Returns the list of details.
     * 
     * @return Immutable list.
     */
    @NotNull
    public final List<Uic301Detail> getList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Adds another detail.
     * 
     * @param detail
     *            Total to add.
     */
    public final void add(@NotNull final Uic301Detail detail) {
        assertNotSealed();
        list.add(detail);
    }

    /**
     * Returns the amounts of all details in the document. Always call
     * {@link #seal()} once before using this method.
     * 
     * @return Calculated amounts from details.
     */
    public final Map<StatementCurrencyPeriod, CalculatedDetailAmounts> getAmounts() {
        if (!sealed) {
            throw new IllegalStateException(
                    "The instance is not sealed. This means the amounts are not calculated yet.");
        }
        return amounts;
    }

    @Override
    public final void seal() {
        if (!isSealed()) {
            amounts = calculateSums(list);
            sealed = true;
        }
    }

    @Override
    public boolean isSealed() {
        return sealed;
    }

    /**
     * Returns a number of errors for all childs
     * 
     * @return Number of errors in the headers.
     */
    public Integer getErrorCount() {
        if (errorCount == null) {
            return 0;
        }
        return errorCount;
    }

    @Override
    public String toString() {
        return "Uic301Details [errorCount=" + getErrorCount() + "]";
    }

    /**
     * Validates the instance (and child instances).
     * 
     * @param validator
     *            Validator to use.
     */
    public void validate(final Validator validator) {
        assertNotSealed();
        for (final Uic301Detail entry : list) {
            entry.validate(validator);
        }
        // Calculate error sum
        int count = 0;
        for (final Uic301Detail entry : list) {
            count = count + entry.getErrorCount();
        }
        if (count > 0) {
            errorCount = count;
        }
    }

    private void assertNotSealed() {
        if (sealed) {
            throw new IllegalStateException("The class is sealed. No more changes are allowed.");
        }
    }

    private static Map<StatementCurrencyPeriod, CalculatedDetailAmounts> calculateSums(
            final List<Uic301Detail> details) {

        final Map<StatementCurrencyPeriod, CalculatedDetailAmounts> amounts = new HashMap<>();

        // Calculate sums
        final Iterator<Uic301Detail> it = details.iterator();
        while (it.hasNext()) {
            final Uic301Detail detail = it.next();

            final StatementCurrencyPeriod currencyPeriod = detail.getStatementCurrencyPeriod();
            CalculatedDetailAmounts calcAmounts = amounts.get(currencyPeriod);
            if (calcAmounts == null) {
                calcAmounts = new CalculatedDetailAmounts();
                amounts.put(currencyPeriod, calcAmounts);
            }
            calcAmounts.addAmounts(detail);
        }

        seal(amounts);

        return amounts;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorCount == null) ? 0 : errorCount.hashCode());
        result = prime * result + ((list == null) ? 0 : list.hashCode());
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
        Uic301Details other = (Uic301Details) obj;
        if (errorCount == null) {
            if (other.errorCount != null) {
                return false;
            }
        } else if (!errorCount.equals(other.errorCount)) {
            return false;
        }
        if (list == null) {
            if (other.list != null) {
                return false;
            }
        } else if (!list.equals(other.list)) {
            return false;
        }
        return true;
    }

    private static void seal(final Map<StatementCurrencyPeriod, CalculatedDetailAmounts> amounts) {
        final Iterator<StatementCurrencyPeriod> keyIt = amounts.keySet().iterator();
        while (keyIt.hasNext()) {
            final StatementCurrencyPeriod key = keyIt.next();
            final CalculatedDetailAmounts calcAmounts = amounts.get(key);
            calcAmounts.seal();
        }
    }

}
