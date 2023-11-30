package com.sobianotes;

// classes boilerplate
//import com.sobianotes.Note;
//import com.sobianotes.NoteFileManager;


import com.sobianotes.NoteFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NoteFileManagerTest {

    private NoteFileManager noteFileManager;
    private String testFilePath;
    private Note note;

    @BeforeEach
    void setUp() {
        noteFileManager = new NoteFileManager();
        testFilePath = "testfile.txt";
        note = new Note();
        note.setContent("Test content");
    }

    // testing saving note to file method
    @Test
    void testSaveNoteToFile() {
        try {
            noteFileManager.saveNoteToFile(note, testFilePath);
            assertTrue(Files.exists(Paths.get(testFilePath)));
        } catch (IOException e) {
            fail("Exception thrown " + e.getMessage());
        }
    }

    // testing saving the content to the file
    @Test
    void testSaveNoteContentToFile() {
        try {
            noteFileManager.saveNoteToFile(note, testFilePath);
            List<String> lines = Files.readAllLines(Paths.get(testFilePath));
            String fileContent = String.join(System.lineSeparator(), lines);
            assertEquals(note.getContent(), fileContent);
        } catch (IOException e) {
            fail("Exception thrown " + e.getMessage());
        }
    }

    // testing loading file from finder view
    @Test
    void testLoadNoteFromFile() {
        try {
            Files.write(Paths.get(testFilePath), note.getContent().getBytes());
            String loadedContent = noteFileManager.loadNoteFromFile(testFilePath);
            assertEquals(note.getContent(), loadedContent);
        } catch (IOException e) {
            fail("Exception thrown " + e.getMessage());
        }
    }
}
