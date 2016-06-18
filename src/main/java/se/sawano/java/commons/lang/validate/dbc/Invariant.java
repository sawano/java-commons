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

import se.sawano.java.commons.lang.validate.AbstractValidate;
import se.sawano.java.commons.lang.validate.Validate;
import se.sawano.java.commons.lang.validate.dbc.exception.*;

import java.util.Collection;
import java.util.Map;

/**
 * Utility methods for performing assertions. This class is identical to {@link Validate} except that the exceptions thrown when an assertion fails is of the type {@link InvarianceException}. Please
 * see {@link Validate} for documentation.
 *
 * @see Validate
 */
public class Invariant {

    private Invariant() {}

    // Method without varargs to increase performance
    public static <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value, final String message) {
        return INSTANCE.exclusiveBetween(start, end, value, message);
    }

    // Method without varargs to increase performance
    public static <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value, final String message) {
        return INSTANCE.inclusiveBetween(start, end, value, message);
    }

    // Method without varargs to increase performance
    public static <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type, final String message) {
        return INSTANCE.isAssignableFrom(superType, type, message);
    }

    // Method without varargs to increase performance
    public static void isTrue(final boolean expression, final String message) {
        INSTANCE.isTrue(expression, message);
    }

    // Method without varargs to increase performance
    public static CharSequence matchesPattern(final CharSequence input, final String pattern, final String message) {
        return INSTANCE.matchesPattern(input, pattern, message);
    }

    // Method without varargs to increase performance
    public static <T extends Iterable<?>> T noNullElements(final T iterable, final String message) {
        return INSTANCE.noNullElements(iterable, message);
    }

    // Method without varargs to increase performance
    public static <T> T[] noNullElements(final T[] array, final String message) {
        return INSTANCE.noNullElements(array, message);
    }

    // Method without varargs to increase performance
    public static <T extends CharSequence> T notBlank(final T chars, final String message) {
        return INSTANCE.notBlank(chars, message);
    }

    // Method without varargs to increase performance
    public static <T extends Collection<?>> T notEmpty(final T collection, final String message) {
        return INSTANCE.notEmpty(collection, message);
    }

    // Method without varargs to increase performance
    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message) {
        return INSTANCE.notEmpty(map, message);
    }

    // Method without varargs to increase performance
    public static <T extends CharSequence> T notEmpty(final T chars, final String message) {
        return INSTANCE.notEmpty(chars, message);
    }

    // Method without varargs to increase performance
    public static <T> T[] notEmpty(final T[] array, final String message) {
        return INSTANCE.notEmpty(array, message);
    }

    // Method without varargs to increase performance
    public static <T> T notNull(final T object, final String message) {
        return INSTANCE.notNull(object, message);
    }

    // Method without varargs to increase performance
    public static <T extends Collection<?>> T validIndex(final T collection, final int index, final String message) {
        return INSTANCE.validIndex(collection, index, message);
    }

    // Method without varargs to increase performance
    public static <T extends CharSequence> T validIndex(final T chars, final int index, final String message) {
        return INSTANCE.validIndex(chars, index, message);
    }

    // Method without varargs to increase performance
    public static <T> T[] validIndex(final T[] array, final int index, final String message) {
        return INSTANCE.validIndex(array, index, message);
    }

    // Method without varargs to increase performance
    public static void validState(final boolean expression, final String message) {
        INSTANCE.validState(expression, message);
    }

    // Method without varargs to increase performance
    public static <T> void isNull(final T object, final String message) {
        INSTANCE.isNull(object, message);
    }

    public static <T> void isNull(final T object) {
        INSTANCE.isNull(object);
    }

    public static <T> void isNull(final T object, final String message, final Object... values) {
        INSTANCE.isNull(object, message, values);
    }

    public static void isFalse(final boolean expression, final String message, final long value) {
        INSTANCE.isFalse(expression, message, value);
    }

    public static void isFalse(final boolean expression, final String message, final double value) {
        INSTANCE.isFalse(expression, message, value);
    }

    // Method without varargs to increase performance
    public static void isFalse(final boolean expression, final String message) {
        INSTANCE.isFalse(expression, message);
    }

    public static void isFalse(final boolean expression, final String message, final Object... values) {
        INSTANCE.isFalse(expression, message, values);
    }

    public static void isFalse(final boolean expression) {
        INSTANCE.isFalse(expression);
    }

    public static void isTrue(final boolean expression, final String message, final long value) {
        INSTANCE.isTrue(expression, message, value);
    }

    public static void isTrue(final boolean expression, final String message, final double value) {
        INSTANCE.isTrue(expression, message, value);
    }

    public static void isTrue(final boolean expression, final String message, final Object... values) {
        INSTANCE.isTrue(expression, message, values);
    }

    public static void isTrue(final boolean expression) {
        INSTANCE.isTrue(expression);
    }

    public static <T> T notNull(final T object) {
        return INSTANCE.notNull(object);
    }

    public static <T> T notNull(final T object, final String message, final Object... values) {
        return INSTANCE.notNull(object, message, values);
    }

    public static <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
        return INSTANCE.notEmpty(array, message, values);
    }

    public static <T> T[] notEmpty(final T[] array) {
        return INSTANCE.notEmpty(array);
    }

    public static <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... values) {
        return INSTANCE.notEmpty(collection, message, values);
    }

    public static <T extends Collection<?>> T notEmpty(final T collection) {
        return INSTANCE.notEmpty(collection);
    }

    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
        return INSTANCE.notEmpty(map, message, values);
    }

    public static <T extends Map<?, ?>> T notEmpty(final T map) {
        return INSTANCE.notEmpty(map);
    }

    public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
        return INSTANCE.notEmpty(chars, message, values);
    }

    public static <T extends CharSequence> T notEmpty(final T chars) {
        return INSTANCE.notEmpty(chars);
    }

    public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
        return INSTANCE.notBlank(chars, message, values);
    }

    public static <T extends CharSequence> T notBlank(final T chars) {
        return INSTANCE.notBlank(chars);
    }

    public static <T> T[] noNullElements(final T[] array, final String message, final Object... values) {
        return INSTANCE.noNullElements(array, message, values);
    }

    public static <T> T[] noNullElements(final T[] array) {
        return INSTANCE.noNullElements(array);
    }

    public static <T extends Iterable<?>> T noNullElements(final T iterable, final String message, final Object... values) {
        return INSTANCE.noNullElements(iterable, message, values);
    }

    public static <T extends Iterable<?>> T noNullElements(final T iterable) {
        return INSTANCE.noNullElements(iterable);
    }

    public static <T> T[] validIndex(final T[] array, final int index, final String message, final Object... values) {
        return INSTANCE.validIndex(array, index, message, values);
    }

    public static <T> T[] validIndex(final T[] array, final int index) {
        return INSTANCE.validIndex(array, index);
    }

    public static <T extends Collection<?>> T validIndex(final T collection, final int index, final String message, final Object... values) {
        return INSTANCE.validIndex(collection, index, message, values);
    }

    public static <T extends Collection<?>> T validIndex(final T collection, final int index) {
        return INSTANCE.validIndex(collection, index);
    }

    public static <T extends CharSequence> T validIndex(final T chars, final int index, final String message, final Object... values) {
        return INSTANCE.validIndex(chars, index, message, values);
    }

    public static <T extends CharSequence> T validIndex(final T chars, final int index) {
        return INSTANCE.validIndex(chars, index);
    }

    public static void validState(final boolean expression) {
        INSTANCE.validState(expression);
    }

    public static void validState(final boolean expression, final String message, final Object... values) {
        INSTANCE.validState(expression, message, values);
    }

    public static CharSequence matchesPattern(final CharSequence input, final String pattern) {
        return INSTANCE.matchesPattern(input, pattern);
    }

    public static CharSequence matchesPattern(final CharSequence input, final String pattern, final String message, final Object... values) {
        return INSTANCE.matchesPattern(input, pattern, message, values);
    }

    public static <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value) {
        return INSTANCE.inclusiveBetween(start, end, value);
    }

    public static <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value, final String message, final Object... values) {
        return INSTANCE.inclusiveBetween(start, end, value, message, values);
    }

    public static long inclusiveBetween(long start, long end, long value) {
        return INSTANCE.inclusiveBetween(start, end, value);
    }

    public static long inclusiveBetween(long start, long end, long value, String message) {
        return INSTANCE.inclusiveBetween(start, end, value, message);
    }

    public static double inclusiveBetween(double start, double end, double value) {
        return INSTANCE.inclusiveBetween(start, end, value);
    }

    public static double inclusiveBetween(double start, double end, double value, String message) {
        return INSTANCE.inclusiveBetween(start, end, value, message);
    }

    public static <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value) {
        return INSTANCE.exclusiveBetween(start, end, value);
    }

    public static <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value, final String message, final Object... values) {
        return INSTANCE.exclusiveBetween(start, end, value, message, values);
    }

    public static long exclusiveBetween(long start, long end, long value) {
        return INSTANCE.exclusiveBetween(start, end, value);
    }

    public static long exclusiveBetween(long start, long end, long value, String message) {
        return INSTANCE.exclusiveBetween(start, end, value, message);
    }

    public static double exclusiveBetween(double start, double end, double value) {
        return INSTANCE.exclusiveBetween(start, end, value);
    }

    public static double exclusiveBetween(double start, double end, double value, String message) {
        return INSTANCE.exclusiveBetween(start, end, value, message);
    }

    public static <T> T isInstanceOf(final Class<?> type, final T obj) {
        return INSTANCE.isInstanceOf(type, obj);
    }

    public static <T> T isInstanceOf(final Class<?> type, final T obj, final String message, final Object... values) {
        return INSTANCE.isInstanceOf(type, obj, message, values);
    }

    public static <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type) {
        return INSTANCE.isAssignableFrom(superType, type);
    }

    public static <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type, final String message, final Object... values) {
        return INSTANCE.isAssignableFrom(superType, type, message, values);
    }

    private static AbstractValidate<InvarianceException> INSTANCE = new AbstractValidate<InvarianceException>() {

        @Override
        protected IllegalArgumentInvarianceException illegalArgument(final String message) {
            return new IllegalArgumentInvarianceException(message);
        }

        @Override
        protected NullPointerInvarianceException nullPointer(final String message) {
            return new NullPointerInvarianceException(message);
        }

        @Override
        protected IndexOutOfBoundsInvarianceException indexOutOfBounds(final String message) {
            return new IndexOutOfBoundsInvarianceException(message);
        }

        @Override
        protected IllegalStateInvarianceException illegalState(final String message) {
            return new IllegalStateInvarianceException(message);
        }

        @Override
        protected IllegalArgumentInvarianceException illegalArgument(final Exception e, final String message) {
            return new IllegalArgumentInvarianceException(message, e);
        }

    };

}
