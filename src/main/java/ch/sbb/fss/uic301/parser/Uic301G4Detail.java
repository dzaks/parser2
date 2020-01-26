package ch.sbb.fss.uic301.parser;

import static ch.sbb.fss.uic301.parser.Uic301Utils.bigDecimalOf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;
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

import ch.sbb.fss.uic301.parser.Date.DateStr;
import ch.sbb.fss.uic301.parser.StatementPeriod.StatementPeriodStr;
import ch.sbb.fss.uic301.parser.constraints.CurrencyStr;
import ch.sbb.fss.uic301.parser.constraints.FixedLenDigitsStr;
import ch.sbb.fss.uic301.parser.constraints.IdentifierStr;
import ch.sbb.fss.uic301.parser.constraints.IsoCountryCodeStr;
import ch.sbb.fss.uic301.parser.constraints.ReservedStr;
import ch.sbb.fss.uic301.parser.constraints.RicsCodeStr;

/**
 * UIC301 G4 Detail (Services Allocated / Zugeteilte Leistungen)
 * <ul>
 * <li>Immer vorhanden</li>
 * <li>Einkauf im Ausland</li>
 * </ul>
 */
@Immutable
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Uic301G4Detail.TAG)
public final class Uic301G4Detail implements Uic301Detail, Sealable {

    public static final String TAG = "detail-g4";

    private static final String G4 = "141210000";

    private static final String G4_DB = "144210000";

    private transient boolean sealed = false;
    
    private transient List<String> sealedAttributes = new ArrayList<String>();

    @XmlAttribute(name = "errorCount")
    private Integer errorCount;

    @XmlElement(name = "field-error")
    private List<FieldError> errors;

    /**
     * Identifies the type of the detail<br>
     * [#1, 9 characters, Mandatory]<br>
     * "142110000" = G4<br>
     */
    @Label("Identifier")
    @ShortLabel("TYPE ID")
    @Tooltip("Identifies the type of the detail (should always be '141310000' for G4")
    @TableColumn(pos = 1, width = 80)
    @NotNull
    @IdentifierStr({ G4, G4_DB })
    @XmlAttribute(name = "_1")
    private String identifier;

    /**
     * RU compiling the statement (RICS code)<br>
     * [#2, 4 characters, Mandatory].
     */
    @Label("RU Compiling")
    @ShortLabel("RU CMP")
    @Tooltip("RU compiling the statement (RICS Code) [2]")
    @TableColumn(pos = 2, width = 60)
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "_2")
    private String railUnionCompiling;

    /**
     * RU receiving the statement (RICS code)<br>
     * [#3, 4 characters, Mandatory].
     */
    @Label("RU Receiving")
    @ShortLabel("RU RCV")
    @Tooltip("RU receiving the statement (RICS Code) [3]")
    @TableColumn(pos = 3, width = 60)
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "_3")
    private String railUnionReceiving;

    /**
     * Statement period (YYMMPP). YY=Year, MM=Month, PP=Period in the month (00
     * default, other usage must be bilaterally agreed upon)<br>
     * [#4, 6 characters, Mandatory].
     */
    @Label("Period")
    @ShortLabel("PERIOD")
    @Tooltip("Statement period (YYMMPP). YY=Year, MM=Month, PP=Period in the month "
            + "(00 default, other usage must be bilaterally agreed upon) [4]")
    @TableColumn(pos = 4, width = 60)
    @NotNull
    @StatementPeriodStr
    @XmlAttribute(name = "_4")
    private String period;

    /**
     * Reserved, always "0"<br>
     * [#5, 1 character, Mandatory].
     */
    @ReservedStr(1)
    @XmlAttribute(name = "_5")
    private String reserved1;

    /**
     * Type of service<br>
     * [#6, 2 characters, Mandatory]<br>
     */
    @Label("Type of service")
    @ShortLabel("STY")
    @Tooltip("Type of service [6]")
    @TableColumn(pos = 5, width = 30)
    @Mappings({ "01=NRT domestic", "02=NRT international", "03=Train supplements", "04=Combined service",
            "05=IRT domestic", "06=Couchette (not IRT)", "07=IRT international", "40=Sleeper (not IRT)" })
    @NotNull
    @FixedLenDigitsStr(2)
    @XmlAttribute(name = "_6")
    private String typeOfService;

    /**
     * Type of transaction<br>
     * [#7, 1 character, Mandatory]<br>
     */
    @Label("Type of transaction")
    @ShortLabel("TTY")
    @Tooltip("Type of transaction [7]")
    @TableColumn(pos = 6, width = 30)
    @Mappings({ "0=reservation/issued ticket", "1=cancellation/refunded ticket", "2=after sales fee",
            "3=electronic refund of ticket", "4=ticket exchange", "5=refund or exchange following a strike" })
    @NotNull
    @FixedLenDigitsStr(1)
    @XmlAttribute(name = "_7")
    private String typeOfTransaction;

    /**
     * Distribution channel<br>
     * [#8, 1 character, Mandatory]<br>
     * Values 3-9 are optional, if not activated – use the values in brackets
     * instead. The use of values 3-9 can be mandatory on bilateral agreement. For
     * detailed reference to UIC 918-1, see examples in app G3.
     */
    @Label("Distribution channel")
    @ShortLabel("CHA")
    @Tooltip("Distribution channel [8]")
    @TableColumn(pos = 7, width = 30)
    @Mappings({ "1=station", "2=travel agency", "3=call center RU", "4=sales on board", "5=ticket vending machine RU",
            "8=website travel agency", "9=website RU" })
    @NotNull
    @FixedLenDigitsStr(1)
    @XmlAttribute(name = "_8")
    private String distributionChannel;

    /**
     * Code issuing office<br>
     * [#9, 5 character, Optional]<br>
     * When not indicated – fill with zeroes. Can be mandatory based on bilateral
     * agreement.
     */
    @Label("Code issuing office")
    @ShortLabel("OFFICE")
    @Tooltip("Code issuing office [9]")
    @TableColumn(pos = 8, width = 60)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_9")
    private String codeIssuingOffice;

    /**
     * Requesting terminal / RU to which it belongs<br>
     * [#10(1), 4 characters, Mandatory]<br>
     */
    @Label("Requesting terminal / RU")
    @ShortLabel("TRM RU")
    @Tooltip("Requesting terminal / RU to which it belongs [10(1)]")
    @TableColumn(pos = 9, width = 60)
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "_10-1")
    private String requestingTerminalRU;

    /**
     * Requesting terminal / Terminal number<br>
     * [#10(2), 7 characters, Mandatory only for Hermes accounting]<br>
     */
    @Label("Requesting terminal / NR")
    @ShortLabel("TRM NO")
    @Tooltip("Requesting terminal / Terminal number [10(2)]")
    @TableColumn(pos = 10, width = 60)
    @NotNull
    @FixedLenDigitsStr(7)
    @XmlAttribute(name = "_10-2")
    private String requestingTerminalNo;

    /**
     * ISO 4207 code for currency, normally EUR<br>
     * [#11(1), 3 characters, Mandatory]<br>
     */
    @Label("Currency code")
    @ShortLabel("CURR")
    @Tooltip("ISO 4207 code for currency, normally EUR [11(1)]")
    @TableColumn(pos = 11, width = 40)
    @NotNull
    @CurrencyStr
    @XmlAttribute(name = "_11-1")
    private String statementCurrency;

    /**
     * Period code stipulated by BCC, always 01 for EUR<br>
     * [#11(2), 2 characters, Mandatory].
     */
    @Label("Period code")
    @ShortLabel("PEC")
    @Tooltip("Period code stipulated by BCC, always 01 for EUR [11(2)]")
    @TableColumn(pos = 12, width = 40)
    @NotNull
    @FixedLenDigitsStr(2)
    @XmlAttribute(name = "_11-2")
    private String statementPeriod;

    /**
     * Class or category<br>
     * [#12, 3 characters, Mandatory]<br>
     */
    @Label("Class or category")
    @ShortLabel("CLS")
    @Tooltip("Class or category [12]")
    @TableColumn(pos = 13, width = 30)
    @Mappings({ "000=no class or category of service", "001=1st class", "002=2nd class", "007=couchette 1st class",
            "008=couchette 2nd class", "009=change to higher class", "011=single sleeper", "012=special sleeper",
            "013=double sleeper", "014=tourist T2 sleeper", "015=tourist T3 sleeper", "016=tourist T4 sleeper",
            "017=single with shower", "018=double with shower", "019=tourist T3 with shower",
            "062=1st class reclining seat", "063=2nd class reclining seat", "076=executive compartment",
            "077=video compartment", "078=4-berth couchette ", "079=bicycle with included seat",
            "080=sleeping car with shower", "081=breakfast included", "082=meal included/lunch",
            "083=meal included/dinner", "084=panoramic seat", "085=euraffair", "087=club", "088=preferente",
            "089=turista", "090=bicycle only" })
    @NotNull
    @FixedLenDigitsStr(3)
    @XmlAttribute(name = "_12")
    private String classOrCategory;

    /**
     * Unit price<br>
     * [#13, 8 characters, Mandatory]<br>
     * Price for one person, six positions preceding and two following the decimal
     * point.
     */
    @Label("Unit price")
    @ShortLabel("UNIT PRICE")
    @Tooltip("Price for one person, six positions preceding and two following the decimal point [13]")
    @TableColumn(pos = 14, width = 80)
    @NotNull
    @FixedLenDigitsStr(8)
    @XmlAttribute(name = "_13")
    private String unitPrice;

    /**
     * Train number, right aligned and filled with leading zeroes.<br>
     * [#14, 5 characters, Mandatory]<br>
     */
    @Label("Train number")
    @ShortLabel("TRN")
    @Tooltip("Train number, right aligned and filled with leading zeroes. [14]")
    @TableColumn(pos = 15, width = 50)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_14")
    private String trainNumber;

    /**
     * Coach number, right aligned and filled with leading zeroes.<br>
     * [#15, 3 characters, Mandatory]<br>
     * Mandatory only for couchette (type of service : 06) and sleeper (type of
     * service : 40) – otherwise filled with zeroes.
     */
    @Label("Coach number")
    @ShortLabel("COA")
    @Tooltip("Coach number, right aligned and filled with leading zeroes. [15]")
    @TableColumn(pos = 16, width = 40)
    @NotNull
    @FixedLenDigitsStr(3)
    @XmlAttribute(name = "_15")
    private String coachNumber;

    /**
     * Day of travel (YYMMDD)<br>
     * [#16, 6 characters, Mandatory]<br>
     * In case of open ticket – indicate the first day of validity.
     */
    @Label("Day of travel")
    @ShortLabel("DAYOT")
    @Tooltip("Day of travel (YYMMDD). For open tickets: indicate the first day of validity. [16]")
    @TableColumn(pos = 17, width = 60)
    @NotNull
    @DateStr
    @XmlAttribute(name = "_16")
    private String dayOfTravel;
    
    /**
     * Departure country code, with preceding zeroes.<br>
     * [#19(1), 4 characters, Mandatory]<br>
     */
    @Label("Departure location RU code")
    @ShortLabel("DEPRU")
    @Tooltip("Departure location RU code, with preceding zeroes. [17(1)]")
    @TableColumn(pos = 18, width = 50)
    @NotNull
    @FixedLenDigitsStr(4)
    @XmlAttribute(name = "_17-1")
    private String departureLocationRU;

    /**
     * Departure ENEE station code, with preceding zeroes.<br>
     * [#17(2), 5 characters, Mandatory]<br>
     */
    @Label("Departure ENEE station code")
    @ShortLabel("DEPST")
    @Tooltip("Departure ENEE station code, with preceding zeroes. [17(2)]")
    @TableColumn(pos = 19, width = 50)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_17-2")
    private String departureLocationStation;

    /**
     * Reserved, always "0"<br>
     * [#18, 1 character, Mandatory]<br>
     */
    @ReservedStr(1)
    @XmlAttribute(name = "_18")
    private String reserved2;

   
    /**
     * Destination country code, with preceding zeroes.<br>
     * [#21(1), 4 characters, Mandatory]<br>
     */
    @Label("Destination location RU  code")
    @ShortLabel("DESRU")
    @Tooltip("Destination location RU code [19(1)]")
    @TableColumn(pos = 20, width = 50)
    @NotNull
    @FixedLenDigitsStr(4)
    @XmlAttribute(name = "_19-1")
    private String destinationLocationRU;

    /**
     * Destination ENEE station code, with preceding zeroes.<br>
     * [#19(2), 5 characters, Mandatory]<br>
     */
    @Label("Destination ENEE station code")
    @ShortLabel("DESST")
    @Tooltip("Destination ENEE station code, with preceding zeroes. [19(2)]")
    @TableColumn(pos = 21, width = 50)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_19-2")
    private String destinationLocationStation;

    /**
     * Reserved, always "0"<br>
     * [#20, 1 character, Mandatory]<br>
     */
    @ReservedStr(1)
    @XmlAttribute(name = "_20")
    private String reserved3;

    /**
     * Reference number, right aligned and filled with leading zeroes.<br>
     * [#21, 14 characters, Mandatory]<br>
     * Only for Hermes 918.1 - Resarail-TCN "00001234567890" should be converted to
     * a valid Reference number.<br>
     * <br>
     * Addition: To match with G5 Number of issue/allocation identifier (#23)
     */
    @Label("Reference number")
    @ShortLabel("REFNO")
    @Tooltip("Reference number, right aligned and filled with leading zeroes [21]")
    @TableColumn(pos = 22, width = 80)
    @NotNull
    @FixedLenDigitsStr(14)
    @XmlAttribute(name = "_21")
    private String referenceNumber;

    /**
     * Dialogue number<br>
     * [#22, 5 characters, Mandatory]<br>
     * For reservations – use the dialogue number for the reservation reply.<br>
     * For cancellations – use the dialogue number for the cancellation reply.<br>
     * Dialogue number only for Hermes, for other allocation Systems may be used on
     * bilateral request for PNR.<br>
     */
    @Label("Dialogue number")
    @ShortLabel("DLGNO")
    @Tooltip("or reservations – use the dialogue number for the reservation reply."
            + " For cancellations – use the dialogue number for the cancellation reply."
            + " Dialogue number only for Hermes, for other allocation Systems may be used on bilateral request for PNR [22]")
    @TableColumn(pos = 23, width = 50)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_22")
    private String dialogueNumber;

    /**
     * Transaction date (YYMMDD)<br>
     * [#23, 6 characters, Mandatory].
     */
    @Label("Transaction date")
    @ShortLabel("TRDATE")
    @Tooltip("Transaction date (YYMMDD) [23]")
    @TableColumn(pos = 24, width = 60)
    @NotNull
    @DateStr
    @XmlAttribute(name = "_23")
    private String transactionDate;

    /**
     * Number of services<br>
     * [#24, 5 characters, Mandatory]<br>
     * In case of multiple service providers, this value is only indicated on the
     * primary record. For secondary records, fill this field with zeroes.
     */
    @Label("Number of services")
    @ShortLabel("SVCNO")
    @Tooltip("In case of multiple service providers, this value is only indicated on the primary record. "
            + "For secondary records, fill this field with zeroes. [24]")
    @TableColumn(pos = 25, width = 50)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_24")
    private String numberOfServices;

    /**
     * Adjustment<br>
     * [#25, 1 characters, Optional]<br>
     * Used on bilateral basis, with nota on how used.
     */
    @Label("Adjustment")
    @ShortLabel("ADJ")
    @Tooltip("Adjustment. Used on bilateral basis, with nota on how used [25]")
    @TableColumn(pos = 26, width = 30)
    @Mappings({ "0=no adjustment", "1=adjustment" })
    @NotNull
    @Pattern(regexp = "0|1", message = "Expected '0' or '1', but was: '${validatedValue}'")
    @XmlAttribute(name = "_25")
    private String adjustment;

    /**
     * Gross amount to be debited<br>
     * [#26, 10 characters, Mandatory]<br>
     * Eight positions preceding and two following the decimal point. When the field
     * used for reservations/sales . In case of multiple service providers, this
     * value is only indicated on the primary record. For secondary records, fill
     * this field with zeroes. When the field is used for after sales fee (that is
     * when field {@link #typeOfTransaction} is coded 2), the amount represents the
     * after sales fee that is kept by the service-providing RU.
     */
    @Label("Gross amount to be debited")
    @ShortLabel("DEBIT")
    @Tooltip("Eight positions preceding and two following the decimal point. When the field"
            + "used for reservations/sales . In case of multiple service providers, this"
            + "value is only indicated on the primary record. For secondary records, fill"
            + "this field with zeroes. When the field is used for after sales fee (that is"
            + "when field {@link #typeOfTransaction} is coded 2), the amount represents the"
            + "after sales fee that is kept by the service-providing RU." + " [26]")
    @TableColumn(pos = 27, width = 80)
    @NotNull
    @FixedLenDigitsStr(10)
    @XmlAttribute(name = "_26")
    private String grossAmountToBeDebited;

    /**
     * Gross amount to be credited<br>
     * [#27, 10 characters, Mandatory]<br>
     * Eight positions preceding and two following the decimal point. The field used
     * for canellations/after sales. In case of multiple service providers, this
     * value is only indicated on the primary record. For secondary records, fill
     * this field with zeroes.
     */
    @Label("Gross amount to be credited")
    @ShortLabel("CREDIT")
    @Tooltip("Eight positions preceding and two following the decimal point. The field used"
            + "for canellations/after sales. In case of multiple service providers, this"
            + "value is only indicated on the primary record. For secondary records, fill" + "this field with zeroes."
            + " [27]")
    @TableColumn(pos = 28, width = 80)
    @NotNull
    @FixedLenDigitsStr(10)
    @XmlAttribute(name = "_27")
    private String grossAmountToBeCredited;

    /**
     * Service-providing RU (RICS code)<br>
     * [#28, 4 characters, Mandatory]<br>
     */
    @Label("Service-providing RU")
    @ShortLabel("SVCRU")
    @Tooltip("Service-providing RU (RICS code) [28]")
    @TableColumn(pos = 29, width = 50)
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "_28")
    private String serviceProvidingRU;

    /**
     * After sales fee share percentage<br>
     * [#29, 2 characters, Mandatory]<br>
     * For after-sales-fee-records (that is when field {@link #typeOfTransaction} is
     * coded 2) this field indicates the percentage of the after sales fee When not
     * used – fill with zeroes
     */
    @Label("After sales fee share percentage")
    @ShortLabel("AFS%")
    @Tooltip("For after-sales-fee-records this field indicates the percentage of the after sales fee. When not used – fill with zeroes [29]")
    @TableColumn(pos = 30, width = 40)
    @NotNull
    @FixedLenDigitsStr(2)
    @XmlAttribute(name = "_29")
    private String afterSalesFeeSharePercentage;

    /**
     * Tariff code<br>
     * [#30, 5 characters, Mandatory]<br>
     */
    @Label("Tariff code")
    @ShortLabel("TAC")
    @Tooltip("Tariff code [30]")
    @TableColumn(pos = 31, width = 50)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_30")
    private String tariffCode;

    /**
     * Type of journey<br>
     * [#31, 1 character, Optional]<br>
     */
    @Label("Type of journey")
    @ShortLabel("TOJ")
    @Tooltip("Type of journey [31]")
    @TableColumn(pos = 32, width = 30)
    @Mappings({ "0: not used", "1=single journey", "2=return, with out- and inbound trip on the same route",
            "3=round trip, outbound leg, with price calculated as a single journey",
            "4=round trip, outbound leg, with price calculated as half return fare",
            "5=round trip, inbound leg, with price calculated as single journey",
            "6=round trip, inbound leg, with price calculated as half return fare", "9=pass" })
    @NotNull
    @FixedLenDigitsStr(1)
    @XmlAttribute(name = "_31")
    private String typeOfJourney;

    /**
     * Primary route 1st section Service-providing RU<br>
     * [#32(1), 4 characters, Optional]<br>
     * The field may be used for sharing purposes, using the same definition of
     * service-providing RU as for field {@link #serviceProvidingRU}.
     */
    @Label("Service RU primary route 1st section")
    @ShortLabel("RU SEC1")
    @Tooltip("Primary route 1st section Service-providing RU (RICS Code) [32(1)]")
    @TableColumn(pos = 33, width = 60)
    @NotNull
    @RicsCodeStr
    @XmlAttribute(name = "_32-1")
    private String primaryRouteFirstSectionRU;

    /**
     * Primary route 1st section Serial number<br>
     * [#32(2), 5 characters, Optional]<br>
     * NRT serial number from 108-1, TCVS file, field 2.
     */
    @Label("Primary route 1st section Serial number")
    @ShortLabel("NO SEC1")
    @Tooltip("Primary route 1st section Serial number. NRT serial number from 108-1, TCVS file, field 2 [32(2)]")
    @TableColumn(pos = 34, width = 60)
    @NotNull
    @FixedLenDigitsStr(5)
    @XmlAttribute(name = "_32-2")
    private String primaryRouteFirstSectionSerialNo;

    /**
     * Passenger catergory<br>
     * [#33, 2 characters, Optional]<br>
     * When not known, fill with zeroes.<br>
     */
    @Label("Passenger catergory")
    @ShortLabel("PC")
    @Tooltip("Passenger catergory [33]")
    @TableColumn(pos = 35, width = 30)
    @Mappings({ "11=adult", "12=youth", "13=child", "18=senior", "19=dog" })
    @NotNull
    @FixedLenDigitsStr(2)
    @XmlAttribute(name = "_33")
    private String passengerCatergory;

    /**
     * Amount/unit share<br>
     * [#34, 8 characters, Mandatory]<br>
     * The share for 1 person, owing to the RU indicated in field
     * {@link #serviceProvidingRU}. Six positions preceding and two positions
     * following the decimal point.
     * 
     */
    @Label("Amount/unit share")
    @ShortLabel("AMNTUS")
    @Tooltip("The share for 1 person, owing to the service providing RU. Six positions preceding and two positions"
            + " following the decimal point. [34]")
    @TableColumn(pos = 36, width = 80)
    @NotNull
    @FixedLenDigitsStr(8)
    @XmlAttribute(name = "_34")
    private String amountUnitShare;

    /**
     * Gross amount to be debited the service providing RU<br>
     * [#35, 10 characters, Mandatory]<br>
     * Gross amount owing to the RU indicated in field {@link #serviceProvidingRU},
     * for cancellation/after sales. Eight positions preceding and two following the
     * decimal point.
     */
    @Label("Gross amount to be debited the service providing RU")
    @ShortLabel("DEBIT")
    @Tooltip("Gross amount to be debited the service providing RU for canellations/after sales."
            + " Eight positions preceding and two following the decimal point."
            + " [35]")
    @TableColumn(pos = 37, width = 80)
    @NotNull
    @FixedLenDigitsStr(10)
    @XmlAttribute(name = "_35")
    private String grossAmountToBeDebitedTheServiceProvidingRU;

    /**
     * Gross amount to be credited the service providing RU<br>
     * [#36, 10 characters, Mandatory]<br>
     * Gross amount owing to the RU indicated in field {@link #serviceProvidingRU},
     * for reservations/ sales. Eight positions preceding and two following the
     * decimal point.
     */
    @Label("Gross amount to be credited the service providing RU")
    @ShortLabel("CREDIT")
    @Tooltip("Gross amount to be credited the service providing RU for reservation/sales."
            + " Eight positions preceding and two following the decimal point."
            + " [36]")
    @TableColumn(pos = 38, width = 80)
    @NotNull
    @FixedLenDigitsStr(10)
    @XmlAttribute(name = "_36")
    private String grossAmountToBeCreditedTheServiceProvidingRU;

    /**
     * Percentage commission rate of service providing RU<br>
     * [#37, 4 characters, Mandatory]<br>
     * The commission rate granted by the service providing RU indicated in field
     * {@link #serviceProvidingRU}. Two positions preceding and two positions
     * following the decimal point.
     */
    @Label("Percentage commission rate of service providing RU")
    @ShortLabel("CRA%")
    @Tooltip("The commission rate granted by the service providing RU."
            + "Two positions preceding and two positions following the decimal point."
            + " [37]")
    @TableColumn(pos = 38, width = 40)
    @NotNull
    @FixedLenDigitsStr(4)
    @XmlAttribute(name = "_37")
    private String percentageCommissionRateOfServiceProvidingRU;

    /**
     * Amount of commission to be debited the service providing RU<br>
     * [#38, 10 characters, Mandatory]<br>
     * For reservations/sales – calculated with field
     * {@link #grossAmountToBeCreditedTheServiceProvidingRU} * field
     * {@link percentageCommissionRateOfServiceProvidingRU}. Eight positions
     * preceding and two following the decimal point.
     */
    @Label("Amount of commission to be debited the service providing RU")
    @ShortLabel("CRDEBAM")
    @Tooltip("Amount of commission to be debited the service providing RU for reservation/sales."
            + " Eight positions preceding and two following the decimal point."
            + " [38]")
    @TableColumn(pos = 39, width = 80)
    @NotNull
    @FixedLenDigitsStr(10)
    @XmlAttribute(name = "_38")
    private String amountOfCommissionToBeDebitedTheServiceProvidingRU;

    /**
     * Amount of commission to be credited the service providing RU<br>
     * [#39, 10 characters, Mandatory]<br>
     * For reservations/sales – calculated with field
     * {@link #grossAmountToBeDebitedTheServiceProvidingRU} * field
     * {@link percentageCommissionRateOfServiceProvidingRU}. Eight positions
     * preceding and two following the decimal point.
     */
    @Label("Amount of commission to be credited the service providing RU")
    @ShortLabel("CRCREDAM")
    @Tooltip("Amount of commission to be credited the service providing RU for reservation/sales."
            + " Eight positions preceding and two following the decimal point."
            + " [39]")
    @TableColumn(pos = 39, width = 80)
    @NotNull
    @FixedLenDigitsStr(10)
    @XmlAttribute(name = "_39")
    private String amountOfCommissionToBeCreditedTheServiceProvidingRU;

    /**
     * Reserved, always "000"<br>
     * [#40a, 3 characters, Mandatory]<br>
     */
    @ReservedStr(3)
    @XmlAttribute(name = "_40a")
    private String reserved4;

    /**
     * ISO 3166 country code for requesting terminal. When not used, fill with
     * zeroes<br>
     * [#40b, 2 characters, Optional]<br>
     */
    @Label("Country code")
    @ShortLabel("TRM")
    @Tooltip("ISO 3166 country code for requesting terminal. When not used, fill with zeroes [40b]")
    @TableColumn(pos = 40, width = 50)
    @NotNull
    @IsoCountryCodeStr
    @XmlAttribute(name = "_40b")
    private String countryCode;

    /**
     * Service brand code. When not used, fill with zeroes<br>
     * [#40c, 4 characters, Optional]<br>
     */
    @Label("Service brand code")
    @ShortLabel("SBC")
    @Tooltip("Service brand code or '0000' [40c]")
    @TableColumn(pos = 41, width = 50)
    @NotNull
    @FixedLenDigitsStr(4)
    @XmlAttribute(name = "_40c")
    private String serviceBrandCode;

    /** Number of parsed line or -1 if not constructed by parser. */
    @Label("Line number")
    @ShortLabel("Line")
    @Tooltip("Parsed line number")
    @TableColumn(pos = 0, width = 50)
    @XmlAttribute(name = "no")
    private int parsedLineNo;

    /**
     * Default constructor for JAX-B.
     */
    protected Uic301G4Detail() {
        super();
    }

    /**
     * Copy constructor. Fields {@link #errorCount}, {@link #errors} and {@link #sealed} will NOT be copied.
     * 
     * @param toCopy Instance to copy all fields from.
     */
    Uic301G4Detail(final Uic301G4Detail toCopy) {

        this.identifier = toCopy.identifier;
        this.railUnionCompiling = toCopy.railUnionCompiling;
        this.railUnionReceiving = toCopy.railUnionReceiving;
        this.period = toCopy.period;
        this.reserved1 = toCopy.reserved1;
        this.typeOfService = toCopy.typeOfService;
        this.typeOfTransaction = toCopy.typeOfTransaction;
        this.distributionChannel = toCopy.distributionChannel;
        this.codeIssuingOffice = toCopy.codeIssuingOffice;
        this.requestingTerminalRU = toCopy.requestingTerminalRU;
        this.requestingTerminalNo = toCopy.requestingTerminalNo;
        this.statementCurrency = toCopy.statementCurrency;
        this.statementPeriod = toCopy.statementPeriod;
        this.classOrCategory = toCopy.classOrCategory;
        this.unitPrice = toCopy.unitPrice;
        this.trainNumber = toCopy.trainNumber;
        this.coachNumber = toCopy.coachNumber;
        this.dayOfTravel = toCopy.dayOfTravel;
        this.departureLocationRU = toCopy.departureLocationRU;
        this.departureLocationStation = toCopy.departureLocationStation;
        this.reserved2 = toCopy.reserved2;
        this.destinationLocationRU = toCopy.destinationLocationRU;
        this.destinationLocationStation = toCopy.destinationLocationStation;
        this.reserved3 = toCopy.reserved3;
        this.referenceNumber = toCopy.referenceNumber;
        this.dialogueNumber = toCopy.dialogueNumber;
        this.transactionDate = toCopy.transactionDate;
        this.numberOfServices = toCopy.numberOfServices;
        this.adjustment = toCopy.adjustment;
        this.grossAmountToBeDebited = toCopy.grossAmountToBeDebited;
        this.grossAmountToBeCredited = toCopy.grossAmountToBeCredited;
        this.serviceProvidingRU = toCopy.serviceProvidingRU;
        this.afterSalesFeeSharePercentage = toCopy.afterSalesFeeSharePercentage;
        this.tariffCode = toCopy.tariffCode;
        this.typeOfJourney = toCopy.typeOfJourney;
        this.primaryRouteFirstSectionRU = toCopy.primaryRouteFirstSectionRU;
        this.primaryRouteFirstSectionSerialNo = toCopy.primaryRouteFirstSectionSerialNo;
        this.passengerCatergory = toCopy.passengerCatergory;
        this.amountUnitShare = toCopy.amountUnitShare;
        this.grossAmountToBeDebitedTheServiceProvidingRU = toCopy.grossAmountToBeDebitedTheServiceProvidingRU;
        this.grossAmountToBeCreditedTheServiceProvidingRU = toCopy.grossAmountToBeCreditedTheServiceProvidingRU;
        this.percentageCommissionRateOfServiceProvidingRU = toCopy.percentageCommissionRateOfServiceProvidingRU;
        this.amountOfCommissionToBeDebitedTheServiceProvidingRU = toCopy.amountOfCommissionToBeDebitedTheServiceProvidingRU;
        this.amountOfCommissionToBeCreditedTheServiceProvidingRU = toCopy.amountOfCommissionToBeCreditedTheServiceProvidingRU;
        this.reserved4 = toCopy.reserved4;
        this.countryCode = toCopy.countryCode;
        this.serviceBrandCode = toCopy.serviceBrandCode;
        this.parsedLineNo = toCopy.parsedLineNo;

    }

    Uic301G4Detail(final String identifier, final String railUnionCompiling, final String railUnionReceiving,
            final String period, final String reserved1, final String typeOfService, final String typeofTransaction,
            final String distributionChannel, final String codeIssuingOffice, final String requestingTerminalRU,
            final String requestingTerminalNo, final String statementCurrency, final String statementPeriod,
            final String classOrCategory, final String unitPrice, final String trainNumber, final String coachNumber,
            final String dayOfTravel, final String departureLocationRU, final String departureLocationStation,
            final String reserved2, final String destinationLocationRU, final String destinationLocationStation,
            final String reserved3, final String referenceNumber, final String dialogueNumber,
            final String transactionDate, final String numberOfServices, final String adjustment,
            final String grossAmountToBeDebited, final String grossAmountToBeCredited, final String serviceProvidingRU,
            final String afterSalesFeeSharePercentage, final String tariffCode, final String typeOfJourney,
            final String primaryRouteFirstSectionRU, final String primaryRouteFirstSectionSerialNo,
            final String passengerCatergory, final String amountUnitShare,
            final String grossAmountToBeDebitedTheServiceProvidingRU,
            final String grossAmountToBeCreditedTheServiceProvidingRU,
            final String percentageCommissionRateOfServiceProvidingRU,
            final String amountOfCommissionToBeDebitedTheServiceProvidingRU,
            final String amountOfCommissionToBeCreditedTheServiceProvidingRU, final String reserved4,
            final String countryCode, final String serviceBrandCode, final int parsedLineNo) {

        this.identifier = identifier;
        this.railUnionCompiling = railUnionCompiling;
        this.railUnionReceiving = railUnionReceiving;
        this.period = period;
        this.reserved1 = reserved1;
        this.typeOfService = typeOfService;
        this.typeOfTransaction = typeofTransaction;
        this.distributionChannel = distributionChannel;
        this.codeIssuingOffice = codeIssuingOffice;
        this.requestingTerminalRU = requestingTerminalRU;
        this.requestingTerminalNo = requestingTerminalNo;
        this.statementCurrency = statementCurrency;
        this.statementPeriod = statementPeriod;
        this.classOrCategory = classOrCategory;
        this.unitPrice = unitPrice;
        this.trainNumber = trainNumber;
        this.coachNumber = coachNumber;
        this.dayOfTravel = dayOfTravel;
        this.departureLocationRU = departureLocationRU;
        this.departureLocationStation = departureLocationStation;
        this.reserved2 = reserved2;
        this.destinationLocationRU = destinationLocationRU;
        this.destinationLocationStation = destinationLocationStation;
        this.reserved3 = reserved3;
        this.referenceNumber = referenceNumber;
        this.dialogueNumber = dialogueNumber;
        this.transactionDate = transactionDate;
        this.numberOfServices = numberOfServices;
        this.adjustment = adjustment;
        this.grossAmountToBeDebited = grossAmountToBeDebited;
        this.grossAmountToBeCredited = grossAmountToBeCredited;
        this.serviceProvidingRU = serviceProvidingRU;
        this.afterSalesFeeSharePercentage = afterSalesFeeSharePercentage;
        this.tariffCode = tariffCode;
        this.typeOfJourney = typeOfJourney;
        this.primaryRouteFirstSectionRU = primaryRouteFirstSectionRU;
        this.primaryRouteFirstSectionSerialNo = primaryRouteFirstSectionSerialNo;
        this.passengerCatergory = passengerCatergory;
        this.amountUnitShare = amountUnitShare;
        this.grossAmountToBeDebitedTheServiceProvidingRU = grossAmountToBeDebitedTheServiceProvidingRU;
        this.grossAmountToBeCreditedTheServiceProvidingRU = grossAmountToBeCreditedTheServiceProvidingRU;
        this.percentageCommissionRateOfServiceProvidingRU = percentageCommissionRateOfServiceProvidingRU;
        this.amountOfCommissionToBeDebitedTheServiceProvidingRU = amountOfCommissionToBeDebitedTheServiceProvidingRU;
        this.amountOfCommissionToBeCreditedTheServiceProvidingRU = amountOfCommissionToBeCreditedTheServiceProvidingRU;
        this.reserved4 = reserved4;
        this.countryCode = countryCode;
        this.serviceBrandCode = serviceBrandCode;
        this.parsedLineNo = parsedLineNo;

    }

    @Override
    public Uic301Type getIdentifierType() {
        return Uic301Type.G4;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getRailUnionCompiling() {
        return railUnionCompiling;
    }

    @Override
    public String getRailUnionReceiving() {
        return railUnionReceiving;
    }

    @Override
    public String getPeriod() {
        return period;
    }

    public String getReserved1() {
        return reserved1;
    }

    @Override
    public String getTypeOfService() {
        return typeOfService;
    }

    @Override
    public ServiceType getTypeOfServiceValue() {
        if (typeOfService == null) {
            return null;
        }
        if (!ServiceType.valid(typeOfService)) {
            return null;
        }
        return ServiceType.forCode(typeOfService);
    }

    @Override
    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    @Override
    public TransactionType getTypeOfTransactionValue() {
        if (typeOfTransaction == null) {
            return null;
        }
        if (!TransactionType.valid(typeOfTransaction)) {
            return null;
        }
        return TransactionType.forCode(typeOfTransaction);
    }

    @Override
    public String getDistributionChannel() {
        return distributionChannel;
    }

    @Override
    public DistributionChannel getDistributionChannelType() {
        if (distributionChannel == null) {
            return null;
        }
        if (!DistributionChannel.valid(distributionChannel)) {
            return null;
        }
        return DistributionChannel.forCode(distributionChannel);
    }

    @Override
    public String getCodeIssuingOffice() {
        return codeIssuingOffice;
    }

    public String getRequestingTerminalRU() {
        return requestingTerminalRU;
    }

    public String getRequestingTerminalNo() {
        return requestingTerminalNo;
    }

    @Override
    public String getStatementCurrency() {
        return statementCurrency;
    }

    @Override
    public String getStatementPeriod() {
        return statementPeriod;
    }

    @Override
    public StatementCurrencyPeriod getStatementCurrencyPeriod() {
        return new StatementCurrencyPeriod(statementCurrency, statementPeriod);
    }

    @Override
    public String getClassOrCategory() {
        return classOrCategory;
    }

    @Override
    public ClassOrCategory getClassOrCategoryValue() {
        if (classOrCategory == null) {
            return null;
        }
        if (!ClassOrCategory.valid(classOrCategory)) {
            return null;
        }
        return ClassOrCategory.forCode(classOrCategory);
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getUnitPriceValue() {
        return bigDecimalOf("unitPrice", unitPrice, 2);
    }

    @Override
    public String getTrainNumber() {
        return trainNumber;
    }

    @Override
    public String getCoachNumber() {
        return coachNumber;
    }

    @Override
    public String getDayOfTravel() {
        return dayOfTravel;
    }
    @Override
    public String getDepartureLocationRU() {
        return departureLocationRU;
    }

    @Override
    public String getDepartureLocationStation() {
        return departureLocationStation;
    }

    public String getReserved2() {
        return reserved2;
    }
    @Override
    public String getDestinationLocationRU() {
        return destinationLocationRU;
    }

    @Override
    public String getDestinationLocationStation() {
        return destinationLocationStation;
    }

    public String getReserved3() {
        return reserved3;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getDialogueNumber() {
        return dialogueNumber;
    }

    @Override
    public String getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String getNumberOfServices() {
        return numberOfServices;
    }

    @Override
    public String getAdjustment() {
        return adjustment;
    }

    @Override
    public String getGrossAmountToBeDebited() {
        return grossAmountToBeDebited;
    }

    @Override
    public BigDecimal getGrossAmountToBeDebitedValue() {
        return bigDecimalOf("grossAmountToBeDebited", grossAmountToBeDebited, 2);
    }

    @Override
    public String getGrossAmountToBeCredited() {
        return grossAmountToBeCredited;
    }

    @Override
    public BigDecimal getGrossAmountToBeCreditedValue() {
        return bigDecimalOf("grossAmountToBeCredited", grossAmountToBeCredited, 2);
    }

    public String getServiceProvidingRU() {
        return serviceProvidingRU;
    }

    public String getAfterSalesFeeSharePercentage() {
        return afterSalesFeeSharePercentage;
    }

    @Override
    public String getTariffCode() {
        return tariffCode;
    }

    @Override
    public String getTypeOfJourney() {
        return typeOfJourney;
    }

    @Override
    public JourneyType getTypeOfJourneyValue() {
        if (typeOfJourney == null) {
            return null;
        }
        if (!JourneyType.valid(typeOfJourney)) {
            return null;
        }
        return JourneyType.forCode(typeOfJourney);
    }

    @Override
    public String getPrimaryRouteFirstSectionRU() {
        return primaryRouteFirstSectionRU;
    }

    @Override
    public String getPrimaryRouteFirstSectionSerialNo() {
        return primaryRouteFirstSectionSerialNo;
    }

    @Override
    public String getPassengerCatergory() {
        return passengerCatergory;
    }

    @Override
    public PassengerCatergory getPassengerCatergoryType() {
        if (passengerCatergory == null) {
            return null;
        }
        if (!PassengerCatergory.valid(passengerCatergory)) {
            return null;
        }
        return PassengerCatergory.forCode(passengerCatergory);
    }

    @Override
    public String getAmountUnitShare() {
        return amountUnitShare;
    }

    @Override
    public BigDecimal getAmountUnitShareValue() {
        return bigDecimalOf("amountUnitShare", amountUnitShare, 2);
    }

    public String getGrossAmountToBeDebitedTheServiceProvidingRU() {
        return grossAmountToBeDebitedTheServiceProvidingRU;
    }

    public BigDecimal getGrossAmountToBeDebitedTheServiceProvidingRUValue() {
        return bigDecimalOf("grossAmountToBeDebitedTheServiceProvidingRU", grossAmountToBeDebitedTheServiceProvidingRU,
                2);
    }

    public String getGrossAmountToBeCreditedTheServiceProvidingRU() {
        return grossAmountToBeCreditedTheServiceProvidingRU;
    }

    public BigDecimal getGrossAmountToBeCreditedTheServiceProvidingRUValue() {
        return bigDecimalOf("grossAmountToBeCreditedTheServiceProvidingRU",
                grossAmountToBeCreditedTheServiceProvidingRU, 2);
    }

    public String getPercentageCommissionRateOfServiceProvidingRU() {
        return percentageCommissionRateOfServiceProvidingRU;
    }

    public BigDecimal getPercentageCommissionRateOfServiceProvidingRUValue() {
        return bigDecimalOf("percentageCommissionRateOfServiceProvidingRU",
                percentageCommissionRateOfServiceProvidingRU, 2);
    }

    @Override
    public String getAmountOfCommissionToBeDebitedTheServiceProvidingRU() {
        return amountOfCommissionToBeDebitedTheServiceProvidingRU;
    }

    @Override
    public BigDecimal getAmountOfCommissionToBeDebitedTheServiceProvidingRUValue() {
        return bigDecimalOf("amountOfCommissionToBeDebitedTheServiceProvidingRU",
                amountOfCommissionToBeDebitedTheServiceProvidingRU, 2);
    }

    public String getAmountOfCommissionToBeCreditedTheServiceProvidingRU() {
        return amountOfCommissionToBeCreditedTheServiceProvidingRU;
    }

    @Override
    public BigDecimal getAmountOfCommissionToBeCreditedTheServiceProvidingRUValue() {
        return bigDecimalOf("amountOfCommissionToBeCreditedTheServiceProvidingRU",
                amountOfCommissionToBeCreditedTheServiceProvidingRU, 2);
    }

    public String getReserved4() {
        return reserved4;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String getServiceBrandCode() {
        return serviceBrandCode;
    }

    @Override
    public int getParsedLineNo() {
        return parsedLineNo;
    }

    public final void setIdentifier(String identifier) {
        assertNotSealedAttribute("identifier");
        this.identifier = identifier;
    }

    public final void setRailUnionCompiling(String railUnionCompiling) {
        assertNotSealedAttribute("railUnionCompiling");
        this.railUnionCompiling = railUnionCompiling;
    }

    public final void setRailUnionReceiving(String railUnionReceiving) {
        assertNotSealedAttribute("railUnionReceiving");
        this.railUnionReceiving = railUnionReceiving;
    }

    public final void setPeriod(String period) {
        assertNotSealedAttribute("period");
        this.period = period;
    }

    public final void setReserved1(String reserved1) {
        assertNotSealedAttribute("reserved1");
        this.reserved1 = reserved1;
    }

    public final void setTypeOfService(String typeOfService) {
        assertNotSealedAttribute("typeOfService");
        this.typeOfService = typeOfService;
    }

    public final void setTypeOfTransaction(String typeOfTransaction) {
        assertNotSealedAttribute("typeOfTransaction");
        this.typeOfTransaction = typeOfTransaction;
    }

    public final void setDistributionChannel(String distributionChannel) {
        assertNotSealedAttribute("distributionChannel");
        this.distributionChannel = distributionChannel;
    }

    public final void setCodeIssuingOffice(String codeIssuingOffice) {
        assertNotSealedAttribute("codeIssuingOffice");
        this.codeIssuingOffice = codeIssuingOffice;
    }

    public final void setRequestingTerminalRU(String requestingTerminalRU) {
        assertNotSealedAttribute("requestingTerminalRU");
        this.requestingTerminalRU = requestingTerminalRU;
    }

    public final void setRequestingTerminalNo(String requestingTerminalNo) {
        assertNotSealedAttribute("requestingTerminalNo");
        this.requestingTerminalNo = requestingTerminalNo;
    }

    public final void setStatementCurrency(String statementCurrency) {
        assertNotSealedAttribute("statementCurrency");
        this.statementCurrency = statementCurrency;
    }

    public final void setStatementPeriod(String statementPeriod) {
        assertNotSealedAttribute("statementPeriod");
        this.statementPeriod = statementPeriod;
    }

    public final void setClassOrCategory(String classOrCategory) {
        assertNotSealedAttribute("classOrCategory");
        this.classOrCategory = classOrCategory;
    }

    public final void setUnitPrice(String unitPrice) {
        assertNotSealedAttribute("unitPrice");
        this.unitPrice = unitPrice;
    }

    public final void setTrainNumber(String trainNumber) {
        assertNotSealedAttribute("trainNumber");
        this.trainNumber = trainNumber;
    }

    public final void setCoachNumber(String coachNumber) {
        assertNotSealedAttribute("coachNumber");
        this.coachNumber = coachNumber;
    }

    public final void setDayOfTravel(String dayOfTravel) {
        assertNotSealedAttribute("dayOfTravel");
        this.dayOfTravel = dayOfTravel;
    }

    public final void setDepartureLocationRU(String departureLocationRU) {
        assertNotSealedAttribute("departureLocationRU");
        this.departureLocationRU = departureLocationRU;
    }

    public final void setDepartureLocationStation(String departureLocationStation) {
        assertNotSealedAttribute("departureLocationStation");
        this.departureLocationStation = departureLocationStation;
    }

    public final void setReserved2(String reserved2) {
        assertNotSealedAttribute("reserved2");
        this.reserved2 = reserved2;
    }

    public final void setDestinationLocationRU(String destinationLocationRU) {
        assertNotSealedAttribute("destinationLocationRU");
        this.destinationLocationRU = destinationLocationRU;
    }

    public final void setDestinationLocationStation(String destinationLocationStation) {
        assertNotSealedAttribute("destinationLocationStation");
        this.destinationLocationStation = destinationLocationStation;
    }

    public final void setReserved3(String reserved3) {
        assertNotSealedAttribute("reserved3");
        this.reserved3 = reserved3;
    }

    public final void setReferenceNumber(String referenceNumber) {
        assertNotSealedAttribute("referenceNumber");
        this.referenceNumber = referenceNumber;
    }

    public final void setDialogueNumber(String dialogueNumber) {
        assertNotSealedAttribute("dialogueNumber");
        this.dialogueNumber = dialogueNumber;
    }

    public final void setTransactionDate(String transactionDate) {
        assertNotSealedAttribute("transactionDate");
        this.transactionDate = transactionDate;
    }

    public final void setNumberOfServices(String numberOfServices) {
        assertNotSealedAttribute("numberOfServices");
        this.numberOfServices = numberOfServices;
    }

    public final void setAdjustment(String adjustment) {
        assertNotSealedAttribute("adjustment");
        this.adjustment = adjustment;
    }

    public final void setGrossAmountToBeDebited(String grossAmountToBeDebited) {
        assertNotSealedAttribute("grossAmountToBeDebited");
        this.grossAmountToBeDebited = grossAmountToBeDebited;
    }

    public final void setGrossAmountToBeCredited(String grossAmountToBeCredited) {
        assertNotSealedAttribute("grossAmountToBeCredited");
        this.grossAmountToBeCredited = grossAmountToBeCredited;
    }

    public final void setServiceProvidingRU(String serviceProvidingRU) {
        assertNotSealedAttribute("xx");
        this.serviceProvidingRU = serviceProvidingRU;
    }

    public final void setAfterSalesFeeSharePercentage(String afterSalesFeeSharePercentage) {
        assertNotSealedAttribute("afterSalesFeeSharePercentage");
        this.afterSalesFeeSharePercentage = afterSalesFeeSharePercentage;
    }

    public final void setTariffCode(String tariffCode) {
        assertNotSealedAttribute("tariffCode");
        this.tariffCode = tariffCode;
    }

    public final void setTypeOfJourney(String typeOfJourney) {
        assertNotSealedAttribute("typeOfJourney");
        this.typeOfJourney = typeOfJourney;
    }

    public final void setPrimaryRouteFirstSectionRU(String primaryRouteFirstSectionRU) {
        assertNotSealedAttribute("primaryRouteFirstSectionRU");
        this.primaryRouteFirstSectionRU = primaryRouteFirstSectionRU;
    }

    public final void setPrimaryRouteFirstSectionSerialNo(String primaryRouteFirstSectionSerialNo) {
        assertNotSealedAttribute("primaryRouteFirstSectionSerialNo");
        this.primaryRouteFirstSectionSerialNo = primaryRouteFirstSectionSerialNo;
    }

    public final void setPassengerCatergory(String passengerCatergory) {
        assertNotSealedAttribute("passengerCatergory");
        this.passengerCatergory = passengerCatergory;
    }

    public final void setAmountUnitShare(String amountUnitShare) {
        assertNotSealedAttribute("amountUnitShare");
        this.amountUnitShare = amountUnitShare;
    }

    public final void setGrossAmountToBeDebitedTheServiceProvidingRU(String grossAmountToBeDebitedTheServiceProvidingRU) {
        assertNotSealedAttribute("grossAmountToBeDebitedTheServiceProvidingRU");
        this.grossAmountToBeDebitedTheServiceProvidingRU = grossAmountToBeDebitedTheServiceProvidingRU;
    }

    public final void setGrossAmountToBeCreditedTheServiceProvidingRU(String grossAmountToBeCreditedTheServiceProvidingRU) {
        assertNotSealedAttribute("grossAmountToBeCreditedTheServiceProvidingRU");
        this.grossAmountToBeCreditedTheServiceProvidingRU = grossAmountToBeCreditedTheServiceProvidingRU;
    }

    public final void setPercentageCommissionRateOfServiceProvidingRU(String percentageCommissionRateOfServiceProvidingRU) {
        assertNotSealedAttribute("percentageCommissionRateOfServiceProvidingRU");
        this.percentageCommissionRateOfServiceProvidingRU = percentageCommissionRateOfServiceProvidingRU;
    }

    public final void setAmountOfCommissionToBeDebitedTheServiceProvidingRU(
            String amountOfCommissionToBeDebitedTheServiceProvidingRU) {
        assertNotSealedAttribute("amountOfCommissionToBeDebitedTheServiceProvidingRU");
        this.amountOfCommissionToBeDebitedTheServiceProvidingRU = amountOfCommissionToBeDebitedTheServiceProvidingRU;
    }

    public final void setAmountOfCommissionToBeCreditedTheServiceProvidingRU(
            String amountOfCommissionToBeCreditedTheServiceProvidingRU) {
        assertNotSealedAttribute("amountOfCommissionToBeCreditedTheServiceProvidingRU");
        this.amountOfCommissionToBeCreditedTheServiceProvidingRU = amountOfCommissionToBeCreditedTheServiceProvidingRU;
    }

    public final void setReserved4(String reserved4) {
        assertNotSealedAttribute("reserved4");
        this.reserved4 = reserved4;
    }

    public final void setCountryCode(String countryCode) {
        assertNotSealedAttribute("countryCode");
        this.countryCode = countryCode;
    }

    public final void setServiceBrandCode(String serviceBrandCode) {
        assertNotSealedAttribute("serviceBrandCode");
        this.serviceBrandCode = serviceBrandCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adjustment == null) ? 0 : adjustment.hashCode());
        result = prime * result
                + ((afterSalesFeeSharePercentage == null) ? 0 : afterSalesFeeSharePercentage.hashCode());
        result = prime * result + ((amountOfCommissionToBeCreditedTheServiceProvidingRU == null) ? 0
                : amountOfCommissionToBeCreditedTheServiceProvidingRU.hashCode());
        result = prime * result + ((amountOfCommissionToBeDebitedTheServiceProvidingRU == null) ? 0
                : amountOfCommissionToBeDebitedTheServiceProvidingRU.hashCode());
        result = prime * result + ((amountUnitShare == null) ? 0 : amountUnitShare.hashCode());
        result = prime * result + ((classOrCategory == null) ? 0 : classOrCategory.hashCode());
        result = prime * result + ((coachNumber == null) ? 0 : coachNumber.hashCode());
        result = prime * result + ((codeIssuingOffice == null) ? 0 : codeIssuingOffice.hashCode());
        result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
        result = prime * result + ((dayOfTravel == null) ? 0 : dayOfTravel.hashCode());
        result = prime * result + ((departureLocationRU == null) ? 0 : departureLocationRU.hashCode());
        result = prime * result + ((departureLocationStation == null) ? 0 : departureLocationStation.hashCode());
        result = prime * result + ((destinationLocationRU == null) ? 0 : destinationLocationRU.hashCode());
        result = prime * result + ((destinationLocationStation == null) ? 0 : destinationLocationStation.hashCode());
        result = prime * result + ((dialogueNumber == null) ? 0 : dialogueNumber.hashCode());
        result = prime * result + ((distributionChannel == null) ? 0 : distributionChannel.hashCode());
        result = prime * result + ((errorCount == null) ? 0 : errorCount.hashCode());
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
        result = prime * result + ((grossAmountToBeCredited == null) ? 0 : grossAmountToBeCredited.hashCode());
        result = prime * result + ((grossAmountToBeCreditedTheServiceProvidingRU == null) ? 0
                : grossAmountToBeCreditedTheServiceProvidingRU.hashCode());
        result = prime * result + ((grossAmountToBeDebited == null) ? 0 : grossAmountToBeDebited.hashCode());
        result = prime * result + ((grossAmountToBeDebitedTheServiceProvidingRU == null) ? 0
                : grossAmountToBeDebitedTheServiceProvidingRU.hashCode());
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((numberOfServices == null) ? 0 : numberOfServices.hashCode());
        result = prime * result + parsedLineNo;
        result = prime * result + ((passengerCatergory == null) ? 0 : passengerCatergory.hashCode());
        result = prime * result + ((percentageCommissionRateOfServiceProvidingRU == null) ? 0
                : percentageCommissionRateOfServiceProvidingRU.hashCode());
        result = prime * result + ((period == null) ? 0 : period.hashCode());
        result = prime * result + ((primaryRouteFirstSectionRU == null) ? 0 : primaryRouteFirstSectionRU.hashCode());
        result = prime * result
                + ((primaryRouteFirstSectionSerialNo == null) ? 0 : primaryRouteFirstSectionSerialNo.hashCode());
        result = prime * result + ((railUnionCompiling == null) ? 0 : railUnionCompiling.hashCode());
        result = prime * result + ((railUnionReceiving == null) ? 0 : railUnionReceiving.hashCode());
        result = prime * result + ((referenceNumber == null) ? 0 : referenceNumber.hashCode());
        result = prime * result + ((requestingTerminalNo == null) ? 0 : requestingTerminalNo.hashCode());
        result = prime * result + ((requestingTerminalRU == null) ? 0 : requestingTerminalRU.hashCode());
        result = prime * result + ((reserved1 == null) ? 0 : reserved1.hashCode());
        result = prime * result + ((reserved2 == null) ? 0 : reserved2.hashCode());
        result = prime * result + ((reserved3 == null) ? 0 : reserved3.hashCode());
        result = prime * result + ((reserved4 == null) ? 0 : reserved4.hashCode());
        result = prime * result + ((serviceBrandCode == null) ? 0 : serviceBrandCode.hashCode());
        result = prime * result + ((serviceProvidingRU == null) ? 0 : serviceProvidingRU.hashCode());
        result = prime * result + ((statementCurrency == null) ? 0 : statementCurrency.hashCode());
        result = prime * result + ((statementPeriod == null) ? 0 : statementPeriod.hashCode());
        result = prime * result + ((tariffCode == null) ? 0 : tariffCode.hashCode());
        result = prime * result + ((trainNumber == null) ? 0 : trainNumber.hashCode());
        result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
        result = prime * result + ((typeOfJourney == null) ? 0 : typeOfJourney.hashCode());
        result = prime * result + ((typeOfService == null) ? 0 : typeOfService.hashCode());
        result = prime * result + ((typeOfTransaction == null) ? 0 : typeOfTransaction.hashCode());
        result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
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
        Uic301G4Detail other = (Uic301G4Detail) obj;
        if (adjustment == null) {
            if (other.adjustment != null) {
                return false;
            }
        } else if (!adjustment.equals(other.adjustment)) {
            return false;
        }
        if (afterSalesFeeSharePercentage == null) {
            if (other.afterSalesFeeSharePercentage != null) {
                return false;
            }
        } else if (!afterSalesFeeSharePercentage.equals(other.afterSalesFeeSharePercentage)) {
            return false;
        }
        if (amountOfCommissionToBeCreditedTheServiceProvidingRU == null) {
            if (other.amountOfCommissionToBeCreditedTheServiceProvidingRU != null) {
                return false;
            }
        } else if (!amountOfCommissionToBeCreditedTheServiceProvidingRU
                .equals(other.amountOfCommissionToBeCreditedTheServiceProvidingRU)) {
            return false;
        }
        if (amountOfCommissionToBeDebitedTheServiceProvidingRU == null) {
            if (other.amountOfCommissionToBeDebitedTheServiceProvidingRU != null) {
                return false;
            }
        } else if (!amountOfCommissionToBeDebitedTheServiceProvidingRU
                .equals(other.amountOfCommissionToBeDebitedTheServiceProvidingRU)) {
            return false;
        }
        if (amountUnitShare == null) {
            if (other.amountUnitShare != null) {
                return false;
            }
        } else if (!amountUnitShare.equals(other.amountUnitShare)) {
            return false;
        }
        if (classOrCategory == null) {
            if (other.classOrCategory != null) {
                return false;
            }
        } else if (!classOrCategory.equals(other.classOrCategory)) {
            return false;
        }
        if (coachNumber == null) {
            if (other.coachNumber != null) {
                return false;
            }
        } else if (!coachNumber.equals(other.coachNumber)) {
            return false;
        }
        if (codeIssuingOffice == null) {
            if (other.codeIssuingOffice != null) {
                return false;
            }
        } else if (!codeIssuingOffice.equals(other.codeIssuingOffice)) {
            return false;
        }
        if (countryCode == null) {
            if (other.countryCode != null) {
                return false;
            }
        } else if (!countryCode.equals(other.countryCode)) {
            return false;
        }
        if (dayOfTravel == null) {
            if (other.dayOfTravel != null) {
                return false;
            }
        } else if (!dayOfTravel.equals(other.dayOfTravel)) {
            return false;
        }
        if (departureLocationRU == null) {
            if (other.departureLocationRU != null) {
                return false;
            }
        } else if (!departureLocationRU.equals(other.departureLocationRU)) {
            return false;
        }
        if (departureLocationStation == null) {
            if (other.departureLocationStation != null) {
                return false;
            }
        } else if (!departureLocationStation.equals(other.departureLocationStation)) {
            return false;
        }
        if (destinationLocationRU == null) {
            if (other.destinationLocationRU != null) {
                return false;
            }
        } else if (!destinationLocationRU.equals(other.destinationLocationRU)) {
            return false;
        }
        if (destinationLocationStation == null) {
            if (other.destinationLocationStation != null) {
                return false;
            }
        } else if (!destinationLocationStation.equals(other.destinationLocationStation)) {
            return false;
        }
        if (dialogueNumber == null) {
            if (other.dialogueNumber != null) {
                return false;
            }
        } else if (!dialogueNumber.equals(other.dialogueNumber)) {
            return false;
        }
        if (distributionChannel == null) {
            if (other.distributionChannel != null) {
                return false;
            }
        } else if (!distributionChannel.equals(other.distributionChannel)) {
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
        if (grossAmountToBeCredited == null) {
            if (other.grossAmountToBeCredited != null) {
                return false;
            }
        } else if (!grossAmountToBeCredited.equals(other.grossAmountToBeCredited)) {
            return false;
        }
        if (grossAmountToBeCreditedTheServiceProvidingRU == null) {
            if (other.grossAmountToBeCreditedTheServiceProvidingRU != null) {
                return false;
            }
        } else if (!grossAmountToBeCreditedTheServiceProvidingRU
                .equals(other.grossAmountToBeCreditedTheServiceProvidingRU)) {
            return false;
        }
        if (grossAmountToBeDebited == null) {
            if (other.grossAmountToBeDebited != null) {
                return false;
            }
        } else if (!grossAmountToBeDebited.equals(other.grossAmountToBeDebited)) {
            return false;
        }
        if (grossAmountToBeDebitedTheServiceProvidingRU == null) {
            if (other.grossAmountToBeDebitedTheServiceProvidingRU != null) {
                return false;
            }
        } else if (!grossAmountToBeDebitedTheServiceProvidingRU
                .equals(other.grossAmountToBeDebitedTheServiceProvidingRU)) {
            return false;
        }
        if (identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!identifier.equals(other.identifier)) {
            return false;
        }
        if (numberOfServices == null) {
            if (other.numberOfServices != null) {
                return false;
            }
        } else if (!numberOfServices.equals(other.numberOfServices)) {
            return false;
        }
        if (parsedLineNo != other.parsedLineNo) {
            return false;
        }
        if (passengerCatergory == null) {
            if (other.passengerCatergory != null) {
                return false;
            }
        } else if (!passengerCatergory.equals(other.passengerCatergory)) {
            return false;
        }
        if (percentageCommissionRateOfServiceProvidingRU == null) {
            if (other.percentageCommissionRateOfServiceProvidingRU != null) {
                return false;
            }
        } else if (!percentageCommissionRateOfServiceProvidingRU
                .equals(other.percentageCommissionRateOfServiceProvidingRU)) {
            return false;
        }
        if (period == null) {
            if (other.period != null) {
                return false;
            }
        } else if (!period.equals(other.period)) {
            return false;
        }
        if (primaryRouteFirstSectionRU == null) {
            if (other.primaryRouteFirstSectionRU != null) {
                return false;
            }
        } else if (!primaryRouteFirstSectionRU.equals(other.primaryRouteFirstSectionRU)) {
            return false;
        }
        if (primaryRouteFirstSectionSerialNo == null) {
            if (other.primaryRouteFirstSectionSerialNo != null) {
                return false;
            }
        } else if (!primaryRouteFirstSectionSerialNo.equals(other.primaryRouteFirstSectionSerialNo)) {
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
        if (referenceNumber == null) {
            if (other.referenceNumber != null) {
                return false;
            }
        } else if (!referenceNumber.equals(other.referenceNumber)) {
            return false;
        }
        if (requestingTerminalNo == null) {
            if (other.requestingTerminalNo != null) {
                return false;
            }
        } else if (!requestingTerminalNo.equals(other.requestingTerminalNo)) {
            return false;
        }
        if (requestingTerminalRU == null) {
            if (other.requestingTerminalRU != null) {
                return false;
            }
        } else if (!requestingTerminalRU.equals(other.requestingTerminalRU)) {
            return false;
        }
        if (reserved1 == null) {
            if (other.reserved1 != null) {
                return false;
            }
        } else if (!reserved1.equals(other.reserved1)) {
            return false;
        }
        if (reserved2 == null) {
            if (other.reserved2 != null) {
                return false;
            }
        } else if (!reserved2.equals(other.reserved2)) {
            return false;
        }
        if (reserved3 == null) {
            if (other.reserved3 != null) {
                return false;
            }
        } else if (!reserved3.equals(other.reserved3)) {
            return false;
        }
        if (reserved4 == null) {
            if (other.reserved4 != null) {
                return false;
            }
        } else if (!reserved4.equals(other.reserved4)) {
            return false;
        }
        if (serviceBrandCode == null) {
            if (other.serviceBrandCode != null) {
                return false;
            }
        } else if (!serviceBrandCode.equals(other.serviceBrandCode)) {
            return false;
        }
        if (serviceProvidingRU == null) {
            if (other.serviceProvidingRU != null) {
                return false;
            }
        } else if (!serviceProvidingRU.equals(other.serviceProvidingRU)) {
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
        if (tariffCode == null) {
            if (other.tariffCode != null) {
                return false;
            }
        } else if (!tariffCode.equals(other.tariffCode)) {
            return false;
        }
        if (trainNumber == null) {
            if (other.trainNumber != null) {
                return false;
            }
        } else if (!trainNumber.equals(other.trainNumber)) {
            return false;
        }
        if (transactionDate == null) {
            if (other.transactionDate != null) {
                return false;
            }
        } else if (!transactionDate.equals(other.transactionDate)) {
            return false;
        }
        if (typeOfJourney == null) {
            if (other.typeOfJourney != null) {
                return false;
            }
        } else if (!typeOfJourney.equals(other.typeOfJourney)) {
            return false;
        }
        if (typeOfService == null) {
            if (other.typeOfService != null) {
                return false;
            }
        } else if (!typeOfService.equals(other.typeOfService)) {
            return false;
        }
        if (typeOfTransaction == null) {
            if (other.typeOfTransaction != null) {
                return false;
            }
        } else if (!typeOfTransaction.equals(other.typeOfTransaction)) {
            return false;
        }
        if (unitPrice == null) {
            if (other.unitPrice != null) {
                return false;
            }
        } else if (!unitPrice.equals(other.unitPrice)) {
            return false;
        }
        return true;
    }

    /**
     * Validates this object and saves the violations as errors.
     */
    @Override
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
    @Override
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
    
    private void assertNotSealedAttribute(String name) {
        if (sealed) {
            throw new IllegalStateException("The class is sealed. No more changes are allowed.");
        }
        if (this.sealedAttributes.contains(name)) {
            throw new IllegalStateException("The Attribute is sealed. No more changes are allowed.");
        }
        sealedAttributes.add(name);
    }

    /**
     * Creates a new detail line by parsing a string.
     * 
     * @param no
     *            Line number.
     * @param line
     *            Line to parse.
     *
     * @return Returns a new instance with the parsed data.
     * 
     * @throws Uic301Exception
     *             Parsing the detail failed.
     */
    public static Uic301G4Detail parse(final int no, final String line) throws Uic301Exception {
        if (!isDetail(line)) {
            throw new IllegalArgumentException("# " + no + " is no G4 detail line: '" + line + "'");
        }

        final SubString subStr = new SubString(line);
        final String identifier = subStr.next("identifier", 9);
        final String railUnionCompiling = subStr.next("railUnionCompiling", 4);
        final String railUnionReceiving = subStr.next("railUnionReceiving", 4);
        final String period = subStr.next("period", 6);
        final String reserved1 = subStr.next("reserved1", 1);
        final String typeOfService = subStr.next("typeOfService", 2);
        final String typeofTransaction = subStr.next("typeofTransaction", 1);
        final String distributionChannel = subStr.next("distributionChannel", 1);
        final String codeIssuingOffice = subStr.next("codeIssuingOffice", 5);
        final String requestingTerminalRU = subStr.next("requestingTerminalRU", 4);
        final String requestingTerminalNo = subStr.next("requestingTerminalNo", 7);
        final String statementCurrency = subStr.next("statementCurrency", 3);
        final String statementPeriod = subStr.next("statementPeriod", 2);
        final String classOrCategory = subStr.next("classOrCategory", 3);
        final String unitPrice = subStr.next("unitPrice", 8);
        final String trainNumber = subStr.next("trainNumber", 5);
        final String coachNumber = subStr.next("coachNumber", 3);
        final String dayOfTravel = subStr.next("dayOfTravel", 6);
        final String departureLocationRU = subStr.next("departureLocationRU", 4);
        final String departureLocationStation = subStr.next("departureLocationStation", 5);
        final String reserved2 = subStr.next("reserved2", 1);
        final String destinationLocationRU = subStr.next("destinationLocationRU", 4);
        final String destinationLocationStation = subStr.next("destinationLocationStation", 5);
        final String reserved3 = subStr.next("reserved3", 1);
        final String referenceNumber = subStr.next("referenceNumber", 14);
        final String dialogueNumber = subStr.next("dialogueNumber", 5);
        final String transactionDate = subStr.next("transactionDate", 6);
        final String numberOfServices = subStr.next("numberOfServices", 5);
        final String adjustment = subStr.next("adjustment", 1);
        final String grossAmountToBeDebited = subStr.next("grossAmountToBeDebited", 10);
        final String grossAmountToBeCredited = subStr.next("grossAmountToBeCredited", 10);
        final String serviceProvidingRU = subStr.next("serviceProvidingRU", 4);
        final String afterSalesFeeSharePercentage = subStr.next("afterSalesFeeSharePercentage", 2);
        final String tariffCode = subStr.next("tariffCode", 5);
        final String typeOfJourney = subStr.next("typeOfJourney", 1);
        final String primaryRouteFirstSectionRU = subStr.next("primaryRouteFirstSectionRU", 4);
        final String primaryRouteFirstSectionSerialNo = subStr.next("primaryRouteFirstSectionSerialNo", 5);
        final String passengerCatergory = subStr.next("passengerCatergory", 2);
        final String amountUnitShare = subStr.next("amountUnitShare", 8);
        final String grossAmountToBeDebitedTheServiceProvidingRU = subStr
                .next("grossAmountToBeDebitedTheServiceProvidingRU", 10);
        final String grossAmountToBeCreditedTheServiceProvidingRU = subStr
                .next("grossAmountToBeCreditedTheServiceProvidingRU", 10);
        final String percentageCommissionRateOfServiceProvidingRU = subStr
                .next("percentageCommissionRateOfServiceProvidingRU", 4);
        final String amountOfCommissionToBeDebitedTheServiceProvidingRU = subStr
                .next("amountOfCommissionToBeDebitedTheServiceProvidingRU", 10);
        final String amountOfCommissionToBeCreditedTheServiceProvidingRU = subStr
                .next("amountOfCommissionToBeCreditedTheServiceProvidingRU", 10);
        final String reserved4 = subStr.next("reserved4", 3);
        final String countryCode = subStr.next("countryCode", 2);
        final String serviceBrandCode = subStr.next("serviceBrandCode", 4);

        return new Uic301G4Detail(identifier, railUnionCompiling, railUnionReceiving, period, reserved1,
                typeOfService, typeofTransaction, distributionChannel, codeIssuingOffice, requestingTerminalRU,
                requestingTerminalNo, statementCurrency, statementPeriod, classOrCategory, unitPrice, trainNumber,
                coachNumber, dayOfTravel, departureLocationRU, departureLocationStation, reserved2,
                destinationLocationRU, destinationLocationStation, reserved3, referenceNumber, dialogueNumber,
                transactionDate, numberOfServices, adjustment, grossAmountToBeDebited, grossAmountToBeCredited,
                serviceProvidingRU, afterSalesFeeSharePercentage, tariffCode, typeOfJourney,
                primaryRouteFirstSectionRU, primaryRouteFirstSectionSerialNo, passengerCatergory, amountUnitShare,
                grossAmountToBeDebitedTheServiceProvidingRU, grossAmountToBeCreditedTheServiceProvidingRU,
                percentageCommissionRateOfServiceProvidingRU, amountOfCommissionToBeDebitedTheServiceProvidingRU,
                amountOfCommissionToBeCreditedTheServiceProvidingRU, reserved4, countryCode, serviceBrandCode, no);

    }

    /**
     * Determines if the given line is a G4 detail line.
     * 
     * @param line
     *            Line to analyze.
     * 
     * @return True if the line starts with a G4 detail identifier.
     */
    public static boolean isDetail(final String line) {
        return line != null && (line.startsWith(G4) || line.startsWith(G4_DB));
    }

}
