package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import model.DatabaseDriver;


class AddBook {

    private static Stage stage;
    private static Scene scene;
    private static AnchorPane pane;
    private static VBox vBox;
    private static HBox hBox;

    private static Label fieldRequiredLabel;
    private static TextField titleField, authorField, publisherField;

    private static Button saveButton, cancelButton;

    static void display(){
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        hBox = new HBox();

        fieldRequiredLabel = new Label();
        titleField = new TextField();
        authorField = new TextField();
        publisherField = new TextField();

        vBox = new VBox();
        pane = new AnchorPane();
        scene = new Scene(pane, 800, 500);
        stage = new Stage();

        // STAGE
        stage.setTitle("Add Book");
        stage.setWidth(500);
        stage.setHeight(270);
        stage.setMinWidth(250);
        stage.setMinHeight(270);

        stage.setScene(scene);
        stage.show();

        // SCENE
        scene.getStylesheets().add("/stylesheets/BaseStyle.css");

        // PANE
        pane.getChildren().addAll(vBox);

        // VBOX
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setSpacing(10);
        vBox.prefWidthProperty().bind(pane.widthProperty());
        vBox.getChildren().addAll(fieldRequiredLabel, titleField, authorField, publisherField, hBox);

        // Label
        fieldRequiredLabel.setText("Enter the required fields.");
        fieldRequiredLabel.setStyle("-fx-text-fill: red;");
        fieldRequiredLabel.setVisible(false);

        // TEXT FIELDS
        titleField.setPromptText("Book Title");
        titleField.prefWidthProperty().bind(vBox.widthProperty());

        authorField.setPromptText("Book Author");
        authorField.prefWidthProperty().bind(vBox.widthProperty());

        publisherField.setPromptText("Book Publisher");
        publisherField.prefWidthProperty().bind(vBox.widthProperty());

        // HBOX
        hBox.getChildren().addAll(saveButton, cancelButton);
        hBox.prefWidthProperty().bind(pane.widthProperty());
        hBox.prefHeightProperty().bind(pane.heightProperty());
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);

        // BUTTONS
        saveButton.prefWidthProperty().bind(hBox.widthProperty());
        saveButton.setOnAction(event -> addBook());

        cancelButton.prefWidthProperty().bind(hBox.widthProperty());
        cancelButton.setOnAction(event -> stage.close());
    }

    private static void addBook(){
        String missingTextStyle = "-fx-border-color: red;";
        boolean textMissing = false;
        if (titleField.getText().isEmpty()){
            titleField.setStyle(missingTextStyle);
            fieldRequiredLabel.setVisible(true);
            textMissing = true;
        }
        if (authorField.getText().isEmpty()){
            authorField.setStyle(missingTextStyle);
            fieldRequiredLabel.setVisible(true);
            textMissing = true;
        }
        if (publisherField.getText().isEmpty()){
            publisherField.setStyle(missingTextStyle);
            fieldRequiredLabel.setVisible(true);
            textMissing = true;
        }

        if (! textMissing){
            Book book = new Book(-1,
                    titleField.getText(),
                    authorField.getText(),
                    publisherField.getText(),
                    true,
                    -1);
            DatabaseDriver.addBook(book);
            stage.close();
        }
    }
}
