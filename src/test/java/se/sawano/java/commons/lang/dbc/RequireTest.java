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

public class RequireTest {

    @Test
    public void should_verify_is_false() throws Exception {
        Require.isFalse(false);
        Require.isFalse(false, "Test %d", 123L);
        Require.isFalse(false, "Test %.0f", 123D);
        Require.isFalse(false, "Test %s, %s", "A", "B");

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
    public void should_verify_matches_pattern_return_value() throws Exception {
        assertSame("abc", Require.matchesPattern("abc", "abc"));
        assertSame("abc", Require.matchesPattern("abc", "abc", "Must match %s"));
    }

    @Test
    public void should_verify_valid_state() throws Exception {
        Require.validState(true);
        Require.validState(true, "Must be %s", true);

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
    }
}
