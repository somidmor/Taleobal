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

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Author author = new Author("Test Case");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAuthor() {
        try {
            Author author = new Author("Test Case");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAuthor.json");
            writer.open();
            writer.write(author);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAuthor.json");
            author = reader.read();
            assertEquals("Test Case", author.getName());
            assertEquals(1, author.getCells().size());
            assertEquals("Once Upon A Time...", author.getCells().get(0).getContent());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalAuthor() {
        try {
            Author author = new Author("Test Case");
            author.addCell("Content 1");
            author.addCell("Content 2");
            JsonWriter writer = new JsonWriter("./data/testWriterNormalAuthor.json");
            writer.open();
            writer.write(author);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalAuthor.json");
            author = reader.read();
            assertEquals("Test Case", author.getName());
            List<Cell> cells = author.getCells();
            assertEquals(3, cells.size());
            assertEquals("Once Upon A Time...", cells.get(0).getContent());
            assertEquals("Content 1", cells.get(1).getContent());
            assertEquals("Content 2", cells.get(2).getContent());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}