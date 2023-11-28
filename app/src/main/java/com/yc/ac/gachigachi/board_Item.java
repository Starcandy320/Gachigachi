package com.yc.ac.gachigachi;

//사용자 정보 담고있는 클래스
public class board_Item {
    private String name;
    private String phoneNumber;
    private String carNumber;
    private String address;

    public board_Item(String name, String carNumber, String address, String phoneNumber) {
        this.name = name;
        this.carNumber = carNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

