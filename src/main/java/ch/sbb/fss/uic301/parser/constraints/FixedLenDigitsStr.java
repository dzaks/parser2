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
 * Fixed lengths, only digits.A <code>null</code> string is considered a valid
 * value.
 */
@Documented
@Constraint(validatedBy = FixedLenDigitsStrValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface FixedLenDigitsStr {

    static final String MESSAGE = "Expected exactly {value} digit(s), but was: '${validatedValue}'";

    String message() default MESSAGE;

    int value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}