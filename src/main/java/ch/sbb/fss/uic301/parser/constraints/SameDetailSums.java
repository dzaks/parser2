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
@Constraint(validatedBy = SameDetailsSumValidator.class)
@Target({ ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
public @interface SameDetailSums {

    static final String MSG_KEY = "details";

    static final String MESSAGE = "Some totals differ from the calculated details sum: {"
            + MSG_KEY + "}";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean block();

}