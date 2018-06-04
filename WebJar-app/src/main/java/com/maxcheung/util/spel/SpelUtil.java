/**
 * 
 */
package com.maxcheung.util.spel;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public final class SpelUtil {

    private static final Logger LOGGER = Logger.getLogger(SpelUtil.class);

    private static final Method LOWER_FUNCTION = SpelFunctions.getLowerMethod();
    private static final Method UPPER_FUNCTION = SpelFunctions.getUpperMethod();
    private static final Method REGX_FUNCTION = SpelFunctions.getRegxMethod();

    private static final Method VALID_DATE_FUNCTION = SpelFunctions.getIsValidDate();
    private static final Method VALID_ISIN_FUNCTION = SpelFunctions.getIsValidISIN();

    private static final Method VALUE_THRESHOLD_FUNCTION = SpelFunctions.getIsValueWithinThresHoldMethod();

    /** Context to use privately in this class for non Juel run time activities. */
    private static StandardEvaluationContext privateContext;

    /*
     * private static final Method DAYSBTW_FUNCTION = JuelFunctions.getDaysBetweenMethod(); private static
     * final Method YEARSBTW_FUNCTION = JuelFunctions.getYearsBetweenMethod(); private static final Method
     * TODAY_FUNCTION = JuelFunctions.getMomentMethod(); private static final Method ABS_FUNCTION =
     * JuelFunctions.getAbsMethod(); private static final Method ROUND_FUNCTION =
     * JuelFunctions.getRoundMethod();
     */

    private SpelUtil() {
    }

    public static StandardEvaluationContext getContext() {
        final StandardEvaluationContext context = new StandardEvaluationContext();
        try {

            context.registerFunction("isValidISIN", VALID_ISIN_FUNCTION);
            context.registerFunction("regx", REGX_FUNCTION);
            context.registerFunction("isValidDate", VALID_DATE_FUNCTION);
            context.registerFunction("isValueWithinThresHold", VALUE_THRESHOLD_FUNCTION);
            context.registerFunction("lower", LOWER_FUNCTION);
            context.registerFunction("upper", UPPER_FUNCTION);

            /*
             * context.registerFunction( "abs", ABS_FUNCTION); context.registerFunction( "round",
             * ROUND_FUNCTION); context.registerFunction("fn", "daysBtw", DAYSBTW_FUNCTION);
             * context.registerFunction("fn", "yearsBtw", YEARSBTW_FUNCTION); context.registerFunction("fn",
             * "today", TODAY_FUNCTION); context.registerFunction("fn", "lookup", VALIDATE_LOOKUP);
             * context.registerFunction("fn", "abn", VALIDATE_ABN); context.registerFunction("fn", "tfn",
             * VALIDATE_TFN);
             */
        } catch (Exception e) {
            LOGGER.error("Exception while registering methods to Juel", e);
        }
        return context;
    }

    // public static de.odysseus.el.util.SimpleResolver getResolver() {
    // final SimpleResolver resolver = new de.odysseus.el.util.SimpleResolver(new
    // BeanELResolver());
    // return resolver;
    // }
    //

    /**
     * Method to identify if the requested method is registered with the expression or not.
     * 
     * @param functionName function to test.
     * @return true if the functionName passed is a valid method registered with JUEL.
     */
    public static boolean isRegisteredFunction(String functionName) {

        if (functionName == null || functionName.isEmpty()) {
            return false;
        }

        if (privateContext == null) {
            privateContext = getContext();
        }

        return privateContext.lookupVariable(functionName) != null;

        // return privateContext..I..getFunctionMapper().resolveFunction("fn",
        // functionName) != null;

    }

    /**
     * Only decimals.
     * 
     * @param str decimal string.
     * @return true if decimal.
     */
    public static boolean isDecimal(String str) {
        if (str != null && !str.isEmpty()) {
            if (str.startsWith("-")) {
                str = str.replaceFirst("-", "").trim();
            }
            // return DECIMAL_PATTERN.matcher(str).matches();
            boolean isDecimalSeperater = false;
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    if (!isDecimalSeperater && c == '.') {
                        isDecimalSeperater = true;
                    } else {
                        return false;
                    }
                }
            }
            return isDecimalSeperater;
        }
        return false;
    }

    /**
     * Digits only (Integer, Long, short).
     * 
     * @param str numberStr to check.
     * @return true if all digits.
     */
    public static boolean isAllDigits(String str) {
        if (str != null && !str.isEmpty()) {
            // return ALL_DIGITS_PATTERN.matcher(str).matches();
            if (str.startsWith("-")) {
                str = str.replaceFirst("-", "").trim();
            }
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Sometimes client side sent wrong data for example 32.5 > 0 and set all types as String. <br/>
     * But when the System tries to evaluate the above expression, since it is a mathematical expression it
     * tries convert it to the Long type and fails on 32.5 <br/>
     * We are resolving those errors here.
     * 
     * @param value value string.
     * @param typeReceivedFromInput dataType.
     * @return object.
     */
    public static Object resolveStringToDecimalTypeConversionIssues(Object value, Class<?> typeReceivedFromInput) {
        if (value != null && typeReceivedFromInput != null && typeReceivedFromInput.getName().equals(String.class.getName())) {

            final String valueAsString = (String) value;

            if (isDecimal(valueAsString)) {
                return getBigDecimal(valueAsString, true);
            }

            if (isAllDigits(valueAsString)) {
                return Long.valueOf(valueAsString);
            }
        }
        return value;
    }

    public static String resolveObjectToString(Object value) {
        return (value instanceof String) ? (String) value : null;
    }

    public static BigDecimal getBigDecimal(Object value, boolean isDecimal) {
        final String valueAsString = value == null ? BigDecimal.ZERO.toPlainString() : trimOffTheSupportedCharactersFromNumber(String.valueOf(value));
        int scale = 2;
        if (isDecimal || isDecimal(valueAsString)) {
            scale = valueAsString.substring(valueAsString.lastIndexOf(".") + 1).length();
        }
        return new BigDecimal(valueAsString).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Trim of the commas and any other symbols if required.
     * 
     * @param val the string value with commas.
     * @return a string value after removing the commas.
     */
    public static String trimOffTheSupportedCharactersFromNumber(String val) {
        if (val != null) {
            return val.replace(",", "");
        }

        return val;
    }

    public static BigDecimal round(Object value, int scale) {
        try {
            final String valueAsString = value == null ? BigDecimal.ZERO.toPlainString() : String.valueOf(value);
            return new BigDecimal(valueAsString).setScale(scale, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            LOGGER.error("Could not round value because of NumberFormatException " + value, e);
        }

        throw new RuntimeException("Could not round value because of NumberFormatException " + value);
    }

    /**
     * @param value string with backslash.
     * @return escaped string.
     */
    public static String escapeBackSlash(String value) {
        final String backSlashToReplace = "\\";
        final String exprToReplaceWith = "\\\\";

        return value != null ? value.replace(backSlashToReplace, exprToReplaceWith) : value;
    }
}
