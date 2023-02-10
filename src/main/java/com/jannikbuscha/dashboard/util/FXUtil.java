package com.jannikbuscha.dashboard.util;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
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

    public static void windowActions(Stage stage, Node min, Node close) {
        min.setOnMouseClicked(e -> stage.setIconified(true));
        close.setOnMouseClicked(e -> System.exit(0));
    }

    public static void resizable(Stage stage) {
        ResizeListener resizeListener = new ResizeListener(stage, winWidth, winHeight);
        Scene scene = stage.getScene();

        EventType[] mouseEvents = new EventType[]{MOUSE_MOVED, MOUSE_PRESSED, MOUSE_DRAGGED, MOUSE_EXITED, MOUSE_EXITED_TARGET};
        Arrays.stream(mouseEvents).forEach(type -> scene.addEventHandler(type, resizeListener));
    }

    public static void tabSwitch(Node navigation, Pane tab, Pane navigationContainer, Pane tabContainer) {
        navigation.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                navigation.getStyleClass().add("selected");

                navigationContainer.getChildren().stream().filter(n -> !n.equals(navigation)).forEach(n ->
                        n.getStyleClass().remove("selected"));

                tabContainer.getChildren().clear();

                tabContainer.getChildren().add(tab);
            }
        });
    }

    public static <S> TableView<S> createTable(ObservableList<S> data, List<TableColumn<S, ?>> columns) {
        TableView<S> table = new TableView<>(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().addAll(columns);
        table.setPlaceholder(new Label("Empty"));

        table.skinProperty().addListener((obs, oldSkin, newSkin) ->
        {
            TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((o, oldVal, newVal) -> header.setReordering(false));
        });

        return table;
    }

    public static <T, S> TableColumn<S, T> createColumn(String name, Function<S, Property<T>> property) {
        TableColumn<S, T> column = new TableColumn<>(name);
        column.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        column.setSortable(true);

        return column;
    }

}
