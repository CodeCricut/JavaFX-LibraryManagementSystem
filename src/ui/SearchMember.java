package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DatabaseDriver;
import model.Member;
import util.NumberValidator;

class SearchMember {
    private static Stage stage;
    private static Scene scene;
    private static AnchorPane pane;
    private static VBox vBox;

    private static Label memberInfoLabel;
    private static TextField memberIdField;

    private static HBox buttonHbox;
    private static Label idLabel, nameLabel, phoneNumberLabel, emailLabel;

    private static Button searchButton, cancelButton;

    static void display(){
        searchButton = new Button("Search");
        cancelButton = new Button("Cancel");

        buttonHbox = new HBox();

        idLabel = new Label();
        nameLabel = new Label();
        phoneNumberLabel = new Label();
        emailLabel = new Label();

        memberInfoLabel = new Label();
        memberIdField = new TextField();

        vBox = new VBox();
        pane = new AnchorPane();
        scene = new Scene(pane, 800, 500);
        stage = new Stage();

        // STAGE
        stage.setTitle("Search Member");
        stage.setWidth(500);
        stage.setHeight(270);
        stage.setMinWidth(250);
        stage.setMinHeight(200);
        scene.getStylesheets().add("/stylesheets/BaseStyle.css");

        stage.setScene(scene);
        stage.show();

        // PANE
        pane.getChildren().addAll(vBox);

        // VBOX
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setSpacing(10);
        vBox.prefWidthProperty().bind(pane.widthProperty());
        vBox.prefHeightProperty().bind(pane.heightProperty());
        vBox.getChildren().addAll(memberInfoLabel, memberIdField, buttonHbox,
                idLabel, nameLabel, phoneNumberLabel, emailLabel);

        // LABELS
        memberInfoLabel.setText("Member Information");

        // TEXT FIELDS
        memberIdField.setPromptText("Member ID");
        memberIdField.prefWidthProperty().bind(vBox.widthProperty());

        // HBOX
        buttonHbox.setSpacing(10);
        buttonHbox.setPadding(new Insets(10));
        buttonHbox.getChildren().addAll(searchButton, cancelButton);
        buttonHbox.prefWidthProperty().bind(vBox.widthProperty());

        // BUTTONS
        searchButton.prefWidthProperty().bind(buttonHbox.widthProperty());
        searchButton.setOnAction(event -> search());

        cancelButton.prefWidthProperty().bind(buttonHbox.widthProperty());
        cancelButton.setOnAction(event -> stage.close());
    }

    private static void search() {
        if (NumberValidator.isInteger(memberIdField.getText())){
            int idNum = Integer.parseInt(memberIdField.getText());
            Member member = DatabaseDriver.searchMember(idNum);
            if (member != null) {
                idLabel.setText("ID: " + member.getId());
                nameLabel.setText("Name: " + member.getName());
                phoneNumberLabel.setText("Phone Number: " + member.getPhoneNum());
                emailLabel.setText("Email: " + member.getEmail());
            }
            else {
                memberIdField.setStyle("-fx-border-color: red");
            }
        }
        else{
            memberIdField.setStyle("-fx-border-color: red");
        }
    }
}
