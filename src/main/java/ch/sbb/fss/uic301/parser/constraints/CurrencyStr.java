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
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * ISO 4207 code for currency like "EUR".
 */
@Pattern(regexp = "[A-Z]*")
@Size(min = 3, max = 3)
@Documented
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface CurrencyStr {

    String message() default "Expected only three uppercase characters for currency, but was '${validatedValue}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}