package ch.hslu.appe.fbs.client.UiModels;

import ch.hslu.appe.fbs.common.dto.ItemDTO;

public class UiItem {
    private Integer id;
    private String name;
    private Integer price;
    private String artNr;
    private Integer localStock;
    private Integer minLocalStock;
    private Integer virtualLocalStock;

    public UiItem(ItemDTO itemDto) {
        this.id = itemDto.getId();
        this.name = itemDto.getName();
        this.price = itemDto.getPrice();
        this.artNr = itemDto.getArtNr();
        this.localStock = itemDto.getLocalStock();
        this.minLocalStock = itemDto.getMinLocalStock();
        this.virtualLocalStock = itemDto.getVirtualLocalStock();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getArtNr() {
        return artNr;
    }

    public Integer getLocalStock() {
        return localStock;
    }

    public Integer getMinLocalStock() {
        return minLocalStock;
    }

    public Integer getVirtualLocalStock() {
        return virtualLocalStock;
    }

    public ItemDTO getItemDTO() {
        return new ItemDTO(this.id, this.name, this.price, this.artNr, this.localStock, this.minLocalStock, this.virtualLocalStock);
    }
}
