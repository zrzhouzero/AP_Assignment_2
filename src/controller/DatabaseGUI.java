package controller;

import org.hsqldb.util.DatabaseManagerSwing;

public class DatabaseGUI {

    public static void main(String args[]) {

        DatabaseManagerSwing.main(new String[] {
                "--url", "jdbc:hsqldb:file:database/FlexiRentSys", "--noexit"
        });

    }

}
