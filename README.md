java-commons
============
Helper utilities for core Java classes.

##Classes

###se.sawano.java.commons.lang.Validate
This class contains utility methods for performing assertions. It's main purpose is to provide exceptions specific to validation errors, and to work as a drop in
replacement for `org.apache.commons.lang3.Validate`.

The exceptions thrown when an assertion fails is of the type `ValidationException` so that it can be distinguished from other exceptions that may occur within a piece of code.
For example, if using the assertions in a Design by Contract (DbC) fashion, being able to separate exceptions caused by contract violation from other exceptions is very helpful.

This class has been derived from the Apache Commons Lang project and should work as a drop in replacement for `org.apache.commons.lang3.Validate`.

Some of the differences/enhancements to the Apache Commons functionality are:

- isFalse() methods

    Very useful since you don't need to use negation in your declarations.
- All methods that checks a value of some sort will return that value.

    This allows for more fluid code as the assertion does not have to be a separate statement.
