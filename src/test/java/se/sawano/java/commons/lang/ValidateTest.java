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

package se.sawano.java.commons.lang;

import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.junit.Assert.*;

/**
 * These are tests that are not present in Apache commons.
 */
public class ValidateTest {

    @Test
    public void should_verify_is_false() throws Exception {
        Validate.isFalse(false);
        Validate.isFalse(false, "Test %d", 123L);
        Validate.isFalse(false, "Test %.0f", 123D);
        Validate.isFalse(false, "Test %s, %s", "A", "B");
        Validate.isFalse(false, "Test error message");

        try {
            Validate.isFalse(true);
            fail();
        } catch (IllegalArgumentValidationException e) {
        }
        try {
            Validate.isFalse(true, "Test %d", 123L);
            fail();
        } catch (IllegalArgumentValidationException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Validate.isFalse(true, "Test %.0f", 123D);
            fail();
        } catch (IllegalArgumentValidationException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Validate.isFalse(true, "Test %s, %s", "A", "B");
            fail();
        } catch (IllegalArgumentValidationException e) {
            assertEquals("Test A, B", e.getMessage());
        }
        try {
            Validate.isFalse(true, "Test error message");
            fail();
        } catch (IllegalArgumentValidationException e) {
            assertEquals("Test error message", e.getMessage());
        }
    }

    @Test
    public void should_verify_is_true() {
        Validate.isTrue(true, "Must be %s", true);

        try {
            Validate.isTrue(false, "Must be %s", true);
            fail();
        } catch (final IllegalArgumentValidationException ex) {
            assertEquals("Must be true", ex.getMessage());
        }
    }

    @Test
    public void should_verify_not_null() {
        Validate.notNull(new Object(), "Cannot be %s", "null");

        try {
            Validate.notNull(null, "Cannot be %s", "null");
            fail();
        } catch (final NullPointerValidationException ex) {
            assertEquals("Cannot be null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Validate.notNull(str, "Message");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_empty_array() {
        Validate.notEmpty(new Object[]{null}, "Must not be %s", "empty");

        try {
            Validate.notEmpty((Object[]) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Validate.notEmpty(new Object[0], "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Validate.notEmpty(array, "Message");
        assertSame(array, test);
    }

    @Test
    public void should_verify_not_empty_collection() {
        try {
            Validate.notEmpty((Collection<?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Validate.notEmpty(emptyList(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Collection<Integer> coll = singletonList(8);
        Validate.notEmpty(coll, "Must not be %s", "empty");

        final Collection<Integer> test = Validate.notEmpty(coll, "Must not be %s", "empty");
        assertSame(coll, test);
    }

    @Test
    public void should_verify_not_empty_map() {
        try {
            Validate.notEmpty((Map<?, ?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Validate.notEmpty(emptyMap(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Map<String, Integer> map = Collections.singletonMap("ll", 8);
        Validate.notEmpty(map, "Must not be %s", "empty");

        final Map<String, Integer> test = Validate.notEmpty(map, "Must not be %s", "empty");
        assertSame(map, test);
    }

    @Test
    public void should_verify_not_empty_char_sequence() {
        Validate.notEmpty("a", "Must not be %s", "empty");

        try {
            Validate.notEmpty((String) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Validate.notEmpty("", "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentValidationException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Validate.notEmpty(str, "Must not be %s", "empty");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_blank() {
        final String str = "a";
        final String testStr = Validate.notBlank(str, "Must not be %s", "blank");
        assertSame(str, testStr);

        try {
            Validate.notBlank(null, "Must not be %s", "blank");
            fail();
        } catch (NullPointerValidationException e) {
            assertEquals("Must not be blank", e.getMessage());
        }

        try {
            Validate.notBlank("", "Must not be %s", "blank");
            fail();
        } catch (IllegalArgumentValidationException e) {
            assertEquals("Must not be blank", e.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_collection() {
        final Collection<String> collection = asList("a", "b");
        final Collection<String> ret = Validate.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Validate.noNullElements((Collection<?>) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerValidationException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Validate.noNullElements(asList("a", null), "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentValidationException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_array_with_message_arguments() {
        final String[] collection = new String[]{"a", "b"};
        final String[] ret = Validate.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Validate.noNullElements((String[]) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerValidationException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Validate.noNullElements(new String[]{"a", null}, "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentValidationException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_valid_index_array() {
        final Object[] array = new Object[2];
        Validate.validIndex(array, 0, "Broken %s", "index");
        Validate.validIndex(array, 1, "Broken %s", "index");

        try {
            Validate.validIndex(array, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsValidationException");
        } catch (final IndexOutOfBoundsValidationException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Validate.validIndex(array, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsValidationException");
        } catch (final IndexOutOfBoundsValidationException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Validate.validIndex(strArray, 0, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void should_verify_valid_index_collection() {
        final Collection<String> coll = new ArrayList<String>();
        coll.add(null);
        coll.add(null);
        Validate.validIndex(coll, 0, "Broken: ");
        Validate.validIndex(coll, 1, "Broken: ");
        try {
            Validate.validIndex(coll, -1, "Broken: ");
            fail("Expecting IndexOutOfBoundsValidationException");
        } catch (final IndexOutOfBoundsValidationException ex) {
            assertEquals("Broken: ", ex.getMessage());
        }
        try {
            Validate.validIndex(coll, 2, "Broken: %s", "bad index");
            fail("Expecting IndexOutOfBoundsValidationException");
        } catch (final IndexOutOfBoundsValidationException ex) {
            assertEquals("Broken: bad index", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Validate.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void should_verify_valid_index_char_sequence() {
        final String str = "ab";
        Validate.validIndex(str, 0, "Broken %s", "index");
        Validate.validIndex(str, 1, "Broken %s", "index");

        try {
            Validate.validIndex(str, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsValidationException");
        } catch (final IndexOutOfBoundsValidationException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Validate.validIndex(str, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsValidationException");
        } catch (final IndexOutOfBoundsValidationException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String hi = "Hi";
        final String test = Validate.validIndex(hi, 0, "Message");
        assertSame(hi, test);
    }

    @Test
    public void should_verify_is_instanceof_return_value() throws Exception {
        final Double value = 1D;
        assertSame(value, Validate.isInstanceOf(Double.class, value));
        assertSame(value, Validate.isInstanceOf(Double.class, value, "Expected %s", Double.class));
    }

    @Test
    public void should_verify_is_assignable_from_return_type() throws Exception {
        final Class<?> type = Double.class;
        assertSame(type, Validate.isAssignableFrom(Number.class, type));
        assertSame(type, Validate.isAssignableFrom(Number.class, type, "Expected to be assignable from %s", Number.class));
    }

    @Test
    public void should_verify_is_assignable_from_with_message_arguments() throws Exception {
        try {
            Validate.isAssignableFrom(Number.class, String.class, "Expected to be assignable from %s", Number.class);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Expected to be assignable from class java.lang.Number", e.getMessage());
        }
    }

    @Test
    public void should_verify_exclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Validate.exclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Validate.exclusiveBetween(1L, 3L, 2L, "Must be between %d and %d"));
    }

    @Test
    public void should_verify_exclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Validate.exclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Validate.exclusiveBetween(1D, 3D, 2D, "Must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_exclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Validate.exclusiveBetween(start, end, value));
        assertSame(value, Validate.exclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_exclusive_between_object_with_message_arguments() throws Exception {
        try {
            Validate.exclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_inclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Validate.inclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Validate.inclusiveBetween(1L, 3L, 2L, "must be between 1 and 3"));
    }

    @Test
    public void should_verify_inclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Validate.inclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Validate.inclusiveBetween(1D, 3D, 2D, "must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_inclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Validate.inclusiveBetween(start, end, value));
        assertSame(value, Validate.inclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_inclusive_between_object_with_message_arguments() throws Exception {
        try {
            Validate.inclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_matches_pattern_return_value() throws Exception {
        assertSame("abc", Validate.matchesPattern("abc", "abc"));
        assertSame("abc", Validate.matchesPattern("abc", "abc", "Must match %s"));
    }

    @Test
    public void should_verify_matches_pattern_with_message_arguments() {
        final CharSequence str = "hi";
        Validate.matchesPattern(str, "[a-z]*", "Does not match %s", "the expected patter");
        try {
            Validate.matchesPattern(str, "[0-9]*", "Does not match %s", "the expected pattern");
            fail("Expecting IllegalArgumentValidationException");
        } catch (final IllegalArgumentValidationException e) {
            assertEquals("Does not match the expected pattern", e.getMessage());
        }
    }

    @Test
    public void should_verify_valid_state() throws Exception {
        Validate.validState(true);
        Validate.validState(true, "Must be %s", true);
        Validate.validState(true, "Must be true");

        try {
            Validate.validState(false);
            fail();
        } catch (IllegalStateValidationException e) {
        }
        try {
            Validate.validState(false, "Must be %s", true);
            fail();
        } catch (IllegalStateValidationException e) {
            assertEquals("Must be true", e.getMessage());
        }
        try {
            Validate.validState(false, "Must be true");
            fail();
        } catch (IllegalStateValidationException e) {
            assertEquals("Must be true", e.getMessage());
        }
    }
}
