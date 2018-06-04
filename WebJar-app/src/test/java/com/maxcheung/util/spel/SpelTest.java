package com.maxcheung.util.spel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelTest {

    private ExpressionParser parser;
    private StandardEvaluationContext itemContext;

    @Before
    public void setup() throws Exception {
        parser = new SpelExpressionParser();
        itemContext = new StandardEvaluationContext();
        itemContext.registerFunction("isValidISIN", SpelFunctions.class.getDeclaredMethod("isValidISIN", new Class[] { String.class }));
        itemContext.registerFunction("toLower", SpelFunctions.class.getDeclaredMethod("toLower", new Class[] { String.class }));
        itemContext.registerFunction("regx", SpelFunctions.class.getDeclaredMethod("regx", new Class[] { Object.class, String.class }));
        itemContext.registerFunction("isValidDate", SpelFunctions.class.getDeclaredMethod("isValidDate", new Class[] { String.class }));
        itemContext.registerFunction("daysBetweenDates", SpelFunctions.class.getDeclaredMethod("daysBetweenDates", new Class[] { LocalDate.class, LocalDate.class }));
        itemContext.registerFunction("isDateBetweenDates", SpelFunctions.class.getDeclaredMethod("isDateBetweenDates", new Class[] { LocalDate.class, LocalDate.class, LocalDate.class }));
        itemContext.registerFunction("isValueWithinThresHold",
                SpelFunctions.class.getDeclaredMethod("isValueWithinThresHold", new Class[] { String.class, String.class, String.class }));
        
        itemContext.registerFunction("formatMsg",
                SpelFunctions.class.getDeclaredMethod("formatMsg", new Class[] { String.class, String.class }));
        
    }

    @Test
    public void testLength() {
        Expression exp2 = parser.parseExpression("'Hello World'.length()");
        int msg2 = (Integer) exp2.getValue();
        System.out.println(msg2);
    }

    @Test
    public void testAPIRCdSize() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("apirCd", "aBc1234xxxxxxxxxxx");
        itemContext.setVariables(data);
        Expression exp6 = parser.parseExpression("#apirCd.length() > 9");
        boolean msg6 = exp6.getValue(itemContext, Boolean.class);
        assertTrue(msg6);
    }

    
    @Test
    public void testAPIRCdNull() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("apirCd", null);
        itemContext.setVariables(data);
        Expression exp6 = parser.parseExpression("#apirCd?.length() > 9");
        boolean msg6 = exp6.getValue(itemContext, Boolean.class);
        assertFalse(msg6);
    }

    
    
    @Test
    public void testThresHold() {

        // BigDecimal num = new BigDecimal(0.00147470419378205).setScale(17,
        // RoundingMode.HALF_UP);
        BigDecimal num = new BigDecimal(0.00147470419378205).setScale(17, RoundingMode.HALF_UP);

        Expression exp6 = parser.parseExpression(" #isValueWithinThresHold('100','100','0.01')  ");
        boolean msg6 = exp6.getValue(itemContext, Boolean.class);
        exp6 = parser.parseExpression(" #isValueWithinThresHold('97','100','0.01')  ");
        msg6 = exp6.getValue(itemContext, Boolean.class);
        assertFalse(msg6);
    }

    @Test
    public void testAPIRCd() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("apirCd", "aBc1234xxxxxxxxxxx");
        itemContext.setVariables(data);
        Expression exp4 = parser.parseExpression("#apirCd");
        String msg4 = exp4.getValue(itemContext, String.class);
        assertEquals("aBc1234xxxxxxxxxxx", msg4);
    }

   
    @Test
    public void testAPIRCdNullCheck() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("apirCd", "aBc1234xxxxxxxxxxx");
        itemContext.setVariables(data);
        Expression exp4 = parser.parseExpression("#apirCd == null");
        boolean isNull = exp4.getValue(itemContext, Boolean.class);
        assertFalse(isNull);
        data.put("apirCd", null);
        itemContext.setVariables(data);
        isNull = exp4.getValue(itemContext, Boolean.class);
        assertTrue(isNull);
    }

    @Test
    public void testToLower() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("apirCd", "aBc1234");
        itemContext.setVariables(data);
        Expression exp7 = parser.parseExpression("#toLower(#apirCd)");
        String msg7 = exp7.getValue(itemContext, String.class);
        assertEquals("abc1234", msg7);
    }

    @Test
    public void testISIN() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("isin", "US0378331005");
        itemContext.setVariables(data);
        Expression isinExpression = parser.parseExpression("#isValidISIN(#isin)");
        boolean isValidISIN = isinExpression.getValue(itemContext, Boolean.class);
        assertTrue(isValidISIN);
        data.put("isin", "US0378331000");
        itemContext.setVariables(data);
        isValidISIN = isinExpression.getValue(itemContext, Boolean.class);
        assertFalse(isValidISIN);
    }

    @Test
    public void testDate() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("holding", "30/09/2017");
        itemContext.setVariables(data);
        Expression isinExpression = parser.parseExpression("#isValidDate(#holding)");
        boolean isValid = isinExpression.getValue(itemContext, Boolean.class);
        assertTrue(isValid);
        data.put("holding", "30/02/2017");
        itemContext.setVariables(data);
        isValid = isinExpression.getValue(itemContext, Boolean.class);
        assertFalse(isValid);
    }

    
    @Test
    public void testSubtract() {
        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("issue", "30/09/2017");
 //       data.put("expiry", "30/09/2018");
        
        data.put("issue", Double.valueOf(22));
        data.put("expiry", Double.valueOf(29));
        itemContext.setVariables(data);
        Expression isinExpression = parser.parseExpression("#expiry - #issue");
        Double daysBetween = isinExpression.getValue(itemContext, Double.class);
        
    }

    @Test
    public void testDayBetween() {
        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("issue", "30/09/2017");
 //       data.put("expiry", "30/09/2018");
        
        data.put("issue", LocalDate.of(2017, 9, 30));
        data.put("expiry", LocalDate.of(2018, 9, 30));
        itemContext.setVariables(data);
        Expression isinExpression = parser.parseExpression("#daysBetweenDates(#issue, #expiry)");
        Long  daysBetween = isinExpression.getValue(itemContext, Long.class);
        
    }


    @Test
    public void testIsDateBetweenDates() {
        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("issue", "30/09/2017");
 //       data.put("expiry", "30/09/2018");
        data.put("localDateTime", LocalDateTime.now());
        data.put("localDate", LocalDate.now());
        data.put("inputDate", LocalDate.of(2017, 12, 30));
        data.put("issue", LocalDate.of(2017, 9, 30));
        data.put("expiry", LocalDate.of(2018, 9, 30));
        itemContext.setVariables(data);
//        Expression isinExpression = parser.parseExpression("#isDateBetweenDates(#inputDate, #issue, #expiry)");
        Expression isinExpression = parser.parseExpression("#isDateBetweenDates(#localDate, #issue, #expiry)");
        Boolean  isDateBetweenDates = isinExpression.getValue(itemContext, Boolean.class);
        assertEquals(isDateBetweenDates, true);
        Expression isinExpression1 = parser.parseExpression(" #isDateBetweenDates(#inputDate, #issue, #expiry)  ? 'ACTIVE' : 'INACTIVE'");
        String  status = isinExpression1.getValue(itemContext, String.class);
        assertEquals("ACTIVE", status);
        data.put("inputDate", LocalDate.of(2016, 12, 30));
        itemContext.setVariables(data);
        status = isinExpression1.getValue(itemContext, String.class);
        assertEquals("INACTIVE", status);
        
    }

    
    
    @Test
    public void testRegEx() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("absPortfolioWeight", "0.24");
        itemContext.setVariables(data);
        Expression expr = parser.parseExpression("#regx(#absPortfolioWeight,\"^[-+]?\\d+(\\.\\d+)?$\")");
        boolean isValidNumeber = expr.getValue(itemContext, Boolean.class);
        assertTrue(isValidNumeber);
        data.put("absPortfolioWeight", "0.2.4");
        itemContext.setVariables(data);
        isValidNumeber = expr.getValue(itemContext, Boolean.class);
        assertFalse(isValidNumeber);
    }


    @Test
    public void testFormatMessage() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("apirCd", "aBc1234xxxxxxxxxxx");
        itemContext.setVariables(data);
        String errorMessageExpr = "#formatMsg(\"apirCd must #value be between 0 and 9 characters \",#apirCd)";
        Expression expr = parser.parseExpression(errorMessageExpr);
        String errorMsg = expr.getValue(itemContext, String.class);
        assertEquals("apirCd must aBc1234xxxxxxxxxxx be between 0 and 9 characters ", errorMsg);
    }

}
