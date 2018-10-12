package model;

import utilities.DateTime;

public class RentalRecord {

    private String customerId;
    private String recordId;
    private DateTime rentDate;
    private DateTime estRtnDate;
    private DateTime actRtnDate;
    private double rentalFee;
    private double lateFee;
    private boolean isFinished;

    String getCustomerId() {
        return this.customerId;
    }

    String getRecordId() {
        return this.recordId;
    }

    String getRentDateToString() {
        return this.rentDate.getFormattedDate();
    }

    String getEstRtnDateToString() {
        return this.estRtnDate.getFormattedDate();
    }

    String getActRtnDateToString() {
        if (this.actRtnDate == null) {
            return null;
        }
        return this.actRtnDate.getFormattedDate();
    }

    double getRentalFee() {
        return this.rentalFee;
    }

    double getLateFee() {
        return this.lateFee;
    }

    boolean getIsFinish() {
        return this.isFinished;
    }

    RentalRecord(String customerId, String recordId, DateTime rentDate, int rentalLength) {

        this.customerId = customerId;
        this.recordId = recordId;
        this.rentDate = rentDate;
        this.estRtnDate = new DateTime(rentDate, rentalLength);
        this.actRtnDate = null;
        this.rentalFee = 0;
        this.lateFee = 0;
        this.isFinished = false;

    }

    RentalRecord(String customerId, String recordId, DateTime rentDate, DateTime estRtnDate, DateTime actRtnDate, double rentalFee, double lateFee, boolean isFinished) {

        this.customerId = customerId;
        this.recordId = recordId;
        this.rentDate = rentDate;
        this.estRtnDate = estRtnDate;
        this.actRtnDate = actRtnDate;
        this.rentalFee = rentalFee;
        this.lateFee = lateFee;
        this.isFinished = isFinished;

    }

    @Override
    public String toString() {
        return this.customerId + ";" + this.recordId + ";" + this.rentDate + ";" + this.estRtnDate + ";" + this.actRtnDate + ";" + String.format("%.2f", this.rentalFee) + ";" + String.format("%.2f", this.lateFee) + ";" + this.isFinished;
    }

    private void finishRecord() {
        this.isFinished = true;
    }

    private void setActRtnDate(DateTime dateTime) {
        this.actRtnDate = dateTime;
    }

    private void setRentalFee(double feeRate) {
        int days;
        if (DateTime.diffDays(this.estRtnDate, this.actRtnDate) <= 0) {
            days = DateTime.diffDays(this.estRtnDate, this.rentDate);
        }
        else {
            days = DateTime.diffDays(this.actRtnDate, this.rentDate);
        }
        if (days >= 0) {
            this.rentalFee = feeRate * days;
        }
        else {
            this.rentalFee = 0;
        }
    }

    private void setLateFee(double feeRate) {
        int days = DateTime.diffDays(this.actRtnDate, this.estRtnDate);
        if (days >= 0) {
            this.lateFee = feeRate * days;
        }
        else {
            this.lateFee = 0;
        }
    }

    void setReturnDetails(DateTime dateTime, double dailyFeeRate, double lateFeeRate) {
        this.finishRecord();
        this.setActRtnDate(dateTime);
        this.setRentalFee(dailyFeeRate);
        this.setLateFee(lateFeeRate);
    }

    public String recordDetails() {

        if (this.isFinished) {
            return "Record ID: " + this.recordId +
                    "\nRent Date: " + this.rentDate.getFormattedDate() +
                    "\nEstimated Return Date: " + this.estRtnDate.getFormattedDate() +
                    "\nActual Return Date: " + this.actRtnDate.getFormattedDate() +
                    "\nRental Fee: " + String.format("%.2f", this.rentalFee) +
                    "\nLate Fee: " + String.format("%.2f", this.lateFee);
        }
        else {
            return "Record ID: " + this.recordId +
                    "\nRent Date: " + this.rentDate.getFormattedDate() +
                    "\nEstimated Return Date: " + this.estRtnDate.getFormattedDate() +
                    "\nCurrently In Use " +
                    "\nRental Fee: " + String.format("%.2f", this.rentalFee) +
                    "\nLate Fee: " + String.format("%.2f", this.lateFee);
        }

    }

}

