package ch.sbb.fss.uic301.parser;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.StringUtils;

/**
 * Utilities for the package.
 */
public final class Uic301Utils {

    private static final Map<Class<?>, Map<String, String>> beanAttrToXmlAttr = new HashMap<Class<?>, Map<String, String>>();

    private Uic301Utils() {
    }

    public static Integer integerOf(final String name, final String str) {
        if (str == null) {
            return null;
        }
        try {
            return Integer.valueOf(str);
        } catch (final NumberFormatException ex) {
            throw new RuntimeException(
                    "Failed to convert value for field '" + name + "' into an integer: '" + str + "'", ex);
        }
    }
   

    public static BigDecimal bigDecimalOf(final String name, final String str, final int scale) {
        if (str == null) {
            return null;
        }
        try {
            final Long value = Long.valueOf(str);
            return BigDecimal.valueOf(value, scale).setScale(scale, BigDecimal.ROUND_HALF_UP);
        } catch (final NumberFormatException ex) {
            throw new RuntimeException(
                    "Failed to convert value for field '" + name + "' into a big decimal: '" + str + "'", ex);
        }
    }

    /**
     * Validates the object and returns a list of field errors.
     * 
     * @param validator Validator to use.
     * @param obj Object to validate.
     *
     * @return List of field errors.
     *
     * @param <T> Type of object being validated.
     */
    public static <T> List<FieldError> validate(final Validator validator, final T obj) {
        final List<FieldError> errors = new ArrayList<>();
        final Set<ConstraintViolation<T>> violations = validator.validate(obj);
        for (final ConstraintViolation<T> violation : violations) {
            final String field = violation.getPropertyPath().iterator().next().toString();
            errors.add(new FieldError(getXmlAttributeForBeanAttribute(field, obj), violation.getMessage()));
        }
        return errors;
    }

    /**
     * 
     * @param <T>
     * @param attributeName - bean attribute
     * @param obj
     * @return xml attribute name of the given bean attribute
     */
    private static <T> String getXmlAttributeForBeanAttribute(String attributeName, final T obj) {
        if (beanAttrToXmlAttr.get(obj.getClass()) == null) {
            initBeanAttrToXmlAttrMapping(obj);
        }
        return beanAttrToXmlAttr.get(obj.getClass()).get(attributeName);
    }

    /**
     * Creates mapping of object attribute name to their XML element attribute names. Stores the object mapping in
     * an object<->mapping map.
     * @param <T>
     * @param obj
     */
    private static <T> void initBeanAttrToXmlAttrMapping(T obj) {
        Field[] nameFields = obj.getClass().getDeclaredFields();
        Map<String, String> map = Arrays.stream(nameFields)
                .collect(Collectors.toMap(f -> f.getName(), f -> mapFiledToXmlAttribute(f)));
        beanAttrToXmlAttr.put(obj.getClass(), map);
    }

   /**
    *
    * maps attribute (filed) name to its xml element name
    * @param field
    * @return xml attribute name of this field
    */
    private static String mapFiledToXmlAttribute(Field field) {
        field.setAccessible(true);
        XmlAttribute a = field.getAnnotation(XmlAttribute.class);
        return a != null ? a.name() : field.getName();
    }
    
    static String addAmounts(String filedName, String a1, String a2) {
        long l1;
        long l2;
        try {
            l1 = Long.parseLong(a1);
            l2 = Long.parseLong(a2);
            long sum = l1 + l2;
            int stringlegth = a1.length();
            return StringUtils.leftPad(Long.toString(sum), stringlegth, "0");
        } catch (final NumberFormatException ex) {
            throw new RuntimeException(
                    "Failed to add '" + filedName + "' of totals ");
        }
    }
    
    public static String changePeriodCounter(final String period, final int i) {
        if(period == null || period.length() < 6) {
            return period;
        }
        String periodCounter = "00";
        if(i > 0 && i < 99) {
           periodCounter = String.format("%02d", i);
        }
        return period.substring(0, 4)+ periodCounter;
    }

}
