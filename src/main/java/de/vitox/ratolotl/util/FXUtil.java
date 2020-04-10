package de.vitox.ratolotl.util;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class FXUtil {

    public static void movable(Stage stage, Pane pane) {
        AtomicReference<Double> xOffset = new AtomicReference<>(0D);
        AtomicReference<Double> yOffset = new AtomicReference<>(0D);

        pane.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (e.getClickCount() == 2) {
                    stage.setMaximized(!stage.isMaximized());
                }
            }
        });

        pane.setOnMousePressed(e -> {
            xOffset.set(e.getSceneX());
            yOffset.set(e.getSceneY());
        });

        pane.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - xOffset.get());
            stage.setY(e.getScreenY() - yOffset.get());
        });
    }

    public static void windowActions(Stage stage, Circle min, Circle max, Circle close) {
        min.setOnMouseClicked(e -> stage.setIconified(true));
        max.setOnMouseClicked(e -> stage.setMaximized(!stage.isMaximized()));
        close.setOnMouseClicked(e -> System.exit(0));
    }

    public static void resizable(Stage stage) {
        addResizeListener(stage, 1280, 720, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private static void addResizeListener(Stage stage, double minWidth, double minHeight, double maxWidth, double maxHeight) {
        ResizeListener resizeListener = new ResizeListener(stage);

        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        resizeListener.setMinWidth(minWidth);
        resizeListener.setMinHeight(minHeight);
        resizeListener.setMaxWidth(maxWidth);
        resizeListener.setMaxHeight(maxHeight);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, resizeListener);
        }
    }

    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);

        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

}
