/*
 * Copyright 2015 Daniel Sawano
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.sawano.java.commons.lang.validate.hystrix;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit tests from org.apache.commons.lang3.Validate
 */
public class HystrixValidate_Test {

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue1() {
        HystrixValidate.isTrue(true);
        try {
            HystrixValidate.isTrue(false);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("The validated expression is false", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue2() {
        HystrixValidate.isTrue(true, "MSG");
        try {
            HystrixValidate.isTrue(false, "MSG");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue3() {
        HystrixValidate.isTrue(true, "MSG", 6);
        try {
            HystrixValidate.isTrue(false, "MSG", 6);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue4() {
        HystrixValidate.isTrue(true, "MSG", 7);
        try {
            HystrixValidate.isTrue(false, "MSG", 7);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue5() {
        HystrixValidate.isTrue(true, "MSG", 7.4d);
        try {
            HystrixValidate.isTrue(false, "MSG", 7.4d);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull1() {
        HystrixValidate.notNull(new Object());
        try {
            HystrixValidate.notNull(null);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = HystrixValidate.notNull(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull2() {
        HystrixValidate.notNull(new Object(), "MSG");
        try {
            HystrixValidate.notNull(null, "MSG");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = HystrixValidate.notNull(str, "Message");
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray1() {
        HystrixValidate.notEmpty(new Object[]{null});
        try {
            HystrixValidate.notEmpty((Object[]) null);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(new Object[0]);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = HystrixValidate.notEmpty(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray2() {
        HystrixValidate.notEmpty(new Object[]{null}, "MSG");
        try {
            HystrixValidate.notEmpty((Object[]) null, "MSG");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(new Object[0], "MSG");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = HystrixValidate.notEmpty(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection1() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            HystrixValidate.notEmpty((Collection<?>) null);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(coll);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        HystrixValidate.notEmpty(coll);

        final Collection<Integer> test = HystrixValidate.notEmpty(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection2() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            HystrixValidate.notEmpty((Collection<?>) null, "MSG");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(coll, "MSG");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        HystrixValidate.notEmpty(coll, "MSG");

        final Collection<Integer> test = HystrixValidate.notEmpty(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap1() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            HystrixValidate.notEmpty((Map<?, ?>) null);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(map);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        HystrixValidate.notEmpty(map);

        final Map<String, Integer> test = HystrixValidate.notEmpty(map);
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap2() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            HystrixValidate.notEmpty((Map<?, ?>) null, "MSG");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(map, "MSG");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        HystrixValidate.notEmpty(map, "MSG");

        final Map<String, Integer> test = HystrixValidate.notEmpty(map, "Message");
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString1() {
        HystrixValidate.notEmpty("hjl");
        try {
            HystrixValidate.notEmpty((String) null);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty("");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = HystrixValidate.notEmpty(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString2() {
        HystrixValidate.notEmpty("a", "MSG");
        try {
            HystrixValidate.notEmpty((String) null, "MSG");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty("", "MSG");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = HystrixValidate.notEmpty(str, "Message");
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNullStringShouldThrow() {
        //given
        final String string = null;

        try {
            //when
            HystrixValidate.notBlank(string);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException e) {
            //then
            assertEquals("The validated character sequence is blank", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNullStringShouldThrow() {
        //given
        final String string = null;

        try {
            //when
            HystrixValidate.notBlank(string, "Message");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException e) {
            //then
            assertEquals("Message", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankEmptyStringShouldThrow() {
        //given
        final String string = "";

        try {
            //when
            HystrixValidate.notBlank(string);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            //then
            assertEquals("The validated character sequence is blank", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankBlankStringWithWhitespacesShouldThrow() {
        //given
        final String string = "   ";

        try {
            //when
            HystrixValidate.notBlank(string);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            //then
            assertEquals("The validated character sequence is blank", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankBlankStringWithNewlinesShouldThrow() {
        //given
        final String string = " \n \t \r \n ";

        try {
            //when
            HystrixValidate.notBlank(string);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            //then
            assertEquals("The validated character sequence is blank", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgBlankStringShouldThrow() {
        //given
        final String string = " \n \t \r \n ";

        try {
            //when
            HystrixValidate.notBlank(string, "Message");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            //then
            assertEquals("Message", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgBlankStringWithWhitespacesShouldThrow() {
        //given
        final String string = "   ";

        try {
            //when
            HystrixValidate.notBlank(string, "Message");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            //then
            assertEquals("Message", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgEmptyStringShouldThrow() {
        //given
        final String string = "";

        try {
            //when
            HystrixValidate.notBlank(string, "Message");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            //then
            assertEquals("Message", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringShouldNotThrow() {
        //given
        final String string = "abc";

        //when
        HystrixValidate.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        HystrixValidate.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        HystrixValidate.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringShouldNotThrow() {
        //given
        final String string = "abc";

        //when
        HystrixValidate.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        HystrixValidate.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        HystrixValidate.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankReturnValues1() {
        final String str = "Hi";
        final String test = HystrixValidate.notBlank(str);
        assertSame(str, test);
    }

    @Test
    public void testNotBlankReturnValues2() {
        final String str = "Hi";
        final String test = HystrixValidate.notBlank(str, "Message");
        assertSame(str, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray1() {
        String[] array = new String[]{"a", "b"};
        HystrixValidate.noNullElements(array);
        try {
            HystrixValidate.noNullElements((Object[]) null);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            HystrixValidate.noNullElements(array);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("The validated array contains null element at index: 1", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = HystrixValidate.noNullElements(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray2() {
        String[] array = new String[]{"a", "b"};
        HystrixValidate.noNullElements(array, "MSG");
        try {
            HystrixValidate.noNullElements((Object[]) null, "MSG");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            HystrixValidate.noNullElements(array, "MSG");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = HystrixValidate.noNullElements(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection1() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        HystrixValidate.noNullElements(coll);
        try {
            HystrixValidate.noNullElements((Collection<?>) null);
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            HystrixValidate.noNullElements(coll);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("The validated collection contains null element at index: 1", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = HystrixValidate.noNullElements(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection2() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        HystrixValidate.noNullElements(coll, "MSG");
        try {
            HystrixValidate.noNullElements((Collection<?>) null, "MSG");
            fail("Expecting NullPointerHystrixBadRequestException");
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            HystrixValidate.noNullElements(coll, "MSG");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = HystrixValidate.noNullElements(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testConstructor() {
        final Constructor<?>[] cons = HystrixValidate.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPrivate(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(HystrixValidate.class.getModifiers()));
        assertFalse(Modifier.isFinal(HystrixValidate.class.getModifiers()));
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_array() {
        final Object[] array = new Object[2];
        HystrixValidate.validIndex(array, 0, "Broken: ");
        HystrixValidate.validIndex(array, 1, "Broken: ");
        try {
            HystrixValidate.validIndex(array, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(array, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = HystrixValidate.noNullElements(strArray, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void testValidIndex_array() {
        final Object[] array = new Object[2];
        HystrixValidate.validIndex(array, 0);
        HystrixValidate.validIndex(array, 1);
        try {
            HystrixValidate.validIndex(array, -1);
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("The validated array index is invalid: -1", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(array, 2);
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("The validated array index is invalid: 2", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = HystrixValidate.noNullElements(strArray);
        assertSame(strArray, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        HystrixValidate.validIndex(coll, 0, "Broken: ");
        HystrixValidate.validIndex(coll, 1, "Broken: ");
        try {
            HystrixValidate.validIndex(coll, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(coll, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = HystrixValidate.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void testValidIndex_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        HystrixValidate.validIndex(coll, 0);
        HystrixValidate.validIndex(coll, 1);
        try {
            HystrixValidate.validIndex(coll, -1);
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("The validated collection index is invalid: -1", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(coll, 2);
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("The validated collection index is invalid: 2", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = HystrixValidate.validIndex(strColl, 0);
        assertSame(strColl, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_charSequence() {
        final CharSequence str = "Hi";
        HystrixValidate.validIndex(str, 0, "Broken: ");
        HystrixValidate.validIndex(str, 1, "Broken: ");
        try {
            HystrixValidate.validIndex(str, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(str, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String input = "Hi";
        final String test = HystrixValidate.validIndex(input, 0, "Message");
        assertSame(input, test);
    }

    @Test
    public void testValidIndex_charSequence() {
        final CharSequence str = "Hi";
        HystrixValidate.validIndex(str, 0);
        HystrixValidate.validIndex(str, 1);
        try {
            HystrixValidate.validIndex(str, -1);
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("The validated character sequence index is invalid: -1", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(str, 2);
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("The validated character sequence index is invalid: 2", ex.getMessage());
        }

        final String input = "Hi";
        final String test = HystrixValidate.validIndex(input, 0);
        assertSame(input, test);
    }

    @Test
    public void testMatchesPattern() {
        final CharSequence str = "hi";
        HystrixValidate.matchesPattern(str, "[a-z]*");
        try {
            HystrixValidate.matchesPattern(str, "[0-9]*");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The string hi does not match the pattern [0-9]*", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern_withMessage() {
        final CharSequence str = "hi";
        HystrixValidate.matchesPattern(str, "[a-z]*", "Does not match");
        try {
            HystrixValidate.matchesPattern(str, "[0-9]*", "Does not match");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Does not match", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween() {
        HystrixValidate.inclusiveBetween("a", "c", "b");
        try {
            HystrixValidate.inclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_withMessage() {
        HystrixValidate.inclusiveBetween("a", "c", "b", "Error");
        try {
            HystrixValidate.inclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong() {
        HystrixValidate.inclusiveBetween(0, 2, 1);
        HystrixValidate.inclusiveBetween(0, 2, 2);
        try {
            HystrixValidate.inclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong_withMessage() {
        HystrixValidate.inclusiveBetween(0, 2, 1, "Error");
        HystrixValidate.inclusiveBetween(0, 2, 2, "Error");
        try {
            HystrixValidate.inclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble() {
        HystrixValidate.inclusiveBetween(0.1, 2.1, 1.1);
        HystrixValidate.inclusiveBetween(0.1, 2.1, 2.1);
        try {
            HystrixValidate.inclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 6.1 is not in the specified inclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble_withMessage() {
        HystrixValidate.inclusiveBetween(0.1, 2.1, 1.1, "Error");
        HystrixValidate.inclusiveBetween(0.1, 2.1, 2.1, "Error");
        try {
            HystrixValidate.inclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween() {
        HystrixValidate.exclusiveBetween("a", "c", "b");
        try {
            HystrixValidate.exclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            HystrixValidate.exclusiveBetween("0", "5", "5");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_withMessage() {
        HystrixValidate.exclusiveBetween("a", "c", "b", "Error");
        try {
            HystrixValidate.exclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            HystrixValidate.exclusiveBetween("0", "5", "5", "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong() {
        HystrixValidate.exclusiveBetween(0, 2, 1);
        try {
            HystrixValidate.exclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            HystrixValidate.exclusiveBetween(0, 5, 5);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong_withMessage() {
        HystrixValidate.exclusiveBetween(0, 2, 1, "Error");
        try {
            HystrixValidate.exclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            HystrixValidate.exclusiveBetween(0, 5, 5, "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble() {
        HystrixValidate.exclusiveBetween(0.1, 2.1, 1.1);
        try {
            HystrixValidate.exclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 6.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
        try {
            HystrixValidate.exclusiveBetween(0.1, 5.1, 5.1);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("The value 5.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble_withMessage() {
        HystrixValidate.exclusiveBetween(0.1, 2.1, 1.1, "Error");
        try {
            HystrixValidate.exclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            HystrixValidate.exclusiveBetween(0.1, 5.1, 5.1, "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf() {
        HystrixValidate.isInstanceOf(String.class, "hi");
        HystrixValidate.isInstanceOf(Integer.class, 1);
    }

    @Test
    public void testIsInstanceOfExceptionMessage() {
        try {
            HystrixValidate.isInstanceOf(List.class, "hi");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Expected type: java.util.List, actual: java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessage() {
        HystrixValidate.isInstanceOf(String.class, "hi", "Error");
        HystrixValidate.isInstanceOf(Integer.class, 1, "Error");
        try {
            HystrixValidate.isInstanceOf(List.class, "hi", "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessageArgs() {
        HystrixValidate.isInstanceOf(String.class, "hi", "Error %s=%s", "Name", "Value");
        HystrixValidate.isInstanceOf(Integer.class, 1, "Error %s=%s", "Name", "Value");
        try {
            HystrixValidate.isInstanceOf(List.class, "hi", "Error %s=%s", "Name", "Value");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error Name=Value", e.getMessage());
        }
        try {
            HystrixValidate.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, "Value");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error interface java.util.List=Value", e.getMessage());
        }
        try {
            HystrixValidate.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, null);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error interface java.util.List=null", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable() {
        HystrixValidate.isAssignableFrom(CharSequence.class, String.class);
        HystrixValidate.isAssignableFrom(AbstractList.class, ArrayList.class);
    }

    @Test
    public void testIsAssignableExceptionMessage() {
        try {
            HystrixValidate.isAssignableFrom(List.class, String.class);
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Cannot assign a java.lang.String to a java.util.List", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable_withMessage() {
        HystrixValidate.isAssignableFrom(CharSequence.class, String.class, "Error");
        HystrixValidate.isAssignableFrom(AbstractList.class, ArrayList.class, "Error");
        try {
            HystrixValidate.isAssignableFrom(List.class, String.class, "Error");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Error", e.getMessage());
        }
    }

}
