#java-commons
[![][travis img]][travis]
[![][maven img]][maven]
[![][release img]][release]
[![][license img]][license]

Helper utilities for core Java classes.

Note: java-commons v1.x, which is Java 6 compatible, can be found [here](https://github.com/sawano/java-commons/tree/1.x).

##Functionality

###Validation and contracts
####se.sawano.java.commons.lang.validate.Validate
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
The classes `Require`, `Ensure`, and `Invariant` in the `se.sawano.java.commons.lang.validate.dbc` package contains exactly the same functionality as `se.sawano.java.commons.lang.validate.Validate` 
but with unique exception types. I.e. of the type `RequirementException`, `EnsuranceException` and `InvarianceException` respectively. The naming convention is borrowing terms form the Eiffel 
programming language and the principles of [Design by Contract](https://docs.eiffel.com/book/method/et-design-contract-tm-assertions-and-exceptions).

####Hystrix specific validation utilities
The class `se.sawano.java.commons.lang.validate.hystrix.HystrixValidate` contains exactly the same functionality as the standard `Validate` except that all exceptions thrown will inherit from
[HystrixBadRequestException](http://netflix.github.io/Hystrix/javadoc/com/netflix/hystrix/exception/HystrixBadRequestException.html).

####Are the validation utilities reliable?
Yes, the code is a fork of the Apache commons lib with essentially no changes in validation logic except for addition of new functionality. Once could even say that these utilities provides even 
greater confidence than the Apache commons counterparts since there are holes in the test coverage in the Apache commons library. These gaps are not present in 
the `se.sawano.java.commons.lang.validate.Validate` code since there is more extensive test coverage in this project. Also, as noted above, this lib has slightly better performance than the Apache 
commons lib.

###Sorting and comparing
####se.sawano.java.commons.lang.Comparable
An extension of `java.lang.Comparable` that adds readable methods for checking equality. I.e. instead of doing int comparisons like `compareTo(that) < 0` you can write code like `isLessThan(that)`.

###Functional Java
####se.sawano.java.commons.lang.Optionals
Utility methods for working with `java.util.Optional`.

##Java version compatibility
As of version 2, java-commons is compiled with Java 8. Java 6 compatible binaries remains in the 1.x branch. I.e. all 1.x versions will continue to be Java 6 compatible and available from Maven
Central.

##Download

Releases are available at the Maven central repository. Or you can just use the source code directly if you prefer that.

The first release of version 2.0 has not been released yet. Please use the latest [v1 release](https://github.com/sawano/java-commons/tree/1.x).

[travis]:https://travis-ci.org/sawano/java-commons
[travis img]:https://travis-ci.org/sawano/java-commons.svg?branch=master
[maven]:http://search.maven.org/#search|gav|1|g:"se.sawano.java"%20AND%20a:"commons"
[maven img]:https://maven-badges.herokuapp.com/maven-central/se.sawano.java/commons/badge.svg
[release]:https://github.com/sawano/java-commons/releases
[release img]:https://img.shields.io/github/release/sawano/java-commons.svg
[license]:LICENSE
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg
