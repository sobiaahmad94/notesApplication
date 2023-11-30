package com.sobianotes;

import database.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {

    private Connection connection;

    @BeforeEach
    void setUp() {
        try {
            connection = DatabaseManager.getConnection();

            // refactored - wanted to include a catch here just incase it didn't connect as expected as any tests would fail without it
        } catch (SQLException e) {
            fail("Failed to connect to the database " + e.getMessage());
        }
    }

    // I've used @AfterEach here to make sure the connection is closed down after every test here, just as it may have caused issues otherwise
    @AfterEach
    void tearDown() {
        DatabaseManager.closeConnection(connection);
    }

    // testing that connection isn't null
    @Test
    void testGetConnection() {
        assertNotNull(connection, "Connection should not be null");
    }

    // testing that connection can be closed by no longer being valid connection when I close it
    @Test
    void testCloseConnection() {
        try {
            DatabaseManager.closeConnection(connection);
            assertFalse(connection.isValid(1), "Connection should be closed");
        } catch (SQLException e) {
            fail("Exception thrown " + e.getMessage());
        }
    }

    // testing to see if a note can be inserted
    @Test
    void testExecuteQueryRetrieveInsertedNote() {
        try {
            // Insert a test note
            String content = "Test Note Content";
            DatabaseManager.executeUpdate("INSERT INTO notes (content) VALUES (?)", content);

            // get the inserted note with executeQuery()
            try (ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM notes WHERE content = ?", content)) {
                assertTrue(resultSet.next(), "Failed to retrieve the inserted note");
                assertEquals(content, resultSet.getString("content"), "Incorrect note content");
            }

            // then I just want to delete the test note
            DatabaseManager.executeUpdate("DELETE FROM notes WHERE content = ?", content);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    // testing to retrieve a note that isn't in finder - refactored to include assertFalse
    @Test
    void testExecuteQueryRetrieveNonexistentNote() {
        try {
            // Attempt to retrieve a note that does not exist
            try (ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM notes WHERE content = ?", "NonexistentContent")) {
                assertFalse(resultSet.next(), "Retrieved a nonexistent note");
            }
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    // testing to see if when a note is inserted it checks it's been added okay - deletion of the test note added at the end
    @Test
    void testExecuteUpdate() {
        try {
            String content = "Test Note Content";
            DatabaseManager.executeUpdate("INSERT INTO notes (content) VALUES (?)", content);

            // checking if the note has been added
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM notes WHERE content = ?")) {
                preparedStatement.setString(1, content);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    assertTrue(resultSet.next(), "Failed to retrieve the inserted note");
                    assertEquals(content, resultSet.getString("content"), "Incorrect note content");
                }
            }
            // deleting the test note
            DatabaseManager.executeUpdate("DELETE FROM notes WHERE content = ?", content);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
