package ch.sbb.fss.uic301.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.validation.Validator;

import org.fuin.utils4j.Utils4J;

/**
 * Parser for a file that contains one or more UIC 301 documents.
 */
public final class Uic301Parser {

    /**
     * Parses an UIC 301 file and validates it.
     * 
     * @param inputFile
     *            Text file with one or more UIC 301 documents to parse.
     * @param validator
     *            Validator to use.
     * 
     * @return Parsed data structure and errors.
     * 
     * @throws Uic301Exception
     *             The file couldn't be parsed.
     */
    public final Uic301Documents parse(final File inputFile, final Validator validator) throws Uic301Exception {

    	Utils4J.checkValidFile(inputFile);
    	Utils4J.checkNotNull("validator", validator);
    	
        final Uic301Documents documents = new Uic301Documents();
        String line = null;
        try {
            int count = 0;
            try (final LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(inputFile)))) {
                while ((line = lnr.readLine()) != null) {
                    final String trimmed = line.trim();
                    if (trimmed.length() > 0) {
                        documents.parse(++count, trimmed);
                    }
                }
            }
            documents.validate(validator);
            documents.seal();
        } catch (final IOException ex) {
            throw new RuntimeException("Failed to read line: '" + line + "'", ex);
        }
        return documents;

    }

}
