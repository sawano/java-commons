java-commons
============
Helper utilities for core Java classes.


##Classes

###se.sawano.java.commons.lang.Validate
This class contains utility methods for performing assertions. It's main purpose is to provide exceptions specific to validation errors, and to work as a drop in
replacement for `org.apache.commons.lang3.Validate`.

The exceptions thrown when an assertion fails is of the type `ValidationException` so that it can be distinguished from other exceptions that may occur within a piece of code.
For example, if using the assertions in a Design by Contract (DbC) fashion, being able to separate exceptions caused by contract violation from other exceptions is very helpful.
Another example is when working with circuit-breaker patterns. Being able to distinguish exceptions caused by bad input (null values etc) from other exceptions is useful since bad input
should typically not cause a circuit-breaker to trip.

This class has been derived from the Apache Commons Lang project and should work as a drop in replacement for `org.apache.commons.lang3.Validate`.

Some of the differences/enhancements to the Apache Commons functionality are:

- isFalse() methods

    Very useful since you don't need to use negation in your declarations.
- All methods that checks a value of some sort will return that value if possible.

    This allows for more fluid code as the assertion does not have to be a separate statement.
- Written for better performance

    There are no-varargs versions of the methods which will increase performance since unnecessary creation of arrays is avoided.

####Design by Contract utilities
The classes `Require`, `Ensure`, and `Invariant` in the `se.sawano.java.commons.lang.dbc` package contains exactly the same functionality as `se.sawano.java.commons.lang.Validate` but with
unique exception types. I.e. of the type `RequirementException`, `EnsuranceException` and `InvarianceException` respectively. The naming convention is borrowing terms form the Eiffel programming
language and the principles of [Design by Contract](https://docs.eiffel.com/book/method/et-design-contract-tm-assertions-and-exceptions).

####Java version compatibility
The binaries available at Maven central is compiled with Java 6 to provide maximum bytecode compatibility.

####Are the validation utilities reliable?
Yes, the code is a fork of the Apache commons lib with essentially no changes in validation logic except for addition of new functionality. Once could even say that these utilities provides even 
greater confidence than the Apache commons counterparts since there are holes in the test coverage in the Apache commons library. These gaps are not present in 
the `se.sawano.java.commons.lang.Validate` code since there is more extensive test coverage in this project. Also, as noted above, this lib has slightly better performance than the Apache commons lib.

##Download

Releases are available at the Maven central repository. Or you can just use the source code directly if you prefer that.

####Maven
```xml
<dependency>
    <groupId>se.sawano.java</groupId>
    <artifactId>commons</artifactId>
    <version>1.2</version>
</dependency>
```

####Gradle
```groovy
'se.sawano.java:commons:1.2'
```
