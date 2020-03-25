package ch.sbb.fss.uic301.parser;

import java.math.BigDecimal;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;


/**
 * Common values for both G4 and G5 details.
 */
public interface Uic301Detail extends ParsedLineItem, Uic301DocumentItem {

    /**
     * Returns a number of detail errors.
     * 
     * @return Number of errors in the record.
     */
    public int getErrorCount();

    /**
     * Validates the instance (and child instances).
     * 
     * @param validator
     *            Validator to use.
     */
    public void validate(@NotNull Validator validator);

    /**
     * Returns the type of the identifier.
     * 
     * @return Identifier type.
     */
    public Uic301Type getIdentifierType();

    /**
     * Returns the identifier field.
     * 
     * @return Identifier (9 characters) - Never <code>null</code>.
     */
    public String getIdentifier();

    /**
     * Returns the RU compiling the statement<br>
     * 
     * @return RICS code (4 characters) - Never <code>null</code>.
     */
    public String getRailUnionCompiling();

    /**
     * Returns the RU receiving the statement<br>
     * 
     * @return RICS code (4 characters) - Never <code>null</code>.
     */
    public String getRailUnionReceiving();

    /**
     * Returns the statement period (YYMMPP). YY=Year, MM=Month, PP=Period in
     * the month (00 default, other usage must be bilaterally agreed upon).
     * 
     * @return Period (6 characters) - Never <code>null</code>.
     */
    public String getPeriod();

    /**
     * Returns the type of service. See {@link ServiceType} for possible values.
     * 
     * @return Type of service.
     */
    public String getTypeOfService();

    /**
     * Returns the type of service.
     * 
     * @return Is <code>null</code> if {@link #getTypeOfService()} returns
     *         <code>null</code> or has a value that cannot be converted into an
     *         enum, else the corresponding enum.
     */
    public ServiceType getTypeOfServiceValue();

    /**
     * Returns the type of transaction. See {@link TransactionType} for possible
     * values.
     * 
     * @return Type of transaction.
     */
    public String getTypeOfTransaction();

    /**
     * Returns the type of transaction.
     * 
     * @return Is <code>null</code> if {@link #getTypeOfTransaction()} returns
     *         <code>null</code> or has a value that cannot be converted into an
     *         enum, else the corresponding enum.
     */
    public TransactionType getTypeOfTransactionValue();

    /**
     * Returns the distribution channel. See {@link DistributionChannel} for
     * possible values.
     * 
     * @return Distribution channel.
     */
    public String getDistributionChannel();

    /**
     * Returns the distribution channel.
     * 
     * @return Is <code>null</code> if {@link #getDistributionChannel()} returns
     *         <code>null</code> or has a value that cannot be converted into an
     *         enum, else the corresponding enum.
     */
    public DistributionChannel getDistributionChannelType();

    /**
     * Returns the code issuing office.
     * 
     * @return Code issuing office.
     */
    public String getCodeIssuingOffice();

    /**
     * Returns the ISO 4207 code for currency, normally EUR.
     * 
     * @return Currency.
     */
    public String getStatementCurrency();

    /**
     * Period code stipulated by BCC.
     *
     * @return Period.
     */
    public String getStatementPeriod();

    /**
     * Combination of ISO 4207 code for currency and period code stipulated by
     * BCC.
     *
     * @return Currency and period.
     */
    public StatementCurrencyPeriod getStatementCurrencyPeriod();

    /**
     * Returns the class or category. See {@link ClassOrCategory} for possible
     * values.
     * 
     * @return Class or category.
     */
    public String getClassOrCategory();

    /**
     * Returns the class or category.
     * 
     * @return Is <code>null</code> if {@link #getClassOrCategory()} returns
     *         <code>null</code> or has a value that cannot be converted into an
     *         enum, else the corresponding enum.
     */
    public ClassOrCategory getClassOrCategoryValue();

    public String getTrainNumber();

    public String getCoachNumber();

    public String getDayOfTravel();
    
    public String getDepartureLocationRU();

    public String getDepartureLocationStation();
    
    public String getDestinationLocationRU();

    public String getDestinationLocationStation();

    public String getTransactionDate();

    public String getNumberOfServices();

    public String getAdjustment();

    public String getGrossAmountToBeDebited();

    public BigDecimal getGrossAmountToBeDebitedValue();

    public String getGrossAmountToBeCredited();

    public BigDecimal getGrossAmountToBeCreditedValue();

    public String getTariffCode();

    public String getTypeOfJourney();

    /**
     * Returns the type of journey.
     * 
     * @return Is <code>null</code> if {@link #getTypeOfJourney()} returns
     *         <code>null</code> or has a value that cannot be converted into an
     *         enum, else the corresponding enum.
     */
    public JourneyType getTypeOfJourneyValue();

    public String getPrimaryRouteFirstSectionRU();

    public String getPrimaryRouteFirstSectionSerialNo();

    /**
     * Returns the passenger category. See {@link PassengerCatergory} for
     * possible values.
     * 
     * @return Passenger category.
     */
    public String getPassengerCatergory();

    /**
     * Returns the passenger category.
     * 
     * @return Is <code>null</code> if {@link #getPassengerCatergory()} returns
     *         <code>null</code> or has a value that cannot be converted into an
     *         enum, else the corresponding enum.
     */
    public PassengerCatergory getPassengerCatergoryType();

    public String getAmountUnitShare();

    public BigDecimal getAmountUnitShareValue();

    public String getCountryCode();

    public String getServiceBrandCode();

    public String getAmountOfCommissionToBeDebitedTheServiceProvidingRU();

    public BigDecimal getAmountOfCommissionToBeDebitedTheServiceProvidingRUValue();

    public BigDecimal getAmountOfCommissionToBeCreditedTheServiceProvidingRUValue();

    public void setDepartureLocationRU(String string);

    public void setDestinationLocationRU(String string);
    
    public void setPrimaryRouteFirstSectionRU(String string);

    boolean isAmountMissing();


}
