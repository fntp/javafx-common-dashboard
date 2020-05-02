package de.vitox.ratolotl.controller;

import de.vitox.ratolotl.Main;
import de.vitox.ratolotl.tab.*;
import de.vitox.ratolotl.util.FXUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private StackPane stckTopBar;

    @FXML
    private VBox vbxMenuNavigation, vbxMenuTabs;

    @FXML
    private Label lblVersion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblVersion.setText(Main.getInstance().getVersion());
        FXUtil.movable(Main.getInstance().getStage(), stckTopBar);

//        FXUtil.windowActions(Main.getInstance().getStage(), minAction, maxAction, closeAction);

        Pane[] tabs = {new Home(), new Users(), new Builder(), new Options()};

        vbxMenuTabs.getChildren().add(tabs[0]);

        for (int i = 0; i < tabs.length; ++i) {
            FXUtil.tabSwitch(vbxMenuNavigation.getChildren().get(i), tabs[i], vbxMenuNavigation, vbxMenuTabs);
        }
    }

}
