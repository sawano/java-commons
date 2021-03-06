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

package se.sawano.java.commons.lang.validate.exception;

public class NullPointerValidationException extends ValidationException {

    private static final long serialVersionUID = -2276212423599845804L;

    public NullPointerValidationException() {}

    public NullPointerValidationException(final String message) {
        super(message);
    }

    public NullPointerValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NullPointerValidationException(final Throwable cause) {
        super(cause);
    }

}
