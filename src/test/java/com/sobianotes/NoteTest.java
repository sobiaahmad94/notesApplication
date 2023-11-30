package com.sobianotes;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    private Note note;

    @BeforeEach
    public void before() {
        note = new Note();
    }

    // testing getting content
    @Test
    public void testGetContent() {
        String expectedContent = "Test content";
        note.setContent(expectedContent);
        String actualContent = note.getContent();
        assertEquals(expectedContent, actualContent);
    }

    // testing setting the content
    @Test
    public void testSetContent() {
        String initialContent = "Initial content";
        note.setContent(initialContent);
        assertEquals(initialContent, note.getContent());
    }

    // testing when content is set to null
    @Test
    public void testSetContentWithNull() {
        note.setContent(null);
        assertNull(note.getContent());
    }
}
