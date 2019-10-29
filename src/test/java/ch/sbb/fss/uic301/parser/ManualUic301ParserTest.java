package ch.sbb.fss.uic301.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.sbb.fss.uic301.parser.Uic301XmlIndexFile.Detail;
import ch.sbb.fss.uic301.parser.Uic301XmlIndexFile.Details;
import ch.sbb.fss.uic301.parser.Uic301XmlIndexFile.Document;
import ch.sbb.fss.uic301.parser.Uic301XmlIndexFile.Documents;
import ch.sbb.fss.uic301.parser.Uic301XmlIndexFile.Header;
import ch.sbb.fss.uic301.parser.Uic301XmlIndexFile.Total;
import ch.sbb.fss.uic301.parser.Uic301XmlIndexFile.Totals;

/**
 * Parser for a file that contains one or more UIC 301 documents.
 */
public final class ManualUic301ParserTest {

    private static String format(final BigDecimal bd) {
        return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(bd);
    }

    private static void marshal(final Uic301Documents docs, final File file) {
        try (final Writer writer = new BufferedWriter(new FileWriter(file), 1024 * 1024)) {
            final JAXBContext ctx = JAXBContext.newInstance(Uic301Documents.class);
            final Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(docs, writer);
        } catch (final IOException | JAXBException ex) {
            throw new RuntimeException("Error saving documents to XML file", ex);
        }
    }

    private static Uic301Documents unmarshal(final File file) {
        try (final Reader reader = new BufferedReader(new FileReader(file), 1024 * 1024)) {
            final JAXBContext ctx = JAXBContext.newInstance(Uic301Documents.class);
            final Unmarshaller unmarshaller = ctx.createUnmarshaller();
            return (Uic301Documents) unmarshaller.unmarshal(reader);
        } catch (final IOException | JAXBException ex) {
            throw new RuntimeException("Error reading documents from XML file", ex);
        }
    }

    public static void main(String[] args) throws Uic301Exception {

        System.out.println("Started parsing...");

        final File inputFile = new File("./data/A10000101185190400.txt");
        final File xmlFile = new File(inputFile + ".xml");

        if (!xmlFile.exists()) {

            final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

            final Uic301Parser parser = new Uic301Parser();
            final Uic301Documents docs = parser.parse(inputFile, validator);
            System.out.println("Documents Errors = " + docs.getErrorCount());
            final long startWrite = System.currentTimeMillis();
            marshal(docs, xmlFile);
            final long durationWrite = System.currentTimeMillis() - startWrite;
            System.out.println("Written XML to file (" + durationWrite + " ms)...");

            final long startRead = System.currentTimeMillis();
            final Uic301Documents copy = unmarshal(xmlFile);
            final long durationRead = System.currentTimeMillis() - startRead;
            System.out.println("Read XML from file (" + durationRead + " ms)...");

            for (final Uic301Document doc : copy.getDocuments()) {

                System.out.println(doc.getHeader().getIdentifierTypeFormatted() + " Errors = " + doc.getErrorCount());

                final Uic301Totals totals = doc.getTotals();
                for (final Uic301Total total : totals.getList()) {
                    final CalculatedDetailAmounts calculated = doc.getDetails().getAmounts()
                            .get(total.getStatementCurrencyPeriod());

                    System.out.println("STATEMENT PERIOD: " + total.getStatementCurrencyPeriod() + " [sealed="
                            + calculated.isSealed() + "]");

                    System.out.println(
                            "  GrossAmountToBeCredited: Calc=" + format(calculated.getGrossAmountToBeCredited())
                                    + ", Total=" + format(total.getGrossCreditValue()));
                    System.out
                            .println("  GrossAmountToBeDebited: Calc=" + format(calculated.getGrossAmountToBeDebited())
                                    + ", Total=" + format(total.getGrossDebitValue()));
                    System.out.println(
                            "  AmountCommissionCredited: Calc=" + format(calculated.getAmountCommissionCredited())
                                    + ", Total=" + format(total.getAmountCommissionCreditedValue()));
                    System.out.println(
                            "  AmountCommissionDebited: Calc=" + format(calculated.getAmountCommissionDebited())
                                    + ", Total=" + format(total.getAmountCommissionDebitedValue()));
                    System.out.println("  NetBalanceAmount: Calc=" + calculated.getNetBalanceAmount() + ", Total="
                            + format(total.getNetBalanceAmountValue()));
                    System.out.println("  NetBalanceType: Calc=" + calculated.getNetBalanceType() + ", Total="
                            + total.getDebitCreditBalanceType());

                }

            }

        }

        // final Uic301Documents docs = unmarshal(new
        // File("src/test/resources/uic301-documents.xml"));
        // final File smallXml = new
        // File("src/test/resources/uic301-documents-compressed.xml");
        // marshal(docs, smallXml);

        final Uic301XmlIndexFile indexFile = new Uic301XmlIndexFile(xmlFile);
        final long startReadIndex = System.currentTimeMillis();
        try {
            indexFile.open();
            final long durationReadIndex = System.currentTimeMillis() - startReadIndex;
            System.out.println("Reading XML index file (" + durationReadIndex + " ms)...");
            final Documents documents = indexFile.getDocuments();
            System.out.println(documents + ": " + indexFile.unmarshalStartTag(documents));
            for (final Document document : documents) {
                System.out.println(document + ": " + indexFile.unmarshalStartTag(document));
                final Header header = document.getHeader();
                System.out.println(header + ": " + indexFile.unmarshal(header));
                final Details details = document.getDetails();
                System.out.println(details + ": " + indexFile.unmarshalStartTag(details));
                for (final Detail detail : details) {
                    System.out.println(detail + ": " + indexFile.unmarshal(detail));
                    break;
                }
                final Totals totals = document.getTotals();
                System.out.println(totals + ": " + indexFile.unmarshal(totals));
                for (final Total total : totals) {
                    System.out.println(total + ": " + indexFile.unmarshal(total));
                }
            }
        } finally {
            indexFile.close();
        }

        System.out.println("Finished parsing...");

    }

}
