/*
 * Copyright 2018 Roland Gisler, HSLU Informatik, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.appe.fbs.business.stock;

/**
 * StockException. Wrapper-Exception für alle Exceptions welche vom Stock geworfen werden.
 */
public class StockException extends Exception {

    private static final long serialVersionUID = -7983947443301384972L;

    /**
     * Konstruktor.
     * @param message Message.
     */
    public StockException(final String message) {
        super(message);
    }

    /**
     * Konstruktor.
     * @param throwable Throwable.
     */
    public StockException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Konstruktor.
     * @param message Message.
     * @param throwable Throwable.
     */
    public StockException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
