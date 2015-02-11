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

import static org.junit.Assert.*;

public class EnsureTest {

    @Test
    public void should_verify_is_false() throws Exception {
        Ensure.isFalse(false);
        Ensure.isFalse(false, "Test %d", 123L);
        Ensure.isFalse(false, "Test %.0f", 123D);
        Ensure.isFalse(false, "Test %s, %s", "A", "B");

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
    public void should_verify_matches_pattern_return_value() throws Exception {
        assertSame("abc", Ensure.matchesPattern("abc", "abc"));
        assertSame("abc", Ensure.matchesPattern("abc", "abc", "Must match %s"));
    }

    @Test
    public void should_verify_valid_state() throws Exception {
        Ensure.validState(true);
        Ensure.validState(true, "Must be %s", true);

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
    }
}
