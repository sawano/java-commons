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

package se.sawano.java.commons.lang.validate;

import se.sawano.java.commons.lang.validate.exception.IllegalArgumentValidationException;
import se.sawano.java.commons.lang.validate.exception.IllegalStateValidationException;
import se.sawano.java.commons.lang.validate.exception.NullPointerValidationException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Concrete implementation of validations.
 *
 * @see Validate
 */
public abstract class AbstractValidate<S extends RuntimeException> {

    private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
    private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
    private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_NOT_NULL_EX_MESSAGE = "The validated object is not null";
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
    private static final String DEFAULT_IS_FALSE_EX_MESSAGE = "The validated expression is true";
    private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
    private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
    private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
    private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
    private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
    private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";

    protected AbstractValidate() {
    }

    //  Methods without varargs
    //---------------------------------------------------------------------------------

    // Method without varargs to increase performance
    public <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value, final String message) {
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            fail(message);
        }
        return value;
    }

    // Method without varargs to increase performance
    public <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value, final String message) {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            fail(message);
        }
        return value;
    }

    // Method without varargs to increase performance
    public <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type, final String message) {
        if (!superType.isAssignableFrom(type)) {
            fail(message);
        }
        return type;
    }

    // Method without varargs to increase performance
    public void isFalse(final boolean expression, final String message) {
        if (expression) {
            fail(message);
        }
    }

    // Method without varargs to increase performance
    public void isTrue(final boolean expression, final String message) {
        if (!expression) {
            fail(message);
        }
    }

    // Method without varargs to increase performance
    public CharSequence matchesPattern(final CharSequence input, final String pattern, final String message) {
        if (!Pattern.matches(pattern, input)) {
            fail(message);
        }
        return input;
    }

    // Method without varargs to increase performance
    public <T extends Iterable<?>> T noNullElements(final T iterable, final String message) {
        notNull(iterable);
        final int index = indexOfNullElement(iterable);
        if (index != -1) {
            fail(String.format(message, index));
        }

        return iterable;
    }

    // Method without varargs to increase performance
    public <T> T[] noNullElements(final T[] array, final String message) {
        notNull(array);
        final int index = indexOfNullElement(array);
        if (index != -1) {
            fail(String.format(message, index));
        }

        return array;
    }

    // Method without varargs to increase performance
    public <T extends CharSequence> T notBlank(final T chars, final String message) {
        if (chars == null) {
            failNull(message);
        }
        if (StringUtils.isBlank(chars)) {
            fail(message);
        }
        return chars;
    }

    // Method without varargs to increase performance
    public <T extends Collection<?>> T notEmpty(final T collection, final String message) {
        if (collection == null) {
            failNull(message);
        }
        if (collection.isEmpty()) {
            fail(message);
        }
        return collection;
    }

    // Method without varargs to increase performance
    public <T extends Map<?, ?>> T notEmpty(final T map, final String message) {
        if (map == null) {
            failNull(message);
        }
        if (map.isEmpty()) {
            fail(message);
        }
        return map;
    }

    // Method without varargs to increase performance
    public <T extends CharSequence> T notEmpty(final T chars, final String message) {
        if (chars == null) {
            failNull(message);
        }
        if (chars.length() == 0) {
            fail(message);
        }
        return chars;
    }

    // Method without varargs to increase performance
    public <T> T[] notEmpty(final T[] array, final String message) {
        if (array == null) {
            failNull(message);
        }
        if (array.length == 0) {
            fail(message);
        }
        return array;
    }

    // Method without varargs to increase performance
    public <T> T notNull(final T object, final String message) {
        if (object == null) {
            failNull(message);
        }
        return object;
    }

    // Method without varargs to increase performance
    public <T extends Collection<?>> T validIndex(final T collection, final int index, final String message) {
        notNull(collection);
        if (index < 0 || index >= collection.size()) {
            failIndexOutOfBounds(message);
        }
        return collection;
    }

    // Method without varargs to increase performance
    public <T extends CharSequence> T validIndex(final T chars, final int index, final String message) {
        notNull(chars);
        if (index < 0 || index >= chars.length()) {
            failIndexOutOfBounds(message);
        }
        return chars;
    }

    // Method without varargs to increase performance
    public <T> T[] validIndex(final T[] array, final int index, final String message) {
        notNull(array);
        if (index < 0 || index >= array.length) {
            failIndexOutOfBounds(message);
        }
        return array;
    }

    // Method without varargs to increase performance
    public void validState(final boolean expression, final String message) {
        if (!expression) {
            failIllegalState(message);
        }
    }

    // isTrue
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     * <pre>Validate.isTrue(i &gt; 0.0, "The value must be greater than zero: &#37;d", i);</pre>
     * <p>For performance reasons, the long value is passed as a separate parameter and appended to the exception message only in the case of an error.</p>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param value
     *         the value to append to the message when invalid
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code false}
     * @see #isTrue(boolean)
     * @see #isTrue(boolean, String, double)
     * @see #isTrue(boolean, String, Object...)
     */
    public void isTrue(final boolean expression, final String message, final long value) {
        if (!expression) {
            fail(String.format(message, value));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     * <pre>
     *     Validate.isTrue(d &gt; 0.0, "The value must be greater than zero: &#37;s", d);
     * </pre>
     * <p>For performance reasons, the double value is passed as a separate parameter and appended to the exception message only in the case of an error.</p>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param value
     *         the value to append to the message when invalid
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code false}
     * @see #isTrue(boolean)
     * @see #isTrue(boolean, String, long)
     * @see #isTrue(boolean, String, Object...)
     */
    public void isTrue(final boolean expression, final String message, final double value) {
        if (!expression) {
            fail(String.format(message, value));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     * <pre>
     * Validate.isTrue(i &gt;= min &amp;&amp; i &lt;= max, "The value must be between &#37;d and &#37;d", min, max);
     * Validate.isTrue(myObject.isOk(), "The object is not okay");
     * </pre>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code false}
     * @see #isTrue(boolean)
     * @see #isTrue(boolean, String, long)
     * @see #isTrue(boolean, String, double)
     */
    public void isTrue(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            fail(String.format(message, values));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise throwing an exception. This method is useful when validating according to an arbitrary boolean expression, such as validating
     * a primitive number or using your own custom validation expression.</p>
     * <pre>
     * Validate.isTrue(i &gt; 0);
     * Validate.isTrue(myObject.isOk());
     * </pre>
     * <p>The message of the exception is &quot;The validated expression is false&quot;.</p>
     *
     * @param expression
     *         the boolean expression to check
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code false}
     * @see #isTrue(boolean, String, long)
     * @see #isTrue(boolean, String, double)
     * @see #isTrue(boolean, String, Object...)
     */
    public void isTrue(final boolean expression) {
        if (!expression) {
            fail(DEFAULT_IS_TRUE_EX_MESSAGE);
        }
    }

    // isFalse
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the argument condition is {@code false}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     * <pre>
     *     Validate.isFalse(age &lt;= 20, "The age must be greater than 20: &#37;d", age);
     * </pre>
     * <p>For performance reasons, the long value is passed as a separate parameter and appended to the exception message only in the case of an error.</p>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param value
     *         the value to append to the message when invalid
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code true}
     * @see #isFalse(boolean)
     * @see #isFalse(boolean, String, double)
     * @see #isFalse(boolean, String, Object...)
     */
    public void isFalse(final boolean expression, final String message, final long value) {
        if (expression) {
            fail(String.format(message, value));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code false}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p> <p>For performance reasons, the double value is passed as a separate parameter and appended
     * to the exception message only in the case of an error.</p>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param value
     *         the value to append to the message when invalid
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code true}
     * @see #isFalse(boolean)
     * @see #isFalse(boolean, String, long)
     * @see #isFalse(boolean, String, Object...)
     */
    public void isFalse(final boolean expression, final String message, final double value) {
        if (expression) {
            fail(String.format(message, value));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code false}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code true}
     * @see #isFalse(boolean)
     * @see #isFalse(boolean, String, long)
     * @see #isFalse(boolean, String, double)
     */
    public void isFalse(final boolean expression, final String message, final Object... values) {
        if (expression) {
            fail(String.format(message, values));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code false}; otherwise throwing an exception. This method is useful when validating according to an arbitrary boolean expression, such as validating
     * a primitive number or using your own custom validation expression.</p> <p>The message of the exception is &quot;The validated expression is false&quot;.</p>
     *
     * @param expression
     *         the boolean expression to check
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code true}
     * @see #isFalse(boolean, String, long)
     * @see #isFalse(boolean, String, double)
     * @see #isFalse(boolean, String, Object...)
     */
    public void isFalse(final boolean expression) {
        if (expression) {
            fail(DEFAULT_IS_FALSE_EX_MESSAGE);
        }
    }

    // notNull
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument is not {@code null}; otherwise throwing an exception. </p>
     * <pre>Validate.notNull(myObject, "The object must not be null");</pre>
     * <p>The message of the exception is &quot;The validated object is null&quot;.</p>
     *
     * @param <T>
     *         the object type
     * @param object
     *         the object to check
     *
     * @return the validated object (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the object is {@code null}
     * @see #notNull(Object, String, Object...)
     */
    public <T> T notNull(final T object) {
        return notNull(object, DEFAULT_IS_NULL_EX_MESSAGE);
    }

    /**
     * <p>Validate that the specified argument is not {@code null}; otherwise throwing an exception with the specified message. </p>
     * <pre>Validate.notNull(myObject, "The object must not be null");</pre>
     *
     * @param <T>
     *         the object type
     * @param object
     *         the object to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message
     *
     * @return the validated object (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the object is {@code null}
     * @see #notNull(Object)
     */
    public <T> T notNull(final T object, final String message, final Object... values) {
        if (object == null) {
            failNull(String.format(message, values));
        }
        return object;
    }

    // notEmpty array
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor a length of zero (no elements); otherwise throwing an exception with the specified message. </p>
     * <pre>Validate.notEmpty(myArray, "The array must not be empty");</pre>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated array (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentValidationException
     *         if the array is empty
     * @see #notEmpty(Object[])
     */
    public <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
        if (array == null) {
            failNull(String.format(message, values));
        }
        if (array.length == 0) {
            fail(String.format(message, values));
        }
        return array;
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor a length of zero (no elements); otherwise throwing an exception. </p>
     * <pre>Validate.notEmpty(myArray);</pre>
     * <p>The message in the exception is &quot;The validated array is empty&quot;.</p>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     *
     * @return the validated array (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentException
     *         if the array is empty
     * @see #notEmpty(Object[], String, Object...)
     */
    public <T> T[] notEmpty(final T[] array) {
        return notEmpty(array, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
    }

    // notEmpty collection
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument collection is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception with the specified message. </p>
     * <pre>Validate.notEmpty(myCollection, "The collection must not be empty");</pre>
     *
     * @param <T>
     *         the collection type
     * @param collection
     *         the collection to check, validated not null by this method
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated collection (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the collection is {@code null}
     * @throws IllegalArgumentException
     *         if the collection is empty
     * @see #notEmpty(Object[])
     */
    public <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... values) {
        if (collection == null) {
            failNull(String.format(message, values));
        }
        if (collection.isEmpty()) {
            fail(String.format(message, values));
        }
        return collection;
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception.</p>
     * <pre>Validate.notEmpty(myCollection);</pre>
     * <p>The message in the exception is &quot;The validated collection is empty&quot;.</p>
     *
     * @param <T>
     *         the collection type
     * @param collection
     *         the collection to check, validated not null by this method
     *
     * @return the validated collection (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the collection is {@code null}
     * @throws IllegalArgumentException
     *         if the collection is empty
     * @see #notEmpty(java.util.Collection, String, Object...)
     */
    public <T extends Collection<?>> T notEmpty(final T collection) {
        return notEmpty(collection, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
    }

    // notEmpty map
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument map is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.notEmpty(myMap, "The map must not be empty");</pre>
     *
     * @param <T>
     *         the map type
     * @param map
     *         the map to check, validated not null by this method
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated map (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the map is {@code null}
     * @throws IllegalArgumentException
     *         if the map is empty
     * @see #notEmpty(Object[])
     */
    public <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
        if (map == null) {
            failNull(String.format(message, values));
        }
        if (map.isEmpty()) {
            fail(String.format(message, values));
        }
        return map;
    }

    /**
     * <p>Validate that the specified argument map is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception. </p>
     * <pre>Validate.notEmpty(myMap);</pre>
     * <p>The message in the exception is &quot;The validated map is empty&quot;.</p>
     *
     * @param <T>
     *         the map type
     * @param map
     *         the map to check, validated not null by this method
     *
     * @return the validated map (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the map is {@code null}
     * @throws IllegalArgumentException
     *         if the map is empty
     * @see #notEmpty(java.util.Map, String, Object...)
     */
    public <T extends Map<?, ?>> T notEmpty(final T map) {
        return notEmpty(map, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
    }

    // notEmpty string
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null} nor a length of zero (no characters); otherwise throwing an exception with the specified message. </p>
     * <pre>Validate.notEmpty(myString, "The string must not be empty");</pre>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated character sequence (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IllegalArgumentException
     *         if the character sequence is empty
     * @see #notEmpty(CharSequence)
     */
    public <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
        if (chars == null) {
            failNull(String.format(message, values));
        }
        if (chars.length() == 0) {
            fail(String.format(message, values));
        }
        return chars;
    }

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null} nor a length of zero (no characters); otherwise throwing an exception with the specified message. </p>
     * <pre>Validate.notEmpty(myString);</pre>
     * <p>The message in the exception is &quot;The validated character sequence is empty&quot;.</p>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     *
     * @return the validated character sequence (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IllegalArgumentException
     *         if the character sequence is empty
     * @see #notEmpty(CharSequence, String, Object...)
     */
    public <T extends CharSequence> T notEmpty(final T chars) {
        return notEmpty(chars, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE);
    }

    // notBlank string
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null}, a length of zero (no characters), empty nor whitespace; otherwise throwing an exception with the specified
     * message. </p>
     * <pre>Validate.notBlank(myString, "The string must not be blank");</pre>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated character sequence (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IllegalArgumentException
     *         if the character sequence is blank
     * @see #notBlank(CharSequence)
     */
    public <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
        if (chars == null) {
            failNull(String.format(message, values));
        }
        if (StringUtils.isBlank(chars)) {
            fail(String.format(message, values));
        }
        return chars;
    }

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null}, a length of zero (no characters), empty nor whitespace; otherwise throwing an exception.</p>
     * <pre>Validate.notBlank(myString);</pre>
     * <p>The message in the exception is &quot;The validated character sequence is blank&quot;.</p>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     *
     * @return the validated character sequence (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IllegalArgumentException
     *         if the character sequence is blank
     * @see #notBlank(CharSequence, String, Object...)
     */
    public <T extends CharSequence> T notBlank(final T chars) {
        return notBlank(chars, DEFAULT_NOT_BLANK_EX_MESSAGE);
    }

    // noNullElements array
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.noNullElements(myArray, "The array contain null at position %d");</pre>
     * <p>If the array is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p> <p>If the array has a {@code null} element, then the iteration index of the
     * invalid element is appended to the {@code values} argument.</p>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated array (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentException
     *         if an element is {@code null}
     * @see #noNullElements(Object[])
     */
    public <T> T[] noNullElements(final T[] array, final String message, final Object... values) {
        notNull(array);
        final int index = indexOfNullElement(array);
        if (index != -1) {
            fail(String.format(message, ArrayUtils.add(values, index, this)));
        }
        return array;
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception.</p>
     * <pre>Validate.noNullElements(myArray);</pre>
     * <p>If the array is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p> <p>If the array has a {@code null} element, then the message in the exception
     * is &quot;The validated array contains null element at index: &quot; followed by the index.</p>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     *
     * @return the validated array (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentException
     *         if an element is {@code null}
     * @see #noNullElements(Object[], String, Object...)
     */
    public <T> T[] noNullElements(final T[] array) {
        return noNullElements(array, DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE);
    }

    // noNullElements iterable
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument iterable is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception with the specified message. </p>
     * <pre>Validate.noNullElements(myCollection, "The collection contains null at position %d");</pre>
     * <p>If the iterable is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p><p>If the iterable has a {@code null} element, then the iteration index of
     * the invalid element is appended to the {@code values} argument.</p>
     *
     * @param <T>
     *         the iterable type
     * @param iterable
     *         the iterable to check, validated not null by this method
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated iterable (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentException
     *         if an element is {@code null}
     * @see #noNullElements(Iterable)
     */
    public <T extends Iterable<?>> T noNullElements(final T iterable, final String message, final Object... values) {
        notNull(iterable);
        final int index = indexOfNullElement(iterable);
        if (index != -1) {
            fail(String.format(message, ArrayUtils.addAll(this, values, index)));
        }

        return iterable;
    }

    /**
     * <p>Validate that the specified argument iterable is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception. </p>
     * <pre>Validate.noNullElements(myCollection);</pre>
     * <p>If the iterable is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p><p>If the array has a {@code null} element, then the message in the
     * exception is &quot;The validated iterable contains null element at index: &quot; followed by the index.</p>
     *
     * @param <T>
     *         the iterable type
     * @param iterable
     *         the iterable to check, validated not null by this method
     *
     * @return the validated iterable (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentException
     *         if an element is {@code null}
     * @see #noNullElements(Iterable, String, Object...)
     */
    public <T extends Iterable<?>> T noNullElements(final T iterable) {
        return noNullElements(iterable, DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE);
    }

    // validIndex array
    //---------------------------------------------------------------------------------

    /**
     * <p>Validates that the index is within the bounds of the argument array; otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.validIndex(myArray, 2, "The array index is invalid: ");</pre>
     * <p>If the array is {@code null}, then the message of the exception is &quot;The validated object is null&quot;.</p>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     * @param index
     *         the index to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated array (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(Object[], int)
     */
    public <T> T[] validIndex(final T[] array, final int index, final String message, final Object... values) {
        notNull(array);
        if (index < 0 || index >= array.length) {
            failIndexOutOfBounds(String.format(message, values));
        }
        return array;
    }

    /**
     * <p>Validates that the index is within the bounds of the argument array; otherwise throwing an exception.</p>
     * <pre>Validate.validIndex(myArray, 2);</pre>
     * <p>If the array is {@code null}, then the message of the exception is &quot;The validated object is null&quot;.</p><p>If the index is invalid, then the message of the exception is &quot;The
     * validated array index is invalid: &quot; followed by the index.</p>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     * @param index
     *         the index to check
     *
     * @return the validated array (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(Object[], int, String, Object...)
     */
    public <T> T[] validIndex(final T[] array, final int index) {
        return validIndex(array, index, DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE, index);
    }

    // validIndex collection
    //---------------------------------------------------------------------------------

    /**
     * <p>Validates that the index is within the bounds of the argument collection; otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.validIndex(myCollection, 2, "The collection index is invalid: ");</pre>
     * <p>If the collection is {@code null}, then the message of the exception is &quot;The validated object is null&quot;.</p>
     *
     * @param <T>
     *         the collection type
     * @param collection
     *         the collection to check, validated not null by this method
     * @param index
     *         the index to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated collection (never {@code null} for chaining)
     *
     * @throws NullPointerValidationException
     *         if the collection is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(java.util.Collection, int)
     */
    public <T extends Collection<?>> T validIndex(final T collection, final int index, final String message, final Object... values) {
        notNull(collection);
        if (index < 0 || index >= collection.size()) {
            failIndexOutOfBounds(String.format(message, values));
        }
        return collection;
    }

    /**
     * <p>Validates that the index is within the bounds of the argument collection; otherwise throwing an exception.</p>
     * <pre>Validate.validIndex(myCollection, 2);</pre>
     * <p>If the index is invalid, then the message of the exception is &quot;The validated collection index is invalid: &quot; followed by the index.</p>
     *
     * @param <T>
     *         the collection type
     * @param collection
     *         the collection to check, validated not null by this method
     * @param index
     *         the index to check
     *
     * @return the validated collection (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the collection is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(java.util.Collection, int, String, Object...)
     */
    public <T extends Collection<?>> T validIndex(final T collection, final int index) {
        return validIndex(collection, index, DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE, index);
    }

    // validIndex string
    //---------------------------------------------------------------------------------

    /**
     * <p>Validates that the index is within the bounds of the argument character sequence; otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.validIndex(myStr, 2, "The string index is invalid: ");</pre>
     * <p>If the character sequence is {@code null}, then the message of the exception is &quot;The validated object is null&quot;.</p>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     * @param index
     *         the index to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the validated character sequence (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(CharSequence, int)
     */
    public <T extends CharSequence> T validIndex(final T chars, final int index, final String message, final Object... values) {
        notNull(chars);
        if (index < 0 || index >= chars.length()) {
            failIndexOutOfBounds(String.format(message, values));
        }
        return chars;
    }

    /**
     * <p>Validates that the index is within the bounds of the argument character sequence; otherwise throwing an exception.</p>
     * <pre>Validate.validIndex(myStr, 2);</pre>
     * <p>If the character sequence is {@code null}, then the message of the exception is &quot;The validated object is null&quot;.</p><p>If the index is invalid, then the message of the exception is
     * &quot;The validated character sequence index is invalid: &quot; followed by the index.</p>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     * @param index
     *         the index to check
     *
     * @return the validated character sequence (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(CharSequence, int, String, Object...)
     */
    public <T extends CharSequence> T validIndex(final T chars, final int index) {
        return validIndex(chars, index, DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE, index);
    }

    // validState
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the stateful condition is {@code true}; otherwise throwing an exception. This method is useful when validating according to an arbitrary boolean expression, such as validating
     * a primitive number or using your own custom validation expression.</p>
     * <pre>
     * Validate.validState(field &gt; 0);
     * Validate.validState(this.isOk());</pre>
     *
     * <p>The message of the exception is &quot;The validated state is false&quot;.</p>
     *
     * @param expression
     *         the boolean expression to check
     *
     * @throws IllegalStateValidationException
     *         if expression is {@code false}
     * @see #validState(boolean, String, Object...)
     */
    public void validState(final boolean expression) {
        if (!expression) {
            failIllegalState(DEFAULT_VALID_STATE_EX_MESSAGE);
        }
    }

    /**
     * <p>Validate that the stateful condition is {@code true}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     * <pre>Validate.validState(this.isOk(), "The state is not OK: %s", myObject);</pre>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @throws IllegalStateValidationException
     *         if expression is {@code false}
     * @see #validState(boolean)
     */
    public void validState(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            failIllegalState(String.format(message, values));
        }
    }

    // matchesPattern
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument character sequence matches the specified regular expression pattern; otherwise throwing an exception.</p>
     * <pre>Validate.matchesPattern("hi", "[a-z]*");</pre>
     * <p>The syntax of the pattern is the one used in the {@link java.util.regex.Pattern} class.</p>
     *
     * @param input
     *         the character sequence to validate, not null
     * @param pattern
     *         the regular expression pattern, not null
     *
     * @return the input
     *
     * @throws IllegalArgumentValidationException
     *         if the character sequence does not match the pattern
     * @see #matchesPattern(CharSequence, String, String, Object...)
     */
    public CharSequence matchesPattern(final CharSequence input, final String pattern) {
        if (!Pattern.matches(pattern, input)) {
            fail(String.format(DEFAULT_MATCHES_PATTERN_EX, input, pattern));
        }
        return input;
    }

    /**
     * <p>Validate that the specified argument character sequence matches the specified regular expression pattern; otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.matchesPattern("hi", "[a-z]*", "%s does not match %s", "hi" "[a-z]*");</pre>
     * <p>The syntax of the pattern is the one used in the {@link java.util.regex.Pattern} class.</p>
     *
     * @param input
     *         the character sequence to validate, not null
     * @param pattern
     *         the regular expression pattern, not null
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the input
     *
     * @throws IllegalArgumentValidationException
     *         if the character sequence does not match the pattern
     * @see #matchesPattern(CharSequence, String)
     */
    public CharSequence matchesPattern(final CharSequence input, final String pattern, final String message, final Object... values) {
        if (!Pattern.matches(pattern, input)) {
            fail(String.format(message, values));
        }
        return input;
    }

    // inclusiveBetween
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument object fall between the two inclusive values specified; otherwise, throws an exception.</p>
     * <pre>Validate.inclusiveBetween(0, 2, 1);</pre>
     *
     * @param <T>
     *         the type of the start and end values
     * @param <V>
     *         the type of the argument
     * @param start
     *         the inclusive start value, not null
     * @param end
     *         the inclusive end value, not null
     * @param value
     *         the object to validate, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     * @see #inclusiveBetween(Object, Object, Comparable, String, Object...)
     */
    public <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value) {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            fail(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
        return value;
    }

    /**
     * <p>Validate that the specified argument object fall between the two inclusive values specified; otherwise, throws an exception with the specified message.</p>
     * <pre>Validate.inclusiveBetween(0, 2, 1, "Not in boundaries");</pre>
     *
     * @param <T>
     *         the type of the start and end values
     * @param <V>
     *         the type of the value
     * @param start
     *         the inclusive start value, not null
     * @param end
     *         the inclusive end value, not null
     * @param value
     *         the object to validate, not null
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     * @see #inclusiveBetween(Object, Object, Comparable)
     */
    public <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value, final String message, final Object... values) {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            fail(String.format(message, values));
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two inclusive values specified; otherwise, throws an exception.
     * <pre>Validate.inclusiveBetween(0, 2, 1);</pre>
     *
     * @param start
     *         the inclusive start value
     * @param end
     *         the inclusive end value
     * @param value
     *         the value to validate
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries (inclusive)
     */
    public long inclusiveBetween(long start, long end, long value) {
        if (value < start || value > end) {
            fail(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two inclusive values specified; otherwise, throws an exception with the specified message.
     * <pre>Validate.inclusiveBetween(0, 2, 1, "Not in range");</pre>
     *
     * @param start
     *         the inclusive start value
     * @param end
     *         the inclusive end value
     * @param value
     *         the value to validate
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     */
    public long inclusiveBetween(long start, long end, long value, String message) {
        if (value < start || value > end) {
            fail(message);
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two inclusive values specified; otherwise, throws an exception.
     * <pre>Validate.inclusiveBetween(0.1, 2.1, 1.1);</pre>
     *
     * @param start
     *         the inclusive start value
     * @param end
     *         the inclusive end value
     * @param value
     *         the value to validate
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries (inclusive)
     */
    public double inclusiveBetween(double start, double end, double value) {
        if (value < start || value > end) {
            fail(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two inclusive values specified; otherwise, throws an exception with the specified message.
     * <pre>Validate.inclusiveBetween(0.1, 2.1, 1.1, "Not in range");</pre>
     *
     * @param start
     *         the inclusive start value
     * @param end
     *         the inclusive end value
     * @param value
     *         the value to validate
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     */
    public double inclusiveBetween(double start, double end, double value, String message) {
        if (value < start || value > end) {
            fail(message);
        }
        return value;
    }

    // exclusiveBetween
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument object fall between the two exclusive values specified; otherwise, throws an exception.</p>
     * <pre>Validate.exclusiveBetween(0, 2, 1);</pre>
     *
     * @param <T>
     *         the type of the argument start and end values
     * @param <V>
     *         the type of the value
     * @param start
     *         the exclusive start value, not null
     * @param end
     *         the exclusive end value, not null
     * @param value
     *         the object to validate, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     * @see #exclusiveBetween(Object, Object, Comparable, String, Object...)
     */
    public <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value) {
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            fail(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
        return value;
    }

    /**
     * <p>Validate that the specified argument object fall between the two exclusive values specified; otherwise, throws an exception with the specified message.</p>
     * <pre>Validate.exclusiveBetween(0, 2, 1, "Not in boundaries");</pre>
     *
     * @param <T>
     *         the type of the start and end values
     * @param <V>
     *         the type of the object
     * @param start
     *         the exclusive start value, not null
     * @param end
     *         the exclusive end value, not null
     * @param value
     *         the object to validate, not null
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     * @see #exclusiveBetween(Object, Object, Comparable)
     */
    public <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value, final String message, final Object... values) {
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            fail(String.format(message, values));
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two exclusive values specified; otherwise, throws an exception.
     * <pre>Validate.exclusiveBetween(0, 2, 1);</pre>
     *
     * @param start
     *         the exclusive start value
     * @param end
     *         the exclusive end value
     * @param value
     *         the value to validate
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls out of the boundaries
     */
    public long exclusiveBetween(long start, long end, long value) {
        if (value <= start || value >= end) {
            fail(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two exclusive values specified; otherwise, throws an exception with the specified message.
     * <pre>Validate.exclusiveBetween(0, 2, 1, "Not in range");</pre>
     *
     * @param start
     *         the exclusive start value
     * @param end
     *         the exclusive end value
     * @param value
     *         the value to validate
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     */
    public long exclusiveBetween(long start, long end, long value, String message) {
        if (value <= start || value >= end) {
            fail(message);
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two exclusive values specified; otherwise, throws an exception.
     * <pre>Validate.exclusiveBetween(0.1, 2.1, 1.1);</pre>
     *
     * @param start
     *         the exclusive start value
     * @param end
     *         the exclusive end value
     * @param value
     *         the value to validate
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls out of the boundaries
     */
    @SuppressWarnings("boxing")
    public double exclusiveBetween(double start, double end, double value) {
        if (value <= start || value >= end) {
            fail(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
        return value;
    }

    /**
     * Validate that the specified primitive value falls between the two exclusive values specified; otherwise, throws an exception with the specified message.
     * <pre>Validate.exclusiveBetween(0.1, 2.1, 1.1, "Not in range");</pre>
     *
     * @param start
     *         the exclusive start value
     * @param end
     *         the exclusive end value
     * @param value
     *         the value to validate
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     */
    public double exclusiveBetween(double start, double end, double value, String message) {
        if (value <= start || value >= end) {
            fail(message);
        }
        return value;
    }

    // isInstanceOf
    //---------------------------------------------------------------------------------

    /**
     * Validates that the argument is an instance of the specified class, if not throws an exception. <p>This method is useful when validating according to an arbitrary class</p>
     * <pre>Validate.isInstanceOf(OkClass.class, object);</pre>
     * <p>The message of the exception is &quot;Expected type: {type}, actual: {obj_type}&quot;</p>
     *
     * @param <T>
     *         the type of the object
     * @param type
     *         the class the object must be validated against, not null
     * @param obj
     *         the object to check, null throws an exception
     *
     * @return the object
     *
     * @throws IllegalArgumentValidationException
     *         if argument is not of specified class
     * @see #isInstanceOf(Class, Object, String, Object...)
     */
    public <T> T isInstanceOf(final Class<?> type, final T obj) {
        if (!type.isInstance(obj)) {
            fail(String.format(DEFAULT_IS_INSTANCE_OF_EX_MESSAGE, type.getName(), obj == null ? "null" : obj.getClass().getName()));
        }
        return obj;
    }

    /**
     * <p>Validate that the argument is an instance of the specified class; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary
     * class</p>
     * <pre>Validate.isInstanceOf(OkClass.classs, object, "Wrong class, object is of class %s",
     *   object.getClass().getName());</pre>
     *
     * @param <T>
     *         the type of the object to check
     * @param type
     *         the class the object must be validated against, not null
     * @param obj
     *         the object to check, null throws an exception
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the object
     *
     * @throws IllegalArgumentValidationException
     *         if argument is not of specified class
     * @see #isInstanceOf(Class, Object)
     */
    public <T> T isInstanceOf(final Class<?> type, final T obj, final String message, final Object... values) {
        if (!type.isInstance(obj)) {
            fail(String.format(message, values));
        }
        return obj;
    }

    // isAssignableFrom
    //---------------------------------------------------------------------------------

    /**
     * Validates that the argument can be converted to the specified class, if not, throws an exception. <p>This method is useful when validating that there will be no casting errors.</p>
     * <pre>Validate.isAssignableFrom(SuperClass.class, object.getClass());</pre>
     * <p>The message format of the exception is &quot;Cannot assign {type} to {superType}&quot;</p>
     *
     * @param <T>
     *         the type of the class to check
     * @param superType
     *         the class the class must be validated against, not null
     * @param type
     *         the class to check, not null
     *
     * @return the type
     *
     * @throws IllegalArgumentValidationException
     *         if type argument is not assignable to the specified superType
     * @see #isAssignableFrom(Class, Class, String, Object...)
     */
    public <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type) {
        if (!superType.isAssignableFrom(type)) {
            fail(String.format(DEFAULT_IS_ASSIGNABLE_EX_MESSAGE, type == null ? "null" : type.getName(), superType.getName()));
        }
        return type;
    }

    /**
     * Validates that the argument can be converted to the specified class, if not throws an exception. <p>This method is useful when validating if there will be no casting errors.</p>
     * <pre>Validate.isAssignableFrom(SuperClass.class, object.getClass());</pre>
     * <p>The message of the exception is &quot;The validated object can not be converted to the&quot; followed by the name of the class and &quot;class&quot;</p>
     *
     * @param <T>
     *         the type of the class to check
     * @param superType
     *         the class the class must be validated against, not null
     * @param type
     *         the class to check, not null
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the class
     *
     * @throws IllegalArgumentValidationException
     *         if argument can not be converted to the specified class
     * @see #isAssignableFrom(Class, Class)
     */
    public <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type, final String message, final Object... values) {
        if (!superType.isAssignableFrom(type)) {
            fail(String.format(message, values));
        }
        return type;
    }

    /**
     * Returns the index of the first null element, or {@code -1} if no null element was found.
     *
     * @param iterable
     *         the iterable to check for null elements
     * @param <T>
     *         the type of the iterable
     *
     * @return the index of the first null element, or {@code -1} if no null element was found
     */
    private static <T extends Iterable<?>> int indexOfNullElement(T iterable) {
        final Iterator<?> it = iterable.iterator();
        for (int i = 0; it.hasNext(); i++) {
            if (it.next() == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first null element, or {@code -1} if no null element was found.
     *
     * @param array
     *         the array to check for null elements
     * @param <T>
     *         the type of the array elements
     *
     * @return the index of the first null element, or {@code -1} if no null element was found
     */
    private static <T> int indexOfNullElement(T[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private void fail(final String message) {
        throw illegalArgument(message);
    }

    private void failNull(final String message) {
        throw nullPointer(message);
    }

    private void failIndexOutOfBounds(final String message) {
        throw indexOutOfBounds(message);
    }

    private void failIllegalState(final String message) {
        throw illegalState(message);
    }

    private void failIllegalArgument(final ArrayStoreException ase, final String message) {
        throw illegalArgument(ase, message);
    }

    /**
     * Factory method for exception used for validation failures caused by an illegal argument.
     *
     * @param message
     *         the message to use in the exception
     *
     * @return the exception
     */
    protected abstract S illegalArgument(final String message);

    /**
     * Factory method for exception used for validation failures caused by a not-null requirement.
     *
     * @param message
     *         the message to use in the exception
     *
     * @return the exception
     */
    protected abstract S nullPointer(final String message);

    /**
     * Factory method for exception used for validation failures caused by an index being out of bounds.
     *
     * @param message
     *         the message to use in the exception
     *
     * @return the exception
     */
    protected abstract S indexOutOfBounds(final String message);

    /**
     * Factory method for exception used for validation failures caused by an illegal state.
     *
     * @param message
     *         the message to use in the exception
     *
     * @return the exception
     */
    protected abstract S illegalState(final String message);

    /**
     * Factory method for exception used for validation failures caused by an index being out of bounds. This is used internally and should generally return the same type of exception as {@link
     * #illegalArgument(String)}
     *
     * @param message
     *         the message to use in the exception
     * @param e
     *         the underlying exception
     *
     * @return the exception
     */
    protected abstract S illegalArgument(final Exception e, final String message);

    // org.apache.commons.lang3.StringUtils
    private static class StringUtils {

        /**
         * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
         * <pre>
         * StringUtils.isBlank(null)      = true
         * StringUtils.isBlank("")        = true
         * StringUtils.isBlank(" ")       = true
         * StringUtils.isBlank("bob")     = false
         * StringUtils.isBlank("  bob  ") = false
         * </pre>
         *
         * @param cs
         *         the CharSequence to check, may be null
         *
         * @return {@code true} if the CharSequence is null, empty or whitespace
         */
        public static boolean isBlank(final CharSequence cs) {
            int strLen;
            if (cs == null || (strLen = cs.length()) == 0) {
                return true;
            }
            for (int i = 0; i < strLen; i++) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

    }

    // org.apache.commons.lang3.ArrayUtils
    private static class ArrayUtils {
        /**
         * Returns a copy of the given array of size 1 greater than the argument. The last value of the array is left to the default value.
         *
         * @param array
         *         The array to copy, must not be {@code null}.
         * @param newArrayComponentType
         *         If {@code array} is {@code null}, create a size 1 array of this type.
         *
         * @return A new copy of the array of size 1 greater than the input.
         */
        @SuppressWarnings("SuspiciousSystemArraycopy")
        private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
            if (array != null) {
                final int arrayLength = Array.getLength(array);
                final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
                System.arraycopy(array, 0, newArray, 0, arrayLength);
                return newArray;
            }
            return Array.newInstance(newArrayComponentType, 1);
        }

        /**
         * <p>Copies the given array and adds the given element at the end of the new array.</p> <p> <p>The new array contains the same elements of the input array plus the given element in the last
         * position. The component type of the new array is the same as that of the input array.</p> <p> <p>If the input array is {@code null}, a new one element array is returned whose component type
         * is the same as the element, unless the element itself is null, in which case the return type is Object[]</p>
         * <pre>
         * ArrayUtils.add(null, null)      = [null]
         * ArrayUtils.add(null, "a")       = ["a"]
         * ArrayUtils.add(["a"], null)     = ["a", null]
         * ArrayUtils.add(["a"], "b")      = ["a", "b"]
         * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
         * </pre>
         *
         * @param <T>
         *         the component type of the array
         * @param array
         *         the array to "add" the element to, may be {@code null}
         * @param element
         *         the object to add, may be {@code null}
         *
         * @return A new array containing the existing elements plus the new element The returned array type will be that of the input array (unless null), in which case it will have the same type as
         * the element. If both are null, an se.sawano.java.commons.lang.validate.exception.IllegalArgumentValidationException is thrown
         *
         * @throws IllegalArgumentValidationException
         *         if both arguments are null
         */
        public static <T> T[] add(final T[] array, final T element, final AbstractValidate abstractValidate) {
            final Class<?> type;
            if (array != null) {
                type = array.getClass();
            }
            else if (element != null) {
                type = element.getClass();
            }
            else {
                throw abstractValidate.illegalArgument("Arguments cannot both be null");
            }
            @SuppressWarnings("unchecked") // type must be T
            final T[] newArray = (T[]) copyArrayGrow1(array, type);
            newArray[newArray.length - 1] = element;
            return newArray;
        }

        /**
         * <p>Adds all the elements of the given arrays into a new array.</p> <p>The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When an
         * array is returned, it is always a new array.</p>
         * <pre>
         * ArrayUtils.addAll(null, null)     = null
         * ArrayUtils.addAll(array1, null)   = cloned copy of array1
         * ArrayUtils.addAll(null, array2)   = cloned copy of array2
         * ArrayUtils.addAll([], [])         = []
         * ArrayUtils.addAll([null], [null]) = [null, null]
         * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
         * </pre>
         *
         * @param <T>
         *         the component type of the array
         * @param array1
         *         the first array whose elements are added to the new array, may be {@code null}
         * @param array2
         *         the second array whose elements are added to the new array, may be {@code null}
         *
         * @return The new array, {@code null} if both arrays are {@code null}. The type of the new array is the type of the first array, unless the first array is null, in which case the type is the
         * same as the second array.
         *
         * @throws IllegalArgumentValidationException
         *         if the array types are incompatible
         */
        public static <T> T[] addAll(final AbstractValidate abstractValidate, final T[] array1, final T... array2) {
            if (array1 == null) {
                return clone(array2);
            }
            else if (array2 == null) {
                return clone(array1);
            }
            final Class<?> type1 = array1.getClass().getComponentType();
            @SuppressWarnings("unchecked") // OK, because array is of type T
            final T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            try {
                System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            } catch (final ArrayStoreException ase) {
                // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
                final Class<?> type2 = array2.getClass().getComponentType();
                if (!type1.isAssignableFrom(type2)) {
                    abstractValidate.failIllegalArgument(ase, "Cannot store " + type2.getName() + " in an array of " + type1.getName());
                }
                throw ase; // No, so rethrow original
            }
            return joinedArray;
        }

        /**
         * <p>Shallow clones an array returning a typecast result and handling {@code null}.</p> <p> <p>The objects in the array are not cloned, thus there is no special handling for multi-dimensional
         * arrays.</p> <p> <p>This method returns {@code null} for a {@code null} input array.</p>
         *
         * @param <T>
         *         the component type of the array
         * @param array
         *         the array to shallow clone, may be {@code null}
         *
         * @return the cloned array, {@code null} if {@code null} input
         */
        public static <T> T[] clone(final T[] array) {
            if (array == null) {
                return null;
            }
            return array.clone();
        }
    }

}
