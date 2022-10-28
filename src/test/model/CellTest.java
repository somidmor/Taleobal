package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private Cell testCell;

    @BeforeEach
    void runBefore() {
        testCell = new Cell("omid", "test case 1");

    }

    @Test
    void testConstructor() {
        assertEquals(0, testCell.getNumberOfLikes());
        assertEquals("omid", testCell.getAuthor());
        assertEquals(0, testCell.getNextCellsList().size());
        assertNull(testCell.getPreCell());
        assertEquals("test case 1", testCell.getContent());

        Cell testCell2 = new Cell("omid", "test case 2", testCell);
        assertEquals(1, testCell.getNextCellsList().size());
        assertEquals(testCell2, testCell.getNextCellsList().get(0));

        assertEquals(0, testCell2.getNumberOfLikes());
        assertEquals("omid", testCell2.getAuthor());
        assertEquals(0, testCell2.getNextCellsList().size());
        assertEquals(testCell, testCell2.getPreCell());
        assertEquals("test case 2", testCell2.getContent());

    }

    @Test
    void testLike () {
        assertEquals(0, testCell.getNumberOfLikes());
        testCell.like();
        assertEquals(1, testCell.getNumberOfLikes());
        testCell.like();
        assertEquals(2, testCell.getNumberOfLikes());
    }

    @Test
    void testSetContent() {
        assertEquals("test case 1", testCell.getContent());
        testCell.setContent("test case 2");
        assertEquals("test case 2", testCell.getContent());
    }

}
