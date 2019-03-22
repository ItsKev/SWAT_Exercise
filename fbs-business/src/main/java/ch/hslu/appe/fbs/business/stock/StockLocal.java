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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Einfache Simulation des zentralen Lagers. Es existieren alle (zulässigen) Artikelnummern, der
 * Anfangsbestand aller Artikel beträgt 100 Stück, es werden keine Artikel nachbestellt.
 * Reservationen bleiben nur pro Instanz bestehen.
 */
public class StockLocal implements Stock {

    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(StockLocal.class);

    /** Minimale Länge Artikelnummer. */
    private static final int KEY_LENGTH = 6;

    /** Map für Lagerartikel. */
    private final Map<String, StockEntry> stockMap = new HashMap<>();

    /** Map für Reservationen. */
    private final Map<Long, Reservation> reservationMap = new HashMap<>();

    /*
     * @see ch.hslu.appe.Stock#getItemCount(java.lang.String)
     */
    @Override
    public final int getItemCount(final String key) throws StockException {
        int itemCount = -1;
        if (isValidKey(key)) {
            StockEntry entry;
            synchronized (this) {
                entry = this.stockMap.get(key);
                if (entry == null) {
                    entry = new StockEntry(key);
                    this.stockMap.put(key, entry);
                    LOG.trace("Stock.getItemCount(key='{}') [{}] created.", key, entry);
                }
            }
            itemCount = entry.getCount();
        }
        LOG.debug("Stock.getItemCount(key='{}') = {}", key, itemCount);
        return itemCount;
    }

    /*
     * @see ch.hslu.appe.Stock#getItemDeliveryDate(java.lang.String)
     */
    @Override
    public final Instant getItemDeliveryDate(final String key) throws StockException {
        Instant instant = null;
        if (isValidKey(key)) {
            StockEntry entry;
            synchronized (this) {
                entry = this.stockMap.get(key);
                if (entry == null) {
                    entry = new StockEntry(key);
                    LOG.trace("Stock.getItemDeliveryDate(key='{}') [{}] created", key, entry);
                    this.stockMap.put(key, entry);
                }
            }
            instant = entry.getDeliveryDate();
        }
        LOG.debug("Stock.getItemDeliveryDate(key='{}') = {}", key, instant);
        return instant;
    }

    /*
     * @see ch.hslu.appe.Stock#orderItem(java.lang.String, int)
     */
    @Override
    public final int orderItem(final String key, final int count) throws StockException {
        StockEntry entry;
        int itemCount = -1;
        if (isValidKey(key) && count >= 0) {
            synchronized (this) {
                this.getItemCount(key);
                entry = this.stockMap.get(key);
                itemCount = entry.consume(count);
            }
        }
        LOG.debug("Stock.orderItem(key='{}', count={}) = {}", key, count, itemCount);
        return itemCount;
    }

    /*
     * @see ch.hslu.appe.Stock#reserveItem(java.lang.String, int)
     */
    @Override
    public final long reserveItem(final String key, final int count) throws StockException {
        long ticket = -1;
        if (isValidKey(key)) {
            synchronized (this) {
                this.getItemCount(key);
                final StockEntry entry = this.stockMap.get(key);
                final int reservationCount = entry.consume(count);
                if (reservationCount == count) {
                    ticket = System.currentTimeMillis();
                    final Reservation reservation = new Reservation(ticket, entry.getKey(), reservationCount);
                    LOG.trace("Stock.reserveItem(key='{}', count={}) entry [{}] created.", key, count, reservation);
                    this.reservationMap.put(ticket, reservation);
                    LOG.debug("Stock.reserveItem(key='{}', count={}) = {}", key, count, ticket);
                }
            }
        }
        return ticket;
    }

    /*
     * @see ch.hslu.appe.Stock#orderItem(long)
     */
    @Override
    public final int orderItem(final long ticket) throws StockException {
        int count = 0;
        if (ticket > 0) {
            synchronized (this) {
                final Reservation reservation = this.reservationMap.get(ticket);
                if (reservation != null) {
                    LOG.trace("Stock.orderItem(ticket={}) [{}] found.", ticket, reservation);
                    count = reservation.getCount();
                    this.reservationMap.remove(ticket);
                    LOG.debug("Stock.orderItem(ticket={}) = {}", ticket, count);
                }
            }
        }
        return count;
    }

    /*
     * @see ch.hslu.appe.Stock#freeItem(long)
     */
    @Override
    public final long freeItem(final long ticket) throws StockException {
        long result = 0;
        if (ticket > 0) {
            synchronized (this) {
                final Reservation reservation = this.reservationMap.get(ticket);
                if (reservation != null) {
                    LOG.trace("Stock.freeItem(ticket={}) {} found.", ticket, reservation);
                    final StockEntry entry = this.stockMap.get(reservation.getKey());
                    entry.setCount(entry.getCount() + reservation.getCount());
                    this.reservationMap.remove(ticket);
                    result = ticket;
                    LOG.debug("Stock.freeItem(ticket={}) canceled.", ticket);
                }
            }
        }
        return result;
    }

    /**
     * Prüft die Artikelnummer.
     * @param key Artikelnummer (sechstellig und nicht null)
     * @return boolean ok oder nok.
     */
    private static boolean isValidKey(final String key) {
        final boolean valid = key != null && key.length() == KEY_LENGTH;
        if (!valid) {
            LOG.trace("Stock.isValidKey(key='{}') not a valid key", key);
        }
        return valid;
    }
}
