package ch.sbb.fss.uic301.parser;

import java.util.ArrayList;
import java.util.Arrays;
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


/**
 * Multiple UIC 301 Totals.<br>
 * <br>
 * This is a value object and so equals and has code are based on the values of
 * child totals with all fields, except errors.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Uic301Totals.TAG)
public final class Uic301Totals implements Sealable  {

    public static final String TAG = "totals";

    private transient boolean sealed;

    @XmlAttribute(name = "errorCount")
    private Integer errorCount;

    @XmlElement(name = "total")
    private List<Uic301Total> list;

    /**
     * Default constructor.
     */
    public Uic301Totals() {
        super();
        if (list == null) {
            list = new ArrayList<>();
        }
        sealed = false;
    }

    /**
     * Constructor with array.
     * 
     * @param totals
     *            List of totals.
     */
    public Uic301Totals(@NotNull final Uic301Total... totals) {
        this(Arrays.asList(totals));
    }

    /**
     * Constructor with list.
     * 
     * @param totals
     *            List of totals.
     */
    public Uic301Totals(@NotNull final List<Uic301Total> totals) {
        super();
        this.sealed = false;
        this.list = new ArrayList<>(totals);
    }

    /**
     * Returns the list of totals.
     * 
     * @return Immutable list.
     */
    @NotNull
    public final List<Uic301Total> getList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Adds another total.
     * 
     * @param total
     *            Total to add.
     */
    public final void add(@NotNull final Uic301Total total) {
        assertNotSealed();
        list.add(total);
    }

    @Override
    public final void seal() {
        if (!isSealed()) {
            if (list != null) {
                for (final Uic301Total total : list) {
                    total.seal();
                }
            }
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

    /**
     * Validates the instance (and child instances).
     * 
     * @param validator
     *            Validator to use.
     */
    public void validate(final Validator validator) {
        assertNotSealed();
        for (final Uic301Total total : list) {
            total.validate(validator);
        }
        int count = 0;
        for (final Uic301Total total : list) {
            count = count + total.getErrors().size();
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
        return "Uic301Totals [errorCount=" + errorCount + "]";
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
        Uic301Totals other = (Uic301Totals) obj;
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

    private void assertNotSealed() {
        if (sealed) {
            throw new IllegalStateException("The class is sealed. No more changes are allowed.");
        }
    }
    
    
    /**
     * Adds another total.
     * 
     * @param totalToMerge
     *            Total to add.
     */
    public final void merge(@NotNull final Uic301Total totalToMerge) {
        assertNotSealed();
        if(list.isEmpty()) {
            list.add(totalToMerge);
        } else {
            Uic301Total current = list.get(0) ;
            Uic301Total merged = new Uic301Total(current.getIdentifier(), current.getRailUnionCompiling(), current.getRailUnionReceiving(), 
                    current.getPeriod(), current.getReserved(),
                    current.getStatementCurrency(), current.getStatementPeriod(), 
                    Uic301Utils.addAmounts("grossDebit", current.getGrossDebit(), totalToMerge.getGrossDebit()), 
                    Uic301Utils.addAmounts("grossCredit", current.getGrossCredit(), totalToMerge.getGrossCredit()), 
                    Uic301Utils.addAmounts("amountCommissionDebited", current.getAmountCommissionDebited(), totalToMerge.getAmountCommissionDebited()), 
                    Uic301Utils.addAmounts("amountCommissionCredited", current.getAmountCommissionCredited(), totalToMerge.getAmountCommissionCredited()), 
  
                    current.getDebitCreditBalance(), 
 
                    Uic301Utils.addAmounts("netBalanceAmount", current.getNetBalanceAmount(), totalToMerge.getNetBalanceAmount()), 

                    current.getParsedLineNo());
            list.clear();
            list.add(merged);
        }
    }
    
    


}
