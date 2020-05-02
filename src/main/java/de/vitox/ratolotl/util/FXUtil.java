package de.vitox.ratolotl.util;

import de.vitox.ratolotl.user.InfectedUser;
import javafx.beans.property.StringProperty;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static javafx.scene.input.MouseEvent.*;

public class FXUtil {

    private static int winWidth = 1240, winHeight = 704;

    public static void movable(Stage stage, Pane pane) {
        AtomicReference<Double> xOffset = new AtomicReference<>(0D);
        AtomicReference<Double> yOffset = new AtomicReference<>(0D);

        pane.setOnMousePressed(e -> {
            xOffset.set(e.getSceneX());
            yOffset.set(e.getSceneY());
        });

        pane.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - xOffset.get());
            stage.setY(e.getScreenY() - yOffset.get());
        });
    }

    public static void windowActions(Stage stage, Pane min, Pane close) {
        min.setOnMouseClicked(e -> stage.setIconified(true));
        close.setOnMouseClicked(e -> System.exit(0));
    }

    public static void resizable (Stage stage) {
        ResizeListener resizeListener = new ResizeListener(stage, winWidth, winHeight);
        Scene scene = stage.getScene();

        EventType[] mouseEvents = new EventType[]{MOUSE_MOVED, MOUSE_PRESSED, MOUSE_DRAGGED, MOUSE_EXITED, MOUSE_EXITED_TARGET};
        Arrays.stream(mouseEvents).forEach(type -> scene.addEventHandler(type, resizeListener));
    }

    public static TableColumn<InfectedUser, String> column(String title, Function<InfectedUser, StringProperty> property) {
        TableColumn<InfectedUser, String> col = new TableColumn<>(title);

        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setResizable(false);

        return col;
    }

    public static void tabSwitch(Node navigation, Pane tab, Pane navigationContainer, Pane tabContainer) {
        navigation.setOnMouseClicked(e -> {
            navigation.getStyleClass().add("selected");

            navigationContainer.getChildren().stream().filter(n -> !n.equals(navigation)).forEach(n ->
                    n.getStyleClass().remove("selected"));

            tabContainer.getChildren().clear();

            tabContainer.getChildren().add(tab);
        });
    }

}
