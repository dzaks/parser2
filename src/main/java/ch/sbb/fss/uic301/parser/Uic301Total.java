package ch.sbb.fss.uic301.parser;

import static ch.sbb.fss.uic301.parser.Uic301Utils.bigDecimalOf;
import static ch.sbb.fss.uic301.parser.Uic301Utils.integerOf;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.Mappings;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.TableColumn;
import org.fuin.objects4j.ui.Tooltip;

import ch.sbb.fss.uic301.parser.StatementPeriod.StatementPeriodStr;
import ch.sbb.fss.uic301.parser.constraints.CurrencyStr;
import ch.sbb.fss.uic301.parser.constraints.FixedLenDigitsStr;
import ch.sbb.fss.uic301.parser.constraints.IdentifierStr;
import ch.sbb.fss.uic301.parser.constraints.ReservedStr;
import ch.sbb.fss.uic301.parser.constraints.RicsCodeStr;

/**
 * UIC 301 Total.<br>
 * <br>
 * This is a value object and so equals and has code are based on the values of
 * all fields.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Uic301Total.TAG)
public final class Uic301Total implements ParsedLineItem, Sealable, Uic301DocumentItem {

    public static final String TAG = "total";

    private static final String G4 = "141310000";

    private static final String G4_DB = "144310000";

    private static final String G5_ALLOCATION = "142310000";

    private static final String G5_ALLOCATION_DB = "145310000";

    private static final String G5_ISSUES = "143310000";

    private transient boolean sealed = false;

    @XmlAttribute(name = "errorCount")
    private Integer errorCount;

    @XmlElement(name = "field-error")
    private List<FieldError> errors;

    /**
     * Identifies the type of the total<br>
     * [#1, 9 characters, Mandatory].
     */
    @Label("Identifier")
    @ShortLabel("Type")
    @Tooltip("141310000=G4, 142310000=G5/Allocation, 143310000=G5/Issues")
    @TableColumn(pos = 1, width = 90)
    @Mappings({ "141310000=G4", "142310000=G5/Allocation", "143310000=G5/Issues" })
    @NotNull
    @IdentifierStr({ G4, G4_DB, G5_ALLOCATION, G5_ALLOCATION_DB, G5_ISSUES })
    @XmlAttribute(name = "identifier")
    private String identifier;

    /**
     * RU compiling the statement (RICS code)<br>
     * [#2, 4 characters, Mandatory].
     */
    @Label("RU Compiling")
    @ShortLabel("RU Cmp.")
    @Tooltip("RU compiling the statement (RICS Code) [2]")
    @TableColumn(pos = 2, width = 70)
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "railUnionCompiling")
    private String railUnionCompiling;

    /**
     * RU receiving the statement (RICS code)<br>
     * [#3, 4 characters, Mandatory].
     */
    @Label("RU Receiving")
    @ShortLabel("RU Rcv.")
    @Tooltip("RU receiving the statement (RICS Code) [3]")
    @TableColumn(pos = 3, width = 70)
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "railUnionReceiving")
    private String railUnionReceiving;

    /**
     * Statement period (YYMMPP). YY=Year, MM=Month, PP=Period in the month (00
     * default, other usage must be bilaterally agreed upon)<br>
     * [#4, 6 characters, Mandatory].
     */
    @Label("Period")
    @ShortLabel("Period")
    @Tooltip("Statement period (YYMMPP). YY=Year, MM=Month, PP=Period in the month "
            + "(00 default, other usage must be bilaterally agreed upon) [4]")
    @TableColumn(pos = 4, width = 60)
    @NotNull
    @StatementPeriodStr
    @XmlAttribute(name = "period")
    private String period;

    /**
     * Reserved, always "0"<br>
     * [#5, 1 character, Mandatory].
     */
    @ReservedStr(1)
    @XmlAttribute(name = "reserved")
    private String reserved;

    /**
     * ISO 4207 code for currency, normally EUR<br>
     * [#6, 3 characters, Mandatory].
     */
    @Label("Statement Currency")
    @ShortLabel("St.Curr.")
    @Tooltip("ISO 4207 code for currency, normally EUR [6(1)]")
    @TableColumn(pos = 5, width = 70)
    @NotNull
    @CurrencyStr
    @XmlAttribute(name = "statementCurrency")
    private String statementCurrency;

    /**
     * Period code stipulated by BCC, always 01 for EUR<br>
     * [#6, 2 characters, Mandatory].
     */
    @Label("Statement Period")
    @ShortLabel("St.Per.")
    @Tooltip("Period code stipulated by BCC [6(2)]")
    @TableColumn(pos = 6, width = 70)
    @NotNull
    @FixedLenDigitsStr(2)
    @XmlAttribute(name = "statementPeriod")
    private String statementPeriod;

    /**
     * Gross debit<br>
     * [#7, 12 characters, Mandatory].<br>
     * Sum of field {@link Uic301G4Detail#getGrossAmountToBeDebited()} (G4) or
     * {@link Uic301G5Detail#getGrossAmountToBeDebited()} (G5) in corresponding
     * detail records. Ten positions preceding and two positions following the
     * decimal point.
     */
    @Label("Gross debit")
    @ShortLabel("Gross debit")
    @Tooltip("Gross debit sum of the detail fields [7]")
    @TableColumn(pos = 7, width = 120)
    @NotNull
    @FixedLenDigitsStr(12)
    @XmlAttribute(name = "grossDebit")
    private String grossDebit;

    /**
     * Gross credit<br>
     * [#8, 12 characters, Mandatory].<br>
     * Sum of field {@link Uic301G4Detail#getGrossAmountToBeCredited()} (G4) or
     * {@link Uic301G5Detail#getGrossAmountToBeCredited()} (G5) in corresponding
     * detail records. Ten positions preceding and two positions following the
     * decimal point.
     */
    @Label("Gross credit")
    @ShortLabel("Gross credit")
    @Tooltip("Gross credit sum of the detail fields [8]")
    @TableColumn(pos = 8, width = 120)
    @NotNull
    @FixedLenDigitsStr(12)
    @XmlAttribute(name = "grossCredit")
    private String grossCredit;

    /**
     * Amount of commission to be debited<br>
     * [#9, 11 characters, Mandatory].<br>
     * Sum of field "amountCommissionCreditedServiceProvidingRU" in the
     * corresponding detail records. Nine positions preceding and two positions
     * following the decimal point.
     * 
     */
    @Label("Amount commission debit")
    @ShortLabel("Comm. debit")
    @Tooltip("Amount of commission to be debited (sum of the detail fields) [9]")
    @TableColumn(pos = 9, width = 120)
    @NotNull
    @FixedLenDigitsStr(11)
    @XmlAttribute(name = "amountCommissionDebited")
    private String amountCommissionDebited;

    /**
     * Amount of commission to be credited<br>
     * [#10, 11 characters, Mandatory].<br>
     * Sum of field "amountCommissionDebitedServiceProvidingRU" in the corresponding
     * detail records. Nine positions preceding and two positions following the
     * decimal point.
     * 
     */
    @Label("Amount commission credit")
    @ShortLabel("Comm. credit")
    @Tooltip("Amount of commission to be credited (sum of the detail fields) [10]")
    @TableColumn(pos = 10, width = 120)
    @NotNull
    @FixedLenDigitsStr(11)
    @XmlAttribute(name = "amountCommissionCredited")
    private String amountCommissionCredited;

    /**
     * Debit/credit balance<br>
     * [#11, 1 character, Mandatory].
     */
    @Label("Debit/credit balance")
    @ShortLabel("D/C")
    @Tooltip("Debit/credit balance (1=Net balance is a debit, 2=Net balance is a credit) [11]")
    @TableColumn(pos = 11, width = 40)
    @Mappings({ "1=the net balance amount is a debit", "2=the net balance amount is a credit" })
    @NotNull
    @Pattern(regexp = "[1-2]", message = "Only '1' or '2' allowed, but was: '${validatedValue}'")
    @XmlAttribute(name = "debitCreditBalance")
    private String debitCreditBalance;

    /**
     * Net balance amount<br>
     * [#12, 12 characters, Mandatory].<br>
     * Calculated with field {@link #grossCredit} - field {@link #grossDebit} +
     * field {@link #amountCommissionCredited} - field
     * {@link #amountCommissionDebited}. Ten positions preceding and two positions
     * following the decimal point.
     * 
     */
    @Label("Net balance amount")
    @ShortLabel("Net balance")
    @Tooltip("Net balance amount calculated from the other sums [12]")
    @TableColumn(pos = 12, width = 120)
    @NotNull
    @FixedLenDigitsStr(12)
    @XmlAttribute(name = "netBalanceAmount")
    private String netBalanceAmount;

    /** Number of parsed line or -1 if not constructed by parser. */
    @XmlAttribute(name = "lineNo")
    private int parsedLineNo;

    /**
     * Constructor for JAX-B.
     */
    protected Uic301Total() {
        super();
    }

    Uic301Total(final String identifier, final String railUnionCompiling, final String railUnionReceiving,
            final String period, final String reserved, final String statementCurrency, final String statementPeriod,
            final String grossDebit, final String grossCredit, final String amountCommissionDebited,
            final String amountCommissionCredited, final String debitCreditBalance, final String netBalanceAmount,
            final int parsedLineNo) {

        this.identifier = identifier;
        this.railUnionCompiling = railUnionCompiling;
        this.railUnionReceiving = railUnionReceiving;
        this.period = period;
        this.reserved = reserved;
        this.statementCurrency = statementCurrency;
        this.statementPeriod = statementPeriod;
        this.grossDebit = grossDebit;
        this.grossCredit = grossCredit;
        this.amountCommissionDebited = amountCommissionDebited;
        this.amountCommissionCredited = amountCommissionCredited;
        this.debitCreditBalance = debitCreditBalance;
        this.netBalanceAmount = netBalanceAmount;
        this.parsedLineNo = parsedLineNo;

    }

    Uic301Total(final Uic301Total totalsToCopy) {
        this.identifier = totalsToCopy.getIdentifier();
        this.railUnionCompiling = totalsToCopy.getRailUnionCompiling();
        this.railUnionReceiving = totalsToCopy.getRailUnionReceiving();
        this.period = totalsToCopy.getPeriod();
        this.reserved = totalsToCopy.getReserved();
        this.statementCurrency = totalsToCopy.getStatementCurrency();
        this.statementPeriod = totalsToCopy.getStatementPeriod();
        this.grossDebit = totalsToCopy.getGrossDebit();
        this.grossCredit = totalsToCopy.getGrossCredit();
        this.amountCommissionDebited = totalsToCopy.getAmountCommissionDebited();
        this.amountCommissionCredited = totalsToCopy.getAmountCommissionCredited();
        this.debitCreditBalance = totalsToCopy.getDebitCreditBalance();
        this.netBalanceAmount = totalsToCopy.getNetBalanceAmount();
        this.parsedLineNo = totalsToCopy.getParsedLineNo();

    }

    /**
     * Returns the type of the identifier.
     * 
     * @return Identifier type.
     */
    public Uic301Type getIdentifierType() {
        if (G4.equals(identifier)) {
            return Uic301Type.G4;
        }
        if (G5_ALLOCATION.equals(identifier)) {
            return Uic301Type.G5_ALLOCATION;
        }
        if (G5_ISSUES.equals(identifier)) {
            return Uic301Type.G5_ISSUE;
        }
        return null;
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

    public String getPeriod() {
        return period;
    }

    public String getReserved() {
        return reserved;
    }

    public String getStatementCurrency() {
        return statementCurrency;
    }

    public String getStatementPeriod() {
        return statementPeriod;
    }

    /**
     * Combination of ISO 4207 code for currency and period code stipulated by BCC.
     *
     * @return Currency and period.
     */
    public StatementCurrencyPeriod getStatementCurrencyPeriod() {
        return new StatementCurrencyPeriod(statementCurrency, statementPeriod);
    }

    public String getGrossDebit() {
        return grossDebit;
    }

    public BigDecimal getGrossDebitValue() {
        return bigDecimalOf("grossDebit", grossDebit, 2);
    }

    public String getGrossCredit() {
        return grossCredit;
    }

    public BigDecimal getGrossCreditValue() {
        return bigDecimalOf("grossCredit", grossCredit, 2);
    }

    public String getAmountCommissionDebited() {
        return amountCommissionDebited;
    }

    public BigDecimal getAmountCommissionDebitedValue() {
        return bigDecimalOf("amountCommissionDebited", amountCommissionDebited, 2);
    }

    public String getAmountCommissionCredited() {
        return amountCommissionCredited;
    }

    public BigDecimal getAmountCommissionCreditedValue() {
        return bigDecimalOf("amountCommissionCredited", amountCommissionCredited, 2);
    }

    public String getDebitCreditBalance() {
        return debitCreditBalance;
    }

    public NetBalanceType getDebitCreditBalanceType() {
        if (debitCreditBalance == null) {
            return null;
        }
        if (!NetBalanceType.valid(debitCreditBalance)) {
            return null;
        }
        return NetBalanceType.forCode(debitCreditBalance);
    }

    public Integer getDebitCreditBalanceValue() {
        return integerOf("debitCreditBalance", debitCreditBalance);
    }

    public String getNetBalanceAmount() {
        return netBalanceAmount;
    }

    public BigDecimal getNetBalanceAmountValue() {
        return bigDecimalOf("netBalanceAmount", netBalanceAmount, 2);
    }

    @Override
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
        result = prime * result + ((amountCommissionCredited == null) ? 0 : amountCommissionCredited.hashCode());
        result = prime * result + ((amountCommissionDebited == null) ? 0 : amountCommissionDebited.hashCode());
        result = prime * result + ((debitCreditBalance == null) ? 0 : debitCreditBalance.hashCode());
        result = prime * result + ((errorCount == null) ? 0 : errorCount.hashCode());
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
        result = prime * result + ((grossCredit == null) ? 0 : grossCredit.hashCode());
        result = prime * result + ((grossDebit == null) ? 0 : grossDebit.hashCode());
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((netBalanceAmount == null) ? 0 : netBalanceAmount.hashCode());
        result = prime * result + parsedLineNo;
        result = prime * result + ((period == null) ? 0 : period.hashCode());
        result = prime * result + ((railUnionCompiling == null) ? 0 : railUnionCompiling.hashCode());
        result = prime * result + ((railUnionReceiving == null) ? 0 : railUnionReceiving.hashCode());
        result = prime * result + ((reserved == null) ? 0 : reserved.hashCode());
        result = prime * result + ((statementCurrency == null) ? 0 : statementCurrency.hashCode());
        result = prime * result + ((statementPeriod == null) ? 0 : statementPeriod.hashCode());
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
        Uic301Total other = (Uic301Total) obj;
        if (amountCommissionCredited == null) {
            if (other.amountCommissionCredited != null) {
                return false;
            }
        } else if (!amountCommissionCredited.equals(other.amountCommissionCredited)) {
            return false;
        }
        if (amountCommissionDebited == null) {
            if (other.amountCommissionDebited != null) {
                return false;
            }
        } else if (!amountCommissionDebited.equals(other.amountCommissionDebited)) {
            return false;
        }
        if (debitCreditBalance == null) {
            if (other.debitCreditBalance != null) {
                return false;
            }
        } else if (!debitCreditBalance.equals(other.debitCreditBalance)) {
            return false;
        }
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
        if (grossCredit == null) {
            if (other.grossCredit != null) {
                return false;
            }
        } else if (!grossCredit.equals(other.grossCredit)) {
            return false;
        }
        if (grossDebit == null) {
            if (other.grossDebit != null) {
                return false;
            }
        } else if (!grossDebit.equals(other.grossDebit)) {
            return false;
        }
        if (identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!identifier.equals(other.identifier)) {
            return false;
        }
        if (netBalanceAmount == null) {
            if (other.netBalanceAmount != null) {
                return false;
            }
        } else if (!netBalanceAmount.equals(other.netBalanceAmount)) {
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
        if (reserved == null) {
            if (other.reserved != null) {
                return false;
            }
        } else if (!reserved.equals(other.reserved)) {
            return false;
        }
        if (statementCurrency == null) {
            if (other.statementCurrency != null) {
                return false;
            }
        } else if (!statementCurrency.equals(other.statementCurrency)) {
            return false;
        }
        if (statementPeriod == null) {
            if (other.statementPeriod != null) {
                return false;
            }
        } else if (!statementPeriod.equals(other.statementPeriod)) {
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
     * Creates a new total by parsing a string.
     * 
     * @param no
     *            Line number.
     * @param line
     *            Line to parse.
     * 
     * @return Returns a new instance with the parsed data.
     * 
     * @throws Uic301Exception
     *             Parsing the total failed.
     */
    public static Uic301Total parse(final int no, final String line) throws Uic301Exception {
        if (!isTotal(line)) {
            throw new IllegalArgumentException("# " + no + " is no total line: '" + line + "'");
        }

        final SubString subStr = new SubString(line);
        final String identifier = subStr.next("identifier", 9);
        final String railUnionCompiling = subStr.next("railUnionCompiling", 4);
        final String railUnionReceiving = subStr.next("railUnionReceiving", 4);
        final String period = subStr.next("period", 6);
        final String reserved = subStr.next("reserved", 1);
        final String statementCurrency = subStr.next("statementCurrency", 3);
        final String statementPeriod = subStr.next("statementPeriod", 2);
        final String grossDebit = subStr.next("grossDebit", 12);
        final String grossCredit = subStr.next("grossCredit", 12);
        final String amountCommissionDebited = subStr.next("amountCommissionDebited", 11);
        final String amountCommissionCredited = subStr.next("amountCommissionCredited", 11);
        final String debitCreditBalance = subStr.next("debitCreditBalance", 1);
        final String netBalanceAmount = subStr.next("netBalanceAmount", 12);

        return new Uic301Total(identifier, railUnionCompiling, railUnionReceiving, period, reserved,
                statementCurrency, statementPeriod, grossDebit, grossCredit, amountCommissionDebited,
                amountCommissionCredited, debitCreditBalance, netBalanceAmount, no);

    }

    /**
     * Determines if the given line is a total line.
     * 
     * @param line
     *            Line to analyze.
     * 
     * @return True if the line starts with a known total identifier.
     */
    public static boolean isTotal(final String line) {
        return line != null && (line.startsWith(G4) || line.startsWith(G4_DB) || line.startsWith(G5_ALLOCATION)
                || line.startsWith(G5_ALLOCATION_DB) || line.startsWith(G5_ISSUES));
    }
}
