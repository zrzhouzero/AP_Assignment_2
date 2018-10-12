package model;

import utilities.DateTime;

import java.io.File;

public class Test {

    public static void main(String args[]) {

        String recordId = "eqrg1 q2";
        String s = "Record ID: " + recordId + "\nRent Date: ";
        System.out.println(s);

        String rawText = "abc/def/ghi/jkl.jpg";
        String processedText[] = rawText.split("/");
        for (String str: processedText) {
            System.out.println(str);
        }
        System.out.println(processedText[processedText.length - 1]);

        System.out.println(new File("/Users/zhouzhirou/IdeaProjects/AP Assignment 2/src/images/a0.png").getAbsolutePath());

        DateTime dateTime = new DateTime(11, 10, 2018);
        System.out.println(dateTime.getFormattedDate());

        RentalProperty rentalProperty = new Apartment("abc", "123", "abc", "abc", 2);
        System.out.println(rentalProperty.getPropertyStatus());

        System.out.println(("GOOGLEDOC").substring(4));
        System.out.println(RentalProperty.PropertyStatus.UNDERMAINTENANCE);

    }

}