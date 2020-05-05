package de.vitox.ratolotl.tab;

import de.vitox.ratolotl.user.InfectedUser;
import de.vitox.ratolotl.util.FXUtil;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class Users extends StackPane {

    public Users() {
        this.getChildren().add(table());
    }

    private TableView<InfectedUser> table() {
        final ObservableList<InfectedUser> data = FXCollections.observableArrayList(
                new InfectedUser("1", "2", "3", "4"),
                new InfectedUser("1", "2", "3", "4"),
                new InfectedUser("1", "2", "3", "4"),
                new InfectedUser("1", "2", "3", "4"),
                new InfectedUser("1", "2", "3", "4"));

        TableView<InfectedUser> table = new TableView<>(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().addAll(FXUtil.column("Name", InfectedUser::getName), FXUtil.column("OS", InfectedUser::getOs),
                FXUtil.column("IP", InfectedUser::getIp), FXUtil.column("Country", InfectedUser::getCountry));
        table.setPlaceholder(new Label("Empty"));

        contextMenu(table);

        return table;
    }

    private void contextMenu(TableView table) {
        MenuItem menuItem = new MenuItem("Menu Item");
        menuItem.setOnAction(e -> {
            Object item = table.getSelectionModel().getSelectedItem();
            System.out.println("Selected Item: " + item);
        });

        ContextMenu contextMenu = new ContextMenu(menuItem);
        table.setContextMenu(contextMenu);
    }

}
