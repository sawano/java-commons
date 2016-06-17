/*
 * Copyright 2016 Daniel Sawano
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import se.sawano.java.commons.lang.validate.exception.IllegalStateValidationException;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static se.sawano.java.commons.lang.Streams.toOnlyOne;

public class StreamsTest {

    @Rule
    public final ExpectedException expectation = ExpectedException.none();

    @Test
    public void should_fail_if_more_than_one_element_exists_in_stream() {
        expectation.expect(IllegalStateValidationException.class);
        expectation.expectMessage("Duplicates not allowed");

        Stream.of("A", "B")
              .reduce(toOnlyOne());
    }

    @Test
    public void should_fail_if_more_than_one_element_exists_in_stream_and_custom_message() {
        expectation.expect(IllegalStateValidationException.class);
        expectation.expectMessage("Only one!");

        Stream.of("A", "B")
              .reduce(toOnlyOne("Only one!"));
    }

    @Test
    public void should_fail_if_more_than_one_element_exists_in_stream_and_custom_message_with_parameters() {
        expectation.expect(IllegalStateValidationException.class);
        expectation.expectMessage("Only one!");

        Stream.of("A", "B")
              .reduce(toOnlyOne("Only %s!", "one"));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void should_succeed() {
        assertEquals("A", Stream.of("A").reduce(toOnlyOne()).get());
        assertEquals("A", Stream.of("A").reduce(toOnlyOne("Only one!")).get());
        assertEquals("A", Stream.of("A").reduce(toOnlyOne("Only %s!", "one")).get());
    }
}
