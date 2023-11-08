package com.yc.ac.gachigachi;

public class board_Item {
    private final String title;
    private final String subText1;
    private final String phoneNum;

    public board_Item(String title, String subText1, String phoneNum) {
        this.title = title;
        this.subText1 = subText1;
        this.phoneNum = phoneNum;
    }

    public String getTitle() {
        return title;
    }

    public String getSubText1() {
        return subText1;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}
