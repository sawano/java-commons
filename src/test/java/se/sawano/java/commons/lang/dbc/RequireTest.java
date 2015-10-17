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

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.junit.Assert.*;

public class RequireTest {

    @Test
    public void should_verify_is_false() throws Exception {
        Require.isFalse(false);
        Require.isFalse(false, "Test %d", 123L);
        Require.isFalse(false, "Test %.0f", 123D);
        Require.isFalse(false, "Test %s, %s", "A", "B");
        Require.isFalse(false, "Test error message");

        try {
            Require.isFalse(true);
            fail();
        } catch (IllegalArgumentRequirementException e) {
        }
        try {
            Require.isFalse(true, "Test %d", 123L);
            fail();
        } catch (IllegalArgumentRequirementException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Require.isFalse(true, "Test %.0f", 123D);
            fail();
        } catch (IllegalArgumentRequirementException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Require.isFalse(true, "Test %s, %s", "A", "B");
            fail();
        } catch (IllegalArgumentRequirementException e) {
            assertEquals("Test A, B", e.getMessage());
        }
        try {
            Require.isFalse(true, "Test error message");
            fail();
        } catch (IllegalArgumentRequirementException e) {
            assertEquals("Test error message", e.getMessage());
        }
    }

    @Test
    public void should_verify_is_true() {
        Require.isTrue(true, "Must be %s", true);

        try {
            Require.isTrue(false, "Must be %s", true);
            fail();
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("Must be true", ex.getMessage());
        }
    }

    @Test
    public void should_verify_not_null() {
        Require.notNull(new Object(), "Cannot be %s", "null");

        try {
            Require.notNull(null, "Cannot be %s", "null");
            fail();
        } catch (final NullPointerRequirementException ex) {
            assertEquals("Cannot be null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Require.notNull(str, "Message");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_empty_array() {
        Require.notEmpty(new Object[]{null}, "Must not be %s", "empty");

        try {
            Require.notEmpty((Object[]) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Require.notEmpty(new Object[0], "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Require.notEmpty(array, "Message");
        assertSame(array, test);
    }

    @Test
    public void should_verify_not_empty_collection() {
        try {
            Require.notEmpty((Collection<?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Require.notEmpty(emptyList(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Collection<Integer> coll = singletonList(8);
        Require.notEmpty(coll, "Must not be %s", "empty");

        final Collection<Integer> test = Require.notEmpty(coll, "Must not be %s", "empty");
        assertSame(coll, test);
    }

    @Test
    public void should_verify_not_empty_map() {
        try {
            Require.notEmpty((Map<?, ?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Require.notEmpty(emptyMap(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Map<String, Integer> map = Collections.singletonMap("ll", 8);
        Require.notEmpty(map, "Must not be %s", "empty");

        final Map<String, Integer> test = Require.notEmpty(map, "Must not be %s", "empty");
        assertSame(map, test);
    }

    @Test
    public void should_verify_not_empty_char_sequence() {
        Require.notEmpty("a", "Must not be %s", "empty");

        try {
            Require.notEmpty((String) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Require.notEmpty("", "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Require.notEmpty(str, "Must not be %s", "empty");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_blank() {
        final String str = "a";
        final String testStr = Require.notBlank(str, "Must not be %s", "blank");
        assertSame(str, testStr);

        try {
            Require.notBlank(null, "Must not be %s", "blank");
            fail();
        } catch (NullPointerRequirementException e) {
            assertEquals("Must not be blank", e.getMessage());
        }

        try {
            Require.notBlank("", "Must not be %s", "blank");
            fail();
        } catch (IllegalArgumentRequirementException e) {
            assertEquals("Must not be blank", e.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_collection() {
        final Collection<String> collection = asList("a", "b");
        final Collection<String> ret = Require.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Require.noNullElements((Collection<?>) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Require.noNullElements(asList("a", null), "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_array_with_message_arguments() {
        final String[] collection = new String[]{"a", "b"};
        final String[] ret = Require.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Require.noNullElements((String[]) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerRequirementException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Require.noNullElements(new String[]{"a", null}, "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentRequirementException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_valid_index_array() {
        final Object[] array = new Object[2];
        Require.validIndex(array, 0, "Broken %s", "index");
        Require.validIndex(array, 1, "Broken %s", "index");

        try {
            Require.validIndex(array, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Require.validIndex(array, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Require.validIndex(strArray, 0, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void should_verify_valid_index_collection() {
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
            Require.validIndex(coll, 2, "Broken: %s", "bad index");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken: bad index", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Require.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void should_verify_valid_index_char_sequence() {
        final String str = "ab";
        Require.validIndex(str, 0, "Broken %s", "index");
        Require.validIndex(str, 1, "Broken %s", "index");

        try {
            Require.validIndex(str, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Require.validIndex(str, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsRequirementException");
        } catch (final IndexOutOfBoundsRequirementException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String hi = "Hi";
        final String test = Require.validIndex(hi, 0, "Message");
        assertSame(hi, test);
    }

    @Test
    public void should_verify_is_instanceof_return_value() throws Exception {
        final Double value = 1D;
        assertSame(value, Require.isInstanceOf(Double.class, value));
        assertSame(value, Require.isInstanceOf(Double.class, value, "Expected %s", Double.class));
    }

    @Test
    public void should_verify_is_assignable_from_return_type() throws Exception {
        final Class<?> type = Double.class;
        assertSame(type, Require.isAssignableFrom(Number.class, type));
        assertSame(type, Require.isAssignableFrom(Number.class, type, "Expected to be assignable from %s", Number.class));
    }

    @Test
    public void should_verify_is_assignable_from_with_message_arguments() throws Exception {
        try {
            Require.isAssignableFrom(Number.class, String.class, "Expected to be assignable from %s", Number.class);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Expected to be assignable from class java.lang.Number", e.getMessage());
        }
    }

    @Test
    public void should_verify_exclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Require.exclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Require.exclusiveBetween(1L, 3L, 2L, "Must be between %d and %d"));
    }

    @Test
    public void should_verify_exclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Require.exclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Require.exclusiveBetween(1D, 3D, 2D, "Must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_exclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Require.exclusiveBetween(start, end, value));
        assertSame(value, Require.exclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_exclusive_between_object_with_message_arguments() throws Exception {
        try {
            Require.exclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_inclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Require.inclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Require.inclusiveBetween(1L, 3L, 2L, "must be between 1 and 3"));
    }

    @Test
    public void should_verify_inclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Require.inclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Require.inclusiveBetween(1D, 3D, 2D, "must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_inclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Require.inclusiveBetween(start, end, value));
        assertSame(value, Require.inclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_inclusive_between_object_with_message_arguments() throws Exception {
        try {
            Require.inclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_matches_pattern_return_value() throws Exception {
        assertSame("abc", Require.matchesPattern("abc", "abc"));
        assertSame("abc", Require.matchesPattern("abc", "abc", "Must match %s"));
    }

    @Test
    public void should_verify_matches_pattern_with_message_arguments() {
        final CharSequence str = "hi";
        Require.matchesPattern(str, "[a-z]*", "Does not match %s", "the expected patter");
        try {
            Require.matchesPattern(str, "[0-9]*", "Does not match %s", "the expected pattern");
            fail("Expecting IllegalArgumentRequirementException");
        } catch (final IllegalArgumentRequirementException e) {
            assertEquals("Does not match the expected pattern", e.getMessage());
        }
    }

    @Test
    public void should_verify_valid_state() throws Exception {
        Require.validState(true);
        Require.validState(true, "Must be %s", true);
        Require.validState(true, "Must be true");

        try {
            Require.validState(false);
            fail();
        } catch (IllegalStateRequirementException e) {
        }
        try {
            Require.validState(false, "Must be %s", true);
            fail();
        } catch (IllegalStateRequirementException e) {
            assertEquals("Must be true", e.getMessage());
        }
        try {
            Require.validState(false, "Must be true");
            fail();
        } catch (IllegalStateRequirementException e) {
            assertEquals("Must be true", e.getMessage());
        }
    }
}
