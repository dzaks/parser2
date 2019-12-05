package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;

/**
 * Tests specific for the UIC301 G5 format.
 */
public class Uic301G5Test {

	private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	public void testBahn55() throws Uic301Exception {

		// PREPARE
		final File inputFile = new File("src/test/resources/BAHN0055.TXT");
		final Uic301Parser parser = new Uic301Parser();

		// TEST
		final Uic301Documents documents = parser.parse(inputFile, VALIDATOR);

		// VERIFY
		assertThat(documents.getErrorCount()).isEqualTo(3204);
		final Uic301Details details = documents.getDocuments().get(0).getDetails();
		assertThat(details.getList()).hasSize(1602);
		final Uic301Detail detail = details.getList().get(0);
		assertThat(detail).isInstanceOf(Uic301G5Detail.class);
		final Uic301G5Detail g5 = (Uic301G5Detail) detail;
		assertThat(g5.getErrors()).containsOnly(new FieldError("_37-1", "Wrong RICS code (    )"),
				new FieldError("_37-2", "Expected exactly 5 digit(s), but was: '     '"));

	}

}
