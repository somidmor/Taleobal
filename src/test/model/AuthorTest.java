package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {
    private Author testAuthor;

    @BeforeEach
    void runBefore() {
        testAuthor = new Author("Omid");
        EventLog.getInstance().clear();
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

    @Test
    void testCreateAuthorEventLog() {
        testAuthor = new Author("Omid");

        List<Event> events = new ArrayList<Event>();

        EventLog eventLog = EventLog.getInstance();
        for (Event next : eventLog) {
            events.add(next);
        }

        String logString = "New author Omid has been created.";
        assertEquals(logString, events.get(1).getDescription());
    }

    @Test
    void testAddCellEventLog()  {
        testAuthor.addCell("test case 1");
        Cell testCell = testAuthor.getCurrentCell();
        String cellID = testCell.getCellID();
        String name = testAuthor.getName();

        testAuthor.addCell("0", "1", "test case 2", 5);

        List<Event> events = new ArrayList<Event>();

        EventLog eventLog = EventLog.getInstance();
        for (Event next : eventLog) {
            events.add(next);
        }

        String logString1 = "Cell ID: " + cellID + " has been added to author " + name;
        String logString2 = "Cell ID: 1 has been added to author " + name;
        assertEquals(logString1, events.get(1).getDescription());
        assertEquals(logString2, events.get(2).getDescription());
    }
}
