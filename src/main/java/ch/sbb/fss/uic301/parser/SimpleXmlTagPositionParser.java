package ch.sbb.fss.uic301.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

/**
 * Simple XML parser that locates start/end tag positions in an XML file. The
 * normal parsers strip white spaces which makes it impossible to have the exact
 * positions where a tag starts and ends. This class returns in contrast exact
 * file positions.
 * 
 * @param <TYPE>
 *            Base type of all parsed elements (if available).
 */
public class SimpleXmlTagPositionParser<TYPE> {

    private final File xmlFile;

    private final int bufSize;

    private final int maxTagName;

    /**
     * Constructor with all mandatory data.
     * 
     * @param xmlFile
     *            XML file to parse.
     * @param bufSize
     *            Buffer size to use for reading.
     * @param maxTagName
     *            Maximal length of a tag name.
     */
    public SimpleXmlTagPositionParser(final File xmlFile, final int bufSize, final int maxTagName) {
        super();
        this.xmlFile = xmlFile;
        this.bufSize = bufSize;
        this.maxTagName = maxTagName + 2;
    }

    /**
     * Parses the file and notifies the listener about starting and ending tags.
     * 
     * @param listener
     *            Listener to notify.
     * 
     * @return Parsed root element or {@literal null} if not provided by the
     *         listener.
     */
    public TYPE parse(final Listener<TYPE> listener) {
        try (final PushbackReader reader = new PushbackReader(new BufferedReader(new FileReader(xmlFile), bufSize),
                maxTagName)) {

            TYPE root = null;

            int pos = 0;
            String startTagName = null;
            String endTagName = null;
            int lastCh = -1;
            int ch = -1;
            while ((ch = reader.read()) != -1) {
                if (ch == '<') {
                    final char[] buf = new char[maxTagName];
                    final int len = reader.read(buf);
                    final String str = String.valueOf(buf, 0, len);
                    reader.unread(buf, 0, len);
                    if (str.startsWith("/")) {
                        // End tag
                        final int p = str.indexOf('>');
                        if (p < 0) {
                            throw new IllegalStateException("Wasn't able to find '>' in '" + str + "'");
                        }
                        endTagName = str.substring(1, p);
                    } else {
                        // Start tag
                        int p = str.indexOf('>');
                        if (p < 0) {
                            p = str.indexOf(' ');
                            if (p < 0) {
                                throw new IllegalStateException(
                                        "Wasn't able to find the ' ' or '>' after the tag name: '" + str + "'");
                            }
                        }
                        startTagName = str.substring(0, p);
                        try {
                            listener.startTagBegin(startTagName, pos);
                        } catch (final RuntimeException ex) {
                            throw new RuntimeException(
                                    "Listener failed on: startTagName='" + startTagName + "', pos=" + pos, ex);
                        }
                    }
                } else if (ch == '>') {
                    final boolean selfClosing = (lastCh == '/');
                    if (startTagName == null) {
                        // End of end tag
                        if (endTagName == null) {
                            throw new IllegalStateException(
                                    "Neither startTagName nor endTagname set when arriving at '>'");
                        } else {
                            try {
                                root = listener.endTagEnd(endTagName, pos, false);
                            } catch (final RuntimeException ex) {
                                throw new RuntimeException("Listener failed on: endTagName='" + endTagName + "', pos="
                                        + pos + ", selfClosing=" + selfClosing, ex);
                            }
                            endTagName = null;
                        }
                    } else {
                        try {
                            listener.startTagEnd(startTagName, pos, selfClosing);
                        } catch (final RuntimeException ex) {
                            throw new RuntimeException("Listener failed on: startTagEnd='" + startTagName + "', pos="
                                    + pos + ", selfClosing=" + selfClosing, ex);
                        }
                        if (lastCh == '/') {
                            try {
                                root = listener.endTagEnd(startTagName, pos, selfClosing);
                            } catch (final RuntimeException ex) {
                                throw new RuntimeException("Listener failed on: startTagName='" + startTagName + "', pos="
                                        + pos + ", selfClosing=" + selfClosing, ex);
                            }
                        }
                        startTagName = null;
                    }
                }
                pos++;
                lastCh = ch;
            }

            return root;

        } catch (final IOException ex) {
            throw new RuntimeException("Failed to parse " + xmlFile, ex);
        }
    }

    /**
     * Listens to start/end tag events.
     *
     * @param <TYPE>
     *            Base type of all parsed elements.
     */
    public static interface Listener<TYPE> {

        /**
         * A start tag begins like '&lt;document '
         * 
         * @param tagName
         *            Name of the tag (in above example case 'document')
         * @param position
         *            Position of the '&lt;' in the file.
         */
        public void startTagBegin(String tagName, int position);

        /**
         * A start tag ended like '&lt;document attr="a"&gt;'
         * 
         * @param tagName
         *            Name of the tag (in above example case 'document')
         * @param position
         *            Position of the '&gt;' in the file.
         * @param selfClosing
         *            If the tag is "self closing' (like &lt;abc/&gt;).
         */
        public void startTagEnd(String tagName, int position, boolean selfClosing);

        /**
         * An end tag ended like '&lt;/document&gt;'
         * 
         * @param tagName
         *            Name of the tag (in above example case 'document')
         * @param position
         *            Position of the '&gt;' in the file.
         * @param selfClosing
         *            If the tag is "self closing' (like &lt;abc/&gt;).
         * 
         * @return Parsed object or {@literal null}. The last object will be returned by
         *         the parser as root object, if available.
         */
        public TYPE endTagEnd(String tagName, int position, boolean selfClosing);

    }

}
