package ui;

import model.Author;
import model.Cell;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// represent Taleobal application with an author
public class TaleobalApp {
    private static final String JSON_STORE = "./data/Author.json";
    private Author author;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

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
        String command;
        boolean shouldRun = true;
        while (shouldRun) {
            showCellMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                shouldRun = false;
                System.out.println("See you soon " + author.getName() + " ;)");
            } else {
                try {
                    processCommand(command);
                } catch (NumberFormatException e) {
                    System.out.println("\n\n\n");
                    System.out.println("Opps! Did you select a correct option? Lets Try again");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("\n\n\n");
                    System.out.println("Opps! Did you select a correct Number? Lets Try again");
                }
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: display the first steps of the application
     * it gets name as an input from user at it set the author name
     */
    private void setUp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Hello Human");
        System.out.println("Here You Can Create Your New World.");
        System.out.println("Lets begin with your name.");
        System.out.print("Who Are U?: ");
        String name = input.next();
        this.author = new Author(name);
        System.out.println("Well Done!, Welcome To Your World " + name + ".");
    }

    /*
     * EFFECTS: display the menu and all the availabe options for the user
     */
    private void showCellMenu() {
        Cell currentCell = author.getCurrentCell();
        System.out.println("**************************************");
        showPreCells();
        System.out.println(currentCell.getContent());
        System.out.println("(Likes: " + author.getCurrentCell().getNumberOfLikes() + ")");
        System.out.println("**************************************");

        int index = 1;
        for (Cell cell : currentCell.getNextCellsList()) {
            System.out.println("\t" + index + ". " + cell.getContent());
            index += 1;
        }
        System.out.println("\ta: Add new tale");
        if (author.getCurrentCell().getPreCell() != null) {
            System.out.println("\te: edit this tale");
        }
        System.out.println("\tl: Like this tale");
        System.out.println("\ts: See the most liked tale");
        System.out.println("\tr: Go to the root (Once Upon A Time...)");
        System.out.println("\ts: Save current tale to file");
        System.out.println("\tl: Load tale from the file");
        System.out.println("\tq: Quit the program");
        System.out.print("Select one of the options above: ");
    }

    /*
     * EFFECTS: display the content of all the cells from beginning to the current cell
     */
    private void showPreCells() {
        ArrayList<Cell> preCells = new ArrayList<>();
        Cell preCell = author.getCurrentCell().getPreCell();
        while (preCell != null) {
            preCells.add(preCell);
            preCell = preCell.getPreCell();
        }
        int lasIndex = preCells.size() - 1;
        for (int i = lasIndex; i > -1; i--) {
            System.out.println(preCells.get(i).getContent());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: the chosen cell by the user will set to the current cell
     */
    private void chooseCell(String command) {
        int cellIndex = Integer.parseInt(command) - 1;
        Cell currentCell = author.getCurrentCell().getNextCellsList().get(cellIndex);
        author.setCurrentCell(currentCell);
    }

    /*
     * REQUIRES: command has a non-zero length
     * MODIFIES: this
     * EFFECTS: process the command it gets
     */
    private void processCommand(String command) {
        if (command.equals("a")) {
            System.out.println("\n\n\n\n\n\n\n\n\nContinue your Tale!");
            showPreCells();
            System.out.println(author.getCurrentCell().getContent());
            String newTale = input.next();
            author.addCell(newTale);
        } else if (command.equals("r")) {
            author.setCurrentCell(author.getRootCell());
        } else if (command.equals("k")) {
            author.getCurrentCell().like();
        } else if (command.equals("m")) {
            author.setCurrentCell(author.getMostLikedCell());
        } else if (command.equals("e") && author.getCurrentCell().getPreCell() != null) {
            System.out.print("pre: " + author.getCurrentCell().getContent() + "\nnew: ");
            String newContent = input.next();
            author.getCurrentCell().setContent(newContent);
        } else if (command.equals("s")) {
            saveWorkRoom();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            chooseCell(command);
        }
    }

    // EFFECTS: saves the Author to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(author);
            jsonWriter.close();
            System.out.println("Saved " + author.getName() + "'s tale to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        System.out.println("Load was successful");
    }

    // MODIFIES: this
    // EFFECTS: loads Author from file
    private void loadWorkRoom() {
        try {
            author = jsonReader.read();
            System.out.println("Loaded " + author.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        System.out.println("Load was successful");
    }
}
