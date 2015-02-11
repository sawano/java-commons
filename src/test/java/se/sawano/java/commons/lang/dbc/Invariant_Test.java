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
public class Invariant_Test {

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue1() {
        Invariant.isTrue(true);
        try {
            Invariant.isTrue(false);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated expression is false", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue2() {
        Invariant.isTrue(true, "MSG");
        try {
            Invariant.isTrue(false, "MSG");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue3() {
        Invariant.isTrue(true, "MSG", 6);
        try {
            Invariant.isTrue(false, "MSG", 6);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue4() {
        Invariant.isTrue(true, "MSG", 7);
        try {
            Invariant.isTrue(false, "MSG", 7);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue5() {
        Invariant.isTrue(true, "MSG", 7.4d);
        try {
            Invariant.isTrue(false, "MSG", 7.4d);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull1() {
        Invariant.notNull(new Object());
        try {
            Invariant.notNull(null);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Invariant.notNull(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull2() {
        Invariant.notNull(new Object(), "MSG");
        try {
            Invariant.notNull(null, "MSG");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Invariant.notNull(str, "Message");
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray1() {
        Invariant.notEmpty(new Object[]{null});
        try {
            Invariant.notEmpty((Object[]) null);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty(new Object[0]);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Invariant.notEmpty(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray2() {
        Invariant.notEmpty(new Object[]{null}, "MSG");
        try {
            Invariant.notEmpty((Object[]) null, "MSG");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Invariant.notEmpty(new Object[0], "MSG");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Invariant.notEmpty(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection1() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            Invariant.notEmpty((Collection<?>) null);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty(coll);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        Invariant.notEmpty(coll);

        final Collection<Integer> test = Invariant.notEmpty(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection2() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            Invariant.notEmpty((Collection<?>) null, "MSG");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Invariant.notEmpty(coll, "MSG");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        Invariant.notEmpty(coll, "MSG");

        final Collection<Integer> test = Invariant.notEmpty(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap1() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Invariant.notEmpty((Map<?, ?>) null);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty(map);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        Invariant.notEmpty(map);

        final Map<String, Integer> test = Invariant.notEmpty(map);
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap2() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Invariant.notEmpty((Map<?, ?>) null, "MSG");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Invariant.notEmpty(map, "MSG");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        Invariant.notEmpty(map, "MSG");

        final Map<String, Integer> test = Invariant.notEmpty(map, "Message");
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString1() {
        Invariant.notEmpty("hjl");
        try {
            Invariant.notEmpty((String) null);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty("");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Invariant.notEmpty(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString2() {
        Invariant.notEmpty("a", "MSG");
        try {
            Invariant.notEmpty((String) null, "MSG");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Invariant.notEmpty("", "MSG");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Invariant.notEmpty(str, "Message");
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
            Invariant.notBlank(string);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException e) {
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
            Invariant.notBlank(string, "Message");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException e) {
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
            Invariant.notBlank(string);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
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
            Invariant.notBlank(string);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
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
            Invariant.notBlank(string);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
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
            Invariant.notBlank(string, "Message");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
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
            Invariant.notBlank(string, "Message");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
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
            Invariant.notBlank(string, "Message");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
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
        Invariant.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        Invariant.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        Invariant.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringShouldNotThrow() {
        //given
        final String string = "abc";

        //when
        Invariant.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        Invariant.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        Invariant.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankReturnValues1() {
        final String str = "Hi";
        final String test = Invariant.notBlank(str);
        assertSame(str, test);
    }

    @Test
    public void testNotBlankReturnValues2() {
        final String str = "Hi";
        final String test = Invariant.notBlank(str, "Message");
        assertSame(str, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray1() {
        String[] array = new String[]{"a", "b"};
        Invariant.noNullElements(array);
        try {
            Invariant.noNullElements((Object[]) null);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            Invariant.noNullElements(array);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated array contains null element at index: 1", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = Invariant.noNullElements(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray2() {
        String[] array = new String[]{"a", "b"};
        Invariant.noNullElements(array, "MSG");
        try {
            Invariant.noNullElements((Object[]) null, "MSG");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            Invariant.noNullElements(array, "MSG");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = Invariant.noNullElements(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection1() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        Invariant.noNullElements(coll);
        try {
            Invariant.noNullElements((Collection<?>) null);
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            Invariant.noNullElements(coll);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated collection contains null element at index: 1", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = Invariant.noNullElements(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection2() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        Invariant.noNullElements(coll, "MSG");
        try {
            Invariant.noNullElements((Collection<?>) null, "MSG");
            fail("Expecting NullPointerInvarianceException");
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            Invariant.noNullElements(coll, "MSG");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = Invariant.noNullElements(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testConstructor() {
        final Constructor<?>[] cons = Invariant.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPrivate(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(Invariant.class.getModifiers()));
        assertFalse(Modifier.isFinal(Invariant.class.getModifiers()));
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_array() {
        final Object[] array = new Object[2];
        Invariant.validIndex(array, 0, "Broken: ");
        Invariant.validIndex(array, 1, "Broken: ");
        try {
            Invariant.validIndex(array, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Invariant.validIndex(array, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Invariant.noNullElements(strArray, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void testValidIndex_array() {
        final Object[] array = new Object[2];
        Invariant.validIndex(array, 0);
        Invariant.validIndex(array, 1);
        try {
            Invariant.validIndex(array, -1);
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("The validated array index is invalid: -1", ex.getMessage());
        }
        try {
            Invariant.validIndex(array, 2);
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("The validated array index is invalid: 2", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Invariant.noNullElements(strArray);
        assertSame(strArray, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        Invariant.validIndex(coll, 0, "Broken: ");
        Invariant.validIndex(coll, 1, "Broken: ");
        try {
            Invariant.validIndex(coll, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Invariant.validIndex(coll, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Invariant.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void testValidIndex_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        Invariant.validIndex(coll, 0);
        Invariant.validIndex(coll, 1);
        try {
            Invariant.validIndex(coll, -1);
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("The validated collection index is invalid: -1", ex.getMessage());
        }
        try {
            Invariant.validIndex(coll, 2);
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("The validated collection index is invalid: 2", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Invariant.validIndex(strColl, 0);
        assertSame(strColl, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_charSequence() {
        final CharSequence str = "Hi";
        Invariant.validIndex(str, 0, "Broken: ");
        Invariant.validIndex(str, 1, "Broken: ");
        try {
            Invariant.validIndex(str, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Invariant.validIndex(str, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String input = "Hi";
        final String test = Invariant.validIndex(input, 0, "Message");
        assertSame(input, test);
    }

    @Test
    public void testValidIndex_charSequence() {
        final CharSequence str = "Hi";
        Invariant.validIndex(str, 0);
        Invariant.validIndex(str, 1);
        try {
            Invariant.validIndex(str, -1);
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("The validated character sequence index is invalid: -1", ex.getMessage());
        }
        try {
            Invariant.validIndex(str, 2);
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("The validated character sequence index is invalid: 2", ex.getMessage());
        }

        final String input = "Hi";
        final String test = Invariant.validIndex(input, 0);
        assertSame(input, test);
    }

    @Test
    public void testMatchesPattern() {
        final CharSequence str = "hi";
        Invariant.matchesPattern(str, "[a-z]*");
        try {
            Invariant.matchesPattern(str, "[0-9]*");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The string hi does not match the pattern [0-9]*", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern_withMessage() {
        final CharSequence str = "hi";
        Invariant.matchesPattern(str, "[a-z]*", "Does not match");
        try {
            Invariant.matchesPattern(str, "[0-9]*", "Does not match");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Does not match", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween() {
        Invariant.inclusiveBetween("a", "c", "b");
        try {
            Invariant.inclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_withMessage() {
        Invariant.inclusiveBetween("a", "c", "b", "Error");
        try {
            Invariant.inclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong() {
        Invariant.inclusiveBetween(0, 2, 1);
        Invariant.inclusiveBetween(0, 2, 2);
        try {
            Invariant.inclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong_withMessage() {
        Invariant.inclusiveBetween(0, 2, 1, "Error");
        Invariant.inclusiveBetween(0, 2, 2, "Error");
        try {
            Invariant.inclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble() {
        Invariant.inclusiveBetween(0.1, 2.1, 1.1);
        Invariant.inclusiveBetween(0.1, 2.1, 2.1);
        try {
            Invariant.inclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 6.1 is not in the specified inclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble_withMessage() {
        Invariant.inclusiveBetween(0.1, 2.1, 1.1, "Error");
        Invariant.inclusiveBetween(0.1, 2.1, 2.1, "Error");
        try {
            Invariant.inclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween() {
        Invariant.exclusiveBetween("a", "c", "b");
        try {
            Invariant.exclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            Invariant.exclusiveBetween("0", "5", "5");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_withMessage() {
        Invariant.exclusiveBetween("a", "c", "b", "Error");
        try {
            Invariant.exclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Invariant.exclusiveBetween("0", "5", "5", "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong() {
        Invariant.exclusiveBetween(0, 2, 1);
        try {
            Invariant.exclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            Invariant.exclusiveBetween(0, 5, 5);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong_withMessage() {
        Invariant.exclusiveBetween(0, 2, 1, "Error");
        try {
            Invariant.exclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Invariant.exclusiveBetween(0, 5, 5, "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble() {
        Invariant.exclusiveBetween(0.1, 2.1, 1.1);
        try {
            Invariant.exclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 6.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
        try {
            Invariant.exclusiveBetween(0.1, 5.1, 5.1);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("The value 5.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble_withMessage() {
        Invariant.exclusiveBetween(0.1, 2.1, 1.1, "Error");
        try {
            Invariant.exclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Invariant.exclusiveBetween(0.1, 5.1, 5.1, "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf() {
        Invariant.isInstanceOf(String.class, "hi");
        Invariant.isInstanceOf(Integer.class, 1);
    }

    @Test
    public void testIsInstanceOfExceptionMessage() {
        try {
            Invariant.isInstanceOf(List.class, "hi");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Expected type: java.util.List, actual: java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessage() {
        Invariant.isInstanceOf(String.class, "hi", "Error");
        Invariant.isInstanceOf(Integer.class, 1, "Error");
        try {
            Invariant.isInstanceOf(List.class, "hi", "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessageArgs() {
        Invariant.isInstanceOf(String.class, "hi", "Error %s=%s", "Name", "Value");
        Invariant.isInstanceOf(Integer.class, 1, "Error %s=%s", "Name", "Value");
        try {
            Invariant.isInstanceOf(List.class, "hi", "Error %s=%s", "Name", "Value");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error Name=Value", e.getMessage());
        }
        try {
            Invariant.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, "Value");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error interface java.util.List=Value", e.getMessage());
        }
        try {
            Invariant.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, null);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error interface java.util.List=null", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable() {
        Invariant.isAssignableFrom(CharSequence.class, String.class);
        Invariant.isAssignableFrom(AbstractList.class, ArrayList.class);
    }

    @Test
    public void testIsAssignableExceptionMessage() {
        try {
            Invariant.isAssignableFrom(List.class, String.class);
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Cannot assign a java.lang.String to a java.util.List", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable_withMessage() {
        Invariant.isAssignableFrom(CharSequence.class, String.class, "Error");
        Invariant.isAssignableFrom(AbstractList.class, ArrayList.class, "Error");
        try {
            Invariant.isAssignableFrom(List.class, String.class, "Error");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

}
