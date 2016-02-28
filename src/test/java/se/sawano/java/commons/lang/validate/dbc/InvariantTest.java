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
import se.sawano.java.commons.lang.validate.dbc.exception.IllegalArgumentInvarianceException;
import se.sawano.java.commons.lang.validate.dbc.exception.IllegalStateInvarianceException;
import se.sawano.java.commons.lang.validate.dbc.exception.IndexOutOfBoundsInvarianceException;
import se.sawano.java.commons.lang.validate.dbc.exception.NullPointerInvarianceException;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.junit.Assert.*;

public class InvariantTest {

    @Test
    public void should_verify_is_false() throws Exception {
        Invariant.isFalse(false);
        Invariant.isFalse(false, "Test %d", 123L);
        Invariant.isFalse(false, "Test %.0f", 123D);
        Invariant.isFalse(false, "Test %s, %s", "A", "B");
        Invariant.isFalse(false, "Test error message");

        try {
            Invariant.isFalse(true);
            fail();
        } catch (IllegalArgumentInvarianceException e) {
        }
        try {
            Invariant.isFalse(true, "Test %d", 123L);
            fail();
        } catch (IllegalArgumentInvarianceException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Invariant.isFalse(true, "Test %.0f", 123D);
            fail();
        } catch (IllegalArgumentInvarianceException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            Invariant.isFalse(true, "Test %s, %s", "A", "B");
            fail();
        } catch (IllegalArgumentInvarianceException e) {
            assertEquals("Test A, B", e.getMessage());
        }
        try {
            Invariant.isFalse(true, "Test error message");
            fail();
        } catch (IllegalArgumentInvarianceException e) {
            assertEquals("Test error message", e.getMessage());
        }
    }

    @Test
    public void should_verify_is_true() {
        Invariant.isTrue(true, "Must be %s", true);

        try {
            Invariant.isTrue(false, "Must be %s", true);
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Must be true", ex.getMessage());
        }
    }

    @Test
    public void should_verify_not_null() {
        Invariant.notNull(new Object(), "Cannot be %s", "null");

        try {
            Invariant.notNull(null, "Cannot be %s", "null");
            fail();
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("Cannot be null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Invariant.notNull(str, "Message");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_is_null() {
        Invariant.isNull(null);
        Invariant.isNull(null, "Should be %s", "null");
        Invariant.isNull(null, "Should be null");

        try {
            Invariant.isNull(new Object(), "Should be %s", "null");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Should be null", ex.getMessage());
        }
        try {
            Invariant.isNull(new Object(), "Should be null");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Should be null", ex.getMessage());
        }
        try {
            Invariant.isNull(new Object());
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("The validated object is not null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_not_empty_array() {
        Invariant.notEmpty(new Object[]{null}, "Must not be %s", "empty");

        try {
            Invariant.notEmpty((Object[]) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty(new Object[0], "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = Invariant.notEmpty(array, "Message");
        assertSame(array, test);
    }

    @Test
    public void should_verify_not_empty_collection() {
        try {
            Invariant.notEmpty((Collection<?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty(emptyList(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Collection<Integer> coll = singletonList(8);
        Invariant.notEmpty(coll, "Must not be %s", "empty");

        final Collection<Integer> test = Invariant.notEmpty(coll, "Must not be %s", "empty");
        assertSame(coll, test);
    }

    @Test
    public void should_verify_not_empty_map() {
        try {
            Invariant.notEmpty((Map<?, ?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty(emptyMap(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Map<String, Integer> map = Collections.singletonMap("ll", 8);
        Invariant.notEmpty(map, "Must not be %s", "empty");

        final Map<String, Integer> test = Invariant.notEmpty(map, "Must not be %s", "empty");
        assertSame(map, test);
    }

    @Test
    public void should_verify_not_empty_char_sequence() {
        Invariant.notEmpty("a", "Must not be %s", "empty");

        try {
            Invariant.notEmpty((String) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            Invariant.notEmpty("", "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = Invariant.notEmpty(str, "Must not be %s", "empty");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_blank() {
        final String str = "a";
        final String testStr = Invariant.notBlank(str, "Must not be %s", "blank");
        assertSame(str, testStr);

        try {
            Invariant.notBlank(null, "Must not be %s", "blank");
            fail();
        } catch (NullPointerInvarianceException e) {
            assertEquals("Must not be blank", e.getMessage());
        }

        try {
            Invariant.notBlank("", "Must not be %s", "blank");
            fail();
        } catch (IllegalArgumentInvarianceException e) {
            assertEquals("Must not be blank", e.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_collection() {
        final Collection<String> collection = asList("a", "b");
        final Collection<String> ret = Invariant.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Invariant.noNullElements((Collection<?>) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Invariant.noNullElements(asList("a", null), "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_array_with_message_arguments() {
        final String[] collection = new String[]{"a", "b"};
        final String[] ret = Invariant.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            Invariant.noNullElements((String[]) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerInvarianceException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            Invariant.noNullElements(new String[]{"a", null}, "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentInvarianceException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_valid_index_array() {
        final Object[] array = new Object[2];
        Invariant.validIndex(array, 0, "Broken %s", "index");
        Invariant.validIndex(array, 1, "Broken %s", "index");

        try {
            Invariant.validIndex(array, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Invariant.validIndex(array, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = Invariant.validIndex(strArray, 0, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void should_verify_valid_index_collection() {
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
            Invariant.validIndex(coll, 2, "Broken: %s", "bad index");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken: bad index", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = Invariant.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void should_verify_valid_index_char_sequence() {
        final String str = "ab";
        Invariant.validIndex(str, 0, "Broken %s", "index");
        Invariant.validIndex(str, 1, "Broken %s", "index");

        try {
            Invariant.validIndex(str, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            Invariant.validIndex(str, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsInvarianceException");
        } catch (final IndexOutOfBoundsInvarianceException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String hi = "Hi";
        final String test = Invariant.validIndex(hi, 0, "Message");
        assertSame(hi, test);
    }

    @Test
    public void should_verify_is_instanceof_return_value() throws Exception {
        final Double value = 1D;
        assertSame(value, Invariant.isInstanceOf(Double.class, value));
        assertSame(value, Invariant.isInstanceOf(Double.class, value, "Expected %s", Double.class));
    }

    @Test
    public void should_verify_is_assignable_from_return_type() throws Exception {
        final Class<?> type = Double.class;
        assertSame(type, Invariant.isAssignableFrom(Number.class, type));
        assertSame(type, Invariant.isAssignableFrom(Number.class, type, "Expected to be assignable from %s", Number.class));
    }

    @Test
    public void should_verify_is_assignable_from_with_message_arguments() throws Exception {
        try {
            Invariant.isAssignableFrom(Number.class, String.class, "Expected to be assignable from %s", Number.class);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Expected to be assignable from class java.lang.Number", e.getMessage());
        }
    }

    @Test
    public void should_verify_exclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Invariant.exclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Invariant.exclusiveBetween(1L, 3L, 2L, "Must be between %d and %d"));
    }

    @Test
    public void should_verify_exclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Invariant.exclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Invariant.exclusiveBetween(1D, 3D, 2D, "Must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_exclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Invariant.exclusiveBetween(start, end, value));
        assertSame(value, Invariant.exclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_exclusive_between_object_with_message_arguments() throws Exception {
        try {
            Invariant.exclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_inclusive_between_long_return_value() throws Exception {
        assertEquals(2L, Invariant.inclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, Invariant.inclusiveBetween(1L, 3L, 2L, "must be between 1 and 3"));
    }

    @Test
    public void should_verify_inclusive_between_double_return_value() throws Exception {
        assertEquals(2D, Invariant.inclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, Invariant.inclusiveBetween(1D, 3D, 2D, "must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_inclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, Invariant.inclusiveBetween(start, end, value));
        assertSame(value, Invariant.inclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_inclusive_between_object_with_message_arguments() throws Exception {
        try {
            Invariant.inclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_matches_pattern_return_value() throws Exception {
        assertSame("abc", Invariant.matchesPattern("abc", "abc"));
        assertSame("abc", Invariant.matchesPattern("abc", "abc", "Must match %s"));
    }

    @Test
    public void should_verify_matches_pattern_with_message_arguments() {
        final CharSequence str = "hi";
        Invariant.matchesPattern(str, "[a-z]*", "Does not match %s", "the expected patter");
        try {
            Invariant.matchesPattern(str, "[0-9]*", "Does not match %s", "the expected pattern");
            fail("Expecting IllegalArgumentInvarianceException");
        } catch (final IllegalArgumentInvarianceException e) {
            assertEquals("Does not match the expected pattern", e.getMessage());
        }
    }

    @Test
    public void should_verify_valid_state() throws Exception {
        Invariant.validState(true);
        Invariant.validState(true, "Must be %s", true);
        Invariant.validState(true, "Must be true");

        try {
            Invariant.validState(false);
            fail();
        } catch (IllegalStateInvarianceException e) {
        }
        try {
            Invariant.validState(false, "Must be %s", true);
            fail();
        } catch (IllegalStateInvarianceException e) {
            assertEquals("Must be true", e.getMessage());
        }
        try {
            Invariant.validState(false, "Must be true");
            fail();
        } catch (IllegalStateInvarianceException e) {
            assertEquals("Must be true", e.getMessage());
        }
    }
}
