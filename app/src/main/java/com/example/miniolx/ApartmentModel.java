package com.example.miniolx;

import java.util.ArrayList;

public class ApartmentModel {

    private String address;
    private double area;
    private int roomsNo;
    private int bathroomsNo;
    private int kitchenNo;
    private String viewDescription;
    private int floorNo;
    private String rentType;
    private String userID;
    private ArrayList<String> availableTimes;

    public ApartmentModel() {
    }

    public ApartmentModel(String address, double area, int roomsNo, int bathroomsNo, int kitchenNo
            , String viewDescription, int floorNo, String rentType, String userID
            , ArrayList<String> availableTimes) {
        this.address = address;
        this.area = area;
        this.roomsNo = roomsNo;
        this.bathroomsNo = bathroomsNo;
        this.kitchenNo = kitchenNo;
        this.viewDescription = viewDescription;
        this.floorNo = floorNo;
        this.rentType = rentType;
        this.userID = userID;
        this.availableTimes = availableTimes;
    }

    public String getAddress() {
        return address;
    }

    public double getArea() {
        return area;
    }

    public int getRoomsNo() {
        return roomsNo;
    }

    public int getBathroomsNo() {
        return bathroomsNo;
    }

    public int getKitchenNo() {
        return kitchenNo;
    }

    public String getViewDescription() {
        return viewDescription;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public String getRentType() {
        return rentType;
    }

    public String getUserID() {
        return userID;
    }

    public ArrayList<String> getAvailableTimes() {
        return availableTimes;
    }
}
