package ch.sbb.fss.uic301.parser.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Ensures that all detail and total entries of the document have the same
 * information as the document header.
 */
@Documented
@Constraint(validatedBy = AllSameHeaderValidator.class)
@Target({ ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
public @interface AllSameHeader {

    static final String MSG_KEY = "firstOccurence";

    static final String MESSAGE = "Some lines do not contain the same information as the header (See log for all errors) - First occurence = {"
            + MSG_KEY + "}";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}