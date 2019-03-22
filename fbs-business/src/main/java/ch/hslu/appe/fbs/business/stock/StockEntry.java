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

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Eintrag im Lager (ein Artikel). Enthält die Daten für einen Artikel im Lager.
 */
public final class StockEntry implements Comparable<StockEntry> {

    /** Default-Lagermenge. */
    private static final int DEFAULT_COUNT = 100;

    /** Maximale Lieferfrist. */
    private static final int MAX_DELIVERY_DAYS = 14;

    /** Artikelnummer. */
    private final String key;

    /** Lagermenge. */
    private int count = DEFAULT_COUNT;

    /** Lieferdatum. */
    private final Instant deliveryDate;

    /**
     * Konstruktor für StockEntry.
     * @param akey Artikel-Key.
     */
    public StockEntry(final String akey) {
        this.key = akey;
        final long days = (long) (Math.random() * MAX_DELIVERY_DAYS + 1);
        this.deliveryDate = Instant.now().plus(Duration.ofDays(days));

    }

    /**
     * Dekrementiert den Lagerbestand.
     * @param acount Artikelmenge.
     * @return Dekrement.
     */
    public int consume(final int acount) {
        if (acount <= this.count) {
            this.count -= acount;
            return acount;
        }
        return 0;
    }

    /**
     * Liefert die aktuelle Artikelmenge.
     * @return the count.
     */
    public int getCount() {
        return count;
    }

    /**
     * Setzt die Artikelmenge.
     * @param acount the count to set.
     */
    public void setCount(final int acount) {
        this.count = acount;
    }

    /**
     * Liefert den Artikelkey.
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Getter für deliveryDate.
     * @return deliveryDate.
     */
    public Instant getDeliveryDate() {
        return this.deliveryDate;
    }

    /*
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final StockEntry other) {
        return this.key.compareTo(other.key);
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StockEntry)) {
            return false;
        }
        return Objects.equals(this.key, ((StockEntry) obj).key);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StockEntry[key:" + this.key + "; count=" + this.count + "]";
    }
}
