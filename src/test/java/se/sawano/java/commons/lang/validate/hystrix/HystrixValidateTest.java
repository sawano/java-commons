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
import se.sawano.java.commons.lang.validate.hystrix.exception.IllegalArgumentHystrixBadRequestException;
import se.sawano.java.commons.lang.validate.hystrix.exception.IllegalStateHystrixBadRequestException;
import se.sawano.java.commons.lang.validate.hystrix.exception.IndexOutOfBoundsHystrixBadRequestException;
import se.sawano.java.commons.lang.validate.hystrix.exception.NullPointerHystrixBadRequestException;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.junit.Assert.*;

/**
 * These are tests that are not present in Apache commons.
 */
public class HystrixValidateTest {

    @Test
    public void should_verify_is_false() throws Exception {
        HystrixValidate.isFalse(false);
        HystrixValidate.isFalse(false, "Test %d", 123L);
        HystrixValidate.isFalse(false, "Test %.0f", 123D);
        HystrixValidate.isFalse(false, "Test %s, %s", "A", "B");
        HystrixValidate.isFalse(false, "Test error message");

        try {
            HystrixValidate.isFalse(true);
            fail();
        } catch (IllegalArgumentHystrixBadRequestException e) {
        }
        try {
            HystrixValidate.isFalse(true, "Test %d", 123L);
            fail();
        } catch (IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            HystrixValidate.isFalse(true, "Test %.0f", 123D);
            fail();
        } catch (IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Test 123", e.getMessage());
        }
        try {
            HystrixValidate.isFalse(true, "Test %s, %s", "A", "B");
            fail();
        } catch (IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Test A, B", e.getMessage());
        }
        try {
            HystrixValidate.isFalse(true, "Test error message");
            fail();
        } catch (IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Test error message", e.getMessage());
        }
    }

    @Test
    public void should_verify_is_true() {
        HystrixValidate.isTrue(true, "Must be %s", true);

        try {
            HystrixValidate.isTrue(false, "Must be %s", true);
            fail();
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("Must be true", ex.getMessage());
        }
    }

    @Test
    public void should_verify_not_null() {
        HystrixValidate.notNull(new Object(), "Cannot be %s", "null");

        try {
            HystrixValidate.notNull(null, "Cannot be %s", "null");
            fail();
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("Cannot be null", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = HystrixValidate.notNull(str, "Message");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_empty_array() {
        HystrixValidate.notEmpty(new Object[]{null}, "Must not be %s", "empty");

        try {
            HystrixValidate.notEmpty((Object[]) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(new Object[0], "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String[] array = new String[]{"hi"};
        final String[] test = HystrixValidate.notEmpty(array, "Message");
        assertSame(array, test);
    }

    @Test
    public void should_verify_not_empty_collection() {
        try {
            HystrixValidate.notEmpty((Collection<?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(emptyList(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Collection<Integer> coll = singletonList(8);
        HystrixValidate.notEmpty(coll, "Must not be %s", "empty");

        final Collection<Integer> test = HystrixValidate.notEmpty(coll, "Must not be %s", "empty");
        assertSame(coll, test);
    }

    @Test
    public void should_verify_not_empty_map() {
        try {
            HystrixValidate.notEmpty((Map<?, ?>) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty(emptyMap(), "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final Map<String, Integer> map = Collections.singletonMap("ll", 8);
        HystrixValidate.notEmpty(map, "Must not be %s", "empty");

        final Map<String, Integer> test = HystrixValidate.notEmpty(map, "Must not be %s", "empty");
        assertSame(map, test);
    }

    @Test
    public void should_verify_not_empty_char_sequence() {
        HystrixValidate.notEmpty("a", "Must not be %s", "empty");

        try {
            HystrixValidate.notEmpty((String) null, "Must not be %s", "empty");
            fail();
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }
        try {
            HystrixValidate.notEmpty("", "Must not be %s", "empty");
            fail();
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("Must not be empty", ex.getMessage());
        }

        final String str = "Hi";
        final String testStr = HystrixValidate.notEmpty(str, "Must not be %s", "empty");
        assertSame(str, testStr);
    }

    @Test
    public void should_verify_not_blank() {
        final String str = "a";
        final String testStr = HystrixValidate.notBlank(str, "Must not be %s", "blank");
        assertSame(str, testStr);

        try {
            HystrixValidate.notBlank(null, "Must not be %s", "blank");
            fail();
        } catch (NullPointerHystrixBadRequestException e) {
            assertEquals("Must not be blank", e.getMessage());
        }

        try {
            HystrixValidate.notBlank("", "Must not be %s", "blank");
            fail();
        } catch (IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Must not be blank", e.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_collection() {
        final Collection<String> collection = asList("a", "b");
        final Collection<String> ret = HystrixValidate.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            HystrixValidate.noNullElements((Collection<?>) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            HystrixValidate.noNullElements(asList("a", null), "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_no_null_elements_array_with_message_arguments() {
        final String[] collection = new String[]{"a", "b"};
        final String[] ret = HystrixValidate.noNullElements(collection, "Must not contain %s", "null");
        assertSame(collection, ret);

        try {
            HystrixValidate.noNullElements((String[]) null, "Must not contain %s", "null");
            fail();
        } catch (final NullPointerHystrixBadRequestException ex) {
            assertEquals("The validated object is null", ex.getMessage());
        }

        try {
            HystrixValidate.noNullElements(new String[]{"a", null}, "Must not contain %s", "null");
            fail();
        } catch (final IllegalArgumentHystrixBadRequestException ex) {
            assertEquals("Must not contain null", ex.getMessage());
        }
    }

    @Test
    public void should_verify_valid_index_array() {
        final Object[] array = new Object[2];
        HystrixValidate.validIndex(array, 0, "Broken %s", "index");
        HystrixValidate.validIndex(array, 1, "Broken %s", "index");

        try {
            HystrixValidate.validIndex(array, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(array, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String[] strArray = new String[]{"Hi"};
        final String[] test = HystrixValidate.validIndex(strArray, 0, "Message");
        assertSame(strArray, test);
    }

    @Test
    public void should_verify_valid_index_collection() {
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
            HystrixValidate.validIndex(coll, 2, "Broken: %s", "bad index");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken: bad index", ex.getMessage());
        }

        final List<String> strColl = Arrays.asList(new String[]{"Hi"});
        final List<String> test = HystrixValidate.validIndex(strColl, 0, "Message");
        assertSame(strColl, test);
    }

    @Test
    public void should_verify_valid_index_char_sequence() {
        final String str = "ab";
        HystrixValidate.validIndex(str, 0, "Broken %s", "index");
        HystrixValidate.validIndex(str, 1, "Broken %s", "index");

        try {
            HystrixValidate.validIndex(str, -1, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken index", ex.getMessage());
        }
        try {
            HystrixValidate.validIndex(str, 2, "Broken %s", "index");
            fail("Expecting IndexOutOfBoundsHystrixBadRequestException");
        } catch (final IndexOutOfBoundsHystrixBadRequestException ex) {
            assertEquals("Broken index", ex.getMessage());
        }

        final String hi = "Hi";
        final String test = HystrixValidate.validIndex(hi, 0, "Message");
        assertSame(hi, test);
    }

    @Test
    public void should_verify_is_instanceof_return_value() throws Exception {
        final Double value = 1D;
        assertSame(value, HystrixValidate.isInstanceOf(Double.class, value));
        assertSame(value, HystrixValidate.isInstanceOf(Double.class, value, "Expected %s", Double.class));
    }

    @Test
    public void should_verify_is_assignable_from_return_type() throws Exception {
        final Class<?> type = Double.class;
        assertSame(type, HystrixValidate.isAssignableFrom(Number.class, type));
        assertSame(type, HystrixValidate.isAssignableFrom(Number.class, type, "Expected to be assignable from %s", Number.class));
    }

    @Test
    public void should_verify_is_assignable_from_with_message_arguments() throws Exception {
        try {
            HystrixValidate.isAssignableFrom(Number.class, String.class, "Expected to be assignable from %s", Number.class);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Expected to be assignable from class java.lang.Number", e.getMessage());
        }
    }

    @Test
    public void should_verify_exclusive_between_long_return_value() throws Exception {
        assertEquals(2L, HystrixValidate.exclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, HystrixValidate.exclusiveBetween(1L, 3L, 2L, "Must be between %d and %d"));
    }

    @Test
    public void should_verify_exclusive_between_double_return_value() throws Exception {
        assertEquals(2D, HystrixValidate.exclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, HystrixValidate.exclusiveBetween(1D, 3D, 2D, "Must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_exclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, HystrixValidate.exclusiveBetween(start, end, value));
        assertSame(value, HystrixValidate.exclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_exclusive_between_object_with_message_arguments() throws Exception {
        try {
            HystrixValidate.exclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_inclusive_between_long_return_value() throws Exception {
        assertEquals(2L, HystrixValidate.inclusiveBetween(1L, 3L, 2L));
        assertEquals(2L, HystrixValidate.inclusiveBetween(1L, 3L, 2L, "must be between 1 and 3"));
    }

    @Test
    public void should_verify_inclusive_between_double_return_value() throws Exception {
        assertEquals(2D, HystrixValidate.inclusiveBetween(1D, 3D, 2D), 0);
        assertEquals(2D, HystrixValidate.inclusiveBetween(1D, 3D, 2D, "must be between 1 and 3"), 0);
    }

    @Test
    public void should_verify_inclusive_between_object_return_value() throws Exception {
        final Double start = 1D;
        final Double end = 3D;
        final Double value = 2D;
        assertSame(value, HystrixValidate.inclusiveBetween(start, end, value));
        assertSame(value, HystrixValidate.inclusiveBetween(start, end, value, "Must be between %0.f and %0.f", start, end));
    }

    @Test
    public void should_verify_inclusive_between_object_with_message_arguments() throws Exception {
        try {
            HystrixValidate.inclusiveBetween(1, 10, 12, "Must be between %d and %d", 1, 12);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals("Must be between 1 and 12", e.getMessage());
        }
    }

    @Test
    public void should_verify_matches_pattern_return_value() throws Exception {
        assertSame("abc", HystrixValidate.matchesPattern("abc", "abc"));
        assertSame("abc", HystrixValidate.matchesPattern("abc", "abc", "Must match %s"));
    }

    @Test
    public void should_verify_matches_pattern_with_message_arguments() {
        final CharSequence str = "hi";
        HystrixValidate.matchesPattern(str, "[a-z]*", "Does not match %s", "the expected patter");
        try {
            HystrixValidate.matchesPattern(str, "[0-9]*", "Does not match %s", "the expected pattern");
            fail("Expecting IllegalArgumentHystrixBadRequestException");
        } catch (final IllegalArgumentHystrixBadRequestException e) {
            assertEquals("Does not match the expected pattern", e.getMessage());
        }
    }

    @Test
    public void should_verify_valid_state() throws Exception {
        HystrixValidate.validState(true);
        HystrixValidate.validState(true, "Must be %s", true);
        HystrixValidate.validState(true, "Must be true");

        try {
            HystrixValidate.validState(false);
            fail();
        } catch (IllegalStateHystrixBadRequestException e) {
        }
        try {
            HystrixValidate.validState(false, "Must be %s", true);
            fail();
        } catch (IllegalStateHystrixBadRequestException e) {
            assertEquals("Must be true", e.getMessage());
        }
        try {
            HystrixValidate.validState(false, "Must be true");
            fail();
        } catch (IllegalStateHystrixBadRequestException e) {
            assertEquals("Must be true", e.getMessage());
        }
    }
}
