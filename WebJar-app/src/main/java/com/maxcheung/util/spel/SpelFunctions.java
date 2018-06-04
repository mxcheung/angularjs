package com.maxcheung.util.spel;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.ISINCheckDigit;
import org.apache.log4j.Logger;


public final class SpelFunctions {

    private static final Logger LOGGER = Logger.getLogger(SpelFunctions.class.getName());

    private SpelFunctions() {
    }

    public static String toLower(String str) {
        return str == null ? str : str.toLowerCase();
    }

    public static String toUpper(String str) {
        return str == null ? str : str.toUpperCase();
    }

    public static String formatMsg(String str, String variable) {
        return str == null ? str : str.replaceFirst("#value", variable);
    }

    public static boolean regx(Object object, String regx) {
        if (regx == null || regx.isEmpty()) {
            LOGGER.error("Not processing regx method as the regx passed is null or empty.");
            return false;
        }

        regx = regx.replace("\\\\", "\\");

        try {
            final Pattern compiledPattern = Pattern.compile(regx);
            final Matcher matcher = compiledPattern.matcher(object == null ? "" : object.toString());
            if (matcher.matches()) {
                return true;
            }
        } catch (Exception elException) {
            LOGGER.error("Not processing regx method regx is invalid.", elException);
        }
        return false;
    }

    public static boolean isValidISIN(String isin) {
        if (isin == null || isin.isEmpty()) {
            LOGGER.error("Not processing isin method as the isin passed is null or empty.");
            return false;
        }
        CheckDigit checkDigit = ISINCheckDigit.ISIN_CHECK_DIGIT;
        return checkDigit.isValid(isin);
    }

    public static boolean isValidDate(String dateStr) {
        Date date = DateValidator.getInstance().validate(dateStr);
        return (date != null);
    }

    public static boolean isValueWithinThresHold(String inputVal, String expectedVal, String thresHold) {
        float a = Float.parseFloat(inputVal);
        float b = Float.parseFloat(expectedVal);
        float thresHoldF = Float.parseFloat(thresHold);
        return Math.abs(a - b) < thresHoldF;
    }

    public static boolean isDateBetweenDates(LocalDate inputDate , LocalDate fromDate, LocalDate toDate) {
    	boolean isBetweenDates = false;
		if (fromDate.isBefore(inputDate) && toDate.isAfter(inputDate)) {
			isBetweenDates = true;
		}
        return isBetweenDates;
    }

    public static Long daysBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return ChronoUnit.DAYS.between(fromDate, toDate);
    }

    private static Optional<BigDecimal> getBGValue(String key, Map<String, Object> dataRow) {
        Object obj = dataRow.get(key);
        if (obj != null) {
            String strVal = obj.toString();

            try {
                BigDecimal num = new BigDecimal(strVal);
                return Optional.of(num);
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    // @formatter:off


    public static Method getIsDateBetweenDatesMethod() {
        try {
            return SpelFunctions.class.getMethod("isDateBetweenDates", new Class[] {LocalDate.class, LocalDate.class, LocalDate.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }

    public static Method getDaysBetweenDatesMethod() {
        try {
            return SpelFunctions.class.getMethod("daysBetweenDates", new Class[] {LocalDate.class, LocalDate.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }
    
    public static Method getLowerMethod() {
        try {
            return SpelFunctions.class.getMethod("toLower", new Class[] {String.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }

    public static Method getUpperMethod() {
        try {
            return SpelFunctions.class.getMethod("toUpper", new Class[] {String.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }

    public static Method getMethodSingleParam(String methodName)  {
        try {
            return SpelFunctions.class.getMethod(methodName, new Class[] {String.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }


    
    public static Method getIsValueWithinThresHoldMethod() {
        try {
            return SpelFunctions.class.getDeclaredMethod("isValueWithinThresHold", 
                    new Class[] {String.class, String.class, String.class });
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }
    


    public static Method getRegxMethod() {
        try {
            return SpelFunctions.class.getMethod("regx", new Class[] {Object.class, String.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }
    
    public static Method getIsValidISIN() {
        try {
            return SpelFunctions.class.getDeclaredMethod("isValidISIN", new Class[] {String.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }
    
    public static Method getIsValidDate() {
        try {
            return SpelFunctions.class.getDeclaredMethod("isValidDate", new Class[] {String.class});
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not register method with juel function", e);
        }
    }
    

    // @formatter:on

}
