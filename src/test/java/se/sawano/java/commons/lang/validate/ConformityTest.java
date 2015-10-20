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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import se.sawano.java.commons.lang.validate.dbc.Ensure;
import se.sawano.java.commons.lang.validate.dbc.Invariant;
import se.sawano.java.commons.lang.validate.dbc.Require;
import se.sawano.java.commons.lang.validate.hystrix.HystrixValidate;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConformityTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
                {Ensure.class},
                {Invariant.class},
                {Require.class},
                {HystrixValidate.class}
        });
    }

    @Parameterized.Parameter(0)
    public Class<?> classToVerify;

    @Test
    public void should_implement_all_methods() {
        for (final Method method : Validate.class.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                assertTrue("Class " + classToVerify + " is missing method: " + method, hasMethod(method));
            }
        }
    }

    private boolean hasMethod(final Method method) {
        try {
            classToVerify.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

}
