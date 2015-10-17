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

public class IndexOutOfBoundsInvarianceException extends InvarianceException {

    private static final long serialVersionUID = 3231917627963748361L;

    public IndexOutOfBoundsInvarianceException() {}

    public IndexOutOfBoundsInvarianceException(final String message) {
        super(message);
    }

    public IndexOutOfBoundsInvarianceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IndexOutOfBoundsInvarianceException(final Throwable cause) {
        super(cause);
    }

}
