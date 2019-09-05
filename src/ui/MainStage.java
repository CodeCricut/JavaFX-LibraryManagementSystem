package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DatabaseDriver;
import util.NumberValidator;

class MainStage {

    private static Stage stage;
    private static Scene scene;
    private static BorderPane borderPane;
    private static TabPane tabPane;
    private static Tab issueBookTab;
    private static Tab returnBookTab;
    private static VBox vBox;
    private static Button addBookButton, addMemberButton,
            searchMemberButton, allBooksButton,
            allMembersButton, issuedBooksButton;

    static void display(){
        addBookButton = new Button();
        addMemberButton = new Button();
        searchMemberButton = new Button();
        allBooksButton = new Button();
        allMembersButton = new Button();
        issuedBooksButton = new Button();

        vBox = new VBox();

        issueBookTab = new Tab();
        returnBookTab = new Tab();

        tabPane = new TabPane();

        borderPane = new BorderPane();

        scene = new Scene(borderPane);
        scene.getStylesheets().add("/stylesheets/BaseStyle.css");
        stage = new Stage();

        setupButtonVbox();
        setupTabPane();
        setupBorderPane();
        setupStage();

        stage.show();
    }

    private static void setupStage() {
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.setWidth(1000);

        stage.setMinHeight(200);
        stage.setHeight(800);

        stage.setMaximized(true);
    }

    private static void setupButtonVbox() {
        vBox.setPrefWidth(150);
        vBox.getChildren().addAll(addBookButton, addMemberButton,
                searchMemberButton, allBooksButton,
                allMembersButton, issuedBooksButton);

        addBookButton.setText("Add Book");
        addBookButton.prefWidthProperty().bind(vBox.widthProperty());
        addBookButton.prefHeightProperty().bind(vBox.heightProperty());
        addBookButton.setOnAction(event -> AddBook.display());

        addMemberButton.setText("Add Member");
        addMemberButton.prefWidthProperty().bind(vBox.widthProperty());
        addMemberButton.prefHeightProperty().bind(vBox.heightProperty());
        addMemberButton.setOnAction(event -> AddMember.display());

        searchMemberButton.setText("Search Member");
        searchMemberButton.prefWidthProperty().bind(vBox.widthProperty());
        searchMemberButton.prefHeightProperty().bind(vBox.heightProperty());
        searchMemberButton.setOnAction(event -> SearchMember.display());

        allBooksButton.setText("All Books");
        allBooksButton.prefWidthProperty().bind(vBox.widthProperty());
        allBooksButton.prefHeightProperty().bind(vBox.heightProperty());
        allBooksButton.setOnAction(event -> AllBooks.display());

        allMembersButton.setText("All Members");
        allMembersButton.prefWidthProperty().bind(vBox.widthProperty());
        allMembersButton.prefHeightProperty().bind(vBox.heightProperty());
        allMembersButton.setOnAction(event -> AllMembers.display());

        issuedBooksButton.setText("Issued Books");
        issuedBooksButton.prefWidthProperty().bind(vBox.widthProperty());
        issuedBooksButton.prefHeightProperty().bind(vBox.heightProperty());
        issuedBooksButton.setOnAction(event -> IssuedBooks.display());
    }

    private static void setupTabPane() {
        tabPane.getTabs().addAll(issueBookTab, returnBookTab);

        setupIssueBookTab();
        setupReturnBookTab();
    }

    private static void setupIssueBookTab() {
        issueBookTab.setText("Issue Book");
        issueBookTab.setClosable(false);

        VBox issueBookVbox = new VBox();
        issueBookVbox.setSpacing(10);
        issueBookVbox.setPadding(new Insets(20, 10, 20, 10));
        issueBookTab.setContent(issueBookVbox);

        Label bookInformationLabel = new Label("Book Information");

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("Book ID");

        Label memberInfoLabel = new Label("Member Information");

        TextField memberIdField = new TextField();
        memberIdField.setPromptText("Member ID");

        Button issueBookButton = new Button("Issue Book");

        issueBookButton.setOnAction(event -> {
            if (! NumberValidator.isInteger(bookIdField.getText()) ||
            ! NumberValidator.isInteger(memberIdField.getText())){
                AlertBox.display("Error Issuing Book",
                        "Please enter integers for both IDs.");
            }
            else {
                int bookID = Integer.parseInt(bookIdField.getText());
                int memberID = Integer.parseInt(memberIdField.getText());
                DatabaseDriver.issueBook(bookID, memberID);
            }
        });

        issueBookVbox.getChildren().addAll(bookInformationLabel, bookIdField,
                memberInfoLabel, memberIdField, issueBookButton);
    }

    private static void setupReturnBookTab() {
        returnBookTab.setText("Return Book");
        returnBookTab.setClosable(false);

        VBox returnBookVbox = new VBox();
        returnBookVbox.setSpacing(10);
        returnBookVbox.setPadding(new Insets(20, 10, 20, 10));

        returnBookTab.setContent(returnBookVbox);

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("Book ID");

        Label IdLabel = new Label("ID");
        Label titleLabel = new Label("Title");
        Label authorLabel = new Label("Author");
        Label publisherLabel = new Label("Publisher");
        Label isAvailableLabel = new Label("Availability");
        Label memberID = new Label("Member ID");

        Button returnBookButton = new Button("Return Book");
        returnBookButton.setOnAction(event -> {
            if (! NumberValidator.isInteger(bookIdField.getText())){
                AlertBox.display("Error Issuing Book",
                        "Please enter integers for the book ID.");
            }
            else {
                int bookID = Integer.parseInt(bookIdField.getText());
                DatabaseDriver.returnBook(bookID);
            }
        });

        returnBookVbox.getChildren().addAll(bookIdField, IdLabel, titleLabel,
                authorLabel, publisherLabel,isAvailableLabel, memberID, returnBookButton);
    }

    private static void setupBorderPane() {
        borderPane.setCenter(tabPane);
        borderPane.setRight(vBox);
        borderPane.setPadding(new Insets(20));
    }
}
