package ch.sbb.fss.uic301.parser;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.Validate;

/**
 * One of the fields of a data structure has an error.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = FieldError.TAG)
public final class FieldError {

    public static final String TAG = "field-error";

    @XmlAttribute(name = "field")
    private String field;

    @XmlAttribute(name = "error")
    private String error;

    /**
     * Constructor for JAX-B.
     */
    protected FieldError() {
        super();
    }

    /**
     * Constructor with all mandatory data.
     * 
     * @param field
     *            Field name.
     * @param error
     *            Error message.
     */
    public FieldError(@NotNull final String field, @NotNull final String error) {
        super();
        Validate.notNull(field, "Argument 'field' must not be null");
        Validate.notNull(error, "Argument 'error' must not be null");
        this.field = field;
        this.error = error;
    }

    /**
     * Returns the name of the field.
     * 
     * @return Field that has the error.
     */
    public final String getField() {
        return field;
    }

    /**
     * Returns the error.
     * 
     * @return Error message.
     */
    public final String getError() {
        return error;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((error == null) ? 0 : error.hashCode());
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FieldError other = (FieldError) obj;
        if (error == null) {
            if (other.error != null) {
                return false;
            }
        } else if (!error.equals(other.error)) {
            return false;
        }
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "FieldError [field=" + field + ", error=" + error + "]";
    }

}
