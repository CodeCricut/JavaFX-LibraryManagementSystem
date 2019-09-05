package ui;

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
import model.Member;

class AllMembers {
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
        stage.setScene(scene);
        stage.show();
    }

    private static void setupTable() {
        table.prefWidthProperty().bind(vBox.heightProperty());
        table.prefHeightProperty().bind(vBox.widthProperty());

        tableData.addAll(DatabaseDriver.getAllMembers());
        table.setItems(tableData);

        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));

        TableColumn phoneNumCol = new TableColumn("Phone Number");
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<Member, String>("phoneNum"));

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<Member, String>("email"));

        table.getColumns().addAll(idCol, nameCol, phoneNumCol, emailCol);
    }
}
