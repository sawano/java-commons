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

package se.sawano.java.commons.lang.dbc;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit tests from org.apache.commons.lang3.Validate
 */
public class Require_Test {

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue1() {
        Require.isTrue(true);
        try {
            Require.isTrue(false);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("The validated expression is false", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue2() {
        Require.isTrue(true, "MSG");
        try {
            Require.isTrue(false, "MSG");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue3() {
        Require.isTrue(true, "MSG", 6);
        try {
            Require.isTrue(false, "MSG", 6);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue4() {
        Require.isTrue(true, "MSG", 7);
        try {
            Require.isTrue(false, "MSG", 7);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue5() {
        Require.isTrue(true, "MSG", 7.4d);
        try {
            Require.isTrue(false, "MSG", 7.4d);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull1() {
        Require.notNull(new Object());
        try {
            Require.notNull(null);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Require.notNull(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull2() {
        Require.notNull(new Object(), "MSG");
        try {
            Require.notNull(null, "MSG");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Require.notNull(str, "Message");
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray1() {
        Require.notEmpty(new Object[]{null});
        try {
            Require.notEmpty((Object[]) null);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }
        try {
            Require.notEmpty(new Object[0]);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Require.notEmpty(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray2() {
        Require.notEmpty(new Object[]{null}, "MSG");
        try {
            Require.notEmpty((Object[]) null, "MSG");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Require.notEmpty(new Object[0], "MSG");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Require.notEmpty(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection1() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            Require.notEmpty((Collection<?>) null);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        try {
            Require.notEmpty(coll);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        Require.notEmpty(coll);

        final Collection<Integer> test = Require.notEmpty(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection2() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            Require.notEmpty((Collection<?>) null, "MSG");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Require.notEmpty(coll, "MSG");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        Require.notEmpty(coll, "MSG");

        final Collection<Integer> test = Require.notEmpty(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap1() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Require.notEmpty((Map<?, ?>) null);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        try {
            Require.notEmpty(map);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        Require.notEmpty(map);

        final Map<String, Integer> test = Require.notEmpty(map);
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap2() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Require.notEmpty((Map<?, ?>) null, "MSG");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Require.notEmpty(map, "MSG");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        Require.notEmpty(map, "MSG");

        final Map<String, Integer> test = Require.notEmpty(map, "Message");
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString1() {
        Require.notEmpty("hjl");
        try {
            Require.notEmpty((String) null);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }
        try {
            Require.notEmpty("");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Require.notEmpty(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString2() {
        Require.notEmpty("a", "MSG");
        try {
            Require.notEmpty((String) null, "MSG");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Require.notEmpty("", "MSG");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Require.notEmpty(str, "Message");
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
            Require.notBlank(string);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException e) {
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
            Require.notBlank(string, "Message");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException e) {
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
            Require.notBlank(string);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
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
            Require.notBlank(string);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
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
            Require.notBlank(string);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
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
            Require.notBlank(string, "Message");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
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
            Require.notBlank(string, "Message");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
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
            Require.notBlank(string, "Message");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
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
        Require.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        Require.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        Require.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringShouldNotThrow() {
        //given
        final String string = "abc";

        //when
        Require.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        Require.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        Require.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankReturnValues1() {
        final String str = "Hi";
        final String test = Require.notBlank(str);
        assertSame(str, test);
    }

    @Test
    public void testNotBlankReturnValues2() {
        final String str = "Hi";
        final String test = Require.notBlank(str, "Message");
        assertSame(str, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray1() {
        String[] array = new String[]{"a", "b"};
        Require.noNullElements(array);
        try {
            Require.noNullElements((Object[]) null);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            Require.noNullElements(array);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("The validated array contains null element at index: 1", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = Require.noNullElements(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray2() {
        String[] array = new String[]{"a", "b"};
        Require.noNullElements(array, "MSG");
        try {
            Require.noNullElements((Object[]) null, "MSG");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            Require.noNullElements(array, "MSG");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = Require.noNullElements(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection1() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        Require.noNullElements(coll);
        try {
            Require.noNullElements((Collection<?>) null);
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            Require.noNullElements(coll);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("The validated collection contains null element at index: 1", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = Require.noNullElements(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection2() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        Require.noNullElements(coll, "MSG");
        try {
            Require.noNullElements((Collection<?>) null, "MSG");
            fail("Expecting NullPointerRequirementException");
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            Require.noNullElements(coll, "MSG");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = Require.noNullElements(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testConstructor() {
        final Constructor<?>[] cons = Require.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPrivate(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(Require.class.getModifiers()));
        assertFalse(Modifier.isFinal(Require.class.getModifiers()));
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_array() {
        final Object[] array = new Object[2];
        Require.validIndex(array, 0, "Broken: ");
        Require.validIndex(array, 1, "Broken: ");
        try {
            Require.validIndex(array, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Require.validIndex(array, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Require.noNullElements(strArray, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void testValidIndex_array() {
        final Object[] array = new Object[2];
        Require.validIndex(array, 0);
        Require.validIndex(array, 1);
        try {
            Require.validIndex(array, -1);
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("The validated array index is invalid: -1", ex.getMessage());
        }
        try {
            Require.validIndex(array, 2);
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("The validated array index is invalid: 2", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Require.noNullElements(strArray);
        assertSame(strArray, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        Require.validIndex(coll, 0, "Broken: ");
        Require.validIndex(coll, 1, "Broken: ");
        try {
            Require.validIndex(coll, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Require.validIndex(coll, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Require.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void testValidIndex_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        Require.validIndex(coll, 0);
        Require.validIndex(coll, 1);
        try {
            Require.validIndex(coll, -1);
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("The validated collection index is invalid: -1", ex.getMessage());
        }
        try {
            Require.validIndex(coll, 2);
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("The validated collection index is invalid: 2", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Require.validIndex(strColl, 0);
        assertSame(strColl, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_charSequence() {
        final CharSequence str = "Hi";
        Require.validIndex(str, 0, "Broken: ");
        Require.validIndex(str, 1, "Broken: ");
        try {
            Require.validIndex(str, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Require.validIndex(str, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String input = "Hi";
        final String test = Require.validIndex(input, 0, "Message");
        assertSame(input, test);
    }

    @Test
    public void testValidIndex_charSequence() {
        final CharSequence str = "Hi";
        Require.validIndex(str, 0);
        Require.validIndex(str, 1);
        try {
            Require.validIndex(str, -1);
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("The validated character sequence index is invalid: -1", ex.getMessage());
        }
        try {
            Require.validIndex(str, 2);
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("The validated character sequence index is invalid: 2", ex.getMessage());
        }

        final String input = "Hi";
        final String test = Require.validIndex(input, 0);
        assertSame(input, test);
    }

    @Test
    public void testMatchesPattern() {
        final CharSequence str = "hi";
        Require.matchesPattern(str, "[a-z]*");
        try {
            Require.matchesPattern(str, "[0-9]*");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The string hi does not match the pattern [0-9]*", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern_withMessage() {
        final CharSequence str = "hi";
        Require.matchesPattern(str, "[a-z]*", "Does not match");
        try {
            Require.matchesPattern(str, "[0-9]*", "Does not match");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Does not match", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween() {
        Require.inclusiveBetween("a", "c", "b");
        try {
            Require.inclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_withMessage() {
        Require.inclusiveBetween("a", "c", "b", "Error");
        try {
            Require.inclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong() {
        Require.inclusiveBetween(0, 2, 1);
        Require.inclusiveBetween(0, 2, 2);
        try {
            Require.inclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong_withMessage() {
        Require.inclusiveBetween(0, 2, 1, "Error");
        Require.inclusiveBetween(0, 2, 2, "Error");
        try {
            Require.inclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble() {
        Require.inclusiveBetween(0.1, 2.1, 1.1);
        Require.inclusiveBetween(0.1, 2.1, 2.1);
        try {
            Require.inclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 6.1 is not in the specified inclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble_withMessage() {
        Require.inclusiveBetween(0.1, 2.1, 1.1, "Error");
        Require.inclusiveBetween(0.1, 2.1, 2.1, "Error");
        try {
            Require.inclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween() {
        Require.exclusiveBetween("a", "c", "b");
        try {
            Require.exclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            Require.exclusiveBetween("0", "5", "5");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_withMessage() {
        Require.exclusiveBetween("a", "c", "b", "Error");
        try {
            Require.exclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Require.exclusiveBetween("0", "5", "5", "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong() {
        Require.exclusiveBetween(0, 2, 1);
        try {
            Require.exclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            Require.exclusiveBetween(0, 5, 5);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong_withMessage() {
        Require.exclusiveBetween(0, 2, 1, "Error");
        try {
            Require.exclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Require.exclusiveBetween(0, 5, 5, "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble() {
        Require.exclusiveBetween(0.1, 2.1, 1.1);
        try {
            Require.exclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 6.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
        try {
            Require.exclusiveBetween(0.1, 5.1, 5.1);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("The value 5.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble_withMessage() {
        Require.exclusiveBetween(0.1, 2.1, 1.1, "Error");
        try {
            Require.exclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Require.exclusiveBetween(0.1, 5.1, 5.1, "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf() {
        Require.isInstanceOf(String.class, "hi");
        Require.isInstanceOf(Integer.class, 1);
    }

    @Test
    public void testIsInstanceOfExceptionMessage() {
        try {
            Require.isInstanceOf(List.class, "hi");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Expected type: java.util.List, actual: java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessage() {
        Require.isInstanceOf(String.class, "hi", "Error");
        Require.isInstanceOf(Integer.class, 1, "Error");
        try {
            Require.isInstanceOf(List.class, "hi", "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessageArgs() {
        Require.isInstanceOf(String.class, "hi", "Error %s=%s", "Name", "Value");
        Require.isInstanceOf(Integer.class, 1, "Error %s=%s", "Name", "Value");
        try {
            Require.isInstanceOf(List.class, "hi", "Error %s=%s", "Name", "Value");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error Name=Value", e.getMessage());
        }
        try {
            Require.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, "Value");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error interface java.util.List=Value", e.getMessage());
        }
        try {
            Require.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, null);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error interface java.util.List=null", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable() {
        Require.isAssignableFrom(CharSequence.class, String.class);
        Require.isAssignableFrom(AbstractList.class, ArrayList.class);
    }

    @Test
    public void testIsAssignableExceptionMessage() {
        try {
            Require.isAssignableFrom(List.class, String.class);
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Cannot assign a java.lang.String to a java.util.List", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable_withMessage() {
        Require.isAssignableFrom(CharSequence.class, String.class, "Error");
        Require.isAssignableFrom(AbstractList.class, ArrayList.class, "Error");
        try {
            Require.isAssignableFrom(List.class, String.class, "Error");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Error", e.getMessage());
        }
    }

}
