package ch.hslu.appe.fbs.data.item;

import ch.hslu.appe.fbs.model.db.Item;

import java.util.List;
import java.util.Optional;

public interface ItemPersistor {

    /***
     * Gets a list of all items in the database.
     *
     * @return A list of type {@link Item} which contains all the items contained in the database
     */
    List<Item> getAllItems();

    /***
     * Saves a new item in the database.
     *
     * @param item The item to be saved
     */
    void save(final Item item);

    /***
     * Updates an existing item in the database (if present).
     *
     * @param item The item to be saved
     */
    void update(final Item item);

    /***
     * Updates the stock of the specified item
     *
     * @param item The item whose stock needs to be updated
     */
    void updateStock(final Item item);

    /***
     * Gets an item by the specified id (if present).
     *
     * @param id The specified id of the item which ought to be returned
     * @return An optional of type {@link Item} which contains the specified item if present
     */
    Optional<Item> getItemById(int id);
}
