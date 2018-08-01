package com.example.mateusjose.newchatos.Objects;

public class Address {

    private String country;
    private String province;
    private String streetAddress;
    private String monicipio;

    public Address(){

    }

    public Address(String country,String province,String streetAddress){

        this.country = country;
        this.province = province;
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getMonicipio() {
        return monicipio;
    }

    public void setMonicipio(String monicipio) {
        this.monicipio = monicipio;
    }
}
