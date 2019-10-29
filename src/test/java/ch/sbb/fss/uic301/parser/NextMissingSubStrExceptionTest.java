package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Test for {@link NextMissingSubStrException}.
 */
public class NextMissingSubStrExceptionTest {

    @Test
    public void testConstruction() {

        // PREPARE
        int pos = 10;
        int len = 20;
        String name = "user";
        String trace = "abc";
        String line = "xyz";

        // TEST
        final NextMissingSubStrException testee = new NextMissingSubStrException(
                pos, len, name, trace, line);

        // VERIFY
        assertThat(testee.getMessage()).isEqualTo(
                "Cannot return 20 characters for field 'user' starting at position 10: trace=[abc], line='xyz'");
        assertThat(testee.getPos()).isEqualTo(pos);
        assertThat(testee.getLen()).isEqualTo(len);
        assertThat(testee.getName()).isEqualTo(name);
        assertThat(testee.getTrace()).isEqualTo(trace);
        assertThat(testee.getLine()).isEqualTo(line);

    }

}
