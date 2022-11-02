package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {
    private Author testAuthor;

    @BeforeEach
    void runBefore() {
        testAuthor = new Author("Omid");
    }

    @Test
    void testConstructor() {
        assertEquals("Omid", testAuthor.getName());
        assertEquals(1, testAuthor.getCells().size());
    }

    @Test
    void testAddCell() {
        testAuthor.addCell("test case 1");
        assertEquals(2, testAuthor.getCells().size());
        assertEquals("test case 1", testAuthor.getCells().get(1).getContent());
        assertEquals(testAuthor.getCurrentCell(), testAuthor.getCells().get(1));
        assertEquals(testAuthor.getCurrentCell(), testAuthor.getRootCell().getNextCellsList().get(0));

        testAuthor.addCell("preCellID", "cellID", "test case 2", 0);
        assertEquals(3, testAuthor.getCells().size());
        assertEquals("preCellID", testAuthor.getCells().get(2).getPreCellID());
        assertEquals("cellID", testAuthor.getCells().get(2).getCellID());
        assertEquals("test case 2", testAuthor.getCells().get(2).getContent());
        assertEquals(0, testAuthor.getCells().get(2).getNumberOfLikes());
        assertEquals(testAuthor.getCurrentCell(), testAuthor.getCells().get(2));

    }

    @Test
    void testGetMostLikedCell() {
        testAuthor.addCell("test case 1");
        assertEquals(testAuthor.getRootCell(), testAuthor.getMostLikedCell());
        testAuthor.getCurrentCell().like();
        assertEquals(testAuthor.getCurrentCell(), testAuthor.getMostLikedCell());
        testAuthor.getRootCell().like();
        testAuthor.getRootCell().like();
        assertEquals(testAuthor.getRootCell(), testAuthor.getMostLikedCell());
    }

    @Test
    void testSetCurrentCell() {
        assertEquals(testAuthor.getCurrentCell(), testAuthor.getCells().get(0));
        Cell testCell = new Cell(testAuthor.getName(), "test content");
        testAuthor.getCells().add(testCell);
        testAuthor.setCurrentCell(testCell);
        assertEquals(2, testAuthor.getCells().size());
        assertEquals(testAuthor.getCurrentCell(), testAuthor.getCells().get(1));
    }
}
