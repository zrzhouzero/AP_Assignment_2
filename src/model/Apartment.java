package model;

import javafx.scene.image.Image;
import utilities.DateTime;
import view.TipWindow;

public class Apartment extends RentalProperty{

    public Apartment(String propertyId, String streetNum, String streetName, String suburb, int numberOfBedrooms) {
        super(propertyId, streetNum, streetName, suburb);
        super.setPropertyTypeIsPremiumSuite(false);
        super.setNumberOfBedrooms(numberOfBedrooms);
        switch (this.getNumberOfBedrooms()) {
            case 1: super.setDailyRental(143);
                break;
            case 2: super.setDailyRental(210);
                break;
            case 3: super.setDailyRental(319);
                break;
            default: super.setDailyRental(999);
                break;
        }
        super.setLateFeeRate(this.getDailyRental() * 1.15);
    }

    public Apartment(String propertyId, String streetNum, String streetName, String suburb, int numberOfBedrooms, Image smallImage, Image largeImage) {
        super(propertyId, streetNum, streetName, suburb, smallImage, largeImage);
        super.setPropertyTypeIsPremiumSuite(false);
        super.setNumberOfBedrooms(numberOfBedrooms);
        switch (this.getNumberOfBedrooms()) {
            case 1: super.setDailyRental(143);
                    break;
            case 2: super.setDailyRental(210);
                    break;
            case 3: super.setDailyRental(319);
                    break;
            default: super.setDailyRental(999);
                    break;
        }
        super.setLateFeeRate(this.getDailyRental() * 1.15);
    }

    public Apartment(String propertyId, String streetNum, String streetName, String suburb, int numberOfBedrooms, String propertyStatus, String customerId, Image smallImage, Image largeImage, String longDescription) {
        this(propertyId, streetNum, streetName, suburb, numberOfBedrooms, smallImage, largeImage);
        switch (propertyStatus.toUpperCase()) {
            case "RENTED":
                this.setPropertyStatus(PropertyStatus.RENTED);
                break;
            case "UNDERMAINTENANCE":
                this.setPropertyStatus(PropertyStatus.UNDERMAINTENANCE);
                break;
            default:
                this.setPropertyStatus(PropertyStatus.AVAILABLE);
                break;
        }
        this.setCurrentCustomerId(customerId);
        this.setLongDescription(longDescription);
    }

    @Override
    public boolean checkAvailability(DateTime dateTime, int rentalLength) {
        String tipBoxTitle = "System Error";
        String tipBoxContent;
        if (this.getPropertyStatus() == PropertyStatus.RENTED) {
            tipBoxContent = "This property is currently rented.";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return false;
        }
        if (this.getPropertyStatus() == PropertyStatus.UNDERMAINTENANCE) {
            tipBoxContent = "This property is currently under maintenance.";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return false;
        }
        if (DateTime.diffDays(dateTime, new DateTime()) < 0) {
            tipBoxContent = "Cannot rent from the past days.";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return false;
        }
        if (dateTime.getWeekDay() >= 1 && dateTime.getWeekDay() <= 5) {
            if (rentalLength < 2) {
                tipBoxContent = "Need to rent for more days.";
                new TipWindow(tipBoxTitle, tipBoxContent);
                return false;
            }
            if (rentalLength > 28) {
                tipBoxContent = "Need to rent for less days.";
                new TipWindow(tipBoxTitle, tipBoxContent);
                return false;
            }
        }
        if (dateTime.getWeekDay() >= 6 && dateTime.getWeekDay() <= 7) {
            if (rentalLength < 3) {
                tipBoxContent = "Need to rent for more days.";
                new TipWindow(tipBoxTitle, tipBoxContent);
                return false;
            }
            if (rentalLength > 28) {
                tipBoxContent = "Need to rent for less days.";
                new TipWindow(tipBoxTitle, tipBoxContent);
                return false;
            }
        }
        return true;
    }

}