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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.function.BiFunction;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ComparableTest {

    @Parameterized.Parameters(name = "{index}: {1}, {2} => {3}")
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
                {whenCalling(Comparable::isEqualTo), on(1), with(2), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isEqualTo), on(1), with(1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isEqualTo), on(0), with(0), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isEqualTo), on(-1), with(-1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isEqualTo), on(-1), with(1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isEqualTo), on(1), with(-1), thenTheResultShouldBe(false)},

                {whenCalling(Comparable::isNotEqualTo), on(1), with(2), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isNotEqualTo), on(1), with(1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isNotEqualTo), on(0), with(0), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isNotEqualTo), on(-1), with(-1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isNotEqualTo), on(-1), with(1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isNotEqualTo), on(1), with(-1), thenTheResultShouldBe(true)},

                {whenCalling(Comparable::isGreaterThan), on(0), with(0), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isGreaterThan), on(1), with(0), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isGreaterThan), on(0), with(1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isGreaterThan), on(-1), with(-1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isGreaterThan), on(-1), with(1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isGreaterThan), on(1), with(-1), thenTheResultShouldBe(true)},

                {whenCalling(Comparable::isGreaterThanOrEqualTo), on(0), with(0), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isGreaterThanOrEqualTo), on(1), with(0), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isGreaterThanOrEqualTo), on(0), with(1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isGreaterThanOrEqualTo), on(-1), with(-1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isGreaterThanOrEqualTo), on(-1), with(1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isGreaterThanOrEqualTo), on(1), with(-1), thenTheResultShouldBe(true)},

                {whenCalling(Comparable::isLessThan), on(0), with(0), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isLessThan), on(1), with(0), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isLessThan), on(0), with(1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isLessThan), on(-1), with(-1), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isLessThan), on(-1), with(1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isLessThan), on(1), with(-1), thenTheResultShouldBe(false)},

                {whenCalling(Comparable::isLessThanOrEqualTo), on(0), with(0), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isLessThanOrEqualTo), on(1), with(0), thenTheResultShouldBe(false)},
                {whenCalling(Comparable::isLessThanOrEqualTo), on(0), with(1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isLessThanOrEqualTo), on(-1), with(-1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isLessThanOrEqualTo), on(-1), with(1), thenTheResultShouldBe(true)},
                {whenCalling(Comparable::isLessThanOrEqualTo), on(1), with(-1), thenTheResultShouldBe(false)},

        });
    }

    private static BiFunction<Comparable<Integer>, Integer, Boolean> whenCalling(final BiFunction<Comparable<Integer>, Integer, Boolean> function) {
        return function;
    }

    private static Integer on(final Integer i) {
        return i;
    }

    private static Integer with(final Integer i) {
        return i;
    }

    private static Boolean thenTheResultShouldBe(final Boolean result) {
        return result;
    }

    @Parameterized.Parameter(0)
    public BiFunction<Comparable<Integer>, Integer, Boolean> methodToVerify;
    @Parameterized.Parameter(1)
    public Integer number1;
    @Parameterized.Parameter(2)
    public Integer number2;
    @Parameterized.Parameter(3)
    public Boolean expectedResult;

    @Test
    public void should_verify_behavior() {
        assertEquals(expectedResult, methodToVerify.apply(new NumberComparable(number1), number2));
    }

    static final class NumberComparable implements Comparable<Integer> {

        private final Integer number;

        NumberComparable(final Integer number) {this.number = number;}

        @Override
        public int compareTo(final Integer that) {
            return number.compareTo(that);
        }
    }
}
