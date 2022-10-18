package model;

import java.util.ArrayList;

// Represents an author having a name, list of cells, current cell and a rootCell
public class Author {
    private String name;
    private ArrayList<Cell> cells;
    private Cell currentCell;
    private Cell rootCell;


    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: name on account is set to name; a new arraylist
     * for cells is created; the root cell with the sentence: Once upon a Time
     * is created and set to rootCell.
     * rootCell is added to the list of cells and current cell
     */
    public Author(String name) {
        this.name = name;
        this.cells = new ArrayList<Cell>();
        this.rootCell = new Cell(this.name, "Once Upon A Time...");
        this.cells.add(rootCell);
        this.currentCell = rootCell;
    }

    /*
     * REQUIRES: content has a non-zero length
     * MODIFIES: this
     * EFFECTS: new cell with the content is created
     * the new cell is added to the cell lists and set to currentCell
     */
    public void addCell(String content) {
        Cell newCell = new Cell(this.name, content, currentCell);
        this.currentCell = newCell;
        this.cells.add(newCell);
    }


    /*
     * EFFECTS: compares the number of likes of the cells in the cellsList
     * and return the most liked cell of the author
     */
    public Cell getMostLikedCell() {
        Cell result = this.rootCell;
        for (Cell cell : cells) {
            if (cell.getNumberOfLikes() > result.getNumberOfLikes()) {
                result = cell;
            }
        }
        return result;
    }


    //getters and setters

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public String getName() {
        return this.name;
    }

    public Cell getRootCell() {
        return rootCell;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }
}
