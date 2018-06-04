/**
 * 
 */
package com.maxcheung.util.spel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import org.junit.Test;

public class SpelUtilTest {

    @Test
    public void testMethodUpper() {
        assertTrue(SpelUtil.isRegisteredFunction("upper"));
    }

    @Test
    public void testMethodLower() {
        assertTrue(SpelUtil.isRegisteredFunction("lower"));
    }

    @Test
    public void testMethodRegx() {
        assertTrue(SpelUtil.isRegisteredFunction("regx"));
    }

    @Test
    public void testMethodNull() {
        assertFalse(SpelUtil.isRegisteredFunction(null));
    }

    @Test
    public void testMethodEmpty() {
        assertFalse(SpelUtil.isRegisteredFunction(""));
    }

    @Test
    public void testMethodNone() {
        assertFalse("blah is not a registered method", SpelUtil.isRegisteredFunction("blah"));
    }

    @Test
    public void testRound() {
        final String decimal = "23.4567";
        BigDecimal expected = new BigDecimal("23.46");
        assertEquals(expected, SpelUtil.round(decimal, 2));
    }

    @Test(expected = RuntimeException.class)
    public void testRoundNonNumericShouldRaiseException() {
        final String decimal = "23.4x567";
        SpelUtil.round(decimal, 2);
    }

    @Test
    public void testBigDecimal() {
        final String decimal = "23.4567";
        BigDecimal expected = new BigDecimal("23.4567");
        assertEquals(expected, SpelUtil.getBigDecimal(decimal, true));
        final String nonDecimal = "23";
        expected = new BigDecimal("23.00");
        assertEquals(expected, SpelUtil.getBigDecimal(nonDecimal, false));
    }

    @Test
    public void testEscapeBackSlash() {
        assertEquals("\\\\abc", SpelUtil.escapeBackSlash("\\abc"));
        assertNull(SpelUtil.escapeBackSlash(null));
    }

    @Test
    public void testTrimOffTheSupportedCharactersFromNumber() {
        assertEquals("1000000", SpelUtil.trimOffTheSupportedCharactersFromNumber("1,000,000"));
        assertNull(SpelUtil.trimOffTheSupportedCharactersFromNumber(null));
    }

    @Test
    public void testDecimal() {
        final String decimal = "23.4";
        assertTrue(SpelUtil.isDecimal(decimal));
    }

    @Test
    public void testNegativeDecimal() {
        final String decimal = "-23.4";
        assertTrue(SpelUtil.isDecimal(decimal));
    }

    @Test
    public void testNotDecimal() {
        final String decimal = "23";
        assertFalse(SpelUtil.isDecimal(decimal));
    }

    @Test
    public void testNotDecimalOfString() {
        final String decimal = "lonsec.com";
        assertFalse(SpelUtil.isDecimal(decimal));
    }

    @Test
    public void testAllDigits() {
        final String value = "234";
        assertTrue(SpelUtil.isAllDigits(value));
        assertTrue(SpelUtil.isAllDigits("-234"));
    }

    @Test
    public void testAllDigitsWithAlpha() {
        final String value = "234a";
        assertFalse(SpelUtil.isAllDigits(value));
    }

    @Test
    public void testAllDigitsWithEmpty() {
        final String value = "";
        assertFalse(SpelUtil.isAllDigits(value));
    }

    @Test
    public void testresolveStringToDecimalTypeConversionIssues() {
        final String value = "123.23";
        Object ret = SpelUtil.resolveStringToDecimalTypeConversionIssues(value, String.class);
        BigDecimal expected = new BigDecimal(value);
        assertEquals(expected, ret);
    }

    @Test
    public void testResolveStringToDecimalAllDigits() {
        final String value = "123";
        Object ret = SpelUtil.resolveStringToDecimalTypeConversionIssues(value, String.class);
        assertEquals(123L, ret);
        assertNull(SpelUtil.resolveStringToDecimalTypeConversionIssues(null, String.class));
    }

    @Test
    public void testResolveObjectToString() {
        Object value = "123";
        Object ret = SpelUtil.resolveObjectToString(value);
        assertEquals( "123", ret);
        value = 10.5;
        assertNull(SpelUtil.resolveObjectToString(value));
        value = new BigDecimal("123.23");
        assertNull(SpelUtil.resolveObjectToString(value));
    }

    
    
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SpelUtil> constructor = SpelUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
