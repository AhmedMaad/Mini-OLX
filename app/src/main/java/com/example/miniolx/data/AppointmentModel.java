package com.example.miniolx.data;

public class AppointmentModel {

    private String time;
    private String mobile;
    private String userID;

    public AppointmentModel(){}

    public AppointmentModel(String time, String mobile, String userID) {
        this.time = time;
        this.mobile = mobile;
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUserID() {
        return userID;
    }

}
