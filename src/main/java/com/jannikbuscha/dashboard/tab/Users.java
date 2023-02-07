package com.jannikbuscha.dashboard.tab;

import com.jannikbuscha.dashboard.user.User;
import com.jannikbuscha.dashboard.util.FXUtil;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.List;

public class Users extends StackPane {

    public Users() {
        final ObservableList<User> data = FXCollections.observableArrayList(
                new User("Violet", 56, "USA"),
                new User("Brianna", 44, "Germany"),
                new User("Freddie", 12, "Belgium"),
                new User("James", 35, "Denmark"),
                new User("Alina", 22, "New Zealand"));

        List<TableColumn<User, ?>> columns = Arrays.asList(
                FXUtil.createColumn("Name", User::getName),
                FXUtil.createColumn("Age", User::getAge),
                FXUtil.createColumn("Country", User::getCountry)
        );

        TableView<User> table = FXUtil.createTable(data, columns);

        this.getChildren().add(table);
    }

}
