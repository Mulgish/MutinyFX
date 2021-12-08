package com.mulgish;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Entry point to application. Starts up CDI Container and signals start with an event.
 */
public class TestApplication {

    public static Stage PRIMARY_STAGE;
    public static ListView<String> LIST_VIEW;
    public static TextField TEXT_FIELD;
    public static Button REMOVE_BUTTON;
    public static Button ADD_BUTTON;

    public static void start(final Stage stage) {
        PRIMARY_STAGE = stage;

        LIST_VIEW = new ListView<>();

        TEXT_FIELD = new TextField();

        REMOVE_BUTTON = new Button("Remove Button");
        REMOVE_BUTTON.setOnAction(actionEvent -> {
            TEXT_FIELD.clear();
            final ObservableList<String> items = LIST_VIEW.getItems();
            final int size = items.size();
            if (size > 0) {
                items.remove(size - 1);
            }
        });

        ADD_BUTTON = new Button("Add Button");
        ADD_BUTTON.setOnAction(actionEvent -> {
            LIST_VIEW.getItems().add(TEXT_FIELD.getText());
            TEXT_FIELD.clear();
        });

        final VBox parent = new VBox(ADD_BUTTON, REMOVE_BUTTON, TEXT_FIELD, LIST_VIEW);
        parent.setPrefHeight(350);
        parent.setPrefWidth(600);

        try {
            stage.initStyle(StageStyle.DECORATED);
        } catch (Throwable e) {
        }

        final Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

}
