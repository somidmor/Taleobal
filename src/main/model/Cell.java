package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.UUID;

/* Represents a cell having an author name, list of the next cells, the cell before,
 * total number of likes for the cell, ID and the content in the cell
 */
public class Cell implements Writable {
    private String cellID;
    private int numberOfLikes;
    private String author;
    private ArrayList<Cell> nextCellsList;
    private String content;
    private Cell preCell;
    private String preCellID;

    /*
     * REQUIRES: author and content have a non-zero length
     * EFFECTS: author on cell is set to author and the content is set to content
     * creates an empty list for the next cells and set the number of likes to zero
     * assign a unique ID to the cell
     */
    public Cell(String author, String content) {
        this.cellID = UUID.randomUUID().toString();
        this.numberOfLikes = 0;
        this.author = author;
        this.nextCellsList = new ArrayList<>();
        this.content = content;
    }

    /*
     * REQUIRES: author and content have a non-zero length
     * EFFECTS: author on cell is set to author and the content is set to content
     * creates an empty list for the next cells and set the number of likes to zero
     * it sets the preCell to the cell it gets in inputs.
     * it sets the next cell of the preCell to current cell
     */
    public Cell(String author, String content, Cell preCell) {
        this(author, content);
        this.preCell = preCell;
        this.preCell.getNextCellsList().add(this);
    }

    /*
     * REQUIRES: author, PreCellID, CellID, content and number of likes.
     * EFFECTS: author on cell is set to author and the content is set to content
     * preCellID of the cell is set to preCellID and cellID is set to cellID
     * number of likes of the cell is also set to numberOfLikes
     * creates an empty list for the next cells and set the number of likes to zero
     */
    public Cell(String author, String preCellID, String cellID, String content, int numberOfLikes) {
        this.author = author;
        this.preCellID = preCellID;
        this.cellID = cellID;
        this.content = content;
        this.numberOfLikes = numberOfLikes;
        this.nextCellsList = new ArrayList<>();
    }


    /*
     * MODIFIES: this
     * EFFECTS: increase the number of likes of the cell by 1
     */
    public void like() {
        this.numberOfLikes++;
    }

    //Source of this function is from UBC CPSC210 jsonExample repository
    //link of the repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", cellID);
        json.put("content", content);
        json.put("likes", numberOfLikes);
        if (preCell == null) {
            json.put("preCellID", "0");
        } else {
            json.put("preCellID", preCell.cellID);
        }
        return json;
    }

    //getters and setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setPreCell(Cell preCell) {
        this.preCell = preCell;
    }


    public void setCellID(String cellID) {
        this.cellID = cellID;
    }

    public String getPreCellID() {
        return preCellID;
    }

    public ArrayList<Cell> getNextCellsList() {
        return nextCellsList;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Cell getPreCell() {
        return preCell;
    }

    public String getCellID() {
        return cellID;
    }
}
