package model;

import javafx.scene.image.Image;
import utilities.DateTime;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseProcessor {

    public DatabaseProcessor() {}

    public void insertPropertyRow(RentalProperty rentalProperty) {

        final String TABLE_NAME = "Properties";

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {

            String smallImageName;
            String largeImageName;
            try {
                String smallImagePath = rentalProperty.getSmallImage().getUrl().substring(5).replaceAll("%20", " ");
                String temp[] = smallImagePath.split("/");
                smallImageName = temp[temp.length - 1];
            }
            catch (NullPointerException e) {
                smallImageName = "a0.png";
            }
            try {
                String largeImagePath = rentalProperty.getLargeImage().getUrl().substring(5).replaceAll("%20", " ");
                String temp[] = largeImagePath.split("/");
                largeImageName = temp[temp.length - 1];
            }
            catch (NullPointerException e) {
                largeImageName = "a1.jpeg";
            }

            String str;
            if (rentalProperty instanceof PremiumSuite) {
                str = ((PremiumSuite) rentalProperty).getLastMaintenanceDate().getFormattedDate();
            } else {
                str = "";
            }

            String query = "INSERT INTO " + TABLE_NAME + " VALUES ('" +
                    rentalProperty.getPropertyId() + "', '" +
                    rentalProperty.getStreetNum() + "', '" +
                    rentalProperty.getStreetName() + "', '" +
                    rentalProperty.getSuburb() + "', '" +
                    rentalProperty.getPropertyType() + "', '" +
                    rentalProperty.getDailyRental() + "', '" +
                    rentalProperty.getLateFeeRate() + "', '" +
                    rentalProperty.getNumberOfBedrooms() + "', '" +
                    rentalProperty.getPropertyStatus() + "', '" +
                    smallImageName + "', '" +
                    largeImageName + "', '" +
                    rentalProperty.getLongDescription() + "', '" +
                    rentalProperty.getCurrentCustomerId() + "', '" +
                    str +
                    "');";

            stmt.executeUpdate(query);

            con.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    void insertRecordRow(String propertyId, RentalRecord rentalRecord) {

        final String TABLE_NAME = "Records";

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "INSERT INTO " + TABLE_NAME + " VALUES ('" +
                        rentalRecord.getRecordId() + "', '" +
                        propertyId + "', '" +
                        rentalRecord.getCustomerId() + "', '" +
                        rentalRecord.getRentDateToString() + "', '" +
                        rentalRecord.getEstRtnDateToString() + "', '" +
                        rentalRecord.getActRtnDateToString() + "', '" +
                        String.valueOf(rentalRecord.getRentalFee()) + "', '" +
                        String.valueOf(rentalRecord.getLateFee()) + "', '" +
                        rentalRecord.getIsFinish() +
                        "');";
            stmt.executeUpdate(query);

            con.commit();

            System.out.println("Record Created");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updatePropertyStatus(String propertyId, RentalProperty.PropertyStatus propertyStatus) {

        final String TABLE_NAME = "Properties";

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "UPDATE " + TABLE_NAME +
                    " SET Property_Status = '" + propertyStatus + "'" +
                    " WHERE Property_ID LIKE '" + propertyId + "';";

            stmt.executeUpdate(query);

            System.out.println("Status Changed");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePropertyLongDescription(String propertyId, String longDescription) {

        final String TABLE_NAME = "Properties";

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "UPDATE " + TABLE_NAME +
                    " SET Long_Description = '" + longDescription + "'" +
                    " WHERE Property_ID LIKE '" + propertyId + "';";

            stmt.executeUpdate(query);

            System.out.println("Des Changed");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePropertyCurrentCustomer(String propertyId, String customerId) {

        final String TABLE_NAME = "Properties";

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "UPDATE " + TABLE_NAME +
                    " SET Current_Customer_ID = '" + customerId + "'" +
                    " WHERE Property_ID LIKE '" + propertyId + "';";

            stmt.executeUpdate(query);

            System.out.println("Customer Changed");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void updatePropertyPremiumSuiteLastMaintenanceDate(String propertyId, DateTime dateTime) {

        final String TABLE_NAME = "Properties";

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "UPDATE " + TABLE_NAME +
                    " SET Last_Maintenance_Date = '" + dateTime.getFormattedDate() + "'" +
                    " WHERE Property_ID LIKE '" + propertyId + "';";

            stmt.executeUpdate(query);

            System.out.println("Date Updated");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void updateRecordRow(RentalRecord rentalRecord, String propertyId) {

        final String TABLE_NAME = "Records";

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "UPDATE " + TABLE_NAME +
                    " SET Actual_Return_Date = '" + rentalRecord.getActRtnDateToString() + "', " +
                    "Rental_Fee = '" + rentalRecord.getRentalFee() + "', " +
                    "Late_Fee = '" + rentalRecord.getLateFee() + "', " +
                    "Is_Finish = '" + rentalRecord.getIsFinish() + "'" +
                    " WHERE Property_ID LIKE '" + propertyId + "';";

            stmt.executeUpdate(query);

            System.out.println("Record Updated");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<RentalProperty> retrieveData() {

        ArrayList<RentalProperty> rentalProperties = new ArrayList<>();

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM Properties";
            try (ResultSet resultSet1 = stmt.executeQuery(query)) {
                while (resultSet1.next()) {
                    Image smallImage;
                    Image largeImage;
                    try {
                        smallImage = new Image("images/" + resultSet1.getString("Display_Image_Path"));
                        largeImage = new Image("images/" + resultSet1.getString("Detail_Image_Path"));
                    } catch (Exception e) {
                        smallImage = null;
                        largeImage = null;
                    }
                    if (resultSet1.getString("Property_Type").toUpperCase().equals(("Apartment").toUpperCase())) {
                        rentalProperties.add(new Apartment(resultSet1.getString("Property_ID"), resultSet1.getString("Street_Number"), resultSet1.getString("Street_Name"), resultSet1.getString("Suburb"), resultSet1.getInt("Number_Of_Bedrooms"), resultSet1.getString("Property_Status"), resultSet1.getString("Current_Customer_ID"), smallImage, largeImage, resultSet1.getString("Long_Description")));
                    } else {
                        rentalProperties.add(new PremiumSuite(resultSet1.getString("Property_ID"), resultSet1.getString("Street_Number"), resultSet1.getString("Street_Name"), resultSet1.getString("Suburb"), resultSet1.getString("Property_Status"), resultSet1.getString("Current_Customer_ID"), convert(resultSet1.getString("Last_Maintenance_Date")), smallImage, largeImage, resultSet1.getString("Long_Description")));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try (Connection con = ConnectToDatabase.getConnection();
             Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM Records";
            try (ResultSet resultSet2 = stmt.executeQuery(query)) {
                while (resultSet2.next()) {
                    int i;
                    int end = rentalProperties.size();
                    for (i = 0; i < end; i++) {
                        if (rentalProperties.get(i).getPropertyId().equals(resultSet2.getString("Property_ID"))) {
                            break;
                        }
                    }
                    if (resultSet2.getString("Actual_Return_Date").trim().equals("null")) {
                        rentalProperties.get(i).addRecord(new RentalRecord(resultSet2.getString("Customer_ID"), resultSet2.getString("Record_ID"), convert(resultSet2.getString("Rent_Date")), convert(resultSet2.getString("Estimated_Return_Date")), null, resultSet2.getDouble("Rental_Fee"), resultSet2.getDouble("Late_Fee"), resultSet2.getBoolean("Is_Finish")));
                    } else {
                        rentalProperties.get(i).addRecord(new RentalRecord(resultSet2.getString("Customer_ID"), resultSet2.getString("Record_ID"), convert(resultSet2.getString("Rent_Date")), convert(resultSet2.getString("Estimated_Return_Date")), convert(resultSet2.getString("Actual_Return_Date")), resultSet2.getDouble("Rental_Fee"), resultSet2.getDouble("Late_Fee"), resultSet2.getBoolean("Is_Finish")));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return rentalProperties;
    }

    private DateTime convert(String parseDate) {
        int day = Integer.parseInt(parseDate.substring(0, 2));
        int month = Integer.parseInt(parseDate.substring(3, 5));
        int year = Integer.parseInt(parseDate.substring(6, 10));
        return new DateTime(day, month, year);
    }

}
