package model;

import ui.AlertBox;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseDriver {
    private static Connection connection;
    private static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "Database");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBook(Book book) {
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL add_book(?, ?, ?)}"
            );
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());

            stmt.execute();
        } catch (SQLException e) {
            AlertBox.display("Add Book Error", "An error occurred when trying to add a new book.");
            e.printStackTrace();
        }
    }

    public static void addMember(Member member) {
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL add_member(?, ?, ?)}"
            );
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getPhoneNum());
            stmt.setString(3, member.getEmail());

            stmt.execute();
        } catch (SQLException e) {
            AlertBox.display("Add Member Error", "An error occurred when trying to add a new member.");
        }
    }

    public static Member searchMember(int id) {
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL search_member(?)}"
            );
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();
            return new Member(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"));
        } catch (SQLException e) {
            AlertBox.display("Search Member Error", "An error occurred when trying to search for a member.");
            return null;
        }
    }

    public static ArrayList<Book> getAllBooks() {
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL get_all_books()}"
            );
            ResultSet resultSet = stmt.executeQuery();

            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getBoolean("isAvail"),
                        resultSet.getInt("member_id"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            AlertBox.display("Get All Books Error", "An error occurred when trying get all books.");
            return null;
        }
    }

    public static ArrayList<Member> getAllMembers() {
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL get_all_members()}"
            );
            ResultSet resultSet = stmt.executeQuery();

            ArrayList<Member> members = new ArrayList<>();
            while (resultSet.next()) {
                Member member = new Member(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"));
                members.add(member);
            }
            return members;
        } catch (SQLException e) {
            AlertBox.display("Get All Members Error", "An error occurred when trying to get all members.");
            return null;
        }
    }

    public static ArrayList<Book> getIssuedBooks() {
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL get_issued_books()}"
            );
            ResultSet resultSet = stmt.executeQuery();

            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getBoolean("isAvail"),
                        resultSet.getInt("member_id"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            AlertBox.display("Get Issued Books Error", "An error occurred when trying to get issued books.");
            return null;
        }
    }

    public static void issueBook(int bookID, int memberID) {
        try {
            CallableStatement getBookByID = connection.prepareCall(
                    "{CALL get_book_by_id(?)}"
            );
            getBookByID.setInt(1, bookID);
            ResultSet bookByID = getBookByID.executeQuery();
            bookByID.next();

            if (! bookByID.getBoolean("isAvail")){
                AlertBox.display("Error Issuing Book", "The book could not" +
                        " be issued because it is already checked out.");
            }
            else{
                CallableStatement issueBook = connection.prepareCall(
                        "{CALL issue_book(?, ?)}"
                );
                issueBook.setInt(1, bookID);
                issueBook.setInt(2, memberID);
                issueBook.execute();
            }
        } catch (SQLException e) {
            AlertBox.display("Issue Book Error", "An error occurred when trying to issue the book.");
        }
    }

    public static void returnBook(int bookID) {
        try {
            CallableStatement getBookByID = connection.prepareCall(
                    "{CALL get_book_by_id(?)}"
            );
            getBookByID.setInt(1, bookID);
            ResultSet bookByID = getBookByID.executeQuery();
            bookByID.next();

            if (bookByID.getBoolean("isAvail")){
                AlertBox.display("Error Returning Book", "The book could not" +
                        " be returned because it is not checked out.");
            }
            else {
                CallableStatement stmt = connection.prepareCall(
                        "{CALL return_book(?)}"
                );
                stmt.setInt(1, bookID);
                stmt.execute();
            }
        } catch (SQLException e) {
            AlertBox.display("Issue Returning Book", "An error occurred when trying to return the book.");
        }
    }

    public static Book getBookByID(int bookID) {
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL get_book_by_id()}"
            );
            stmt.setInt(1, bookID);

            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return new Book(resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("publisher"),
                    resultSet.getBoolean("isAvail"),
                    resultSet.getInt("member_id"));
        } catch (SQLException e) {
            AlertBox.display("Get Book by ID Error", "An error occurred when trying to get book by ID.");
            return null;
        }
    }
}
