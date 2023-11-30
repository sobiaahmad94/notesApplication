package com.sobianotes;

import database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// SQL related stuff
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Hello world!
 *
 */
public class NotesApp extends JFrame {

    // text pane
    private JTextPane textPane;

    // buttons
    private JButton saveButton;
    private JButton saveToFileButton;
    private JButton loadFromFileButton;
    private JButton deleteButton;

    // my classes - private access modifiers
    private Note currentNote;
    private NoteFileManager noteFileManager;
    private final NodeModifier noteModifier;

    // instances of my Note, NoteFileManager and DatabaseManager classes




    
}
