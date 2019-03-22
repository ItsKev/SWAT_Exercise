package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class ItemDTO implements Serializable {
    private final Integer id;
    private final String name;
    private final Integer price;
    private final String artNr;
    private final Integer localStock;
    private final Integer minLocalStock;
    private final Integer virtualLocalStock;

    public ItemDTO(Integer id, String name, Integer price, String artNr, Integer localStock, Integer minLocalStock, Integer virtualLocalStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.artNr = artNr;
        this.localStock = localStock;
        this.minLocalStock = minLocalStock;
        this.virtualLocalStock = virtualLocalStock;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public String getArtNr() {
        return this.artNr;
    }

    public Integer getLocalStock() {
        return this.localStock;
    }

    public Integer getMinLocalStock() {
        return this.minLocalStock;
    }

    public Integer getVirtualLocalStock() {
        return this.virtualLocalStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDTO)) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return Objects.equals(this.name, itemDTO.name) &&
                Objects.equals(this.price, itemDTO.price) &&
                Objects.equals(this.artNr, itemDTO.artNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.price, this.artNr);
    }
}
