package com.jannikbuscha.dashboard.tab;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Home extends StackPane {

    public Home() {
        this.getChildren().add(new Label(this.getClass().getSimpleName()));
    }

}
