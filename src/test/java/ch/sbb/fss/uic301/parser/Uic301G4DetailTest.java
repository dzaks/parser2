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
 * Test for the {@link Uic301G4Detail} class.
 */
public class Uic301G4DetailTest {

    private final static Validator VALIDATOR = Validation
            .buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301G4Detail.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValid() {

        // PREPARE
        final String identifier = "141210000";
        final String railUnionCompiling = "0087";
        final String railUnionReceiving = "1185";
        final String period = "171100";
        final String reserved1 = "0";
        final String typeOfService = "07";
        final String typeofTransaction = "0";
        final String distributionChannel = "1";
        final String codeIssuingOffice = "00000";
        final String requestingTerminalRU = "1185";
        final String requestingTerminalNo = "0000000";
        final String statementCurrency = "EUR";
        final String statementPeriod = "01";
        final String classOrCategory = "001";
        final String unitPrice = "00012100";
        final String trainNumber = "09213";
        final String coachNumber = "000";
        final String dayOfTravel = "171124";
        final String departureLocationCountry = "0087";
        final String departureLocationStation = "71304";
        final String reserved2 = "0";
        final String destinationLocationCountry = "1185";
        final String destinationLocationStation = "00010";
        final String reserved3 = "0";
        final String referenceNumber = "00871898023142";
        final String dialogueNumber = "00000";
        final String transactionDate = "171122";
        final String numberOfServices = "00001";
        final String adjustment = "0";
        final String grossAmountToBeDebited = "0000012100";
        final String grossAmountToBeCredited = "0000000000";
        final String serviceProvidingRU = "0087";
        final String afterSalesFeeSharePercentage = "00";
        final String tariffCode = "10001";
        final String typeOfJourney = "1";
        final String primaryRouteFirstSectionRU = "0087";
        final String primaryRouteFirstSectionSerialNo = "00271";
        final String passengerCatergory = "11";
        final String amountUnitShare = "00012100";
        final String grossAmountToBeDebitedTheServiceProvidingRU = "0000000000";
        final String grossAmountToBeCreditedTheServiceProvidingRU = "0000012100";
        final String percentageCommissionRateOfServiceProvidingRU = "1000";
        final String amountOfCommissionToBeDebitedTheServiceProvidingRU = "0000001210";
        final String amountOfCommissionToBeCreditedTheServiceProvidingRU = "0000000000";
        final String reserved4 = "000";
        final String countryCode = "CH";
        final String serviceBrandCode = "0000";
        final int lineNo = -1;

        final Uic301G4Detail testee = new Uic301G4Detail(identifier,
                railUnionCompiling, railUnionReceiving, period, reserved1,
                typeOfService, typeofTransaction, distributionChannel,
                codeIssuingOffice, requestingTerminalRU, requestingTerminalNo,
                statementCurrency, statementPeriod, classOrCategory, unitPrice,
                trainNumber, coachNumber, dayOfTravel, departureLocationCountry,
                departureLocationStation, reserved2, destinationLocationCountry,
                destinationLocationStation, reserved3, referenceNumber,
                dialogueNumber, transactionDate, numberOfServices, adjustment,
                grossAmountToBeDebited, grossAmountToBeCredited,
                serviceProvidingRU, afterSalesFeeSharePercentage, tariffCode,
                typeOfJourney, primaryRouteFirstSectionRU,
                primaryRouteFirstSectionSerialNo, passengerCatergory,
                amountUnitShare, grossAmountToBeDebitedTheServiceProvidingRU,
                grossAmountToBeCreditedTheServiceProvidingRU,
                percentageCommissionRateOfServiceProvidingRU,
                amountOfCommissionToBeDebitedTheServiceProvidingRU,
                amountOfCommissionToBeCreditedTheServiceProvidingRU, reserved4,
                countryCode, serviceBrandCode, lineNo);

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(testee);

        // VERIFY
        assertThat(violations).hasSize(0);
        assertThat(testee.getIdentifier()).isEqualTo(identifier);
        assertThat(testee.getIdentifierType()).isEqualTo(Uic301Type.G4);
        assertThat(testee.getRailUnionCompiling())
                .isEqualTo(railUnionCompiling);
        assertThat(testee.getRailUnionReceiving())
                .isEqualTo(railUnionReceiving);
        assertThat(testee.getPeriod()).isEqualTo(period);
        assertThat(testee.getReserved1()).isEqualTo(reserved1);
        assertThat(testee.getTypeOfService()).isEqualTo(typeOfService);
        assertThat(testee.getTypeOfServiceValue())
                .isEqualTo(ServiceType.IRT_INTERNATIONAL);
        assertThat(testee.getTypeOfTransaction()).isEqualTo(typeofTransaction);
        assertThat(testee.getTypeOfTransactionValue())
                .isEqualTo(TransactionType.RESERVATION_SALES);
        assertThat(testee.getDistributionChannel())
                .isEqualTo(distributionChannel);
        assertThat(testee.getDistributionChannelType())
                .isEqualTo(DistributionChannel.STATION);
        assertThat(testee.getCodeIssuingOffice()).isEqualTo(codeIssuingOffice);
        assertThat(testee.getRequestingTerminalRU())
                .isEqualTo(requestingTerminalRU);
        assertThat(testee.getRequestingTerminalNo())
                .isEqualTo(requestingTerminalNo);
        assertThat(testee.getStatementCurrency()).isEqualTo(statementCurrency);
        assertThat(testee.getStatementPeriod()).isEqualTo(statementPeriod);
        assertThat(testee.getClassOrCategory()).isEqualTo(classOrCategory);
        assertThat(testee.getClassOrCategoryValue())
                .isEqualTo(ClassOrCategory.CLASS_1ST);
        assertThat(testee.getUnitPrice()).isEqualTo(unitPrice);
        assertThat(testee.getUnitPriceValue()).isEqualTo(
                BigDecimal.valueOf(121).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getTrainNumber()).isEqualTo(trainNumber);
        assertThat(testee.getCoachNumber()).isEqualTo(coachNumber);
        assertThat(testee.getDayOfTravel()).isEqualTo(dayOfTravel);
        assertThat(testee.getDepartureLocationCountry())
                .isEqualTo(departureLocationCountry);
        assertThat(testee.getDepartureLocationStation())
                .isEqualTo(departureLocationStation);
        assertThat(testee.getReserved2()).isEqualTo(reserved2);
        assertThat(testee.getDestinationLocationCountry())
                .isEqualTo(destinationLocationCountry);
        assertThat(testee.getDestinationLocationStation())
                .isEqualTo(destinationLocationStation);
        assertThat(testee.getReserved3()).isEqualTo(reserved3);
        assertThat(testee.getReferenceNumber()).isEqualTo(referenceNumber);
        assertThat(testee.getDialogueNumber()).isEqualTo(dialogueNumber);
        assertThat(testee.getTransactionDate()).isEqualTo(transactionDate);
        assertThat(testee.getNumberOfServices()).isEqualTo(numberOfServices);
        assertThat(testee.getAdjustment()).isEqualTo(adjustment);
        assertThat(testee.getGrossAmountToBeDebited())
                .isEqualTo(grossAmountToBeDebited);
        assertThat(testee.getGrossAmountToBeDebitedValue()).isEqualTo(
                BigDecimal.valueOf(121).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getGrossAmountToBeCredited())
                .isEqualTo(grossAmountToBeCredited);
        assertThat(testee.getGrossAmountToBeCreditedValue()).isEqualTo(
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getServiceProvidingRU())
                .isEqualTo(serviceProvidingRU);
        assertThat(testee.getAfterSalesFeeSharePercentage())
                .isEqualTo(afterSalesFeeSharePercentage);
        assertThat(testee.getTariffCode()).isEqualTo(tariffCode);
        assertThat(testee.getTypeOfJourney()).isEqualTo(typeOfJourney);
        assertThat(testee.getTypeOfJourneyValue())
                .isEqualTo(JourneyType.SINGLE_JOURNEY);
        assertThat(testee.getPrimaryRouteFirstSectionRU())
                .isEqualTo(primaryRouteFirstSectionRU);
        assertThat(testee.getPrimaryRouteFirstSectionSerialNo())
                .isEqualTo(primaryRouteFirstSectionSerialNo);
        assertThat(testee.getPassengerCatergory())
                .isEqualTo(passengerCatergory);
        assertThat(testee.getPassengerCatergoryType())
                .isEqualTo(PassengerCatergory.ADULT);
        assertThat(testee.getAmountUnitShare()).isEqualTo(amountUnitShare);
        assertThat(testee.getAmountUnitShareValue()).isEqualTo(
                BigDecimal.valueOf(121).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getGrossAmountToBeDebitedTheServiceProvidingRU())
                .isEqualTo(grossAmountToBeDebitedTheServiceProvidingRU);
        assertThat(testee.getGrossAmountToBeDebitedTheServiceProvidingRUValue())
                .isEqualTo(
                        BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getGrossAmountToBeCreditedTheServiceProvidingRU())
                .isEqualTo(grossAmountToBeCreditedTheServiceProvidingRU);
        assertThat(
                testee.getGrossAmountToBeCreditedTheServiceProvidingRUValue())
                        .isEqualTo(BigDecimal.valueOf(121).setScale(2,
                                BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getPercentageCommissionRateOfServiceProvidingRU())
                .isEqualTo(percentageCommissionRateOfServiceProvidingRU);
        assertThat(
                testee.getPercentageCommissionRateOfServiceProvidingRUValue())
                        .isEqualTo(BigDecimal.valueOf(10).setScale(2,
                                BigDecimal.ROUND_HALF_UP));
        assertThat(
                testee.getAmountOfCommissionToBeDebitedTheServiceProvidingRU())
                        .isEqualTo(
                                amountOfCommissionToBeDebitedTheServiceProvidingRU);
        assertThat(testee
                .getAmountOfCommissionToBeDebitedTheServiceProvidingRUValue())
                        .isEqualTo(BigDecimal.valueOf(12.1).setScale(2,
                                BigDecimal.ROUND_HALF_UP));
        assertThat(
                testee.getAmountOfCommissionToBeCreditedTheServiceProvidingRU())
                        .isEqualTo(
                                amountOfCommissionToBeCreditedTheServiceProvidingRU);
        assertThat(testee
                .getAmountOfCommissionToBeCreditedTheServiceProvidingRUValue())
                        .isEqualTo(BigDecimal.ZERO.setScale(2,
                                BigDecimal.ROUND_HALF_UP));
        assertThat(testee.getReserved4()).isEqualTo(reserved4);
        assertThat(testee.getCountryCode()).isEqualTo(countryCode);
        assertThat(testee.getServiceBrandCode()).isEqualTo(serviceBrandCode);
        assertThat(testee.getParsedLineNo()).isEqualTo(lineNo);

    }

    @Test
    public void testParse() throws Uic301Exception {

        // PREPARE
        final String line = "14121000000871185171100007010000011850000000EUR01001000121000921300017112400877130401185000100008718980231420000017112200001000000121000000000000008700100011008700271110001210000000000000000012100100000000012100000000000000CH0000";

        // TEST
        final Uic301G4Detail testee = Uic301G4Detail.parse(1, line);

        // VERIFY
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(testee);
        assertThat(violations).hasSize(0);

    }

    @Test
    public void testInvalidIdentifier() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("1411A0000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "identifier",
                "Expected one of [141210000, 144210000], but was: 1411A0000");

    }

    @Test
    public void testInvalidRailUnionCompiling() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "x", "1185", "171100",
                        "0", "07", "0", "1", "00000", "1185", "0000000", "EUR",
                        "01", "001", "00012100", "09213", "000", "171124",
                        "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionCompiling",
                "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidRailUnionReceiving() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "x", "171100",
                        "0", "07", "0", "1", "00000", "1185", "0000000", "EUR",
                        "01", "001", "00012100", "09213", "000", "171124",
                        "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionReceiving",
                "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidPeriod() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185", "x",
                        "0", "07", "0", "1", "00000", "1185", "0000000", "EUR",
                        "01", "001", "00012100", "09213", "000", "171124",
                        "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "period",
                "Expected 'YYMMPP', but was: 'x'");

    }

    @Test
    public void testInvalidReserved1() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "x", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved1",
                "Expected only 1 zero(s), but was: 'x'");

    }

    @Test
    public void testInvalidTypeOfService() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "x", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "typeOfService",
                "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTypeOfTransaction() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "x", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "typeOfTransaction",
                "Expected exactly 1 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDistributionChannel() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "x", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "distributionChannel",
                "Expected exactly 1 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidCodeIssuingOffice() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "x", "1185", "0000000",
                        "EUR", "01", "001", "00012100", "09213", "000",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "codeIssuingOffice",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidRequestingTerminalRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "x", "0000000",
                        "EUR", "01", "001", "00012100", "09213", "000",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "requestingTerminalRU",
                "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidRequestingTerminalNo() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185", "x",
                        "EUR", "01", "001", "00012100", "09213", "000",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "requestingTerminalNo",
                "Expected exactly 7 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidStatementCurrency() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "x", "01", "001", "00012100", "09213", "000",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "statementCurrency",
                "Expected only three uppercase characters for currency, but was 'x'");

    }

    @Test
    public void testInvalidStatementPeriod() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "x", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "statementPeriod",
                "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidClassOrCategory() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "x", "00012100", "09213", "000",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "classOrCategory",
                "Expected exactly 3 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidUnitPrice() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "x", "09213", "000",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "unitPrice",
                "Expected exactly 8 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTrainNumber() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "x", "000",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "trainNumber",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidCoachNumber() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213", "x",
                        "171124", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "coachNumber",
                "Expected exactly 3 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDayOfTravel() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "x", "0087", "71304", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "dayOfTravel",
                "Expected 'YYMMDD', but was: 'x'");

    }

    @Test
    public void testInvalidDepartureLocationCountry() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "x", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "departureLocationCountry",
                "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDepartureLocationStation() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "x", "0", "1185", "00010", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "departureLocationStation",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidReserved2() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "x", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved2",
                "Expected only 1 zero(s), but was: 'x'");

    }

    @Test
    public void testInvalidDestinationLocationCountry() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "x", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "destinationLocationCountry",
                "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDestinationLocationStation() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "x", "0",
                        "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "destinationLocationStation",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidReserved3() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "x", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved3",
                "Expected only 1 zero(s), but was: 'x'");

    }

    @Test
    public void testInvalidReferenceNumber() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "x", "00000", "171122", "00001", "0", "0000012100",
                        "0000000000", "0087", "00", "10001", "1", "0087",
                        "00271", "11", "00012100", "0000000000", "0000012100",
                        "1000", "0000001210", "0000000000", "000", "CH", "0000",
                        -1));

        // VERIFY
        assertSingleViolation(violations, "referenceNumber",
                "Expected exactly 14 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDialogueNumber() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "x", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "dialogueNumber",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTransactionDate() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "x", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "transactionDate",
                "Expected 'YYMMDD', but was: 'x'");

    }

    @Test
    public void testInvalidNumberOfServices() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "x", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "numberOfServices",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAdjustment() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "x",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "adjustment",
                "Expected '0' or '1', but was: 'x'");

    }

    @Test
    public void testInvalidGrossAmountToBeDebited() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "x", "0000000000", "0087", "00", "10001", "1", "0087",
                        "00271", "11", "00012100", "0000000000", "0000012100",
                        "1000", "0000001210", "0000000000", "000", "CH", "0000",
                        -1));

        // VERIFY
        assertSingleViolation(violations, "grossAmountToBeDebited",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidGrossAmountToBeCredited() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "x", "0087", "00", "10001", "1", "0087",
                        "00271", "11", "00012100", "0000000000", "0000012100",
                        "1000", "0000001210", "0000000000", "000", "CH", "0000",
                        -1));

        // VERIFY
        assertSingleViolation(violations, "grossAmountToBeCredited",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "x", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "serviceProvidingRU",
                "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidAfterSalesFeeSharePercentage() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "x", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "afterSalesFeeSharePercentage",
                "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTariffCode() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "x", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "tariffCode",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidTypeOfJourney() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "x",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "typeOfJourney",
                "Expected exactly 1 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidPrimaryRouteFirstSectionRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "x", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "primaryRouteFirstSectionRU",
                "Wrong RICS code (x)");

    }

    @Test
    public void testInvalidPrimaryRouteFirstSectionSerialNo() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "x", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "primaryRouteFirstSectionSerialNo",
                "Expected exactly 5 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidPassengerCatergory() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "x", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "passengerCatergory",
                "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountUnitShare() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "x", "0000000000", "0000012100",
                        "1000", "0000001210", "0000000000", "000", "CH", "0000",
                        -1));

        // VERIFY
        assertSingleViolation(violations, "amountUnitShare",
                "Expected exactly 8 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidGrossAmountToBeDebitedTheServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "x", "0000012100",
                        "1000", "0000001210", "0000000000", "000", "CH", "0000",
                        -1));

        // VERIFY
        assertSingleViolation(violations,
                "grossAmountToBeDebitedTheServiceProvidingRU",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidGrossAmountToBeCreditedTheServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000", "x",
                        "1000", "0000001210", "0000000000", "000", "CH", "0000",
                        -1));

        // VERIFY
        assertSingleViolation(violations,
                "grossAmountToBeCreditedTheServiceProvidingRU",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidPercentageCommissionRateOfServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "x", "0000001210", "0000000000", "000",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations,
                "percentageCommissionRateOfServiceProvidingRU",
                "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountOfCommissionToBeDebitedTheServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "x", "0000000000", "000", "CH",
                        "0000", -1));

        // VERIFY
        assertSingleViolation(violations,
                "amountOfCommissionToBeDebitedTheServiceProvidingRU",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountOfCommissionToBeCreditedTheServiceProvidingRU() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "x", "000", "CH",
                        "0000", -1));

        // VERIFY
        assertSingleViolation(violations,
                "amountOfCommissionToBeCreditedTheServiceProvidingRU",
                "Expected exactly 10 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidReserved4() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "x",
                        "CH", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved4",
                "Expected only 3 zero(s), but was: 'x'");

    }

    @Test
    public void testInvalidcountryCode() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "x", "0000", -1));

        // VERIFY
        assertSingleViolation(violations, "countryCode",
                "Expected ISO 3166 country code (e.g. 'CH') or '00', but was: 'x'");

    }

    @Test
    public void testInvalidserviceBrandCode() {

        // TEST
        final Set<ConstraintViolation<Uic301G4Detail>> violations = VALIDATOR
                .validate(new Uic301G4Detail("141210000", "0087", "1185",
                        "171100", "0", "07", "0", "1", "00000", "1185",
                        "0000000", "EUR", "01", "001", "00012100", "09213",
                        "000", "171124", "0087", "71304", "0", "1185", "00010",
                        "0", "00871898023142", "00000", "171122", "00001", "0",
                        "0000012100", "0000000000", "0087", "00", "10001", "1",
                        "0087", "00271", "11", "00012100", "0000000000",
                        "0000012100", "1000", "0000001210", "0000000000", "000",
                        "CH", "x", -1));

        // VERIFY
        assertSingleViolation(violations, "serviceBrandCode",
                "Expected exactly 4 digit(s), but was: 'x'");

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301G4Detail original = createValidSample();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301G4Detail.class);
        final Uic301G4Detail copy = JaxbUtils.unmarshal(xml, Uic301G4Detail.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301G4Detail original = createInvalidSample();
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301G4Detail.class);
        System.out.println(xml);
        final Uic301G4Detail copy = JaxbUtils.unmarshal(xml, Uic301G4Detail.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J.readAsString(this.getClass().getResource("/uic301-detail-g4.xml").toURI().toURL(),
                "utf-8", 1024);
        // TEST
        final Uic301G4Detail header = JaxbUtils.unmarshal(xml, Uic301G4Detail.class);

        // VALIDATE
        assertThat(header.getErrors()).hasSize(2);
        assertThat(header.getErrors()).containsOnly(
                new FieldError("serviceBrandCode", "Expected exactly 4 digit(s), but was: 'x'"),
                new FieldError("identifier", "Expected one of [141210000, 144210000], but was: 1411A0000"));

    }

    private void assertSingleViolation(
            final Set<ConstraintViolation<Uic301G4Detail>> violations,
            final String property, final String message) {
        assertThat(violations).hasSize(1);
        final ConstraintViolation<Uic301G4Detail> violation = violations
                .iterator().next();
        assertThat(violation.getMessage()).isEqualTo(message);
        assertThat(violation.getPropertyPath().iterator().next().toString())
                .isEqualTo(property);
    }

    /**
     * Creates a sample with valid data.
     * 
     * @return Valid instance.
     */
    static Uic301G4Detail createValidSample() {

        final String identifier = "141210000";
        final String railUnionCompiling = "0087";
        final String railUnionReceiving = "1185";
        final String period = "171100";
        final String reserved1 = "0";
        final String typeOfService = "07";
        final String typeofTransaction = "0";
        final String distributionChannel = "1";
        final String codeIssuingOffice = "00000";
        final String requestingTerminalRU = "1185";
        final String requestingTerminalNo = "0000000";
        final String statementCurrency = "EUR";
        final String statementPeriod = "01";
        final String classOrCategory = "001";
        final String unitPrice = "00012100";
        final String trainNumber = "09213";
        final String coachNumber = "000";
        final String dayOfTravel = "171124";
        final String departureLocationCountry = "0087";
        final String departureLocationStation = "71304";
        final String reserved2 = "0";
        final String destinationLocationCountry = "1185";
        final String destinationLocationStation = "00010";
        final String reserved3 = "0";
        final String referenceNumber = "00871898023142";
        final String dialogueNumber = "00000";
        final String transactionDate = "171122";
        final String numberOfServices = "00001";
        final String adjustment = "0";
        final String grossAmountToBeDebited = "0000012100";
        final String grossAmountToBeCredited = "0000000000";
        final String serviceProvidingRU = "0087";
        final String afterSalesFeeSharePercentage = "00";
        final String tariffCode = "10001";
        final String typeOfJourney = "1";
        final String primaryRouteFirstSectionRU = "0087";
        final String primaryRouteFirstSectionSerialNo = "00271";
        final String passengerCatergory = "11";
        final String amountUnitShare = "00012100";
        final String grossAmountToBeDebitedTheServiceProvidingRU = "0000000000";
        final String grossAmountToBeCreditedTheServiceProvidingRU = "0000012100";
        final String percentageCommissionRateOfServiceProvidingRU = "1000";
        final String amountOfCommissionToBeDebitedTheServiceProvidingRU = "0000001210";
        final String amountOfCommissionToBeCreditedTheServiceProvidingRU = "0000000000";
        final String reserved4 = "000";
        final String countryCode = "CH";
        final String serviceBrandCode = "0000";
        final int lineNo = -1;

        return new Uic301G4Detail(identifier,
                railUnionCompiling, railUnionReceiving, period, reserved1,
                typeOfService, typeofTransaction, distributionChannel,
                codeIssuingOffice, requestingTerminalRU, requestingTerminalNo,
                statementCurrency, statementPeriod, classOrCategory, unitPrice,
                trainNumber, coachNumber, dayOfTravel, departureLocationCountry,
                departureLocationStation, reserved2, destinationLocationCountry,
                destinationLocationStation, reserved3, referenceNumber,
                dialogueNumber, transactionDate, numberOfServices, adjustment,
                grossAmountToBeDebited, grossAmountToBeCredited,
                serviceProvidingRU, afterSalesFeeSharePercentage, tariffCode,
                typeOfJourney, primaryRouteFirstSectionRU,
                primaryRouteFirstSectionSerialNo, passengerCatergory,
                amountUnitShare, grossAmountToBeDebitedTheServiceProvidingRU,
                grossAmountToBeCreditedTheServiceProvidingRU,
                percentageCommissionRateOfServiceProvidingRU,
                amountOfCommissionToBeDebitedTheServiceProvidingRU,
                amountOfCommissionToBeCreditedTheServiceProvidingRU, reserved4,
                countryCode, serviceBrandCode, lineNo);

    }

    /**
     * Creates a sample with invalid data.
     * 
     * @return Invalid instance.
     */
    static Uic301G4Detail createInvalidSample() {

        final String identifier = "1411A0000";
        final String railUnionCompiling = "0087";
        final String railUnionReceiving = "1185";
        final String period = "171100";
        final String reserved1 = "0";
        final String typeOfService = "07";
        final String typeofTransaction = "0";
        final String distributionChannel = "1";
        final String codeIssuingOffice = "00000";
        final String requestingTerminalRU = "1185";
        final String requestingTerminalNo = "0000000";
        final String statementCurrency = "EUR";
        final String statementPeriod = "01";
        final String classOrCategory = "001";
        final String unitPrice = "00012100";
        final String trainNumber = "09213";
        final String coachNumber = "000";
        final String dayOfTravel = "171124";
        final String departureLocationCountry = "0087";
        final String departureLocationStation = "71304";
        final String reserved2 = "0";
        final String destinationLocationCountry = "1185";
        final String destinationLocationStation = "00010";
        final String reserved3 = "0";
        final String referenceNumber = "00871898023142";
        final String dialogueNumber = "00000";
        final String transactionDate = "171122";
        final String numberOfServices = "00001";
        final String adjustment = "0";
        final String grossAmountToBeDebited = "0000012100";
        final String grossAmountToBeCredited = "0000000000";
        final String serviceProvidingRU = "0087";
        final String afterSalesFeeSharePercentage = "00";
        final String tariffCode = "10001";
        final String typeOfJourney = "1";
        final String primaryRouteFirstSectionRU = "0087";
        final String primaryRouteFirstSectionSerialNo = "00271";
        final String passengerCatergory = "11";
        final String amountUnitShare = "00012100";
        final String grossAmountToBeDebitedTheServiceProvidingRU = "0000000000";
        final String grossAmountToBeCreditedTheServiceProvidingRU = "0000012100";
        final String percentageCommissionRateOfServiceProvidingRU = "1000";
        final String amountOfCommissionToBeDebitedTheServiceProvidingRU = "0000001210";
        final String amountOfCommissionToBeCreditedTheServiceProvidingRU = "0000000000";
        final String reserved4 = "000";
        final String countryCode = "CH";
        final String serviceBrandCode = "x";
        final int lineNo = -1;

        return new Uic301G4Detail(identifier,
                railUnionCompiling, railUnionReceiving, period, reserved1,
                typeOfService, typeofTransaction, distributionChannel,
                codeIssuingOffice, requestingTerminalRU, requestingTerminalNo,
                statementCurrency, statementPeriod, classOrCategory, unitPrice,
                trainNumber, coachNumber, dayOfTravel, departureLocationCountry,
                departureLocationStation, reserved2, destinationLocationCountry,
                destinationLocationStation, reserved3, referenceNumber,
                dialogueNumber, transactionDate, numberOfServices, adjustment,
                grossAmountToBeDebited, grossAmountToBeCredited,
                serviceProvidingRU, afterSalesFeeSharePercentage, tariffCode,
                typeOfJourney, primaryRouteFirstSectionRU,
                primaryRouteFirstSectionSerialNo, passengerCatergory,
                amountUnitShare, grossAmountToBeDebitedTheServiceProvidingRU,
                grossAmountToBeCreditedTheServiceProvidingRU,
                percentageCommissionRateOfServiceProvidingRU,
                amountOfCommissionToBeDebitedTheServiceProvidingRU,
                amountOfCommissionToBeCreditedTheServiceProvidingRU, reserved4,
                countryCode, serviceBrandCode, lineNo);

    }

}
