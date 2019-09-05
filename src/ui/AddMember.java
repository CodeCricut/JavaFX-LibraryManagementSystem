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
import model.DatabaseDriver;
import model.Member;

class AddMember {

    private static Stage stage;
    private static Scene scene;
    private static AnchorPane pane;
    private static VBox vBox;
    private static HBox hBox;

    private static Label fieldRequiredLabel;
    private static TextField nameField, phoneNumField, emailField;

    private static Button saveButton, cancelButton;

    static void display(){
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        hBox = new HBox();

        fieldRequiredLabel = new Label();
        nameField = new TextField();
        phoneNumField = new TextField();
        emailField = new TextField();

        vBox = new VBox();
        pane = new AnchorPane();
        scene = new Scene(pane, 800, 500);
        scene.getStylesheets().add("/stylesheets/BaseStyle.css");
        stage = new Stage();

        // STAGE
        stage.setTitle("Add Member");
        stage.setWidth(500);
        stage.setHeight(270);
        stage.setMinWidth(250);
        stage.setMinHeight(270);

        stage.setScene(scene);
        stage.show();

        // PANE
        pane.getChildren().addAll(vBox);

        // VBOX
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setSpacing(10);
        vBox.prefWidthProperty().bind(pane.widthProperty());
        vBox.getChildren().addAll(fieldRequiredLabel, nameField, phoneNumField, emailField, hBox);

        // Label
        fieldRequiredLabel.setText("Enter the required fields.");
        fieldRequiredLabel.setStyle("-fx-text-fill: red;");
        fieldRequiredLabel.setVisible(false);

        // TEXT FIELDS
        nameField.setPromptText("Name");
        nameField.prefWidthProperty().bind(vBox.widthProperty());

        phoneNumField.setPromptText("Phone Number");
        phoneNumField.prefWidthProperty().bind(vBox.widthProperty());

        emailField.setPromptText("Email (optional)");
        emailField.prefWidthProperty().bind(vBox.widthProperty());

        // HBOX
        hBox.getChildren().addAll(saveButton, cancelButton);
        hBox.prefWidthProperty().bind(pane.widthProperty());
        hBox.prefHeightProperty().bind(pane.heightProperty());
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);

        // BUTTONS
        saveButton.prefWidthProperty().bind(hBox.widthProperty());
        saveButton.setOnAction(event -> addMember());

        cancelButton.prefWidthProperty().bind(hBox.widthProperty());
        cancelButton.setOnAction(event -> stage.close());
    }

    private static void addMember(){
        String missingTextStyle = "-fx-border-color: red;";
        boolean textMissing = false;
        if (nameField.getText().isEmpty()){
            nameField.setStyle(missingTextStyle);
            fieldRequiredLabel.setVisible(true);
            textMissing = true;
        }
        if (phoneNumField.getText().isEmpty()){
            phoneNumField.setStyle(missingTextStyle);
            fieldRequiredLabel.setVisible(true);
            textMissing = true;
        }

        if (! textMissing){
            Member member = new Member(-1,
                    nameField.getText(),
                    phoneNumField.getText(),
                    emailField.getText());
            DatabaseDriver.addMember(member);
            stage.close();
        }
    }
}
