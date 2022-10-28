//Source of this file is from UBC CPSC210 jsonExample repository
//link of the repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Cell;
import model.Author;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Author author = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAuthor() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAuthor.json");
        try {
            Author author = reader.read();
            assertEquals("Test Case", author.getName());
            assertEquals(1, author.getCells().size());
            assertEquals("Once Upon A Time...", author.getCells().get(0).getContent());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNormalAuthor() {
        JsonReader reader = new JsonReader("./data/testReaderNormalAuthor.json");
        try {
            Author author = reader.read();
            assertEquals("Test Case", author.getName());
            List<Cell> cells = author.getCells();
            assertEquals(3, cells.size());
            assertEquals("Once Upon A Time...", cells.get(0).getContent());
            assertEquals("Content 1", cells.get(1).getContent());
            assertEquals("Content 2", cells.get(2).getContent());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}