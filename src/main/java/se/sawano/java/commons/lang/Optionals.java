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

import se.sawano.java.commons.lang.validate.exception.IllegalArgumentValidationException;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Optionals {

    private Optionals() {}

    /**
     * Gets the value of an optional, throwing an exception if no value is present. This can be more convenient than using {@link Optional#orElseThrow(Supplier)}. <p>E.g. instead of doing:
     * <pre>
     * final Optional&lt;Integer&gt; opt = Optional.of(1);
     * final Integer value = opt.orElseThrow(() -&gt; new IllegalArgumentException());
     * </pre>
     * we can do:
     * <pre>
     * final Optional&lt;Integer&gt; opt = Optional.of(1);
     * final Integer value = Optionals.required(opt);
     * </pre>
     * Another example is ot insert informative exceptions on missing values in a stream:
     * <pre>
     * final Stream&lt;Optional&lt;T&gt;&gt; optStream = ...;
     * final Stream&lt;T&gt; stream = optStream.map(t -&gt; Optionals.required(t));
     * </pre>
     *
     * @param optional
     *         the optional to get the value from
     * @param <T>
     *         the type of the value
     *
     * @return the value of the optional
     *
     * @throws IllegalArgumentValidationException
     *         if no value is present
     */
    public static <T> T required(final Optional<T> optional) {
        notNull(optional);
        isTrue(optional.isPresent(), "No value present");

        return optional.get();
    }

    /**
     * * Gets the value of an optional, throwing an exception if no value is present. <p>E.g. instead of doing:
     * <pre>
     * final Optional&lt;Integer&gt; opt = Optional.of(1);
     * final Integer value = opt.orElseThrow(() -&gt; new IllegalArgumentException("Value is missing"));
     * </pre>
     * we can do:
     * <pre>
     * final Optional&lt;Integer&gt; opt = Optional.of(1);
     * final Integer value = Optionals.required(opt, "Value is missing");
     * </pre>
     * Another example is ot insert informative exceptions on missing values in a stream:
     * <pre>
     * final Stream&lt;Optional&lt;T&gt;&gt; optStream = ...;
     * final Stream&lt;T&gt; stream = optStream.map(t -&gt; Optionals.required(t, "Value T is missing"));
     * </pre>
     *
     * @param optional
     *         the optional to get the value from
     * @param message
     *         the exception message to use
     * @param <T>
     *         the type of the value
     *
     * @return the value of the optional
     *
     * @throws IllegalArgumentValidationException
     *         if no value is present
     */
    public static <T> T required(final Optional<T> optional, final String message) {
        notNull(optional);
        notNull(message);
        isTrue(optional.isPresent(), message);

        return optional.get();
    }

    /**
     * * Gets the value of an optional, throwing an exception if no value is present. <p>E.g. instead of doing:
     * <pre>
     * final Optional&lt;Integer&gt; opt = Optional.of(1);
     * final Integer value = opt.orElseThrow(() -&gt; new IllegalArgumentException("Value is missing. Expected: " + 1));
     * </pre>
     * we can do:
     * <pre>
     * final Optional&lt;Integer&gt; opt = Optional.of(1);
     * final Integer value = Optionals.required(opt, "Value is missing. Expected: %d", 1);
     * </pre>
     * Another example is ot insert informative exceptions on missing values in a stream:
     * <pre>
     * final Stream&lt;Optional&lt;T&gt;&gt; optStream = ...;
     * final Stream&lt;T&gt; stream = optStream.map(t -&gt; Optionals.required(t, "Value %s is missing", "T"));
     * </pre>
     *
     * @param optional
     *         the optional to get the value from
     * @param message
     *         the {@link String#format(String, Object...)} exception message to use
     * @param values
     *         the optional values for the formatted exception message
     * @param <T>
     *         the type of the value
     *
     * @return the value of the optional
     *
     * @throws IllegalArgumentValidationException
     *         if no value is present
     */
    public static <T> T required(final Optional<T> optional, final String message, final Object... values) {
        notNull(optional);
        notNull(message);
        notNull(values);
        isTrue(optional.isPresent(), message, values);

        return optional.get();
    }

    /**
     * Create a stream from an optional. If a value is present in the given optional then a stream with a single value will be returned. If no value is present then an empty stream will be returned.
     * This can be used to convert a stream of optional values to a stream of values:
     * <pre>
     * final Stream&lt;Optional&lt;T&gt;&gt; optStream = ...;
     * final Stream&lt;T&gt; stream = optStream.flatMap(Optionals::stream);
     * </pre>
     *
     * @param optional
     *         the optional to create a stream from
     * @param <T>
     *         the type of the value in the optional
     *
     * @return the created stream
     */
    // This will become obsolete once Java 9 is released
    public static <T> Stream<T> stream(final Optional<T> optional) {
        notNull(optional);
        return optional.map(Stream::of).orElseGet(Stream::empty);
    }

}
