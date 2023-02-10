package com.jannikbuscha.dashboard.tab;

import com.jannikbuscha.dashboard.user.LocalUserData;
import com.jannikbuscha.dashboard.util.Theme;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

import java.util.Arrays;

public class Options extends StackPane {

    public Options() {
        ComboBox cbxTheme = new ComboBox();
        CheckBox ccxDarkMode = new CheckBox("Dark Mode");

        // Prepare click event for theme change
        EventHandler<ActionEvent> themeClickEvent = e -> {
            LocalUserData.setProperty("theme", String.valueOf(cbxTheme.getSelectionModel().getSelectedIndex()));
            LocalUserData.setProperty("dark_mode", String.valueOf(ccxDarkMode.isSelected()));
            Theme.setCurrentTheme(Theme.values()[cbxTheme.getSelectionModel().getSelectedIndex()], ccxDarkMode.isSelected());
        };

        // Prepare theme combo box
        cbxTheme.setItems(FXCollections.observableArrayList(Theme.values()));
        cbxTheme.setOnAction(themeClickEvent);
        cbxTheme.setConverter(new StringConverter<Theme>() {
            @Override
            public String toString(Theme object) {
                return object.getName();
            }

            @Override
            public Theme fromString(String string) {
                return Arrays.stream(Theme.values()).filter(m -> m.getName().equals(string)).findFirst().orElse(null);
            }
        });

        // Prepare dark mode selector
        ccxDarkMode.setOnAction(themeClickEvent);

        // Set current theme
        cbxTheme.getSelectionModel().select(Integer.parseInt(LocalUserData.getProperty("theme").get()));
        ccxDarkMode.setSelected(LocalUserData.getProperty("dark_mode").map(Boolean::valueOf).orElse(false));

        HBox hbxTheme = new HBox(8, cbxTheme, ccxDarkMode);
        hbxTheme.setAlignment(Pos.BASELINE_LEFT);

        this.getChildren().addAll(hbxTheme);
    }

}
