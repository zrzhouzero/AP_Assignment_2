package model;

import javafx.scene.image.Image;
import utilities.DateTime;
import view.TipWindow;

public class PremiumSuite extends RentalProperty {

    private DateTime lastMaintenanceDate;
    private DateTime nextMaintenanceDate;

    public PremiumSuite(String propertyId, String streetNum, String streetName, String suburb) {
        super(propertyId, streetNum, streetName, suburb);
        super.setPropertyTypeIsPremiumSuite(true);
        super.setNumberOfBedrooms(3);
        super.setDailyRental(554);
        super.setLateFeeRate(662);
        this.lastMaintenanceDate = new DateTime();
        this.nextMaintenanceDate = new DateTime(this.lastMaintenanceDate, 10);
    }

    public PremiumSuite(String propertyId, String streetNum, String streetName, String suburb, Image smallImage, Image largeImage) {
        super(propertyId, streetNum, streetName, suburb, smallImage, largeImage);
        super.setPropertyTypeIsPremiumSuite(true);
        super.setNumberOfBedrooms(3);
        super.setDailyRental(554);
        super.setLateFeeRate(662);
        this.lastMaintenanceDate = new DateTime();
        this.nextMaintenanceDate = new DateTime(this.lastMaintenanceDate, 10);
    }

    public PremiumSuite(String propertyId, String streetNum, String streetName, String suburb, String propertyStatus, String customerId, DateTime lastMaintenanceDate, Image smallImage, Image largeImage, String longDescription) {
        super(propertyId, streetNum, streetName, suburb, smallImage, largeImage);
        super.setPropertyTypeIsPremiumSuite(true);
        super.setNumberOfBedrooms(3);
        super.setDailyRental(554);
        super.setLateFeeRate(662);
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
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.nextMaintenanceDate = new DateTime(this.lastMaintenanceDate, 10);
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
        if (DateTime.diffDays(this.nextMaintenanceDate, dateTime) < rentalLength) {
            tipBoxContent = "The next maintenance day is included in the rent days, reservation failed.";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + this.lastMaintenanceDate.getFormattedDate();
    }

    void setLastMaintenanceDate(DateTime lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    void setNextMaintenanceDate(DateTime nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public DateTime getNextMaintenanceDate() {
        return this.nextMaintenanceDate;
    }

    public DateTime getLastMaintenanceDate() {
        return this.lastMaintenanceDate;
    }
}
