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
 * Ensures that a RU, receving statement, is SBB.
 */
@Documented
@Constraint(validatedBy = RuRecievingValidator.class)
@Target({ ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
public @interface RuRecieving {

    static final String MSG_KEY = "document";

    static final String MESSAGE = "{"+ MSG_KEY + "}";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean block();
    
    String sbb();

}