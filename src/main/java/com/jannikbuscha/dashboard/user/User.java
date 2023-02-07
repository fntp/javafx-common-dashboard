package com.jannikbuscha.dashboard.user;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class User {

    private final StringProperty name, country;
    private final IntegerProperty age;

    public User(String name, int age, String country) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.country = new SimpleStringProperty(country);
    }

}
