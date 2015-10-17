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

package se.sawano.java.commons.lang.validate.dbc;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit tests from org.apache.commons.lang3.Validate
 */
public class Ensure_Test {

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue1() {
        Ensure.isTrue(true);
        try {
            Ensure.isTrue(false);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("The validated expression is false", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue2() {
        Ensure.isTrue(true, "MSG");
        try {
            Ensure.isTrue(false, "MSG");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue3() {
        Ensure.isTrue(true, "MSG", 6);
        try {
            Ensure.isTrue(false, "MSG", 6);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue4() {
        Ensure.isTrue(true, "MSG", 7);
        try {
            Ensure.isTrue(false, "MSG", 7);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIsTrue5() {
        Ensure.isTrue(true, "MSG", 7.4d);
        try {
            Ensure.isTrue(false, "MSG", 7.4d);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull1() {
        Ensure.notNull(new Object());
        try {
            Ensure.notNull(null);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Ensure.notNull(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings("unused")
    @Test
    public void testNotNull2() {
        Ensure.notNull(new Object(), "MSG");
        try {
            Ensure.notNull(null, "MSG");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Ensure.notNull(str, "Message");
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray1() {
        Ensure.notEmpty(new Object[]{null});
        try {
            Ensure.notEmpty((Object[]) null);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty(new Object[0]);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("The validated array is empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Ensure.notEmpty(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyArray2() {
        Ensure.notEmpty(new Object[]{null}, "MSG");
        try {
            Ensure.notEmpty((Object[]) null, "MSG");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Ensure.notEmpty(new Object[0], "MSG");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Ensure.notEmpty(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection1() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            Ensure.notEmpty((Collection<?>) null);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty(coll);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("The validated collection is empty", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        Ensure.notEmpty(coll);

        final Collection<Integer> test = Ensure.notEmpty(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyCollection2() {
        final Collection<Integer> coll = new ArrayList<Integer>();
        try {
            Ensure.notEmpty((Collection<?>) null, "MSG");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Ensure.notEmpty(coll, "MSG");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        coll.add(Integer.valueOf(8));
        Ensure.notEmpty(coll, "MSG");

        final Collection<Integer> test = Ensure.notEmpty(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap1() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Ensure.notEmpty((Map<?, ?>) null);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty(map);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("The validated map is empty", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        Ensure.notEmpty(map);

        final Map<String, Integer> test = Ensure.notEmpty(map);
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyMap2() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Ensure.notEmpty((Map<?, ?>) null, "MSG");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Ensure.notEmpty(map, "MSG");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        map.put("ll", Integer.valueOf(8));
        Ensure.notEmpty(map, "MSG");

        final Map<String, Integer> test = Ensure.notEmpty(map, "Message");
        assertSame(map, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString1() {
        Ensure.notEmpty("hjl");
        try {
            Ensure.notEmpty((String) null);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty("");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("The validated character sequence is empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Ensure.notEmpty(str);
        assertSame(str, testStr);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotEmptyString2() {
        Ensure.notEmpty("a", "MSG");
        try {
            Ensure.notEmpty((String) null, "MSG");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }
        try {
            Ensure.notEmpty("", "MSG");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Ensure.notEmpty(str, "Message");
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
            Ensure.notBlank(string);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException e) {
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
            Ensure.notBlank(string, "Message");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException e) {
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
            Ensure.notBlank(string);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
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
            Ensure.notBlank(string);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
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
            Ensure.notBlank(string);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
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
            Ensure.notBlank(string, "Message");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
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
            Ensure.notBlank(string, "Message");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
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
            Ensure.notBlank(string, "Message");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
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
        Ensure.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        Ensure.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        Ensure.notBlank(string);

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringShouldNotThrow() {
        //given
        final String string = "abc";

        //when
        Ensure.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithWhitespacesShouldNotThrow() {
        //given
        final String string = "  abc   ";

        //when
        Ensure.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankMsgNotBlankStringWithNewlinesShouldNotThrow() {
        //given
        final String string = " \n \t abc \r \n ";

        //when
        Ensure.notBlank(string, "Message");

        //then should not throw
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNotBlankReturnValues1() {
        final String str = "Hi";
        final String test = Ensure.notBlank(str);
        assertSame(str, test);
    }

    @Test
    public void testNotBlankReturnValues2() {
        final String str = "Hi";
        final String test = Ensure.notBlank(str, "Message");
        assertSame(str, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray1() {
        String[] array = new String[]{"a", "b"};
        Ensure.noNullElements(array);
        try {
            Ensure.noNullElements((Object[]) null);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            Ensure.noNullElements(array);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("The validated array contains null element at index: 1", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = Ensure.noNullElements(array);
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsArray2() {
        String[] array = new String[]{"a", "b"};
        Ensure.noNullElements(array, "MSG");
        try {
            Ensure.noNullElements((Object[]) null, "MSG");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        array[1] = null;
        try {
            Ensure.noNullElements(array, "MSG");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        array = new String[]{"a", "b"};
        final String[] test = Ensure.noNullElements(array, "Message");
        assertSame(array, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection1() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        Ensure.noNullElements(coll);
        try {
            Ensure.noNullElements((Collection<?>) null);
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            Ensure.noNullElements(coll);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("The validated collection contains null element at index: 1", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = Ensure.noNullElements(coll);
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testNoNullElementsCollection2() {
        final List<String> coll = new ArrayList<String>();
        coll.add("a");
        coll.add("b");
        Ensure.noNullElements(coll, "MSG");
        try {
            Ensure.noNullElements((Collection<?>) null, "MSG");
            fail("Expecting NullPointerEnsuranceException");
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }
        coll.set(1, null);
        try {
            Ensure.noNullElements(coll, "MSG");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("MSG", ex.getMessage());
        }

        coll.set(1, "b");
        final List<String> test = Ensure.noNullElements(coll, "Message");
        assertSame(coll, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testConstructor() {
        final Constructor<?>[] cons = Ensure.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPrivate(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(Ensure.class.getModifiers()));
        assertFalse(Modifier.isFinal(Ensure.class.getModifiers()));
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_array() {
        final Object[] array = new Object[2];
        Ensure.validIndex(array, 0, "Broken: ");
        Ensure.validIndex(array, 1, "Broken: ");
        try {
            Ensure.validIndex(array, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Ensure.validIndex(array, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Ensure.noNullElements(strArray, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void testValidIndex_array() {
        final Object[] array = new Object[2];
        Ensure.validIndex(array, 0);
        Ensure.validIndex(array, 1);
        try {
            Ensure.validIndex(array, -1);
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("The validated array index is invalid: -1", ex.getMessage());
        }
        try {
            Ensure.validIndex(array, 2);
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("The validated array index is invalid: 2", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Ensure.noNullElements(strArray);
        assertSame(strArray, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        Ensure.validIndex(coll, 0, "Broken: ");
        Ensure.validIndex(coll, 1, "Broken: ");
        try {
            Ensure.validIndex(coll, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Ensure.validIndex(coll, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Ensure.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void testValidIndex_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        Ensure.validIndex(coll, 0);
        Ensure.validIndex(coll, 1);
        try {
            Ensure.validIndex(coll, -1);
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("The validated collection index is invalid: -1", ex.getMessage());
        }
        try {
            Ensure.validIndex(coll, 2);
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("The validated collection index is invalid: 2", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Ensure.validIndex(strColl, 0);
        assertSame(strColl, test);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @Test
    public void testValidIndex_withMessage_charSequence() {
        final CharSequence str = "Hi";
        Ensure.validIndex(str, 0, "Broken: ");
        Ensure.validIndex(str, 1, "Broken: ");
        try {
            Ensure.validIndex(str, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Ensure.validIndex(str, 2, "Broken: ");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }

        final String input = "Hi";
        final String test = Ensure.validIndex(input, 0, "Message");
        assertSame(input, test);
    }

    @Test
    public void testValidIndex_charSequence() {
        final CharSequence str = "Hi";
        Ensure.validIndex(str, 0);
        Ensure.validIndex(str, 1);
        try {
            Ensure.validIndex(str, -1);
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("The validated character sequence index is invalid: -1", ex.getMessage());
        }
        try {
            Ensure.validIndex(str, 2);
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("The validated character sequence index is invalid: 2", ex.getMessage());
        }

        final String input = "Hi";
        final String test = Ensure.validIndex(input, 0);
        assertSame(input, test);
    }

    @Test
    public void testMatchesPattern() {
        final CharSequence str = "hi";
        Ensure.matchesPattern(str, "[a-z]*");
        try {
            Ensure.matchesPattern(str, "[0-9]*");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The string hi does not match the pattern [0-9]*", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern_withMessage() {
        final CharSequence str = "hi";
        Ensure.matchesPattern(str, "[a-z]*", "Does not match");
        try {
            Ensure.matchesPattern(str, "[0-9]*", "Does not match");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Does not match", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween() {
        Ensure.inclusiveBetween("a", "c", "b");
        try {
            Ensure.inclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_withMessage() {
        Ensure.inclusiveBetween("a", "c", "b", "Error");
        try {
            Ensure.inclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong() {
        Ensure.inclusiveBetween(0, 2, 1);
        Ensure.inclusiveBetween(0, 2, 2);
        try {
            Ensure.inclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 6 is not in the specified inclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong_withMessage() {
        Ensure.inclusiveBetween(0, 2, 1, "Error");
        Ensure.inclusiveBetween(0, 2, 2, "Error");
        try {
            Ensure.inclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble() {
        Ensure.inclusiveBetween(0.1, 2.1, 1.1);
        Ensure.inclusiveBetween(0.1, 2.1, 2.1);
        try {
            Ensure.inclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 6.1 is not in the specified inclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble_withMessage() {
        Ensure.inclusiveBetween(0.1, 2.1, 1.1, "Error");
        Ensure.inclusiveBetween(0.1, 2.1, 2.1, "Error");
        try {
            Ensure.inclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween() {
        Ensure.exclusiveBetween("a", "c", "b");
        try {
            Ensure.exclusiveBetween("0", "5", "6");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            Ensure.exclusiveBetween("0", "5", "5");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_withMessage() {
        Ensure.exclusiveBetween("a", "c", "b", "Error");
        try {
            Ensure.exclusiveBetween("0", "5", "6", "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Ensure.exclusiveBetween("0", "5", "5", "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong() {
        Ensure.exclusiveBetween(0, 2, 1);
        try {
            Ensure.exclusiveBetween(0, 5, 6);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 6 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
        try {
            Ensure.exclusiveBetween(0, 5, 5);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 5 is not in the specified exclusive range of 0 to 5", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong_withMessage() {
        Ensure.exclusiveBetween(0, 2, 1, "Error");
        try {
            Ensure.exclusiveBetween(0, 5, 6, "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Ensure.exclusiveBetween(0, 5, 5, "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble() {
        Ensure.exclusiveBetween(0.1, 2.1, 1.1);
        try {
            Ensure.exclusiveBetween(0.1, 5.1, 6.1);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 6.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
        try {
            Ensure.exclusiveBetween(0.1, 5.1, 5.1);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("The value 5.1 is not in the specified exclusive range of 0.1 to 5.1", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble_withMessage() {
        Ensure.exclusiveBetween(0.1, 2.1, 1.1, "Error");
        try {
            Ensure.exclusiveBetween(0.1, 5.1, 6.1, "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
        try {
            Ensure.exclusiveBetween(0.1, 5.1, 5.1, "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf() {
        Ensure.isInstanceOf(String.class, "hi");
        Ensure.isInstanceOf(Integer.class, 1);
    }

    @Test
    public void testIsInstanceOfExceptionMessage() {
        try {
            Ensure.isInstanceOf(List.class, "hi");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Expected type: java.util.List, actual: java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessage() {
        Ensure.isInstanceOf(String.class, "hi", "Error");
        Ensure.isInstanceOf(Integer.class, 1, "Error");
        try {
            Ensure.isInstanceOf(List.class, "hi", "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_withMessageArgs() {
        Ensure.isInstanceOf(String.class, "hi", "Error %s=%s", "Name", "Value");
        Ensure.isInstanceOf(Integer.class, 1, "Error %s=%s", "Name", "Value");
        try {
            Ensure.isInstanceOf(List.class, "hi", "Error %s=%s", "Name", "Value");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error Name=Value", e.getMessage());
        }
        try {
            Ensure.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, "Value");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error interface java.util.List=Value", e.getMessage());
        }
        try {
            Ensure.isInstanceOf(List.class, "hi", "Error %s=%s", List.class, null);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error interface java.util.List=null", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable() {
        Ensure.isAssignableFrom(CharSequence.class, String.class);
        Ensure.isAssignableFrom(AbstractList.class, ArrayList.class);
    }

    @Test
    public void testIsAssignableExceptionMessage() {
        try {
            Ensure.isAssignableFrom(List.class, String.class);
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Cannot assign a java.lang.String to a java.util.List", e.getMessage());
        }
    }

    @Test
    public void testIsAssignable_withMessage() {
        Ensure.isAssignableFrom(CharSequence.class, String.class, "Error");
        Ensure.isAssignableFrom(AbstractList.class, ArrayList.class, "Error");
        try {
            Ensure.isAssignableFrom(List.class, String.class, "Error");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Error", e.getMessage());
        }
    }

}
