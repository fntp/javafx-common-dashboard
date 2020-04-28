package de.vitox.ratolotl.tab;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Users extends StackPane {

    public Users() {
        this.getChildren().add(new Label(this.getClass().getSimpleName()));
    }

}
