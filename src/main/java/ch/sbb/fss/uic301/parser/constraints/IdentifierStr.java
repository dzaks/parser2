package ch.sbb.fss.uic301.parser.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Unique identifier for header, detail and total lines.
 */
@Documented
@Constraint(validatedBy = IdentifierStrValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface IdentifierStr {

    static final String MESSAGE = "Expected one of {value}, but was: ${validatedValue}";

    String message() default MESSAGE;

    String[] value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}