package ch.sbb.fss.uic301.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import ch.sbb.fss.uic301.parser.SimpleXmlTagPositionParser.Listener;

/**
 * Test for {@link SimpleXmlTagPositionParser}.
 */
public class SimpleXmlTagPositionParserTest {

    @Test
    public void testParse() {

        // PREPARE
        final File xmlFile = new File("src/test/resources/uic301-documents.xml");
        final SimpleXmlTagPositionParser<Void> testee = new SimpleXmlTagPositionParser<>(xmlFile, 1024,
                FieldError.TAG.length());

        // TEST
        final List<String> result = new ArrayList<>();
        testee.parse(new Listener<Void>() {
            @Override
            public void startTagBegin(final String tagName, final int position) {
                result.add("<" + tagName + " ... @ " + position);
            }

            @Override
            public void startTagEnd(final String tagName, final int position, final boolean selfClosing) {
                if (selfClosing) {
                    result.add(" ... " + tagName + "/> @ " + position);
                } else {
                    result.add(" ... " + tagName + "> @ " + position);
                }
            }

            @Override
            public Void endTagEnd(final String tagName, final int position, final boolean selfClosing) {
                if (!selfClosing) {
                    result.add("</" + tagName + "> @ " + position);
                }
                return null;
            }
        });

        // VERIFY
        final Iterator<String> it = result.iterator();
        assertThat(it.next()).isEqualTo("<?xml ... @ 0");
        assertThat(it.next()).isEqualTo(" ... ?xml> @ 54");
        assertThat(it.next()).isEqualTo("<documents ... @ 56");
        assertThat(it.next()).isEqualTo(" ... documents> @ 82");
        assertThat(it.next()).isEqualTo("<document ... @ 85");
        assertThat(it.next()).isEqualTo(" ... document> @ 110");
        assertThat(it.next()).isEqualTo("<header ... @ 114");
        assertThat(it.next()).isEqualTo(" ... header> @ 280");
        assertThat(it.next()).isEqualTo("<field-error ... @ 285");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 354");
        assertThat(it.next()).isEqualTo("<field-error ... @ 359");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 492");
        assertThat(it.next()).isEqualTo("<field-error ... @ 497");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 586");
        assertThat(it.next()).isEqualTo("<field-error ... @ 591");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 663");
        assertThat(it.next()).isEqualTo("<field-error ... @ 668");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 740");
        assertThat(it.next()).isEqualTo("<field-error ... @ 745");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 835");
        assertThat(it.next()).isEqualTo("</header> @ 847");
        assertThat(it.next()).isEqualTo("<details ... @ 851");
        assertThat(it.next()).isEqualTo(" ... details> @ 874");
        assertThat(it.next()).isEqualTo("<detail-g4 ... @ 885");
        assertThat(it.next()).isEqualTo(" ... detail-g4> @ 1604");
        assertThat(it.next()).isEqualTo("<field-error ... @ 1622");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 1711");
        assertThat(it.next()).isEqualTo("<field-error ... @ 1729");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 1829");
        assertThat(it.next()).isEqualTo("</detail-g4> @ 1851");
        assertThat(it.next()).isEqualTo("</details> @ 1864");
        assertThat(it.next()).isEqualTo("<totals ... @ 1868");
        assertThat(it.next()).isEqualTo(" ... totals> @ 1875");
        assertThat(it.next()).isEqualTo("<total ... @ 1880");
        assertThat(it.next()).isEqualTo(" ... total> @ 2195");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2201");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2316");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2322");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2406");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2412");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2481");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2487");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2577");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2583");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2716");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2722");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2794");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2800");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2887");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2893");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 2990");
        assertThat(it.next()).isEqualTo("<field-error ... @ 2996");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 3073");
        assertThat(it.next()).isEqualTo("<field-error ... @ 3079");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 3167");
        assertThat(it.next()).isEqualTo("<field-error ... @ 3173");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 3271");
        assertThat(it.next()).isEqualTo("<field-error ... @ 3277");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 3362");
        assertThat(it.next()).isEqualTo("<field-error ... @ 3368");
        assertThat(it.next()).isEqualTo(" ... field-error/> @ 3440");
        assertThat(it.next()).isEqualTo("</total> @ 3452");
        assertThat(it.next()).isEqualTo("</totals> @ 3464");
        assertThat(it.next()).isEqualTo("</document> @ 3477");
        assertThat(it.next()).isEqualTo("</documents> @ 3490");

    }

}
