package com.jannikbuscha.dashboard.util;

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public class ResizeListener implements EventHandler<MouseEvent> {
    private final Stage stage;
    private final Scene scene;

    private Cursor cursorEvent = Cursor.DEFAULT;

    private double startX = 0, startY = 0;
    private double sceneOffsetX = 0, sceneOffsetY = 0;

    public ResizeListener(Stage stage, int width, int height) {
        this.stage = stage;
        this.scene = stage.getScene();

        stage.setMinWidth(width);
        stage.setMinHeight(height);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();

        double mouseEventX = mouseEvent.getSceneX(), mouseEventY = mouseEvent.getSceneY();
        double viewWidth = stage.getWidth(), viewHeight = stage.getHeight();

        int border = 4;

        if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
            if (mouseEventX < border) {
                cursorEvent = Cursor.W_RESIZE;
            } else if (mouseEventX > viewWidth - border) {
                cursorEvent = Cursor.E_RESIZE;
            } else if (mouseEventY < border) {
                cursorEvent = Cursor.N_RESIZE;
            } else if (mouseEventY > viewHeight - border) {
                cursorEvent = Cursor.S_RESIZE;
            } else {
                cursorEvent = Cursor.DEFAULT;
            }

            scene.setCursor(cursorEvent);
        } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
            scene.setCursor(Cursor.DEFAULT);
        } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
            startX = viewWidth - mouseEventX;
            startY = viewHeight - mouseEventY;

            sceneOffsetX = mouseEvent.getSceneX();
            sceneOffsetY = mouseEvent.getSceneY();
        } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) && !Cursor.DEFAULT.equals(cursorEvent)) {
            if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);

                if (Cursor.N_RESIZE.equals(cursorEvent)) {
                    if (stage.getHeight() > minHeight || mouseEventY < 0) {
                        double height = stage.getY() - mouseEvent.getScreenY() + stage.getHeight() + sceneOffsetY;
                        double y = mouseEvent.getScreenY() - sceneOffsetY;

                        stage.setHeight(height);
                        stage.setY(y);
                    }
                } else {
                    if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
                        stage.setHeight(mouseEventY + startY);
                    }
                }
            }

            if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);

                if (Cursor.W_RESIZE.equals(cursorEvent)) {
                    if (stage.getWidth() > minWidth || mouseEventX < 0) {
                        double width = stage.getX() - mouseEvent.getScreenX() + stage.getWidth() + sceneOffsetX;
                        double x = mouseEvent.getScreenX() - sceneOffsetX;

                        stage.setWidth(width);
                        stage.setX(x);
                    }
                } else {
                    if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
                        stage.setWidth(mouseEventX + startX);
                    }
                }
            }
        }
    }
}