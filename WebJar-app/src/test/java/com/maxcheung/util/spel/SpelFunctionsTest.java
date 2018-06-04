package com.maxcheung.util.spel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class SpelFunctionsTest {

    @Test
    public void testLower() {
        assertEquals("abc", SpelFunctions.toLower("aBC"));
        assertNull("ABC", SpelFunctions.toLower(null));
    }

    @Test
    public void testUpper() {
        assertEquals("ABC", SpelFunctions.toUpper("aBC"));
        assertNull("ABC", SpelFunctions.toUpper(null));
    }

    @Test
    public void testRegx() {
        assertTrue(SpelFunctions.regx("a", "^[a-zA-Z]"));
        assertFalse(SpelFunctions.regx("a", ""));
        assertFalse(SpelFunctions.regx("a", null));
        assertFalse(SpelFunctions.regx("a", "xxxx"));
        assertFalse(SpelFunctions.regx(null, "^[a-zA-Z]"));
    }

    @Test
    public void testisValidISIN() {
        assertTrue(SpelFunctions.isValidISIN("US30231G1022"));
        assertFalse(SpelFunctions.isValidISIN(""));
        assertFalse(SpelFunctions.isValidISIN(null));
    }



    @Test
    public void testisValidDate() {
        assertTrue(SpelFunctions.isValidDate("20/12/2018"));
    }

    @Test
    public void testisValueWithinThresHold() {
        assertTrue(SpelFunctions.isValueWithinThresHold("99.999999", "100", "0.1"));
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SpelFunctions> constructor = SpelFunctions.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
