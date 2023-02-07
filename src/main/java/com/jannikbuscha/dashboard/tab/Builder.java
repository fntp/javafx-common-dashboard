package com.jannikbuscha.dashboard.tab;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Builder extends StackPane {

    public Builder() {
        this.getChildren().add(new Label(this.getClass().getSimpleName()));
    }

}
