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
     * EFFECTS: initials the setup of the application and displays the menu
     * and process the user input
     */
    private void runApp() {
        setUp();
//        String command;
//        boolean shouldRun = true;
//        while (shouldRun) {
//            showCellMenu();
//            command = input.next();
//            command = command.toLowerCase();
//            if (command.equals("q")) {
//                shouldRun = false;
//                System.out.println("See you soon " + author.getName() + " ;)");
//            } else {
//                try {
//                    processCommand(command);
//                } catch (NumberFormatException e) {
//                    System.out.println("\n\n\n");
//                    System.out.println("Opps! Did you select a correct option? Lets Try again");
//                } catch (IndexOutOfBoundsException e) {
//                    System.out.println("\n\n\n");
//                    System.out.println("Opps! Did you select a correct Number? Lets Try again");
//                }
//            }
//        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: display the first steps of the application
     * it gets name as an input from user at it set the author name
     */
    private void setUp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//        System.out.println("Hello Human");
//        System.out.println("Here You Can Create Your New World.");
//        System.out.println("Lets begin with your name.");
//        System.out.print("Who Are U?: ");
//        String name = input.next();
        this.author = new Author("omid");
//        System.out.println("Well Done!, Welcome To Your World " + name + ".");
        setUpMainWindow();
    }

    /*
     * EFFECTS: display the menu and all the available options for the user
     */
    private void showCellMenu() {
        Cell currentCell = author.getCurrentCell();
        System.out.println("**************************************");
        System.out.println(getStory());
        //System.out.println(currentCell.getContent());
        System.out.println("(Likes: " + author.getCurrentCell().getNumberOfLikes() + ")");
        System.out.println("**************************************");

        int index = 1;
        for (Cell cell : currentCell.getNextCellsList()) {
            System.out.println("\t" + index + ". " + cell.getContent());
            index += 1;
        }
        if (author.getCurrentCell().getPreCell() != null) {
            System.out.print("\te: edit this tale\t\t\t\t\t");
        }
        System.out.println("\ta: Add new tale");
        System.out.print("\tk: Like this tale\t\t\t\t\t");
        System.out.println("\tm: See the most liked tale");
        System.out.print("\ts: Save current tale to file\t\t");
        System.out.println("\tl: Load tale from the file");
        System.out.println("\tr: Go to the root (Once Upon A Time...)");
        System.out.println("\tq: Quit the program");
        System.out.print("Select one of the options above: ");
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

    /*
     * MODIFIES: this
     * EFFECTS: the chosen cell by the user will set to the current cell
     */
//    private void chooseCell(String command) {
//        int cellIndex = Integer.parseInt(command) - 1;
//        Cell currentCell = author.getCurrentCell().getNextCellsList().get(cellIndex);
//        author.setCurrentCell(currentCell);
//    }

    /*
     * REQUIRES: command has a non-zero length
     * MODIFIES: this
     * EFFECTS: process the command it gets
     */
//    private void processCommand(String command) {
//        if (command.equals("a")) {
//            System.out.println("\n\n\n\n\n\n\n\n\nContinue your Tale!");
//            System.out.println(getStory());
//            //System.out.println(author.getCurrentCell().getContent());
//            String newTale = input.next();
//            author.addCell(newTale);
//        } else if (command.equals("r")) {
//            author.setCurrentCell(author.getRootCell());
//        } else if (command.equals("k")) {
//            author.getCurrentCell().like();
//        } else if (command.equals("m")) {
//            author.setCurrentCell(author.getMostLikedCell());
//        } else if (command.equals("e") && author.getCurrentCell().getPreCell() != null) {
//            System.out.print("old: " + author.getCurrentCell().getContent() + "\nnew: ");
//            String newContent = input.next();
//            author.getCurrentCell().setContent(newContent);
//        } else if (command.equals("s")) {
//            saveAuthor();
//        } else if (command.equals("l")) {
//            loadAuthor();
//        } else {
//            chooseCell(command);
//        }
//        updateMainWindow();
//    }

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

    private void updateMainWindow() {
        updateStoryLine();
        updateMiddle();
    }

    private void updateStoryLine() {
        storyLine.setText(getStory());
    }

    private void updateMiddle() {
        middle.removeAll();
        for (Cell cell : author.getCurrentCell().getNextCellsList()) {
            GraphicalCell graphicalCell = new GraphicalCell(cell);
            middle.add(graphicalCell);
            setActionCellSelectButton(graphicalCell);
        }
        middle.revalidate();
    }


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

    public void setActionLikeButton() {
        menu.getLikeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.getCurrentCell().like();
                updateMainWindow();
            }
        });
    }

    public void setActionMostLikeButton() {
        menu.getSeeMostLikedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.setCurrentCell(author.getMostLikedCell());
                updateMainWindow();
            }
        });
    }

    public void setActionGoToRootButton() {
        menu.getGoToRootButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.setCurrentCell(author.getRootCell());
                updateMainWindow();
            }
        });
    }

    public void setActionSaveButton() {
        menu.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAuthor();
                updateMainWindow();
            }
        });
    }

    public void setActionLoadButton() {
        menu.getLoadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAuthor();
                updateMainWindow();
            }
        });
    }

    public void setActionQuitButton() {
        menu.getQuitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void setActionCellSelectButton(GraphicalCell graphicalCell) {
        graphicalCell.getSelectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                author.setCurrentCell(graphicalCell.getCell());
                updateMainWindow();
            }
        });
    }

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
