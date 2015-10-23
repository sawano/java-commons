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
import se.sawano.java.commons.lang.validate.exception.IllegalArgumentValidationException;
import se.sawano.java.commons.lang.validate.exception.NullPointerValidationException;

import java.util.function.Supplier;

import static java.lang.Integer.valueOf;
import static java.util.Optional.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OptionalsTest {

    @Test
    public void should_create_stream() {
        assertEquals(valueOf(1), Optionals.stream(of(1)).findFirst().get());
        assertEquals(0, Optionals.stream(empty()).count());
        assertEquals(0, Optionals.stream(ofNullable(null)).count());
    }

    @Test
    public void should_not_accept_null_when_creating_stream() {
        testForException(() -> Optionals.stream(null), NullPointerValidationException.class);
    }

    @Test
    public void should_get_value() {
        assertEquals(valueOf(1), Optionals.required(of(1)));
        assertEquals(valueOf(1), Optionals.required(of(1), "message %d"));
        assertEquals(valueOf(1), Optionals.required(of(1), "message %d", 1));
    }

    @Test
    public void should_not_accept_null_when_getting_required() {
        testForException(() -> Optionals.required(null), NullPointerValidationException.class);
    }

    @Test
    @SuppressWarnings("NullArgumentToVariableArgMethod")
    public void should_not_accept_null_when_getting_required_with_message() {
        testForException(() -> Optionals.required(null, "message %d", 1), NullPointerValidationException.class);
        testForException(() -> Optionals.required(empty(), null, 1), NullPointerValidationException.class);
        testForException(() -> Optionals.required(empty(), "message %d", null), NullPointerValidationException.class);
        testForException(() -> Optionals.required(null, "message"), NullPointerValidationException.class);
        testForException(() -> Optionals.required(empty(), null), NullPointerValidationException.class);
    }

    @Test
    public void should_not_accept_empty() {
        testForException(() -> Optionals.required(empty()), IllegalArgumentValidationException.class);
        testForException(() -> Optionals.required(empty(), "message"), IllegalArgumentValidationException.class);
        testForException(() -> Optionals.required(empty(), "message %d", 1), IllegalArgumentValidationException.class);
    }

    private void testForException(final Supplier subject, final Class<? extends Exception> exceptionClass) {
        try {
            subject.get();
            fail("Expected " + exceptionClass.getSimpleName());
        } catch (Exception e) {
            assertEquals(exceptionClass, e.getClass());
        }
    }
}
