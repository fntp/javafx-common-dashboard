package de.vitox.ratolotl.controller;

import de.vitox.ratolotl.Main;
import de.vitox.ratolotl.util.FXUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private HBox hbxTopbar;

    @FXML
    private Circle minAction, maxAction, closeAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXUtil.movable(Main.getInstance().getStage(), hbxTopbar);
        FXUtil.windowActions(Main.getInstance().getStage(), minAction, maxAction, closeAction);
    }

}
