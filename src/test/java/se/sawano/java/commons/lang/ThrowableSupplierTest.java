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

import static org.junit.Assert.assertEquals;

public class ThrowableSupplierTest {

    @Rule
    public final ExpectedException expectation = ExpectedException.none();

    @Test
    public void should_allow_for_an_exception_to_be_thrown() throws Exception {
        expectation.expect(Exception.class);
        expectation.expectMessage("From supplier");

        final ThrowableSupplier<String> supplier = ThrowableSupplierTest::throwingMethod;
        supplier.get();
    }

    @Test
    public void should_return_value() throws Exception {

        final ThrowableSupplier<String> supplier = ThrowableSupplierTest::nonThrowingMethod;
        assertEquals("hello world", supplier.get());
    }

    static String throwingMethod() throws Exception {
        throw new Exception("From supplier");
    }

    static String nonThrowingMethod() {
        return "hello world";
    }
}
