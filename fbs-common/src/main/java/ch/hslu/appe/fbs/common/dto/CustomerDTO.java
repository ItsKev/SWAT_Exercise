package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class CustomerDTO implements Serializable {

    private final int id;
    private final String prename;
    private final String surname;
    private final int plz;
    private final String city;
    private final String address;


    public CustomerDTO(int id, String prename, String surname, int plz, String city, String address) {
        this.id = id;
        this.prename = prename;
        this.surname = surname;
        this.plz = plz;
        this.city = city;
        this.address = address;
    }

    public int getId() {
        return this.id;
    }

    public String getPrename() {
        return this.prename;
    }

    public String getSurname() {
        return this.surname;
    }

    public int getPlz() {
        return this.plz;
    }

    public String getCity() {
        return this.city;
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public String toString() {
        return this.prename + " " + this.surname + " from " + this.city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDTO)) return false;
        CustomerDTO customerDTO = (CustomerDTO) o;
        return Objects.equals(this.prename, customerDTO.prename) &&
                Objects.equals(this.surname, customerDTO.surname) &&
                Objects.equals(this.plz, customerDTO.plz) &&
                Objects.equals(this.city, customerDTO.city) &&
                Objects.equals(this.address, customerDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.prename, this.surname, this.plz, this.city, this.address);
    }
}
