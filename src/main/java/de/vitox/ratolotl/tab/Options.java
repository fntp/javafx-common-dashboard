package de.vitox.ratolotl.tab;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Options extends StackPane {

    public Options() {
        this.getChildren().add(new Label(this.getClass().getSimpleName()));
    }

}
