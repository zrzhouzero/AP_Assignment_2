package model;

import utilities.*;
import view.TipWindow;

import java.util.ArrayList;

public class FlexiSystem {

    private DatabaseProcessor databaseProcessor = new DatabaseProcessor();
    private ArrayList<RentalProperty> rentalProperties = new ArrayList<>();

    public ArrayList<RentalProperty> getRentalProperties() {
        return this.rentalProperties;
    }

    public void setRentalProperties(ArrayList<RentalProperty> rentalProperties) {
        this.rentalProperties = rentalProperties;
    }

    public void addProperty(RentalProperty rentalProperty) {
        this.rentalProperties.add(rentalProperty);
    }

    public void rentProperty(String propertyId, String customerId, DateTime checkInDate, int rentalLength) {

        String tipBoxContent;
        String tipBoxTitle = "System Error";

        int index = findPropertyByPropertyId(propertyId);
        if (index < 0) {
            tipBoxContent = "Property not found";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }
        if (!this.rentalProperties.get(index).checkAvailability(checkInDate, rentalLength)) {
            return;
        }
        this.rentalProperties.get(index).setPropertyStatus(RentalProperty.PropertyStatus.RENTED);
        this.databaseProcessor.updatePropertyStatus(propertyId, RentalProperty.PropertyStatus.RENTED);
        this.rentalProperties.get(index).setCurrentCustomerId(customerId);
        this.databaseProcessor.updatePropertyCurrentCustomer(propertyId, customerId);
        String recordId = this.rentalProperties.get(index).getPropertyId() + customerId + checkInDate.getFormattedDate();
        RentalRecord record = new RentalRecord(customerId, recordId, checkInDate, rentalLength);
        this.rentalProperties.get(index).addRecord(record);
        this.databaseProcessor.insertRecordRow(propertyId, record);

        tipBoxContent = recordId + " is now rented by " + customerId;
        tipBoxTitle = "Congratulations";
        new TipWindow(tipBoxTitle, tipBoxContent);

    }

    public boolean returnProperty(String customerId, String propertyId, DateTime checkOutDate) {
        int index = this.findPropertyByPropertyId(propertyId);
        if (this.rentalProperties.get(index).checkNotReturnable()) {
            new TipWindow("System Error", "This property is not currently rented");
            return false;
        }
        if (DateTime.diffDays(checkOutDate, new DateTime()) < 0) {
            new TipWindow("System Error", "Need to defer the check-out date");
            return false;
        }
        if (this.rentalProperties.get(index).getLatestRecord() == null) {
            new TipWindow("System Error", "Cannot find any records!");
            return false;
        }
        this.rentalProperties.get(index).getLatestRecord().setReturnDetails(checkOutDate, this.rentalProperties.get(index).getDailyRental(), this.rentalProperties.get(index).getLateFeeRate());
        this.databaseProcessor.updateRecordRow(this.rentalProperties.get(index).getLatestRecord(), propertyId);
        this.rentalProperties.get(index).setPropertyStatus(RentalProperty.PropertyStatus.AVAILABLE);
        this.databaseProcessor.updatePropertyStatus(propertyId, RentalProperty.PropertyStatus.AVAILABLE);
        this.rentalProperties.get(index).setCurrentCustomerId(null);
        this.databaseProcessor.updatePropertyCurrentCustomer(propertyId, null);
        new TipWindow("Congratulation", propertyId + " is returned by " + customerId + " successfully!");
        return true;
    }

    public void performMaintenance(String propertyId, DateTime performDate) {
        int index = findPropertyByPropertyId(propertyId);
        if (index < 0) {
            new TipWindow("System Error", "Property not found");
            return;
        }
        if (this.rentalProperties.get(index).getPropertyStatus() != RentalProperty.PropertyStatus.AVAILABLE) {
            new TipWindow("System Error", "The property cannot be maintained now");
            return;
        }
        if (DateTime.diffDays(performDate, new DateTime()) < 0) {
            new TipWindow("System Error", "Need to defer the maintenance day");
            return;
        }
        this.rentalProperties.get(index).setPropertyStatus(RentalProperty.PropertyStatus.UNDERMAINTENANCE);
        this.databaseProcessor.updatePropertyStatus(propertyId, RentalProperty.PropertyStatus.UNDERMAINTENANCE);
        new TipWindow("Congratulation", propertyId + " is now under maintenance");
    }

    public void completeMaintenance(String propertyId) {
        int index = findPropertyByPropertyId(propertyId);
        if (index < 0) {
            new TipWindow("System Error", "Property not found");
            return;
        }
        if (this.rentalProperties.get(index).getPropertyStatus() != RentalProperty.PropertyStatus.UNDERMAINTENANCE) {
            new TipWindow("System Error", "The property cannot be maintained now");
            return;
        }
        this.rentalProperties.get(index).setPropertyStatus(RentalProperty.PropertyStatus.AVAILABLE);
        this.databaseProcessor.updatePropertyStatus(propertyId, RentalProperty.PropertyStatus.AVAILABLE);
        if (this.rentalProperties.get(index) instanceof PremiumSuite) {
            ((PremiumSuite) this.rentalProperties.get(index)).setLastMaintenanceDate(new DateTime());
            this.databaseProcessor.updatePropertyPremiumSuiteLastMaintenanceDate(propertyId, new DateTime());
            ((PremiumSuite) this.rentalProperties.get(index)).setNextMaintenanceDate(new DateTime(new DateTime(), 10));
            new TipWindow("Congratulation", "Premium Suite " + propertyId + " is well maintained and ready for rent");
        } else {
            new TipWindow("Congratulation", "Maintenance of apartment " + propertyId + " is successfully finished");
        }

    }

    public int findPropertyByPropertyId(String propertyId) {
        int index;
        boolean ifFound = false;
        int end = this.rentalProperties.size();
        for (index = 0; index < end; index++) {
            if (propertyId.toUpperCase().equals(rentalProperties.get(index).getPropertyId().toUpperCase())) {
                ifFound = true;
                break;
            }
        }
        if (ifFound) {
            return index;
        }
        else {
            return -1;
        }
    }

    public ArrayList<Integer> findPropertyByCustomerIdForReturn(String customerId) {
        ArrayList<Integer> indexList = new ArrayList<>();
        int index;
        int end = this.rentalProperties.size();
        for (index = 0; index < end; index++) {
            if (this.rentalProperties.get(index).getCurrentCustomerId() == null || this.rentalProperties.get(index).checkNotReturnable()) {
                continue;
            }
            if (customerId.toUpperCase().equals(this.rentalProperties.get(index).getCurrentCustomerId().toUpperCase())) {
                indexList.add(index);
            }
        }
        return indexList;
    }
}
