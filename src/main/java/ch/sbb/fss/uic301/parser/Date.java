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
 * Date with YY=Year, MM=Month, DD=Day in the month.
 */
public final class Date implements Comparable<Date> {

    private final int year;

    private final int month;

    private final int day;

    /**
     * Constructor with all data.
     * 
     * @param year
     *            Year (0-99).
     * @param month
     *            Month (1-12).
     * @param day
     *            Day (1-31).
     */
    public Date(final int year, final int month, final int day) {
        super();
        if (!allZero(year, month, day)) {
            if (year < 0 || year > 99) {
                throw new IllegalArgumentException(
                        "Expected year >=0 and <=99, but was: " + year);
            }
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException(
                        "Expected month 1-12, but was: " + month);
            }
            if (day < 1 || day > 31) {
                throw new IllegalArgumentException(
                        "Expected day 1-31, but was: " + day);
            }
        }
        this.year = year;
        this.month = month;
        this.day = day;
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
     * Returns the day.
     * 
     * @return Day (1-31).
     */
    public final int getDay() {
        return day;
    }

    /**
     * Determines if this is a zero date.
     * 
     * @return If all parts (year, month and day) are '0' <code>true</code>.
     */
    public boolean isZero() {
        return allZero(year, month, day);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        result = prime * result + month;
        result = prime * result + day;
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
        final Date other = (Date) obj;
        if (year != other.year) {
            return false;
        }
        if (month != other.month) {
            return false;
        }
        if (day != other.day) {
            return false;
        }
        return true;
    }

    @Override
    public final int compareTo(final Date other) {
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
        // Day
        if (day > other.day) {
            return 1;
        }
        if (day < other.day) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "";
        }
        return year + "/" + month + "/" + day;
    }

    private static boolean allZero(final int year, final int month,
            final int day) {
        return (year == 0 && month == 0 && day == 0);
    }

    /**
     * Determines if the given string is a valid date period.
     * 
     * @param str
     *            String to verify or <code>null</code>.
     * 
     * @return <code>true</code> if the string can be converted into a date.
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
        final String dayStr = str.substring(4, 6);
        final int year = Integer.valueOf(yearStr);
        final int month = Integer.valueOf(monthStr);
        final int day = Integer.valueOf(dayStr);
        if (allZero(year, month, day)) {
            return true;
        }
        if (year < 0 || year > 99) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        return true;
    }

    /**
     * Creates an instance from the given string.
     * 
     * @param str
     *            Date (YYMMPP). YY=Year, MM=Month, DD=Day in the month or
     *            <code>null</code>.
     * 
     * @return New instance or <code>null</code>.
     */
    public static Date valueOf(final String str) {
        if (str == null) {
            return null;
        }
        if (!valid(str)) {
            throw new IllegalArgumentException(
                    "Expected YYMMDD, but was: '" + str + "'");
        }
        final String year = str.substring(0, 2);
        final String month = str.substring(2, 4);
        final String day = str.substring(4, 6);
        return new Date(Integer.valueOf(year), Integer.valueOf(month),
                Integer.valueOf(day));
    }

    /**
     * String that contains a date in format 'YYMMDD'.
     */
    @Documented
    @Constraint(validatedBy = Validator.class)
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface DateStr {

        String message() default "Expected 'YYMMDD', but was: '${validatedValue}'";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    /**
     * Verifies if a string has format 'YYMMDD'.
     */
    public static final class Validator
            implements ConstraintValidator<DateStr, String> {

        @Override
        public boolean isValid(final String value,
                final ConstraintValidatorContext context) {
            return Date.valid(value);
        }

    }

}
