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

import java.util.function.BinaryOperator;

import static se.sawano.java.commons.lang.validate.Validate.validState;

public class Streams {

    private Streams() {}

    /**
     * A {@link BinaryOperator} that can be used in reduce operations to uphold invariants. For example, to ensure a stream only contains at most one element:
     *
     * <pre>
     * final Stream&lt;Element&gt; streamOfElements = ...
     * final Long uniqueId = 1L;
     * final Optional&lt;Element&gt; foundElement = streamOfElements.filter(e -&gt; uniqueId.equals(e.id))
     *                                                        .reduce(toOnlyOne());
     * </pre>
     *
     * @param <T>
     *         the type of the elements in the stream
     *
     * @return nothing, as this method will always throw an {@link se.sawano.java.commons.lang.validate.exception.IllegalStateValidationException}
     */
    public static <T> BinaryOperator<T> toOnlyOne() {
        return toOnlyOne("Duplicates not allowed");
    }

    /**
     * A {@link BinaryOperator} that can be used in reduce operations to uphold invariants. For example, to ensure a stream only contains at most one element:
     *
     * <pre>
     * final Stream&lt;Element&gt; streamOfElements = ...
     * final Long uniqueId = 1L;
     * final Optional&lt;Element&gt; foundElement = streamOfElements.filter(e -&gt; uniqueId.equals(e.id))
     *                                                        .reduce(toOnlyOne("Expected only one or none element to match given id"));
     * </pre>
     *
     * @param <T>
     *         the type of the elements in the stream
     * @param message
     *         the exception message to use
     *
     * @return nothing, as this method will always throw an {@link se.sawano.java.commons.lang.validate.exception.IllegalStateValidationException}
     */
    public static <T> BinaryOperator<T> toOnlyOne(final String message) {
        return (t, t2) -> {
            validState(false, message);
            return null;
        };
    }

    /**
     * A {@link BinaryOperator} that can be used in reduce operations to uphold invariants. For example, to ensure a stream only contains at most one element:
     *
     * <pre>
     * final Stream&lt;Element&gt; streamOfElements = ...
     * final Long uniqueId = 1L;
     * final Optional&lt;Element&gt; foundElement = streamOfElements.filter(e -&gt; uniqueId.equals(e.id))
     *                                                        .reduce(toOnlyOne("Expected only one or none element to match given id: %d", uniqueId));
     * </pre>
     *
     * @param <T>
     *         the type of the elements in the stream
     * @param message
     *         the exception message to use
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return nothing, as this method will always throw an {@link se.sawano.java.commons.lang.validate.exception.IllegalStateValidationException}
     */
    public static <T> BinaryOperator<T> toOnlyOne(final String message, final Object... values) {
        return (t, t2) -> {
            validState(false, message, values);
            return null;
        };
    }
}
