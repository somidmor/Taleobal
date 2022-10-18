package ui;

import model.Author;
import model.Cell;

import java.util.ArrayList;
import java.util.Scanner;

// represent Taleobal application with an author
public class TaleobalApp {
    private Author author;
    private Scanner input;

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
     * REQUIRES: command has a non-zero length
     * MODIFIES: this
     * EFFECTS: process the command it gets
     */
    private void processCommand(String command) {
        if (command.equals("a")) {
            System.out.println("\n\n\n\n\n\n\n\n");
            System.out.println("Continue your Tale!");
            showPreCells();
            System.out.println(author.getCurrentCell().getContent());
            String newTale = input.next();
            author.addCell(newTale);
        } else if (command.equals("r")) {
            author.setCurrentCell(author.getRootCell());
        } else if (command.equals("l")) {
            author.getCurrentCell().like();
        } else if (command.equals("s")) {
            author.setCurrentCell(author.getMostLikedCell());
        } else if (command.equals("e") && author.getCurrentCell().getPreCell() != null) {
            System.out.println("pre: " + author.getCurrentCell().getContent());
            System.out.print("new: ");
            String newContent = input.next();
            author.getCurrentCell().setContent(newContent);
        } else {
            int cellIndex = Integer.parseInt(command) - 1;
            Cell currentCell = author.getCurrentCell().getNextCellsList().get(cellIndex);
            author.setCurrentCell(currentCell);
        }
    }
}
