package com.yc.ac.gachigachi;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarList {
    //////////////// 맴버 /////////////////
    private String name;
    private String carNumber;
    private String Address;
    private String phoneNumber;
    private boolean isShow;
    private List<Map<String, Object>> timetable;
    /////////////// 생성자 /////////////////
    public CarList() { }

    public CarList(String name, String carNumber, String address, String phoneNumber, boolean isShow, List<Map<String, Object>> timetable) {
        this.name = name;
        this.carNumber = carNumber;
        Address = address;
        this.phoneNumber = phoneNumber;
        this.isShow = isShow;
        this.timetable = timetable;
    }

    ///////////// Getter/Setter 메소드 //////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public List<Map<String, Object>> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Map<String, Object>> timetable) {
        this.timetable = timetable;
    }
}
