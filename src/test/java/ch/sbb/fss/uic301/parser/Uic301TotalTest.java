package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

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
 * Test for the {@link Uic301Total} class.
 */
public class Uic301TotalTest {

    private final static Validator VALIDATOR = Validation
            .buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Uic301Total.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testValid() {

        // PREPARE
        final Uic301Total testee = createValidSample();

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(testee);

        // VERIFY
        assertThat(violations).hasSize(0);

    }

    @Test
    public void testInvalidIdentifier() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("1411A0000", "0087", "1185", "171100",
                        "0", "EUR", "01", "000462312415", "000052549130",
                        "00004985964", "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "identifier",
                "Expected one of [141310000, 144310000, 142310000, 145310000, 143310000], but was: 1411A0000");

    }

    @Test
    public void testInvalidRailUnionCompiling() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0X87", "1185", "171100",
                        "0", "EUR", "01", "000462312415", "000052549130",
                        "00004985964", "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionCompiling",
                "Wrong RICS code (0X87)");

    }

    @Test
    public void testInvalidRailUnionReceiving() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "11850",
                        "171100", "0", "EUR", "01", "000462312415",
                        "000052549130", "00004985964", "00043878516", "1",
                        "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "railUnionReceiving",
                "Wrong RICS code (11850)");

    }

    @Test
    public void testInvalidPeriod() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "x", "0",
                        "EUR", "01", "000462312415", "000052549130",
                        "00004985964", "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "period",
                "Expected 'YYMMPP', but was: 'x'");

    }

    @Test
    public void testInvalidReserved() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "x", "EUR", "01", "000462312415", "000052549130",
                        "00004985964", "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "reserved",
                "Expected only 1 zero(s), but was: 'x'");

    }

    @Test
    public void testInvalidStatementCurrency() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "abc", "01", "000462312415", "000052549130",
                        "00004985964", "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "statementCurrency",
                "Expected only three uppercase characters for currency, but was 'abc'");

    }

    @Test
    public void testInvalidStatementPeriod() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "EUR", "x", "000462312415", "000052549130",
                        "00004985964", "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "statementPeriod",
                "Expected exactly 2 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidGrossDebit() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "EUR", "01", "x", "000052549130", "00004985964",
                        "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "grossDebit",
                "Expected exactly 12 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidGrossCredit() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "EUR", "01", "000462312415", "x", "00004985964",
                        "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "grossCredit",
                "Expected exactly 12 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountCommissionDebited() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "EUR", "01", "000462312415", "000052549130", "x",
                        "00043878516", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "amountCommissionDebited",
                "Expected exactly 11 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidAmountCommissionCredited() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "EUR", "01", "000462312415", "000052549130",
                        "00004985964", "x", "1", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "amountCommissionCredited",
                "Expected exactly 11 digit(s), but was: 'x'");

    }

    @Test
    public void testInvalidDebitCreditBalance() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "EUR", "01", "000462312415", "000052549130",
                        "00004985964", "00043878516", "x", "000370870733", -1));

        // VERIFY
        assertSingleViolation(violations, "debitCreditBalance",
                "Only '1' or '2' allowed, but was: 'x'");

    }

    @Test
    public void testInvalidNetBalanceAmount() {

        // TEST
        final Set<ConstraintViolation<Uic301Total>> violations = VALIDATOR
                .validate(new Uic301Total("141310000", "0087", "1185", "171100",
                        "0", "EUR", "01", "000462312415", "000052549130",
                        "00004985964", "00043878516", "1", "x", -1));

        // VERIFY
        assertSingleViolation(violations, "netBalanceAmount",
                "Expected exactly 12 digit(s), but was: 'x'");

    }

    @Test
    public void testMarshalUnmarshalValid() {

        // PREPARE
        final Uic301Total original = createValidSample();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Total.class);
        final Uic301Total copy = JaxbUtils.unmarshal(xml, Uic301Total.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testMarshalUnmarshalInvalid() {

        // PREPARE
        final Uic301Total original = createInvalidSample();
        original.validate(VALIDATOR);
        original.seal();

        // TEST
        final String xml = JaxbUtils.marshal(original, Uic301Total.class);
        final Uic301Total copy = JaxbUtils.unmarshal(xml, Uic301Total.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);

    }

    @Test
    public void testUnmarshalWithErrors() throws Exception {

        // PREPARE
        final String xml = Utils4J
                .readAsString(this.getClass().getResource("/uic301-total.xml").toURI().toURL(), "utf-8", 1024);
        // TEST
        final Uic301Total testee = JaxbUtils.unmarshal(xml, Uic301Total.class);

        // VALIDATE
        assertThat(testee.getErrors()).hasSize(13);
        assertThat(testee.getErrors()).contains(new FieldError("grossCredit", "Expected exactly 12 digit(s), but was: 'f'"));
        try {
            testee.validate(VALIDATOR);
            fail();
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo("The class is sealed. No more changes are allowed.");
        }

    }

    private void assertSingleViolation(
            final Set<ConstraintViolation<Uic301Total>> violations,
            final String property, final String message) {
        assertThat(violations).hasSize(1);
        final ConstraintViolation<Uic301Total> violation = violations.iterator()
                .next();
        assertThat(violation.getMessage()).isEqualTo(message);
        assertThat(violation.getPropertyPath().iterator().next().toString())
                .isEqualTo(property);
    }

    /**
     * Creates a sample with valid data.
     * 
     * @return Valid instance.
     */
    static Uic301Total createValidSample() {
        final String identifier = "141310000"; // G4
        final String railUnionCompiling = "0087"; // SNCF
        final String railUnionReceiving = "1185"; // SBB
        final String period = "171100"; // 17 Nov
        final String reserved1 = "0";
        final String statementCurrency = "EUR";
        final String statementPeriod = "01";
        final String grossDebit = "000462312415";
        final String grossCredit = "000052549130";
        final String amountCommissionDebited = "00004985964";
        final String amountCommissionCredited = "00043878516";
        final String debitCreditBalance = "1";
        final String netBalanceAmount = "000370870733";

        final Uic301Total testee = new Uic301Total(identifier,
                railUnionCompiling, railUnionReceiving, period, reserved1,
                statementCurrency, statementPeriod, grossDebit, grossCredit,
                amountCommissionDebited, amountCommissionCredited,
                debitCreditBalance, netBalanceAmount, -1);
        return testee;
    }

    /**
     * Creates a sample with invalid data.
     * 
     * @return Invalid instance.
     */
    static Uic301Total createInvalidSample() {
        final String identifier = "1411A0000";
        final String railUnionCompiling = "0X87";
        final String railUnionReceiving = "1X85";
        final String period = "a";
        final String reserved1 = "b";
        final String statementCurrency = "c";
        final String statementPeriod = "d";
        final String grossDebit = "e";
        final String grossCredit = "f";
        final String amountCommissionDebited = "g";
        final String amountCommissionCredited = "h";
        final String debitCreditBalance = "i";
        final String netBalanceAmount = "j";

        final Uic301Total testee = new Uic301Total(identifier,
                railUnionCompiling, railUnionReceiving, period, reserved1,
                statementCurrency, statementPeriod, grossDebit, grossCredit,
                amountCommissionDebited, amountCommissionCredited,
                debitCreditBalance, netBalanceAmount, -1);
        return testee;
    }

}
