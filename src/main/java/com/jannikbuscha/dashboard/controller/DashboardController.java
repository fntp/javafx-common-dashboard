package com.jannikbuscha.dashboard.controller;

import com.jannikbuscha.dashboard.Main;
import com.jannikbuscha.dashboard.tab.*;
import com.jannikbuscha.dashboard.util.FXUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private StackPane stckTopBar, stckMin, stckClose;

    @FXML
    private VBox vbxMenuNavigation, vbxMenuTabs;

    @FXML
    private Label lblVersion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblVersion.setText(Main.getInstance().getVersion());
        FXUtil.movable(Main.getInstance().getStage(), stckTopBar);

        FXUtil.windowActions(Main.getInstance().getStage(), stckMin, stckClose);

        Pane[] tabs = {new Home(), new Users(), new Builder(), new Options()};

        vbxMenuTabs.getChildren().add(tabs[0]);

        for (int i = 0; i < tabs.length; ++i) {
            VBox.setVgrow(tabs[i], Priority.ALWAYS);
            FXUtil.tabSwitch(vbxMenuNavigation.getChildren().get(i), tabs[i], vbxMenuNavigation, vbxMenuTabs);
        }
    }

}
