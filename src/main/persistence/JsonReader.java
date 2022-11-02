//Source of this file is from UBC CPSC210 jsonExample repository
//link of the repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Author;


import model.Cell;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Tale from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads author from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Author read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAuthor(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses author from JSON object and returns it
    private Author parseAuthor(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Author author = new Author(name);
        addCells(author, jsonObject);
        return author;
    }

    // MODIFIES: author
    // EFFECTS: parses Cells from JSON object and adds them to author
    private void addCells(Author author, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cells");
        for (Object json : jsonArray) {
            JSONObject nextCell = (JSONObject) json;
            addCell(author, nextCell);
        }
        reorganizeCells(author);
    }

    // MODIFIES: author
    // EFFECTS: parses Cell from JSON object and adds it to author
    private void addCell(Author author, JSONObject jsonObject) {
        String preCellID = jsonObject.getString("preCellID");
        String cellID = jsonObject.getString("id");
        String content = jsonObject.getString("content");
        int numberOfLikes = jsonObject.getInt("likes");
        author.addCell(preCellID, cellID, content, numberOfLikes);
    }

    // MODIFIES: author
    // EFFECTS: reorganize all the cells of the author base on their IDs
    // it put the cells in the correct position in hierarchy
    private void reorganizeCells(Author author) {
        for (Cell cell: author.getCells()) {
            String cellID = cell.getCellID();
            for (Cell innerCell: author.getCells()) {
                String preCellID = innerCell.getPreCellID();
                if (cellID.equals(preCellID)) {
                    cell.getNextCellsList().add(innerCell);
                    innerCell.setPreCell(cell);
                }
            }
        }
        author.setCurrentCell(author.getRootCell());
    }


}
