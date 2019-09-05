package ui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import model.DatabaseDriver;

class IssuedBooks {

    private static Stage stage;
    private static Scene scene;
    private static AnchorPane pane;
    private static VBox vBox;

    private static TableView table;
    private static ObservableList tableData;

    static void display(){
        table = new TableView();
        tableData = FXCollections.observableArrayList();

        vBox = new VBox();
        pane = new AnchorPane();
        scene = new Scene(pane, 800, 500);
        scene.getStylesheets().add("/stylesheets/BaseStyle.css");
        stage = new Stage();

        // Vbox
        vBox.getChildren().addAll(table);
        vBox.prefWidthProperty().bind(pane.widthProperty());
        vBox.prefHeightProperty().bind(pane.heightProperty());

        setupTable();


        // AnchorPane
        pane.getChildren().addAll(vBox);

        // Scene
        stage.setWidth(400);
        stage.setMinWidth(400);
        stage.setHeight(500);
        stage.setMinHeight(300);

        stage.setScene(scene);
        stage.show();
    }

    private static void setupTable() {
        table.prefWidthProperty().bind(vBox.widthProperty());
        table.prefHeightProperty().bind(vBox.heightProperty());

        tableData.addAll(DatabaseDriver.getIssuedBooks());
        table.setItems(tableData);

        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        TableColumn authorCol = new TableColumn("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

        TableColumn publisherCol = new TableColumn("Publisher");
        publisherCol.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));

        TableColumn<Book, String> isAvailable = new TableColumn<>("Available");
        isAvailable.setCellValueFactory(cellData -> {
            boolean available = cellData.getValue().isAvailable();
            String availableString;
            if (available)
                availableString = "Available";
            else
                availableString = "Unavailable";
            return new ReadOnlyStringWrapper(availableString);
        });

        TableColumn memberID = new TableColumn("Member ID");
        memberID.setCellValueFactory(new PropertyValueFactory<Book, Integer>("memberID"));

        table.getColumns().addAll(idCol, titleCol, authorCol, publisherCol, isAvailable, memberID);
    }
}
