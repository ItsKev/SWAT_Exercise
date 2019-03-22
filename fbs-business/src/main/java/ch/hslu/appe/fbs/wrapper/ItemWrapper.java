package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.model.db.Item;

public final class ItemWrapper implements Wrappable<Item, ItemDTO> {
    @Override
    public ItemDTO dtoFromEntity(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getArtNr(),
                item.getLocalStock(),
                item.getMinLocalStock(),
                item.getVirtualLocalStock()
        );
    }

    @Override
    public Item entityFromDTO(ItemDTO itemDTO) {
        final Item item = new Item();
        if (itemDTO.getId() != -1) {
            item.setId(itemDTO.getId());
        }
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setArtNr(itemDTO.getArtNr());
        item.setLocalStock(itemDTO.getLocalStock());
        item.setMinLocalStock(itemDTO.getMinLocalStock());
        item.setVirtualLocalStock(itemDTO.getVirtualLocalStock());
        return item;
    }
}
