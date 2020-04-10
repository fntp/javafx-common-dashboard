package de.vitox.ratolotl.database;

import lombok.Getter;

import java.io.*;
import java.util.Properties;

@Getter
public class Config extends Properties {

    private String mySQLHost, mySQLDatabase, mySQLUser, mySQLPassword;

    private int mySQLPort;

    public Config(InputStream inputStream) {
        try {
            this.load(inputStream);

            this.mySQLHost = this.getProperty("mysql.host");
            this.mySQLDatabase = this.getProperty("mysql.database");
            this.mySQLUser = this.getProperty("mysql.user");
            this.mySQLPassword = this.getProperty("mysql.password");
            this.mySQLPort = Integer.parseInt(this.getProperty("mysql.port"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}