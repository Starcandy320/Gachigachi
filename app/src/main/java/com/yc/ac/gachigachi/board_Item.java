package com.yc.ac.gachigachi;

import java.util.List;

public class board_Item {
    private final String name;
    private final String carNumber;
    private final String phoneNumber;
    private final String address;
    private final List<String> timetable;

    public board_Item(String name, String carNumber, String phoneNumber, String address, List<String> timetable) {
        this.name = name;
        this.carNumber = carNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.timetable = timetable;
    }

    public List<String> getTimetable() {
        return timetable;
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
