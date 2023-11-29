package com.yc.ac.gachigachi;

public class board_Item {
    private final String name;
    private final String carNumber;
    private final String phoneNumber;
    private final String address;
    private final  boolean isShow;

    public board_Item(String name, String carNumber, String phoneNumber, String address, boolean isShow) {
        this.name = name;
        this.carNumber = carNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getName() {
        return name;
    }
}
