package ch.sbb.fss.uic301.parser;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.sbb.fss.uic301.parser.SimpleXmlTagPositionParser.Listener;

/**
 * Provides fast access to parts of a large UIC301 XML file. Reading a big XML
 * file (~130mb) using JAX-B takes around 8000ms (8 seconds). Reading the
 * corresponding index file (~4mb) is only ~400ms. This is achieved by building
 * an index file if it does not exist. This means you can safely delete the
 * ".index" file at any time in case of deserialization problems and it will be
 * re-created on next read.<br>
 * <br>
 * <b>WARNING</b>: The XML should not contain whitespaces (like line feeds),
 * because the SAX Parser will drop those and the index will not be correct.
 * 
 */
public final class Uic301XmlIndexFile implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(Uic301XmlIndexFile.class);

    private final File xmlFile;

    private final File indexFile;

    private RandomAccessFile file;

    private Documents documents;

    private boolean open;

    /**
     * Constructor with UIC301 G4/G5 XML source file.
     * 
     * @param xmlFile
     *            File to index.
     */
    public Uic301XmlIndexFile(final File xmlFile) {
        super();
        this.xmlFile = xmlFile;
        this.indexFile = new File(xmlFile + ".index");
    }

    public boolean exists() {
        return indexFile.exists();
    }

    public void open() {
        open = true;
        if (exists()) {
            try {
                documents = Utils4J.deserialize(FileUtils.readFileToByteArray(indexFile));
            } catch (final Exception ex) {
                LOG.error("Failed to read index file: " + indexFile, ex);
                documents = parse(indexFile, xmlFile);
            }
        } else {
            documents = parse(indexFile, xmlFile);
        }
        try {
            file = new RandomAccessFile(xmlFile, "r");
        } catch (final FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Documents getDocuments() {
        ensureOpen();
        return documents;
    }

    public String read(final Tag<?> tag) {
        ensureOpen();
        return tag.read(file);
    }

    public String readStartTag(final Tag<?> tag) {
        ensureOpen();
        return tag.readStartTag(file);
    }

    public <T> T unmarshalStartTag(final Tag<T> tag) {
        ensureOpen();
        return tag.unmarshalStartTag(file);
    }

    public <T> T unmarshal(final Tag<T> tag) {
        ensureOpen();
        return tag.unmarshal(file);
    }

    private void ensureOpen() {
        if (!open) {
            throw new IllegalStateException("Please call 'open()' before using other methods");
        }
    }

    @Override
    public void close() {
        open = false;
        try {
            if (file != null) {
                file.close();
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return "Uic301XmlIndexFile [xmlFile=" + xmlFile + ", indexFile=" + indexFile + "]";
    }

    private static Documents parse(final File indexFile, final File xmlFile) {

        final SimpleXmlTagPositionParser<Tag<?>> parser = new SimpleXmlTagPositionParser<>(xmlFile, 1024,
                FieldError.TAG.length());

        final Stack<Tag<?>> stack = new Stack<>();

        final Listener<Tag<?>> listener = new Listener<Tag<?>>() {

            @Override
            public void startTagBegin(final String tagName, final int position) {
                if (tagName.equals("?xml")) {
                    return;
                }
                if (stack.isEmpty()) {
                    stack.push(new Documents(position));
                } else {
                    Tag<?> current = stack.peek();
                    if (!current.parentOf(tagName)) {
                        stack.pop();
                        current = stack.peek();
                    }
                    final Tag<?> child = current.addChild(tagName, position);
                    stack.push(child);
                }
            }

            @Override
            public void startTagEnd(final String tagName, final int position, final boolean selfClosing) {
                if (tagName.equals("?xml")) {
                    return;
                }
                Tag<?> current = stack.peek();
                current.setEndOfStartTag(position, selfClosing);
            }

            @Override
            public Tag<?> endTagEnd(final String tagName, final int position, final boolean selfClosing) {
                if (tagName.equals("?xml")) {
                    return null;
                }
                final Tag<?> tag = stack.pop();
                tag.setEndOfEndTag(position);
                return tag;
            }

        };

        final Documents documents = (Documents) parser.parse(listener);
        try {
            FileUtils.writeByteArrayToFile(indexFile, Utils4J.serialize(documents));
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
        return documents;
    }

    public static final class Documents extends Tag<Uic301Documents> implements Iterable<Document> {

        private static final long serialVersionUID = 1L;

        private List<Document> childs;

        public Documents(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { Uic301Documents.class };
        }

        public List<Document> getChilds() {
            return childs;
        }

        @Override
        public boolean parentOf(final String localName) {
            return Uic301Document.TAG.equals(localName);
        }

        @Override
        public Document addChild(final String localName, final int beginOfStartTag) {
            if (!parentOf(localName)) {
                throw new IllegalStateException("No child: " + localName);
            }
            final Document doc = new Document(beginOfStartTag);
            if (childs == null) {
                childs = new ArrayList<>();
            }
            childs.add(doc);
            return doc;
        }

        @Override
        public Iterator<Document> iterator() {
            return childs.iterator();
        }

    }

    public static final class Document extends FieldErrorParentTag<Uic301Document> {

        private static final long serialVersionUID = 1L;

        private Header header;

        private Details details;

        private Totals totals;

        public Document(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { Uic301Document.class };
        }

        public Header getHeader() {
            return header;
        }

        public Details getDetails() {
            return details;
        }

        public Totals getTotals() {
            return totals;
        }

        @Override
        public boolean parentOf(final String localName) {
            return Uic301Header.TAG.equals(localName) || Uic301Details.TAG.equals(localName)
                    || Uic301Totals.TAG.equals(localName) || FieldError.TAG.equals(localName);
        }

        @Override
        public Tag<?> addChild(final String localName, final int beginOfStartTag) {
            if (Uic301Header.TAG.equals(localName)) {
                header = new Header(beginOfStartTag);
                return header;
            } else if (Uic301Details.TAG.equals(localName)) {
                details = new Details(beginOfStartTag);
                return details;
            } else if (Uic301Totals.TAG.equals(localName)) {
                totals = new Totals(beginOfStartTag);
                return totals;
            } else if (FieldError.TAG.equals(localName)) {
                return addFieldErrorInfo(beginOfStartTag);
            } else {
                throw new IllegalStateException("No child: " + localName);
            }
        }

    }

    public static final class Header extends FieldErrorParentTag<Uic301Header> {

        private static final long serialVersionUID = 1L;

        public Header(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { Uic301Header.class };
        }

        @Override
        public boolean parentOf(final String localName) {
            return FieldError.TAG.equals(localName);
        }

        @Override
        public FieldErrorInfo addChild(final String localName, final int beginOfStartTag) {
            if (!parentOf(localName)) {
                throw new IllegalStateException("No child: " + localName);
            }
            return addFieldErrorInfo(beginOfStartTag);
        }

    }

    public static final class Details extends Tag<Uic301Details> implements Iterable<Detail> {

        private static final long serialVersionUID = 1L;

        private List<Detail> childs;

        public Details(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { Uic301Details.class };
        }

        public List<Detail> getChilds() {
            return childs;
        }

        @Override
        public boolean parentOf(final String localName) {
            return Uic301G4Detail.TAG.equals(localName) || Uic301G5Detail.TAG.equals(localName);
        }

        @Override
        public Detail addChild(final String localName, final int beginOfStartTag) {
            if (!parentOf(localName)) {
                throw new IllegalStateException("No child: " + localName);
            }
            final Detail detail = new Detail(beginOfStartTag);
            if (childs == null) {
                childs = new ArrayList<>();
            }
            childs.add(detail);
            return detail;
        }

        @Override
        public Iterator<Detail> iterator() {
            return childs.iterator();
        }

    }

    public static final class Detail extends FieldErrorParentTag<Uic301Detail> {

        private static final long serialVersionUID = 1L;

        public Detail(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { Uic301G4Detail.class, Uic301G5Detail.class };
        }

        @Override
        public boolean parentOf(final String localName) {
            return FieldError.TAG.equals(localName);
        }

        @Override
        public FieldErrorInfo addChild(final String localName, final int beginOfStartTag) {
            if (!parentOf(localName)) {
                throw new IllegalStateException("No child: " + localName);
            }
            return addFieldErrorInfo(beginOfStartTag);
        }

    }

    public static final class Totals extends Tag<Uic301Totals> implements Iterable<Total> {

        private static final long serialVersionUID = 1L;

        private List<Total> childs;

        public Totals(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { Uic301Totals.class };
        }

        public List<Total> getChilds() {
            return childs;
        }

        @Override
        public boolean parentOf(final String localName) {
            return Uic301Total.TAG.equals(localName);
        }

        @Override
        public Total addChild(final String localName, final int beginOfStartTag) {
            if (!parentOf(localName)) {
                throw new IllegalStateException("No child: " + localName);
            }
            final Total total = new Total(beginOfStartTag);
            if (childs == null) {
                childs = new ArrayList<>();
            }
            childs.add(total);
            return total;
        }

        @Override
        public Iterator<Total> iterator() {
            return childs.iterator();
        }

    }

    public static final class Total extends FieldErrorParentTag<Uic301Total> {

        private static final long serialVersionUID = 1L;

        public Total(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { Uic301Total.class };
        }

        @Override
        public boolean parentOf(final String localName) {
            return FieldError.TAG.equals(localName);
        }

        @Override
        public FieldErrorInfo addChild(final String localName, final int beginOfStartTag) {
            if (!parentOf(localName)) {
                throw new IllegalStateException("No child: " + localName);
            }
            return addFieldErrorInfo(beginOfStartTag);
        }

    }

    public static final class FieldErrorInfo extends Tag<FieldError> {

        private static final long serialVersionUID = 1L;

        public FieldErrorInfo(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        @Override
        public Class<?>[] getTypes() {
            return new Class<?>[] { FieldError.class };
        }

        @Override
        public boolean parentOf(final String localName) {
            return false;
        }

        @Override
        public Tag<?> addChild(final String localName, final int endOfStartTag) {
            throw new IllegalStateException("No child: " + localName);
        }

    }

    /**
     * Class that has the start/end position of a tag in the XML file and that is a
     * parent element of field errors.
     *
     * @param <T>
     *            Type of corresponding Java class.
     */
    public static abstract class FieldErrorParentTag<T> extends Tag<T> {

        private static final long serialVersionUID = 1L;

        private List<FieldErrorInfo> childs;

        public FieldErrorParentTag(final int beginOfStartTag) {
            super(beginOfStartTag);
        }

        public List<FieldErrorInfo> getChilds() {
            return childs;
        }

        protected FieldErrorInfo addFieldErrorInfo(final int endOfStartTag) {
            final FieldErrorInfo errorInfo = new FieldErrorInfo(endOfStartTag);
            if (childs == null) {
                childs = new ArrayList<>();
            }
            childs.add(errorInfo);
            return errorInfo;
        }

    }

    /**
     * Class that has the start/end position of a tag in the XML file.
     *
     * @param <T>
     *            Type of corresponding Java class.
     */
    public static abstract class Tag<T> implements Serializable {

        private static final long serialVersionUID = 1L;

        private int beginOfStartTag;

        private int endOfStartTag;

        private int endOfEndTag;

        private boolean selfClosing;

        public Tag(final int beginOfStartTag) {
            super();
            this.beginOfStartTag = beginOfStartTag;
        }

        public int getBeginOfStartTag() {
            return beginOfStartTag;
        }

        public int getEndOfStartTag() {
            return endOfStartTag;
        }

        public boolean isSelfClosing() {
            return selfClosing;
        }

        public void setEndOfStartTag(int endOfStartTag, boolean selfClosing) {
            this.endOfStartTag = endOfStartTag;
            this.selfClosing = selfClosing;
        }

        public int getEndOfEndTag() {
            return endOfEndTag;
        }

        public void setEndOfEndTag(int endOfEndTag) {
            this.endOfEndTag = endOfEndTag;
        }

        public int getStartTagLength() {
            return endOfStartTag - beginOfStartTag;
        }

        public int getLength() {
            return endOfEndTag - beginOfStartTag;
        }

        public String readStartTag(final RandomAccessFile file) {
            final int len = getStartTagLength() + 1;
            try {
                file.seek(beginOfStartTag);
                final byte[] buf = new byte[len];
                file.read(buf, 0, len);
                return new String(buf, Charset.forName("utf-8"));
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public String read(final RandomAccessFile file) {
            final int len = getLength() + 1;
            try {
                file.seek(beginOfStartTag);
                final byte[] buf = new byte[len];
                file.read(buf, 0, len);
                return new String(buf, Charset.forName("utf-8"));
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public T unmarshalStartTag(final RandomAccessFile file) {
            String tag = readStartTag(file);
            if (!selfClosing) {
                tag = tag.substring(0, tag.length() - 1) + "/>";
            }
            return JaxbUtils.unmarshal(tag, getTypes());
        }

        public T unmarshal(final RandomAccessFile file) {
            return JaxbUtils.unmarshal(read(file), getTypes());
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [beginOfStartTag=" + beginOfStartTag + ", endOfStartTag="
                    + endOfStartTag + ", endOfEndTag=" + endOfEndTag + ", startTagLength=" + getStartTagLength()
                    + ", length=" + getLength() + "]";
        }

        public abstract Class<?>[] getTypes();

        public abstract boolean parentOf(String localName);

        public abstract Tag<?> addChild(String localName, int endOfStartTag);

    }

}
