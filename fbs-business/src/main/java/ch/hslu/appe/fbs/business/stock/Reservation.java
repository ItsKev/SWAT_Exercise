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

import java.util.Objects;

/**
 * Reservation eines Artikels. Immutable.
 */
public final class Reservation implements Comparable<Reservation> {

    /** Ticket-Nummer. */
    private final long ticket;

    /** Artikel. */
    private final String key;

    /** Reservationsmenge. */
    private final int count;

    /**
     * Konstruktor.
     * @param aticket Ticket der Bestellung (ID).
     * @param akey Artikelkey.
     * @param acount Reservierte Menge.
     */
    public Reservation(final long aticket, final String akey, final int acount) {
        super();
        this.ticket = aticket;
        this.key = akey;
        this.count = acount;
    }

    /**
     * Liefert die Ticket-Nummer.
     * @return the ticket.
     */
    public long getTicket() {
        return this.ticket;
    }

    /**
     * Liefert den Artikelkey.
     * @return Artikelkey.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Liefert die Anzahl.
     * @return the count.
     */
    public int getCount() {
        return count;
    }

    /*
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Reservation other) {
        return Long.compare(this.ticket, other.ticket);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Reservation)) {
            return false;
        }
        return Objects.equals(this.ticket, ((Reservation) other).ticket);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (int) this.ticket;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Reservation[ticket:" + this.ticket + "; key:" + this.key + "; count=" + this.count + "]";
    }
}
