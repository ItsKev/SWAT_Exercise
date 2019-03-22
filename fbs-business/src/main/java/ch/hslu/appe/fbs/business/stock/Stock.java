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

import java.time.Instant;

/**
 * Interface für ein einfaches Lager-System. Erlaubt den Lagerbestand abzufragen, Artikel zu
 * reservieren und Artikel zu bestellen.
 */
public interface Stock {

    /**
     * Liefert den vorhandenen Lagerbestand eines Artikels.
     * @param key String Artikel-ID (6-stellig).
     * @return int Anzahl an Lager oder -1 wenn Artikel ungültig.
     * @throws StockException Exception.
     */
    int getItemCount(String key) throws StockException;

    /**
     * Liefert das nächste Lieferdatum eines Artikels.
     * @param key String Artikel-ID (6-stellig).
     * @return Instant Nächstes Lieferdatum.
     * @throws StockException Exception.
     */
    Instant getItemDeliveryDate(String key) throws StockException;

    /**
     * Bestellt einen Artikel in der gewünschten Menge.
     * @param key String Artikel-ID (6-stellig).
     * @param count Anzahl.
     * @return int Anzahl bestellte Artikel, 0 wenn nicht verfügbar, -1 wenn Artikel nicht gültig.
     * @throws StockException Exception.
     */
    int orderItem(String key, int count) throws StockException;

    /**
     * Bestellt mit einem Ticket reservierte Artikel.
     * @param ticket long Reservationsticket.
     * @return int Anzahl bestellte Artikel oder 0 wenn Ticket ungültig.
     * @throws StockException Exception.
     */
    int orderItem(long ticket) throws StockException;

    /**
     * Reserviert eine bestimmte Menge eines Artikels.
     * @param key String Artikel-ID (6-stellig).
     * @param count Anzahl.
     * @return long Reservationsticket oder 0 wenn Menge nicht verfügbar.
     * @throws StockException Exception.
     */
    long reserveItem(String key, int count) throws StockException;

    /**
     * Mit einem Ticket reservierte Artikel freigeben.
     * @param ticket long Reservationsticket.
     * @return long Reservationsticket oder 0 wenn Ticket ungültig.
     * @throws StockException Exception.
     */
    long freeItem(long ticket) throws StockException;
}
