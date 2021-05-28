package com.example.miniolx.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ApartmentModel implements Comparable<ApartmentModel>, Parcelable {

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
    private String picture;
    private double price;
    private String apartmentID;
    private String mobile;

    public ApartmentModel() {
    }

    public ApartmentModel(String address, double area, int roomsNo, int bathroomsNo, int kitchenNo
            , String viewDescription, int floorNo, String rentType, String userID
            , ArrayList<String> availableTimes, String picture, double price, String mobile) {
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
        this.picture = picture;
        this.price = price;
        this.mobile = mobile;
    }

    protected ApartmentModel(Parcel in) {
        address = in.readString();
        area = in.readDouble();
        roomsNo = in.readInt();
        bathroomsNo = in.readInt();
        kitchenNo = in.readInt();
        viewDescription = in.readString();
        floorNo = in.readInt();
        rentType = in.readString();
        userID = in.readString();
        availableTimes = in.createStringArrayList();
        picture = in.readString();
        price = in.readDouble();
        apartmentID = in.readString();
        mobile = in.readString();
    }

    public static final Creator<ApartmentModel> CREATOR = new Creator<ApartmentModel>() {
        @Override
        public ApartmentModel createFromParcel(Parcel in) {
            return new ApartmentModel(in);
        }

        @Override
        public ApartmentModel[] newArray(int size) {
            return new ApartmentModel[size];
        }
    };

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

    public String getPicture() {
        return picture;
    }

    public Double getPrice() {
        return price;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public int compareTo(ApartmentModel o) {
        return this.getPrice().compareTo(o.getPrice());
    }

    @Override
    public String toString() {
        return "Price List [" + price + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeDouble(area);
        dest.writeInt(roomsNo);
        dest.writeInt(bathroomsNo);
        dest.writeInt(kitchenNo);
        dest.writeString(viewDescription);
        dest.writeInt(floorNo);
        dest.writeString(rentType);
        dest.writeString(userID);
        dest.writeStringList(availableTimes);
        dest.writeString(picture);
        dest.writeDouble(price);
        dest.writeString(apartmentID);
        dest.writeString(mobile);
    }
}
