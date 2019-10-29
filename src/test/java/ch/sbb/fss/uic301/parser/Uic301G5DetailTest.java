package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Test for the {@link Uic301G5Detail} class.
 */
public class Uic301G5DetailTest {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301G5Detail.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValid() {

        // PREPARE
        final String identifier = "142210000";
        final String railUnionCompiling = "0087";
        final String railUnionReceiving = "1185";
        final String period = "171100";
        final String reserved1 = "0";
        final String typeOfService = "05";
        final String typeofTransaction = "1";
        final String distributionChannel = "1";
        final String codeIssuingOffice = "00000";
        final String reserved2 = "0";
        final String countryCode = "IT";
        final String serviceBrandCode = "0000";
        final String statementCurrency = "EUR";
        final String statementPeriod = "01";
        final String tariffCode = "10001";
        final String typeOfJourney = "1";
        final String classOrCategory = "002";
        final String amountUnitShare = "00003040";
        final String trainNumber = "09211";
        final String coachNumber = "000";
        final String dayOfTravel = "171223";
        final String departureLocationRU = "1185";
        final String departureLocationStation = "00010";
        final String reserved3 = "0";
        final String destinationLocationRU = "1185";
        final String destinationLocationStation = "03000";
        final String typeIdentifier = "1";
        final String numberIdentifiers = "00870933451367";
        final String dialogueNumber = "00059";
        final String transactionDate = "171105";
        final String numberOfServices = "00002";
        final String adjustment = "0";
        final String grossAmountToBeDebited = "0000006080";
        final String grossAmountToBeCredited = "0000000000";
        final String afterSalesPercentageFee = "00";
        final String transactionRU = "0083";
        final String primaryRouteFirstSectionRU = "1185";
        final String primaryRouteFirstSectionSerialNo = "02089";
        final String passengerCatergory = "11";
        final String serviceProvidingRUsCommissionRate = "0500";
        final String amountOfCommissionToBeDebitedTheServiceProvidingRU = "0000000000";
        final String amountOfCommissionToBeCreditedTheServiceProvidingRU = "0000000304";
        final String primaryRouteSecondSectionRU = "0000";
        final String primaryRouteSecondSectionSerialNo = "00000";
        final int lineNo = -1;

        final Uic301G5Detail testee = new Uic301G5Detail(identifier, railUnionCompiling, railUnionReceiving, period,
                reserved1, typeOfService, typeofTransaction, distributionChannel, codeIssuingOffice, reserved2,
                countryCode, serviceBrandCode, statementCurrency, statementPeriod, tariffCode, typeOfJourney,
                classOrCategory, amountUnitShare, trainNumber, coachNumber, dayOfTravel, departureLocationRU,
                departureLocationStation, reserved3, destinationLocationRU, destinationLocationStation,
                typeIdentifier, numberIdentifiers, dialogueNumber, transactionDate, numberOfServices, adjustment,
                grossAmountToBeDebited, grossAmountToBeCredited, afterSalesPercentageFee, transactionRU,
                primaryRouteFirstSectionRU, primaryRouteFirstSectionSerialNo, passengerCatergory,
                serviceProvidingRUsCommissionRate, amountOfCommissionToBeDebitedTheServiceProvidingRU,
                amountOfCommissionToBeCreditedTheServiceProvidingRU, primaryRouteSecondSectionRU,
                primaryRouteSecondSectionSerialNo, lineNo);

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(testee);

        // VERIFY
        assertThat(violations).hasSize(0);

        assertThat(testee.getIdentifier()).isEqualTo(identifier);
        assertThat(testee.getIdentifierType()).isEqualTo(Uic301Type.G5_ALLOCATION);
        assertThat(testee.getRailUnionCompiling()).isEqualTo(railUnionCompiling);
        assertThat(testee.getRailUnionReceiving()).isEqualTo(railUnionReceiving);
        assertThat(testee.getPeriod()).isEqualTo(period);
        assertThat(testee.getReserved1()).isEqualTo(reserved1);
        assertThat(testee.getTypeOfService()).isEqualTo(typeOfService);
        assertThat(testee.getTypeOfServiceValue()).isEqualTo(ServiceType.IRT_DOMESTIC);
        assertThat(testee.getTypeOfTransaction()).isEqualTo(typeofTransaction);
        assertThat(testee.getTypeOfTransactionValue()).isEqualTo(TransactionType.CANCELLATION);
        assertThat(testee.getDistributionChannel()).isEqualTo(distributionChannel);
        assertThat(testee.getDistributionChannelType()).isEqualTo(DistributionChannel.STATION);
        assertThat(testee.getCodeIssuingOffice()).isEqualTo(codeIssuingOffice);
        assertThat(testee.getReserved2()).isEqualTo(reserved2);
        assertThat(testee.getCountryCode()).isEqualTo(countryCode);
        assertThat(testee.getServiceBrandCode()).isEqualTo(serviceBrandCode);
        assertThat(testee.getStatementCurrency()).isEqualTo(statementCurrency);
        assertThat(testee.getStatementPeriod()).isEqualTo(statementPeriod);
        assertThat(testee.getTariffCode()).isEqualTo(tariffCode);
        assertThat(testee.getTypeOfJourney()).isEqualTo(typeOfJourney);
        assertThat(testee.getTypeOfJourneyValue()).isEqualTo(JourneyType.SINGLE_JOURNEY);
        assertThat(testee.getClassOrCategory()).isEqualTo(classOrCategory);
        assertThat(testee.getClassOrCategoryValue()).isEqualTo(ClassOrCategory.CLASS_2ND);
        assertThat(testee.getAmountUnitShare()).isEqualTo(amountUnitShare);
        assertThat(testee.getAmountUnitShareValue())
                .isEqualTo(BigDecimal.valueOf(30.4).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getTrainNumber()).isEqualTo(trainNumber);
        assertThat(testee.getCoachNumber()).isEqualTo(coachNumber);
        assertThat(testee.getDayOfTravel()).isEqualTo(dayOfTravel);
        assertThat(testee.getDepartureLocationRU()).isEqualTo(departureLocationRU);
        assertThat(testee.getDepartureLocationStation()).isEqualTo(departureLocationStation);
        assertThat(testee.getReserved3()).isEqualTo(reserved3);
        assertThat(testee.getDestinationLocationRU()).isEqualTo(destinationLocationRU);
        assertThat(testee.getDestinationLocationStation()).isEqualTo(destinationLocationStation);
        assertThat(testee.getTypeIdentifier()).isEqualTo(typeIdentifier);
        assertThat(testee.getNumberIdentifiers()).isEqualTo(numberIdentifiers);
        assertThat(testee.getDialogueNumber()).isEqualTo(dialogueNumber);
        assertThat(testee.getTransactionDate()).isEqualTo(transactionDate);
        assertThat(testee.getNumberOfServices()).isEqualTo(numberOfServices);
        assertThat(testee.getAdjustment()).isEqualTo(adjustment);
        assertThat(testee.getGrossAmountToBeDebited()).isEqualTo(grossAmountToBeDebited);
        assertThat(testee.getGrossAmountToBeDebitedValue())
                .isEqualTo(BigDecimal.valueOf(60.80).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getGrossAmountToBeCredited()).isEqualTo(grossAmountToBeCredited);
        assertThat(testee.getGrossAmountToBeCreditedValue())
                .isEqualTo(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getAfterSalesPercentageFee()).isEqualTo(afterSalesPercentageFee);
        assertThat(testee.getTransactionRU()).isEqualTo(transactionRU);
        assertThat(testee.getPrimaryRouteFirstSectionRU()).isEqualTo(primaryRouteFirstSectionRU);
        assertThat(testee.getPrimaryRouteFirstSectionSerialNo()).isEqualTo(primaryRouteFirstSectionSerialNo);
        assertThat(testee.getPassengerCatergory()).isEqualTo(passengerCatergory);
        assertThat(testee.getPassengerCatergoryType()).isEqualTo(PassengerCatergory.ADULT);
        assertThat(testee.getServiceProvidingRUsCommissionRate()).isEqualTo(serviceProvidingRUsCommissionRate);
        assertThat(testee.getServiceProvidingRUsCommissionRateValue())
                .isEqualTo(BigDecimal.valueOf(5).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getAmountOfCommissionToBeDebitedTheServiceProvidingRU())
                .isEqualTo(amountOfCommissionToBeDebitedTheServiceProvidingRU);
        assertThat(testee.getAmountOfCommissionToBeDebitedTheServiceProvidingRUValue())
                .isEqualTo(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getAmountOfCommissionToBeCreditedTheServiceProvidingRU())
                .isEqualTo(amountOfCommissionToBeCreditedTheServiceProvidingRU);
        assertThat(testee.getAmountOfCommissionToBeCreditedTheServiceProvidingRUValue())
                .isEqualTo(BigDecimal.valueOf(3.04).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getPrimaryRouteSecondSectionRU()).isEqualTo(primaryRouteSecondSectionRU);
        assertThat(testee.getPrimaryRouteSecondSectionSerialNo()).isEqualTo(primaryRouteSecondSectionSerialNo);
        assertThat(testee.getParsedLineNo()).isEqualTo(lineNo);

    }

    @Test
    public void testParse() throws Uic301Exception {

        // PREPARE
        final String line = "1422100000087118517110000511000000IT0000EUR0110001100200003040092110001712231185000100118503000100870933451367000591711050000200000006080000000000000008311850208911050000000000000000000304000000000";

        // TEST
        final Uic301G5Detail testee = Uic301G5Detail.parse(1, line);

        // VERIFY
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(testee);
        assertThat(violations).hasSize(0);

    }

    @Test
    public void testInvalidIdentifier() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("1411A0000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "identifier", "Expected one of [142210000, 145210000, 143210000], but was: 1411A0000");

    }

    @Test
    public void testInvalidRailUnionCompiling() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "x", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionCompiling", "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidRailUnionReceiving() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "x", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionReceiving", "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidPeriod() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "x", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1", "002",
                "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "period", "Expected 'YYMMPP', but was: 'x'");

    }

    @Test
    public void testInvalidReserved1() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "x", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved1", "Expected only 1 zero(s), but was: 'x'");

    }

    @Test
    public void testInvalidTypeOfService() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "x", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "typeOfService", "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTypeOfTransaction() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "x", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "typeOfTransaction", "Expected exactly 1 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDistributionChannel() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "x", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "distributionChannel", "Expected exactly 1 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidCodeIssuingOffice() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "x", "0", "IT", "0000", "EUR", "01", "10001", "1", "002",
                "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "codeIssuingOffice", "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidReserved2() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "x", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved2", "Expected only 1 zero(s), but was: 'x'");

    }

    @Test
    public void testInvalidCountryCode() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "x", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "countryCode",
                "Expected ISO 3166 country code (e.g. 'CH') or '00', but was: 'x'");

    }

    @Test
    public void testInvalidServiceBrandCode() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "x", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "serviceBrandCode", "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidStatementCurrency() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "x", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "statementCurrency",
                "Expected only three uppercase characters for currency, but was 'x'");

    }

    @Test
    public void testInvalidStatementPeriod() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "x", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "statementPeriod", "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTariffCode() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "x", "1", "002",
                "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "tariffCode", "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTypeOfJourney() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "x",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "typeOfJourney", "Expected exactly 1 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidClassOrCategory() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "x", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "classOrCategory", "Expected exactly 3 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountUnitShare() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "x", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "amountUnitShare", "Expected exactly 8 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTrainNumber() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "x", "000", "171223", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "trainNumber", "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidCoachNumber() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "x", "171223", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "coachNumber", "Expected exactly 3 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDayOfTravel() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "x", "1185", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "dayOfTravel", "Expected 'YYMMDD', but was: 'x'");

    }

    @Test
    public void testInvalidDepartureLocationRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "x", "00010", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "departureLocationRU", "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDepartureLocationStation() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "x", "0", "1185", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "departureLocationStation", "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidReserved3() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "x", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved3", "Expected only 1 zero(s), but was: 'x'");

    }

    @Test
    public void testInvaliddestinationLocationRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "x", "03000", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "destinationLocationRU", "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDestinationLocationStation() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "x", "1", "00870933451367",
                "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089", "11",
                "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "destinationLocationStation", "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTypeIdentifier() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "x",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "typeIdentifier", "Expected exactly 1 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidNumberIdentifiers() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(
                new Uic301G5Detail("142210000", "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT",
                        "0000", "EUR", "01", "10001", "1", "002", "00003040", "09211", "000", "171223", "1185", "00010",
                        "0", "1185", "03000", "1", "x", "00059", "171105", "00002", "0", "0000006080", "0000000000",
                        "00", "0083", "1185", "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "numberIdentifiers", "Expected exactly 14 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDialogueNumber() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "x", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "dialogueNumber", "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTransactionDate() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "x", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185", "02089",
                "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "transactionDate", "Expected 'YYMMDD', but was: 'x'");

    }

    @Test
    public void testInvalidNumberOfServices() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "x", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "numberOfServices", "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAdjustment() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "x", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "adjustment", "Expected '0', '1' or '2', but was: 'x'");

    }

    @Test
    public void testInvalidGrossAmountToBeDebited() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(
                new Uic301G5Detail("142210000", "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT",
                        "0000", "EUR", "01", "10001", "1", "002", "00003040", "09211", "000", "171223", "1185", "00010",
                        "0", "1185", "03000", "1", "00870933451367", "00059", "171105", "00002", "0", "x", "0000000000",
                        "00", "0083", "1185", "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "grossAmountToBeDebited", "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidGrossAmountToBeCredited() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(
                new Uic301G5Detail("142210000", "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT",
                        "0000", "EUR", "01", "10001", "1", "002", "00003040", "09211", "000", "171223", "1185", "00010",
                        "0", "1185", "03000", "1", "00870933451367", "00059", "171105", "00002", "0", "0000006080", "x",
                        "00", "0083", "1185", "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "grossAmountToBeCredited", "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAfterSalesPercentageFee() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "x", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "afterSalesPercentageFee", "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTransactionRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "x", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "transactionRU", "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidPrimaryRouteFirstSectionRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "x",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "primaryRouteFirstSectionRU", "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidPrimaryRouteFirstSectionSerialNo() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "x", "11", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "primaryRouteFirstSectionSerialNo",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidPassengerCatergory() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "x", "0500", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "passengerCatergory", "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidServiceProvidingRUsCommissionRate() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "x", "0000000000", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "serviceProvidingRUsCommissionRate",
                "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountOfCommissionToBeDebitedTheServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "x", "0000000304", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "amountOfCommissionToBeDebitedTheServiceProvidingRU",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountOfCommissionToBeCreditedTheServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "x", "0000", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "amountOfCommissionToBeCreditedTheServiceProvidingRU",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidPrimaryRouteSecondSectionRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "x", "00000", -1));

        // VERIFY
        assertSingleViolation(violations, "primaryRouteSecondSectionRU", "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidPrimaryRouteSecondSectionSerialNo() {

        // TEST
        final Set<ConstraintViolation<Uic301G5Detail>> violations = VALIDATOR.validate(new Uic301G5Detail("142210000",
                "0087", "1185", "171100", "0", "05", "1", "1", "00000", "0", "IT", "0000", "EUR", "01", "10001", "1",
                "002", "00003040", "09211", "000", "171223", "1185", "00010", "0", "1185", "03000", "1",
                "00870933451367", "00059", "171105", "00002", "0", "0000006080", "0000000000", "00", "0083", "1185",
                "02089", "11", "0500", "0000000000", "0000000304", "0000", "x", -1));

        // VERIFY
        assertSingleViolation(violations, "primaryRouteSecondSectionSerialNo",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301G5Detail original = createValidSample();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301G5Detail.class);
        final Uic301G5Detail copy = JaxbUtils.unmarshal(xml, Uic301G5Detail.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301G5Detail original = createInvalidSample();
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301G5Detail.class);
        final Uic301G5Detail copy = JaxbUtils.unmarshal(xml, Uic301G5Detail.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J.readAsString(this.getClass().getResource("/uic301-detail-g5.xml").toURI().toURL(),
                "utf-8", 1024);
        // TEST
        final Uic301G5Detail header = JaxbUtils.unmarshal(xml, Uic301G5Detail.class);

        // VALIDATE
        assertThat(header.getErrors()).hasSize(2);
        assertThat(header.getErrors()).containsOnly(
                new FieldError("primaryRouteSecondSectionSerialNo", "Expected exactly 5 digit(s), but was: 'x'"),
                new FieldError("identifier", "Expected one of [142210000, 145210000, 143210000], but was: 1411A0000"));

    }

    private void assertSingleViolation(final Set<ConstraintViolation<Uic301G5Detail>> violations, final String property,
            final String message) {
        assertThat(violations).hasSize(1);
        final ConstraintViolation<Uic301G5Detail> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo(message);
        assertThat(violation.getPropertyPath().iterator().next().toString()).isEqualTo(property);
    }

    /**
     * Creates a sample with valid data.
     * 
     * @return Valid instance.
     */
    Uic301G5Detail createValidSample() {

        final String identifier = "142210000";
        final String railUnionCompiling = "0087";
        final String railUnionReceiving = "1185";
        final String period = "171100";
        final String reserved1 = "0";
        final String typeOfService = "05";
        final String typeofTransaction = "1";
        final String distributionChannel = "1";
        final String codeIssuingOffice = "00000";
        final String reserved2 = "0";
        final String countryCode = "IT";
        final String serviceBrandCode = "0000";
        final String statementCurrency = "EUR";
        final String statementPeriod = "01";
        final String tariffCode = "10001";
        final String typeOfJourney = "1";
        final String classOrCategory = "002";
        final String amountUnitShare = "00003040";
        final String trainNumber = "09211";
        final String coachNumber = "000";
        final String dayOfTravel = "171223";
        final String departureLocationCountry = "1185";
        final String departureLocationStation = "00010";
        final String reserved3 = "0";
        final String destinationLocationRU = "1185";
        final String destinationLocationStation = "03000";
        final String typeIdentifier = "1";
        final String numberIdentifiers = "00870933451367";
        final String dialogueNumber = "00059";
        final String transactionDate = "171105";
        final String numberOfServices = "00002";
        final String adjustment = "0";
        final String grossAmountToBeDebited = "0000006080";
        final String grossAmountToBeCredited = "0000000000";
        final String afterSalesPercentageFee = "00";
        final String transactionRU = "0083";
        final String primaryRouteFirstSectionRU = "1185";
        final String primaryRouteFirstSectionSerialNo = "02089";
        final String passengerCatergory = "11";
        final String serviceProvidingRUsCommissionRate = "0500";
        final String amountOfCommissionToBeDebitedTheServiceProvidingRU = "0000000000";
        final String amountOfCommissionToBeCreditedTheServiceProvidingRU = "0000000304";
        final String primaryRouteSecondSectionRU = "0000";
        final String primaryRouteSecondSectionSerialNo = "00000";
        final int lineNo = -1;

        return new Uic301G5Detail(identifier, railUnionCompiling, railUnionReceiving, period, reserved1, typeOfService,
                typeofTransaction, distributionChannel, codeIssuingOffice, reserved2, countryCode, serviceBrandCode,
                statementCurrency, statementPeriod, tariffCode, typeOfJourney, classOrCategory, amountUnitShare,
                trainNumber, coachNumber, dayOfTravel, departureLocationCountry, departureLocationStation, reserved3,
                destinationLocationRU, destinationLocationStation, typeIdentifier, numberIdentifiers,
                dialogueNumber, transactionDate, numberOfServices, adjustment, grossAmountToBeDebited,
                grossAmountToBeCredited, afterSalesPercentageFee, transactionRU, primaryRouteFirstSectionRU,
                primaryRouteFirstSectionSerialNo, passengerCatergory, serviceProvidingRUsCommissionRate,
                amountOfCommissionToBeDebitedTheServiceProvidingRU, amountOfCommissionToBeCreditedTheServiceProvidingRU,
                primaryRouteSecondSectionRU, primaryRouteSecondSectionSerialNo, lineNo);

    }

    /**
     * Creates a sample with invalid data.
     * 
     * @return Invalid instance.
     */
    Uic301G5Detail createInvalidSample() {

        final String identifier = "1411A0000";
        final String railUnionCompiling = "0087";
        final String railUnionReceiving = "1185";
        final String period = "171100";
        final String reserved1 = "0";
        final String typeOfService = "05";
        final String typeofTransaction = "1";
        final String distributionChannel = "1";
        final String codeIssuingOffice = "00000";
        final String reserved2 = "0";
        final String countryCode = "IT";
        final String serviceBrandCode = "0000";
        final String statementCurrency = "EUR";
        final String statementPeriod = "01";
        final String tariffCode = "10001";
        final String typeOfJourney = "1";
        final String classOrCategory = "002";
        final String amountUnitShare = "00003040";
        final String trainNumber = "09211";
        final String coachNumber = "000";
        final String dayOfTravel = "171223";
        final String departureLocationCountry = "1185";
        final String departureLocationStation = "00010";
        final String reserved3 = "0";
        final String destinationLocationRU = "1185";
        final String destinationLocationStation = "03000";
        final String typeIdentifier = "1";
        final String numberIdentifiers = "00870933451367";
        final String dialogueNumber = "00059";
        final String transactionDate = "171105";
        final String numberOfServices = "00002";
        final String adjustment = "0";
        final String grossAmountToBeDebited = "0000006080";
        final String grossAmountToBeCredited = "0000000000";
        final String afterSalesPercentageFee = "00";
        final String transactionRU = "0083";
        final String primaryRouteFirstSectionRU = "1185";
        final String primaryRouteFirstSectionSerialNo = "02089";
        final String passengerCatergory = "11";
        final String serviceProvidingRUsCommissionRate = "0500";
        final String amountOfCommissionToBeDebitedTheServiceProvidingRU = "0000000000";
        final String amountOfCommissionToBeCreditedTheServiceProvidingRU = "0000000304";
        final String primaryRouteSecondSectionRU = "0000";
        final String primaryRouteSecondSectionSerialNo = "x";
        final int lineNo = -1;

        return new Uic301G5Detail(identifier, railUnionCompiling, railUnionReceiving, period, reserved1, typeOfService,
                typeofTransaction, distributionChannel, codeIssuingOffice, reserved2, countryCode, serviceBrandCode,
                statementCurrency, statementPeriod, tariffCode, typeOfJourney, classOrCategory, amountUnitShare,
                trainNumber, coachNumber, dayOfTravel, departureLocationCountry, departureLocationStation, reserved3,
                destinationLocationRU, destinationLocationStation, typeIdentifier, numberIdentifiers,
                dialogueNumber, transactionDate, numberOfServices, adjustment, grossAmountToBeDebited,
                grossAmountToBeCredited, afterSalesPercentageFee, transactionRU, primaryRouteFirstSectionRU,
                primaryRouteFirstSectionSerialNo, passengerCatergory, serviceProvidingRUsCommissionRate,
                amountOfCommissionToBeDebitedTheServiceProvidingRU, amountOfCommissionToBeCreditedTheServiceProvidingRU,
                primaryRouteSecondSectionRU, primaryRouteSecondSectionSerialNo, lineNo);

    }

}
