package ch.sbb.fss.uic301.parser;

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
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Statement period. YY=Year, MM=Month, PP=Period in the month (00 default,
 * other usage must be bilaterally agreed upon).
 */
public final class StatementPeriod implements Comparable<StatementPeriod> {

    private final int year;

    private final int month;

    private final int period;

    /**
     * Constructor with all data.
     * 
     * @param year
     *            Year (0-99).
     * @param month
     *            Month (1-12).
     * @param period
     *            Period - Default 0, other usage must be bilaterally agreed
     *            upon (&gt;=0).
     */
    public StatementPeriod(final int year, final int month, final int period) {
        super();
        if (year < 0 || year > 99) {
            throw new IllegalArgumentException(
                    "Expected year >=0 and <=99, but was: " + year);
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(
                    "Expected month 1-12, but was: " + month);
        }
        if (period < 0 || period > 99) {
            throw new IllegalArgumentException(
                    "Expected period > 0, but was: " + period);
        }
        this.year = year;
        this.month = month;
        this.period = period;
    }

    /**
     * Returns the year.
     * 
     * @return Year (&gt;= 1900).
     */
    public final int getYear() {
        return year;
    }

    /**
     * Returns the month.
     * 
     * @return Month (1-12).
     */
    public final int getMonth() {
        return month;
    }

    /**
     * Returns the period.
     * 
     * @return Period - Default 0, other usage must be bilaterally agreed upon
     *         (&gt;=0)
     */
    public final int getPeriod() {
        return period;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        result = prime * result + month;
        result = prime * result + period;
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StatementPeriod other = (StatementPeriod) obj;
        if (year != other.year) {
            return false;
        }
        if (month != other.month) {
            return false;
        }
        if (period != other.period) {
            return false;
        }
        return true;
    }

    @Override
    public final int compareTo(final StatementPeriod other) {
        // Year
        if (year > other.year) {
            return 1;
        }
        if (year < other.year) {
            return -1;
        }
        // Month
        if (month > other.month) {
            return 1;
        }
        if (month < other.month) {
            return -1;
        }
        // Period
        if (period > other.period) {
            return 1;
        }
        if (period < other.period) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return year + "/" + month + "-" + period;
    }

    /**
     * Determines if the given string is a valid statement period.
     * 
     * @param str
     *            String to verify or <code>null</code>.
     * 
     * @return <code>true</code> if the string can be converted into a statement
     *         period.
     */
    public static boolean valid(final String str) {
        if (str == null) {
            return true;
        }
        if (str.length() != 6 || !str.matches("\\d{6}")) {
            return false;
        }
        final String yearStr = str.substring(0, 2);
        final String monthStr = str.substring(2, 4);
        final String periodStr = str.substring(4, 6);
        final int year = Integer.valueOf(yearStr);
        final int month = Integer.valueOf(monthStr);
        final int period = Integer.valueOf(periodStr);
        if (year < 0 || year > 99) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        if (period < 0 || period > 99) {
            return false;
        }
        return true;
    }

    /**
     * Creates an instance from the given string.
     * 
     * @param str
     *            Statement period (YYMMPP). YY=Year, MM=Month, PP=Period in the
     *            month or <code>null</code>.
     * 
     * @return New instance or <code>null</code>.
     */
    public static StatementPeriod valueOf(final String str) {
        if (str == null) {
            return null;
        }
        if (!valid(str)) {
            throw new IllegalArgumentException(
                    "Expected YYMMPP, but was: '" + str + "'");
        }
        final String year = str.substring(0, 2);
        final String month = str.substring(2, 4);
        final String period = str.substring(4, 6);
        return new StatementPeriod(Integer.valueOf(year),
                Integer.valueOf(month), Integer.valueOf(period));
    }

    /**
     * String that contains a statement period in format 'YYMMPP'.
     */
    @Documented
    @Constraint(validatedBy = Validator.class)
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface StatementPeriodStr {

        String message() default "Expected 'YYMMPP', but was: '${validatedValue}'";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    /**
     * Verifies if a string has format 'YYMMPP'.
     */
    public static final class Validator
            implements ConstraintValidator<StatementPeriodStr, String> {

        @Override
        public boolean isValid(final String value,
                final ConstraintValidatorContext context) {
            return StatementPeriod.valid(value);
        }

    }

}
