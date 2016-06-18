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

import se.sawano.java.commons.lang.validate.exception.*;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility methods for performing assertions. The exceptions thrown when an assertion fails is of the type {@link ValidationException} so that it can be distinguished from other exceptions that may
 * occur within a piece of code. For example, if using the assertions in a Design by Contract (DbC) fashion, being able to separate exceptions caused by contract violation from other exceptions is
 * very helpful.
 *
 * <p>This class is written in such a way that it is easy to customize to use custom exceptions. (See {@link AbstractValidate})</p>
 *
 * <p>All exceptions messages are format strings as defined by the Java platform (see {@link java.util.Formatter}). For example:</p>
 * <pre>
 * Validate.isTrue(age &gt; 20, "Must be older than %d years", 20);
 * </pre>
 *
 * <p>This class is thread safe</p>
 *
 * @see java.lang.String#format(String, Object...)
 * @see AbstractValidate
 */
public class Validate {

    private Validate() {}

    // Custom additional methods
    //#################################################################################

    // no vararg methods
    //---------------------------------------------------------------------------------

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
     *         the exception message if invalid, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     * @see #exclusiveBetween(Object, Object, Comparable)
     */
    // Method without varargs to increase performance
    public static <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value, final String message) {
        return INSTANCE.exclusiveBetween(start, end, value, message);
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
     *         the exception message if invalid, not null
     *
     * @return the value
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     * @see #inclusiveBetween(Object, Object, Comparable)
     */
    // Method without varargs to increase performance
    public static <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value, final String message) {
        return INSTANCE.inclusiveBetween(start, end, value, message);
    }

    /**
     * Validates that the argument can be converted to the specified class, if not throws an exception.<p>This method is useful when validating if there will be no casting errors.</p>
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
     *         the exception message if invalid, not null
     *
     * @return the class
     *
     * @throws IllegalArgumentValidationException
     *         if argument can not be converted to the specified class
     * @see #isAssignableFrom(Class, Class)
     */
    // Method without varargs to increase performance
    public static <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type, final String message) {
        return INSTANCE.isAssignableFrom(superType, type, message);
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
     *         the exception message if invalid, not null
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code false}
     * @see #isTrue(boolean)
     * @see #isTrue(boolean, String, long)
     * @see #isTrue(boolean, String, double)
     */
    // Method without varargs to increase performance
    public static void isTrue(final boolean expression, final String message) {
        INSTANCE.isTrue(expression, message);
    }

    /**
     * <p>Validate that the specified argument character sequence matches the specified regular expression pattern; otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.matchesPattern("hi", "[a-z]*", "%s does not match %s", "hi" "[a-z]*");</pre>
     * <p>The syntax of the pattern is the one used in the {@link Pattern} class.</p>
     *
     * @param input
     *         the character sequence to validate, not null
     * @param pattern
     *         the regular expression pattern, not null
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the input
     *
     * @throws IllegalArgumentValidationException
     *         if the character sequence does not match the pattern
     * @see #matchesPattern(CharSequence, String)
     */
    // Method without varargs to increase performance
    public static CharSequence matchesPattern(final CharSequence input, final String pattern, final String message) {
        return INSTANCE.matchesPattern(input, pattern, message);
    }

    /**
     * <p>Validate that the specified argument iterable is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception with the specified message.
     * <pre>Validate.noNullElements(myCollection, "The collection contains null at position %d");</pre>
     * <p>If the iterable is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p><p>If the iterable has a {@code null} element, then the iteration index of
     * the invalid element is appended to the {@code values} argument.</p>
     *
     * @param <T>
     *         the iterable type
     * @param iterable
     *         the iterable to check, validated not null by this method
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated iterable (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentException
     *         if an element is {@code null}
     * @see #noNullElements(Iterable)
     */
    // Method without varargs to increase performance
    public static <T extends Iterable<?>> T noNullElements(final T iterable, final String message) {
        return INSTANCE.noNullElements(iterable, message);
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception with the specified message.
     * <pre>Validate.noNullElements(myArray, "The array contain null at position %d");</pre>
     * <p>If the array is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p> <p>If the array has a {@code null} element, then the iteration index of the
     * invalid element is appended to the {@code values} argument.</p>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated array (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentException
     *         if an element is {@code null}
     * @see #noNullElements(Object[])
     */
    // Method without varargs to increase performance
    public static <T> T[] noNullElements(final T[] array, final String message) {
        return INSTANCE.noNullElements(array, message);
    }

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null}, a length of zero (no characters), empty nor whitespace; otherwise throwing an exception with the specified
     * message.
     * <pre>Validate.notBlank(myString, "The string must not be blank");</pre>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated character sequence (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IllegalArgumentException
     *         if the character sequence is blank
     * @see #notBlank(CharSequence)
     */
    // Method without varargs to increase performance
    public static <T extends CharSequence> T notBlank(final T chars, final String message) {
        return INSTANCE.notBlank(chars, message);
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception with the specified message.
     * <pre>Validate.notEmpty(myCollection, "The collection must not be empty");</pre>
     *
     * @param <T>
     *         the collection type
     * @param collection
     *         the collection to check, validated not null by this method
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated collection (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the collection is {@code null}
     * @throws IllegalArgumentException
     *         if the collection is empty
     * @see #notEmpty(Object[])
     */
    // Method without varargs to increase performance
    public static <T extends Collection<?>> T notEmpty(final T collection, final String message) {
        return INSTANCE.notEmpty(collection, message);
    }

    /**
     * <p>Validate that the specified argument map is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception with the specified message.
     * <pre>Validate.notEmpty(myMap, "The map must not be empty");</pre>
     *
     * @param <T>
     *         the map type
     * @param map
     *         the map to check, validated not null by this method
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated map (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the map is {@code null}
     * @throws IllegalArgumentException
     *         if the map is empty
     * @see #notEmpty(Object[])
     */
    // Method without varargs to increase performance
    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message) {
        return INSTANCE.notEmpty(map, message);
    }

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null} nor a length of zero (no characters); otherwise throwing an exception with the specified message.
     * <pre>Validate.notEmpty(myString, "The string must not be empty");</pre>
     *
     * @param <T>
     *         the character sequence type
     * @param chars
     *         the character sequence to check, validated not null by this method
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated character sequence (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IllegalArgumentException
     *         if the character sequence is empty
     * @see #notEmpty(CharSequence)
     */
    // Method without varargs to increase performance
    public static <T extends CharSequence> T notEmpty(final T chars, final String message) {
        return INSTANCE.notEmpty(chars, message);
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor a length of zero (no elements); otherwise throwing an exception with the specified message.
     * <pre>Validate.notEmpty(myArray, "The array must not be empty");</pre>
     *
     * @param <T>
     *         the array type
     * @param array
     *         the array to check, validated not null by this method
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated array (never {@code null} method for chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IllegalArgumentValidationException
     *         if the array is empty
     * @see #notEmpty(Object[])
     */
    // Method without varargs to increase performance
    public static <T> T[] notEmpty(final T[] array, final String message) {
        return INSTANCE.notEmpty(array, message);
    }

    /**
     * <p>Validate that the specified argument is not {@code null}; otherwise throwing an exception with the specified message.
     * <pre>Validate.notNull(myObject, "The object must not be null");</pre>
     *
     * @param <T>
     *         the object type
     * @param object
     *         the object to check
     * @param message
     *         the exception message if invalid, not null
     *
     * @return the validated object (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the object is {@code null}
     * @see #notNull(Object)
     */
    // Method without varargs to increase performance
    public static <T> T notNull(final T object, final String message) {
        return INSTANCE.notNull(object, message);
    }

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
     *         the exception message if invalid, not null
     *
     * @return the validated collection (never {@code null} for chaining)
     *
     * @throws NullPointerValidationException
     *         if the collection is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(Collection, int)
     */
    // Method without varargs to increase performance
    public static <T extends Collection<?>> T validIndex(final T collection, final int index, final String message) {
        return INSTANCE.validIndex(collection, index, message);
    }

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
     *         the exception message if invalid, not null
     *
     * @return the validated character sequence (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the character sequence is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(CharSequence, int)
     */
    // Method without varargs to increase performance
    public static <T extends CharSequence> T validIndex(final T chars, final int index, final String message) {
        return INSTANCE.validIndex(chars, index, message);
    }

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
     *         the exception message if invalid, not null
     *
     * @return the validated array (never {@code null} for method chaining)
     *
     * @throws NullPointerValidationException
     *         if the array is {@code null}
     * @throws IndexOutOfBoundsException
     *         if the index is invalid
     * @see #validIndex(Object[], int)
     */
    // Method without varargs to increase performance
    public static <T> T[] validIndex(final T[] array, final int index, final String message) {
        return INSTANCE.validIndex(array, index, message);
    }

    /**
     * <p>Validate that the stateful condition is {@code true}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     * <pre>Validate.validState(this.isOk(), "The state is not OK: %s", myObject);</pre>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the exception message if invalid, not null
     *
     * @throws IllegalStateValidationException
     *         if expression is {@code false}
     * @see #validState(boolean)
     */
    // Method without varargs to increase performance
    public static void validState(final boolean expression, final String message) {
        INSTANCE.validState(expression, message);
    }

    // isNull
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument is {@code null}; otherwise throwing an exception with the specified message.
     * <pre>Validate.isNull(myObject, "The object must be null");</pre>
     *
     * @param <T>
     *         the object type
     * @param object
     *         the object to check
     * @param message
     *         the exception message if invalid, not null
     *
     * @throws IllegalArgumentValidationException
     *         if the object is not {@code null}
     * @see #notNull(Object)
     */
    // Method without varargs to increase performance
    public static <T> void isNull(final T object, final String message) {
        INSTANCE.isNull(object, message);
    }

    /**
     * <p>Validate that the specified argument is {@code null}; otherwise throwing an exception.
     * <pre>Validate.isNull(myObject, "The object must be null");</pre>
     * <p>The message of the exception is &quot;The validated object is not null&quot;.</p>
     *
     * @param <T>
     *         the object type
     * @param object
     *         the object to check
     *
     * @throws IllegalArgumentValidationException
     *         if the object is not {@code null}
     * @see #isNull(Object, String, Object...)
     */
    public static <T> void isNull(final T object) {
        INSTANCE.isNull(object);
    }

    /**
     * <p>Validate that the specified argument is {@code null}; otherwise throwing an exception with the specified message.
     * <pre>Validate.isNull(myObject, "The object must be null");</pre>
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
     * @throws IllegalArgumentValidationException
     *         if the object is not {@code null}
     * @see #notNull(Object)
     */
    public static <T> void isNull(final T object, final String message, final Object... values) {
        INSTANCE.isNull(object, message, values);
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
    public static void isFalse(final boolean expression, final String message, final long value) {
        INSTANCE.isFalse(expression, message, value);
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
    public static void isFalse(final boolean expression, final String message, final double value) {
        INSTANCE.isFalse(expression, message, value);
    }

    /**
     * <p>Validate that the argument condition is {@code false}; otherwise throwing an exception with the specified message. This method is useful when validating according to an arbitrary boolean
     * expression, such as validating a primitive number or using your own custom validation expression.</p>
     *
     * @param expression
     *         the boolean expression to check
     * @param message
     *         the exception message if invalid, not null
     *
     * @throws IllegalArgumentValidationException
     *         if expression is {@code true}
     * @see #isFalse(boolean)
     * @see #isFalse(boolean, String, long)
     * @see #isFalse(boolean, String, double)
     */
    // Method without varargs to increase performance
    public static void isFalse(final boolean expression, final String message) {
        INSTANCE.isFalse(expression, message);
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
    public static void isFalse(final boolean expression, final String message, final Object... values) {
        INSTANCE.isFalse(expression, message, values);
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
    public static void isFalse(final boolean expression) {
        INSTANCE.isFalse(expression);
    }

    // End custom additional methods
    //#################################################################################

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
    public static void isTrue(final boolean expression, final String message, final long value) {
        INSTANCE.isTrue(expression, message, value);
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
    public static void isTrue(final boolean expression, final String message, final double value) {
        INSTANCE.isTrue(expression, message, value);
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
    public static void isTrue(final boolean expression, final String message, final Object... values) {
        INSTANCE.isTrue(expression, message, values);
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
    public static void isTrue(final boolean expression) {
        INSTANCE.isTrue(expression);
    }

    // notNull
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument is not {@code null}; otherwise throwing an exception.
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
    public static <T> T notNull(final T object) {
        return INSTANCE.notNull(object);
    }

    /**
     * <p>Validate that the specified argument is not {@code null}; otherwise throwing an exception with the specified message.
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
    public static <T> T notNull(final T object, final String message, final Object... values) {
        return INSTANCE.notNull(object, message, values);
    }

    // notEmpty array
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor a length of zero (no elements); otherwise throwing an exception with the specified message.
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
    public static <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
        return INSTANCE.notEmpty(array, message, values);
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor a length of zero (no elements); otherwise throwing an exception.
     * <pre>Validate.notEmpty(myArray);</pre>
     * <p>The message in the exception is &quot;The validated array is empty&quot;.
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
    public static <T> T[] notEmpty(final T[] array) {
        return INSTANCE.notEmpty(array);
    }

    // notEmpty collection
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument collection is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception with the specified message.
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
    public static <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... values) {
        return INSTANCE.notEmpty(collection, message, values);
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception.
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
     * @see #notEmpty(Collection, String, Object...)
     */
    public static <T extends Collection<?>> T notEmpty(final T collection) {
        return INSTANCE.notEmpty(collection);
    }

    // notEmpty map
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument map is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception with the specified message.
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
    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
        return INSTANCE.notEmpty(map, message, values);
    }

    /**
     * <p>Validate that the specified argument map is neither {@code null} nor a size of zero (no elements); otherwise throwing an exception.
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
     * @see #notEmpty(Map, String, Object...)
     */
    public static <T extends Map<?, ?>> T notEmpty(final T map) {
        return INSTANCE.notEmpty(map);
    }

    // notEmpty string
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null} nor a length of zero (no characters); otherwise throwing an exception with the specified message.
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
    public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
        return INSTANCE.notEmpty(chars, message, values);
    }

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null} nor a length of zero (no characters); otherwise throwing an exception with the specified message.
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
    public static <T extends CharSequence> T notEmpty(final T chars) {
        return INSTANCE.notEmpty(chars);
    }

    // notBlank string
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null}, a length of zero (no characters), empty nor whitespace; otherwise throwing an exception with the specified
     * message.
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
    public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
        return INSTANCE.notBlank(chars, message, values);
    }

    /**
     * <p>Validate that the specified argument character sequence is neither {@code null}, a length of zero (no characters), empty nor whitespace; otherwise throwing an exception.
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
    public static <T extends CharSequence> T notBlank(final T chars) {
        return INSTANCE.notBlank(chars);
    }

    // noNullElements array
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception with the specified message.
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
    public static <T> T[] noNullElements(final T[] array, final String message, final Object... values) {
        return INSTANCE.noNullElements(array, message, values);
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception.</p>
     * <pre>Validate.noNullElements(myArray);</pre>
     * <p>If the array is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p><p>If the array has a {@code null} element, then the message in the exception
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
    public static <T> T[] noNullElements(final T[] array) {
        return INSTANCE.noNullElements(array);
    }

    // noNullElements iterable
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument iterable is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception with the specified message.
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
    public static <T extends Iterable<?>> T noNullElements(final T iterable, final String message, final Object... values) {
        return INSTANCE.noNullElements(iterable, message, values);
    }

    /**
     * <p>Validate that the specified argument iterable is neither {@code null} nor contains any elements that are {@code null}; otherwise throwing an exception.
     * <pre>Validate.noNullElements(myCollection);</pre>
     * <p>If the iterable is {@code null}, then the message in the exception is &quot;The validated object is null&quot;.</p> <p>If the array has a {@code null} element, then the message in the
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
    public static <T extends Iterable<?>> T noNullElements(final T iterable) {
        return INSTANCE.noNullElements(iterable);
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
    public static <T> T[] validIndex(final T[] array, final int index, final String message, final Object... values) {
        return INSTANCE.validIndex(array, index, message, values);
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
    public static <T> T[] validIndex(final T[] array, final int index) {
        return INSTANCE.validIndex(array, index);
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
     * @see #validIndex(Collection, int)
     */
    public static <T extends Collection<?>> T validIndex(final T collection, final int index, final String message, final Object... values) {
        return INSTANCE.validIndex(collection, index, message, values);
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
     * @see #validIndex(Collection, int, String, Object...)
     */
    public static <T extends Collection<?>> T validIndex(final T collection, final int index) {
        return INSTANCE.validIndex(collection, index);
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
    public static <T extends CharSequence> T validIndex(final T chars, final int index, final String message, final Object... values) {
        return INSTANCE.validIndex(chars, index, message, values);
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
    public static <T extends CharSequence> T validIndex(final T chars, final int index) {
        return INSTANCE.validIndex(chars, index);
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
    public static void validState(final boolean expression) {
        INSTANCE.validState(expression);
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
    public static void validState(final boolean expression, final String message, final Object... values) {
        INSTANCE.validState(expression, message, values);
    }

    // matchesPattern
    //---------------------------------------------------------------------------------

    /**
     * <p>Validate that the specified argument character sequence matches the specified regular expression pattern; otherwise throwing an exception.</p>
     * <pre>Validate.matchesPattern("hi", "[a-z]*");</pre>
     * <p>The syntax of the pattern is the one used in the {@link Pattern} class.</p>
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
    public static CharSequence matchesPattern(final CharSequence input, final String pattern) {
        return INSTANCE.matchesPattern(input, pattern);
    }

    /**
     * <p>Validate that the specified argument character sequence matches the specified regular expression pattern; otherwise throwing an exception with the specified message.</p>
     * <pre>Validate.matchesPattern("hi", "[a-z]*", "%s does not match %s", "hi" "[a-z]*");</pre>
     * <p>The syntax of the pattern is the one used in the {@link Pattern} class.</p>
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
    public static CharSequence matchesPattern(final CharSequence input, final String pattern, final String message, final Object... values) {
        return INSTANCE.matchesPattern(input, pattern, message, values);
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
     *         the type of the object to validate
     * @param start
     *         the inclusive start value, not null
     * @param end
     *         the inclusive end value, not null
     * @param value
     *         the object to validate, not null
     *
     * @return the object
     *
     * @throws IllegalArgumentValidationException
     *         if the value falls outside the boundaries
     * @see #inclusiveBetween(Object, Object, Comparable, String, Object...)
     */
    public static <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value) {
        return INSTANCE.inclusiveBetween(start, end, value);
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
    public static <T, V extends Comparable<T>> V inclusiveBetween(final T start, final T end, final V value, final String message, final Object... values) {
        return INSTANCE.inclusiveBetween(start, end, value, message, values);
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
    public static long inclusiveBetween(long start, long end, long value) {
        return INSTANCE.inclusiveBetween(start, end, value);
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
    public static long inclusiveBetween(long start, long end, long value, String message) {
        return INSTANCE.inclusiveBetween(start, end, value, message);
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
    public static double inclusiveBetween(double start, double end, double value) {
        return INSTANCE.inclusiveBetween(start, end, value);
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
    public static double inclusiveBetween(double start, double end, double value, String message) {
        return INSTANCE.inclusiveBetween(start, end, value, message);
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
    public static <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value) {
        return INSTANCE.exclusiveBetween(start, end, value);
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
    public static <T, V extends Comparable<T>> V exclusiveBetween(final T start, final T end, final V value, final String message, final Object... values) {
        return INSTANCE.exclusiveBetween(start, end, value, message, values);
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
    public static long exclusiveBetween(long start, long end, long value) {
        return INSTANCE.exclusiveBetween(start, end, value);
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
    public static long exclusiveBetween(long start, long end, long value, String message) {
        return INSTANCE.exclusiveBetween(start, end, value, message);
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
    public static double exclusiveBetween(double start, double end, double value) {
        return INSTANCE.exclusiveBetween(start, end, value);
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
    public static double exclusiveBetween(double start, double end, double value, String message) {
        return INSTANCE.exclusiveBetween(start, end, value, message);
    }

    // isInstanceOf
    //---------------------------------------------------------------------------------

    /**
     * Validates that the argument is an instance of the specified class, if not throws an exception. <p>This method is useful when validating according to an arbitrary class</p>
     * <pre>Validate.isInstanceOf(OkClass.class, object);</pre>
     * <p>The message of the exception is &quot;Expected type: {type}, actual: {obj_type}&quot;</p>
     *
     * @param <T>
     *         the type of the object to check
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
    public static <T> T isInstanceOf(final Class<?> type, final T obj) {
        return INSTANCE.isInstanceOf(type, obj);
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
    public static <T> T isInstanceOf(final Class<?> type, final T obj, final String message, final Object... values) {
        return INSTANCE.isInstanceOf(type, obj, message, values);
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
    public static <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type) {
        return INSTANCE.isAssignableFrom(superType, type);
    }

    /**
     * Validates that the argument can be converted to the specified class, if not throws an exception.<p>This method is useful when validating if there will be no casting errors.</p>
     * <pre>Validate.isAssignableFrom(SuperClass.class, object.getClass());</pre>
     * <p>The message of the exception is &quot;The validated object can not be converted to the&quot; followed by the name of the class and &quot;class&quot;</p>
     *
     * @param <T>
     *         the type of the object to check
     * @param superType
     *         the class the class must be validated against, not null
     * @param type
     *         the class to check, not null
     * @param message
     *         the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values
     *         the optional values for the formatted exception message, null array not recommended
     *
     * @return the object
     *
     * @throws IllegalArgumentValidationException
     *         if argument can not be converted to the specified class
     * @see #isAssignableFrom(Class, Class)
     */
    public static <T> Class<T> isAssignableFrom(final Class<?> superType, final Class<T> type, final String message, final Object... values) {
        return INSTANCE.isAssignableFrom(superType, type, message, values);
    }

    private static AbstractValidate<ValidationException> INSTANCE = new AbstractValidate<ValidationException>() {

        @Override
        protected IllegalArgumentValidationException illegalArgument(final String message) {
            return new IllegalArgumentValidationException(message);
        }

        @Override
        protected NullPointerValidationException nullPointer(final String message) {
            return new NullPointerValidationException(message);
        }

        @Override
        protected IndexOutOfBoundsValidationException indexOutOfBounds(final String message) {
            return new IndexOutOfBoundsValidationException(message);
        }

        @Override
        protected IllegalStateValidationException illegalState(final String message) {
            return new IllegalStateValidationException(message);
        }

        @Override
        protected IllegalArgumentValidationException illegalArgument(final Exception e, final String message) {
            return new IllegalArgumentValidationException(message, e);
        }

    };

}
