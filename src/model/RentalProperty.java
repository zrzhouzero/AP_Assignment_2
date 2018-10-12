package model;

import javafx.scene.image.Image;
import utilities.*;

import java.util.ArrayList;

public abstract class RentalProperty {

    public enum PropertyStatus {
        AVAILABLE, RENTED, UNDERMAINTENANCE
    }

    private String propertyId;
    private String streetNum;
    private String streetName;
    private String suburb;
    private double dailyRental;
    private double lateFeeRate;
    private int numberOfBedrooms;
    private PropertyStatus propertyStatus;
    private Image smallImage;
    private Image largeImage;
    private ArrayList<RentalRecord> rentalRecord;
    private String propertyType = "";
    private String currentCustomerId;
    private String longDescription;

    RentalProperty(String propertyId, String streetNum, String streetName, String suburb) {
        this.propertyId = propertyId;
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.suburb = suburb;
        this.propertyStatus = PropertyStatus.AVAILABLE;
        this.smallImage = null;
        this.largeImage = null;
        this.rentalRecord = new ArrayList<>();
        this.currentCustomerId = null;
        this.longDescription = null;
    }

    RentalProperty(String propertyId, String streetNum, String streetName, String suburb, Image smallImage, Image largeImage) {
        this.propertyId = propertyId;
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.suburb = suburb;
        this.propertyStatus = PropertyStatus.AVAILABLE;
        this.smallImage = smallImage;
        this.largeImage = largeImage;
        this.rentalRecord = new ArrayList<>();
        this.currentCustomerId = null;
        this.longDescription = null;
    }

    void setPropertyTypeIsPremiumSuite(boolean type) {
        if (type) {
            this.propertyType = "Premium Suite";
        }
        else {
            this.propertyType = "Apartment";
        }
    }

    String getStreetNum() {
        return this.streetNum;
    }

    String getStreetName() {
        return this.streetName;
    }

    void setPropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    void setDailyRental(double dailyRental) {
        this.dailyRental = dailyRental;
    }

    void setLateFeeRate(double lateFeeRate) {
        this.lateFeeRate = lateFeeRate;
    }

    void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public void setSmallImage(Image smallImage) {
        this.smallImage = smallImage;
    }

    public void setLargeImage(Image largeImage) {
        this.largeImage = largeImage;
    }

    public Image getLargeImage() {
        return this.largeImage;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public Image getSmallImage() {
        return this.smallImage;
    }

    public double getDailyRental() {
        return this.dailyRental;
    }

    public double getLateFeeRate() {
        return this.lateFeeRate;
    }

    public String getAddress() {
        return this.streetNum + " " + this.streetName + ", " + this.suburb;
    }

    public String getPropertyId() {
        return this.propertyId;
    }

    public String getSuburb() {
        return this.suburb;
    }

    public ArrayList<RentalRecord> getRentalRecord() {
        return this.rentalRecord;
    }

    public PropertyStatus getPropertyStatus() {
        return this.propertyStatus;
    }

    String getCurrentCustomerId() {
        return currentCustomerId;
    }

    public int getNumberOfBedrooms() {
        return this.numberOfBedrooms;
    }

    public abstract boolean checkAvailability(DateTime dateTime, int rentalLength);

    void addRecord(RentalRecord rentalRecord) {
        this.rentalRecord.add(rentalRecord);
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public void setLongDescription(String s) {
        this.longDescription = s;
    }

    @Override
    public String toString() {
        String smallImagePath;
        String largeImagePath;
        try {
            smallImagePath = this.smallImage.getUrl().substring(5).replaceAll("%20", " ");
        }
        catch (NullPointerException e) {
            smallImagePath = null;
        }
        try {
            largeImagePath = this.largeImage.getUrl().substring(5).replaceAll("%20", " ");
        }
        catch (NullPointerException e) {
            largeImagePath = null;
        }
        return this.propertyId + ";" + this.streetNum + ";" + this.streetName + ";" + this.suburb + ";" + this.propertyType + ";" + String.format("%.2f", this.dailyRental) + ";" + String.format("%.2f", this.lateFeeRate) + ";" + this.numberOfBedrooms + ";" + smallImagePath + ";" + largeImagePath + ";" + this.longDescription;
    }

    void setCurrentCustomerId(String currentCustomerId) {
        this.currentCustomerId = currentCustomerId;
    }

    boolean checkNotReturnable() {
        return this.propertyStatus != PropertyStatus.RENTED;
    }

    public RentalRecord getLatestRecord() {
        if (this.rentalRecord != null) {
            return this.rentalRecord.get(this.rentalRecord.size() - 1);
        }
        else {
            return null;
        }
    }

}
