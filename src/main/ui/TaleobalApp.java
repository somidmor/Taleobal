package ui;

import model.Author;
import model.Cell;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// represent Taleobal application with an author
public class TaleobalApp extends JFrame {
    private static Dimension dimension = new Dimension(600, 800);
    private static final String JSON_STORE = "./data/Author.json";
    private Author author;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame main;
    private GraphicalMenu menu;
    private JPanel middle;
    private JTextPane storyLine;

    // EFFECTS: runs the Taleobal application
    public TaleobalApp() {
        this.runApp();
    }


    /*
     * MODIFIES: this
     * EFFECTS: display the first steps of the application
     * it gets name as an input from user at it set the author name
     */
    private void runApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.author = new Author("omid");
        setUpMainWindow();
    }

    /*
     * EFFECTS: display the content of all the cells from beginning to the current cell
     */
    private String getStory() {
        ArrayList<Cell> preCells = new ArrayList<>();
        Cell preCell = author.getCurrentCell().getPreCell();
        StringBuilder result = new StringBuilder("");
        result.append("(Number Of Likes: " + author.getCurrentCell().getNumberOfLikes() + ") ");
        while (preCell != null) {
            preCells.add(preCell);
            preCell = preCell.getPreCell();
        }
        int lasIndex = preCells.size() - 1;
        for (int i = lasIndex; i > -1; i--) {
            result.append(preCells.get(i).getContent()).append(" ");
        }
        return result.append(author.getCurrentCell().getContent()).toString();
    }

    //Source of this function is from UBC CPSC210 jsonExample repository
    //link of the repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the Author to file
    private void saveAuthor() {
        try {
            jsonWriter.open();
            jsonWriter.write(author);
            jsonWriter.close();
            System.out.println("Saved " + author.getName() + "'s tale to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        System.out.println("Save was successful");
    }

    //Source of this function is from UBC CPSC210 jsonExample repository
    //link of the repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads Author from file
    private void loadAuthor() {
        try {
            author = jsonReader.read();
            System.out.println("Loaded " + author.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        System.out.println("Load was successful");
    }

    /*
     * MODIFIES: this
     * EFFECTS: set up the main window with three part:
     * storyline at top, tale space at middle and buttons at bottom
     */
    private void setUpMainWindow() {
        main = new JFrame("Taleobal");
        main.setSize(dimension);
        main.setVisible(true);
        main.setLayout(new GridLayout(3, 0));

        middle = new JPanel();
        middle.setVisible(true);
        middle.setLayout(new GridLayout(0, 3));

        menu = new GraphicalMenu(dimension);
        storyLine = new JTextPane();


        StyledDocument documentStyle = storyLine.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_LEFT);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        storyLine.setEditable(false);
        storyLine.setSize(dimension.width, dimension.height / 4);

        main.add(storyLine);
        main.add(middle);
        main.add(menu);
        setAllActions();
        updateMainWindow();
    }

    /*
     * EFFECTS: update the main window with new values
     */
    private void updateMainWindow() {
        updateStoryLine();
        updateMiddle();
    }

    /*
     * EFFECTS: update the story line part of the main window with new values
     */
    private void updateStoryLine() {
        storyLine.setText(getStory());
    }

    /*
     * MODIFIES: this
     * EFFECTS: remove all the cells from the middle part and load the window with proper cells
     */
    private void updateMiddle() {
        middle.removeAll();
        for (Cell cell : author.getCurrentCell().getNextCellsList()) {
            GraphicalCell graphicalCell = new GraphicalCell(cell);
            graphicalCell.setBackground(randomColor());
            middle.add(graphicalCell);
            setActionCellSelectButton(graphicalCell);
        }
        middle.revalidate();
    }

    /*
     * EFFECTS: it generates and return a random bright color with rgb greater than 150
     */
    public static Color randomColor() {
        int red = (int) (Math.random() * 100) + 150;
        int green = (int) (Math.random() * 100) + 150;
        int blue = (int) (Math.random() * 100) + 150;
        return new Color(red, green, blue);
    }


    /*
     * EFFECTS: set up all the action functions for the buttons
     */
    public void setAllActions() {
        setActionLikeButton();
        setActionMostLikeButton();
        setActionGoToRootButton();
        setActionSaveButton();
        setActionLoadButton();
        setActionQuitButton();
        setActionAddButton();
        setActionEditButton();
    }


    /*
     * MODIFIES: this
     * EFFECTS: increase number of likes of the current cell by one when the button is clicked
     */
    public void setActionLikeButton() {
        menu.getLikeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.getCurrentCell().like();
                updateMainWindow();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: set the current cell to the cell with most like when button is clicked
     */
    public void setActionMostLikeButton() {
        menu.getSeeMostLikedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.setCurrentCell(author.getMostLikedCell());
                updateMainWindow();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: change the current cell to root and goes to the beginning of the story when button is clicked
     */
    public void setActionGoToRootButton() {
        menu.getGoToRootButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.setCurrentCell(author.getRootCell());
                updateMainWindow();
            }
        });
    }

    /*
     * EFFECTS: save the data of the application in a new file when button is clicked
     */
    public void setActionSaveButton() {
        menu.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAuthor();
                updateMainWindow();
                JOptionPane.showMessageDialog(main, "Save was Successful");
            }
        });
    }

    /*
     * EFFECTS: load the story from the file when button is clicked
     */
    public void setActionLoadButton() {
        menu.getLoadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAuthor();
                updateMainWindow();
                JOptionPane.showMessageDialog(main, "Load was Successful");
            }
        });
    }

    /*
     * EFFECTS: stops everything and closes the program when button is clicked
     */
    public void setActionQuitButton() {
        menu.getQuitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    /*
     * MODIFIES: this
     * EFFECTS: set the current cell to the selected cell when button is clicked
     */
    public void setActionCellSelectButton(GraphicalCell graphicalCell) {
        graphicalCell.getSelectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.setCurrentCell(graphicalCell.getCell());
                updateMainWindow();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: opens a popup window and asks user to insert a new story for the new cell when button is clicked
     */
    public void setActionAddButton() {
        menu.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String story = getStory();
                Object result = JOptionPane.showInputDialog(main, story + ":");
                author.addCell(result.toString());
                updateMainWindow();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: open a popup window and asks user to edit the current when button is clicked
     */
    public void setActionEditButton() {
        menu.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tale = author.getCurrentCell().getContent();
                String message = "Old: " + tale;
                message += "\nNew:";
                Object result = JOptionPane.showInputDialog(main, message);
                author.getCurrentCell().setContent(result.toString());
                updateMainWindow();
            }
        });
    }
}
