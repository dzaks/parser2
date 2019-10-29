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
 * Unique RICS code (4 digits).
 */
@Pattern(regexp = "[0-9]*")
@Size(min = 4, max = 4)
@Documented
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface RicsCodeStr {

    String message() default "Wrong RICS code (${validatedValue})";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}