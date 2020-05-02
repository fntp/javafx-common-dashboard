package de.vitox.ratolotl.user;

import javafx.beans.property.*;
import lombok.Getter;

@Getter
public class InfectedUser {

    private final StringProperty name, os, ip, country;

    public InfectedUser(String name, String os, String ip, String country) {
        this.name = new SimpleStringProperty(name);
        this.os = new SimpleStringProperty(os);
        this.ip = new SimpleStringProperty(ip);
        this.country = new SimpleStringProperty(country);
    }

}
