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

package se.sawano.java.commons.lang.dbc;

public class IllegalStateInvarianceException extends InvarianceException {

    private static final long serialVersionUID = -8993604807742260119L;

    public IllegalStateInvarianceException() {}

    public IllegalStateInvarianceException(final String message) {
        super(message);
    }

    public IllegalStateInvarianceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IllegalStateInvarianceException(final Throwable cause) {
        super(cause);
    }

}
