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
import se.sawano.java.commons.lang.*;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

public class EnsureTest {

    @Test
    public void should_verify_is_false() throws Exception {
        Ensure.isFalse(false);
        Ensure.isFalse(false, "Test %d", 123L);
        Ensure.isFalse(false, "Test %.0f", 123D);
        Ensure.isFalse(false, "Test %s, %s", "A", "B");
        Ensure.isFalse(false, "Test error message");

        try {
            Ensure.isFalse(true);
            fail();
        } catch (IllegalArgumentEnsuranceException e) {
        }
        try {
            Ensure.isFalse(true, "Test %d", 123L);
            fail();
        } catch (IllegalArgumentEnsuranceException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Ensure.isFalse(true, "Test %.0f", 123D);
            fail();
        } catch (IllegalArgumentEnsuranceException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Ensure.isFalse(true, "Test %s, %s", "A", "B");
            fail();
        } catch (IllegalArgumentEnsuranceException e) {
            assertEquals("Test A, B", e.getMessage());
        }
        try {
            Ensure.isFalse(true, "Test error message");
            fail();
        } catch (IllegalArgumentEnsuranceException e) {
            assertEquals("Test error message", e.getMessage());
        }
    }

    @Test
    public void should_verify_is_true() {
        Ensure.isTrue(true, "Must be %s", true);

        try {
            Ensure.isTrue(false, "Must be %s", true);
            fail();
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("Must be true", ex.getMessage());
        }
    }

    @Test
    public void should_verify_not_null() {
        Ensure.notNull(new Object(), "Cannot be %s", null);

        try {
            Ensure.notNull(null, "Cannot be %s", null);
            fail();
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("Cannot be null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Ensure.notNull(str, "Message");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_empty_array() {
        Ensure.notEmpty(new Object[]{null}, "Must not be %s", "empty");

        try {
            Ensure.notEmpty((Object[]) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty(new Object[0], "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Ensure.notEmpty(array, "Message");
        assertSame(array, test);
    }

    @Test
    public void should_verify_not_empty_collection() {
        try {
            Ensure.notEmpty((Collection<?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty(emptyList(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Collection<Integer> coll = singletonList(8);
        Ensure.notEmpty(coll, "Must not be %s", "empty");

        final Collection<Integer> test = Ensure.notEmpty(coll, "Must not be %s", "empty");
        assertSame(coll, test);
    }

    @Test
    public void should_verify_not_empty_map() {
        try {
            Ensure.notEmpty((Map<?, ?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty(emptyMap(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Map<String, Integer> map = Collections.singletonMap("ll", 8);
        Ensure.notEmpty(map, "Must not be %s", "empty");

        final Map<String, Integer> test = Ensure.notEmpty(map, "Must not be %s", "empty");
        assertSame(map, test);
    }

    @Test
    public void should_verify_not_empty_char_sequence() {
        Ensure.notEmpty("a", "Must not be %s", "empty");

        try {
            Ensure.notEmpty((String) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Ensure.notEmpty("", "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Ensure.notEmpty(str, "Must not be %s", "empty");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_blank() {
        final String str = "a";
        final String testStr = Ensure.notBlank(str, "Must not be %s", "blank");
        assertSame(str, testStr);

        try {
            Ensure.notBlank(null, "Must not be %s", "blank");
            fail();
        } catch (NullPointerEnsuranceException e) {
            assertEquals("Must not be blank", e.getMessage());
        }

        try {
            Ensure.notBlank("", "Must not be %s", "blank");
            fail();
        } catch (IllegalArgumentEnsuranceException e) {
            assertEquals("Must not be blank", e.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_collection() {
        final Collection<String> collection = asList("a", "b");
        final Collection<String> ret = Ensure.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Ensure.noNullElements((Collection<?>) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Ensure.noNullElements(asList("a", null), "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_array_with_message_arguments() {
        final String[] collection = new String[]{"a", "b"};
        final String[] ret = Ensure.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Ensure.noNullElements((String[]) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerEnsuranceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Ensure.noNullElements(new String[]{"a", null}, "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentEnsuranceException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_valid_index_array() {
        final Object[] array = new Object[2];
        Ensure.validIndex(array, 0, "Broken %s", "index");
        Ensure.validIndex(array, 1, "Broken %s", "index");

        try {
            Ensure.validIndex(array, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Ensure.validIndex(array, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Ensure.validIndex(strArray, 0, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void should_verify_valid_index_collection() {
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
            Ensure.validIndex(coll, 2, "Broken: %s", "bad index");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken: bad index", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Ensure.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void should_verify_valid_index_char_sequence() {
        final String str = "ab";
        Ensure.validIndex(str, 0, "Broken %s", "index");
        Ensure.validIndex(str, 1, "Broken %s", "index");

        try {
            Ensure.validIndex(str, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Ensure.validIndex(str, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsEnsuranceException");
        } catch (final IndexOutOfBoundsEnsuranceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String hi = "Hi";
        final String test = Ensure.validIndex(hi, 0, "Message");
        assertSame(hi, test);
    }

    @Test
    public void should_verify_is_instanceof_return_value() throws Exception {
        final Double value = 1D;
        assertSame(value, Ensure.isInstanceOf(Double.class, value));
        assertSame(value, Ensure.isInstanceOf(Double.class, value, "Expected %s", Double.class));
    }

    @Test
    public void should_verify_is_assignable_from_return_type() throws Exception {
        final Class<?> type = Double.class;
        assertSame(type, Ensure.isAssignableFrom(Number.class, type));
        assertSame(type, Ensure.isAssignableFrom(Number.class, type, "Expected to be assignable from %s", Number.class));
    }

    @Test
    public void should_verify_is_assignable_from_with_message_arguments() throws Exception {
        try {
            Ensure.isAssignableFrom(Number.class, String.class, "Expected to be assignable from %s", Number.class);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Expected to be assignable from class java.lang.Number", e.getMessage());
        }
    }

    @Test
    public void should_verify_exclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Ensure.exclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Ensure.exclusiveBetween(1L, 3L, 2L, "Must be between %d and %d"));
    }

    @Test
    public void should_verify_exclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Ensure.exclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Ensure.exclusiveBetween(1D, 3D, 2D, "Must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_exclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Ensure.exclusiveBetween(start, end, value));
        assertSame(value, Ensure.exclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_exclusive_between_object_with_message_arguments() throws Exception {
        try {
            Ensure.exclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_inclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Ensure.inclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Ensure.inclusiveBetween(1L, 3L, 2L, "must be between 1 and 3"));
    }

    @Test
    public void should_verify_inclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Ensure.inclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Ensure.inclusiveBetween(1D, 3D, 2D, "must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_inclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Ensure.inclusiveBetween(start, end, value));
        assertSame(value, Ensure.inclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_inclusive_between_object_with_message_arguments() throws Exception {
        try {
            Ensure.inclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_matches_pattern_return_value() throws Exception {
        assertSame("abc", Ensure.matchesPattern("abc", "abc"));
        assertSame("abc", Ensure.matchesPattern("abc", "abc", "Must match %s"));
    }

    @Test
    public void should_verify_matches_pattern_with_message_arguments() {
        final CharSequence str = "hi";
        Ensure.matchesPattern(str, "[a-z]*", "Does not match %s", "the expected patter");
        try {
            Ensure.matchesPattern(str, "[0-9]*", "Does not match %s", "the expected pattern");
            fail("Expecting IllegalArgumentEnsuranceException");
        } catch (final IllegalArgumentEnsuranceException e) {
            assertEquals("Does not match the expected pattern", e.getMessage());
        }
    }

    @Test
    public void should_verify_valid_state() throws Exception {
        Ensure.validState(true);
        Ensure.validState(true, "Must be %s", true);
        Ensure.validState(true, "Must be true");

        try {
            Ensure.validState(false);
            fail();
        } catch (IllegalStateEnsuranceException e) {
        }
        try {
            Ensure.validState(false, "Must be %s", true);
            fail();
        } catch (IllegalStateEnsuranceException e) {
            assertEquals("Must be true", e.getMessage());
        }
        try {
            Ensure.validState(false, "Must be true");
            fail();
        } catch (IllegalStateEnsuranceException e) {
            assertEquals("Must be true", e.getMessage());
        }
    }
}
