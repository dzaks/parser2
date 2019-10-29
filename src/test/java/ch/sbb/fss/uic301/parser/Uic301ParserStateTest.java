package ch.sbb.fss.uic301.parser;

import static ch.sbb.fss.uic301.parser.Uic301ParserState.DETAIL;
import static ch.sbb.fss.uic301.parser.Uic301ParserState.HEADER;
import static ch.sbb.fss.uic301.parser.Uic301ParserState.INIT;
import static ch.sbb.fss.uic301.parser.Uic301ParserState.TOTAL;
import static ch.sbb.fss.uic301.parser.Uic301ParserState.verifyTransition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test for the {@link Uic301ParserState} class.
 */
public class Uic301ParserStateTest {

    @Test
    public void testVerifyInitValidTransitions() throws Uic301Exception {
        verifyTransition(1, INIT, HEADER);
    }

    @Test
    public void testVerifyInitInvalidTransitions() {
        try {
            verifyTransition(1, INIT, DETAIL);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is INIT and expected next is HEADER, but was: DETAIL [Line # 1]");
        }
        try {
            verifyTransition(1, INIT, TOTAL);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is INIT and expected next is HEADER, but was: TOTAL [Line # 1]");
        }
    }

    @Test
    public void testVerifyHeaderValidTransitions() throws Uic301Exception {
        verifyTransition(1, HEADER, DETAIL);
    }

    @Test
    public void testVerifyHeaderInvalidTransitions() {
        try {
            verifyTransition(1, HEADER, INIT);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is HEADER and expected next is DETAIL, but was: INIT [Line # 1]");
        }
        try {
            verifyTransition(1, HEADER, TOTAL);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is HEADER and expected next is DETAIL, but was: TOTAL [Line # 1]");
        }
    }

    @Test
    public void testVerifyDetailValidTransitions() throws Uic301Exception {
        verifyTransition(1, DETAIL, TOTAL);
        verifyTransition(1, DETAIL, DETAIL);
    }

    @Test
    public void testVerifyDetailInvalidTransitions() {
        try {
            verifyTransition(1, DETAIL, INIT);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is DETAIL and expected next is DETAIL or TOTAL, but was: INIT [Line # 1]");
        }
        try {
            verifyTransition(1, DETAIL, HEADER);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is DETAIL and expected next is DETAIL or TOTAL, but was: HEADER [Line # 1]");
        }
    }

    @Test
    public void testVerifyTotalValidTransitions() throws Uic301Exception {
        verifyTransition(1, TOTAL, TOTAL);
        verifyTransition(1, TOTAL, HEADER);
    }

    @Test
    public void testVerifyTotalInvalidTransitions() {
        try {
            verifyTransition(1, TOTAL, INIT);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is TOTAL and expected next is HEADER or TOTAL, but was: INIT [Line # 1]");
        }
        try {
            verifyTransition(1, TOTAL, DETAIL);
            fail();
        } catch (final Uic301Exception ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "State is TOTAL and expected next is HEADER or TOTAL, but was: DETAIL [Line # 1]");
        }
    }

}
