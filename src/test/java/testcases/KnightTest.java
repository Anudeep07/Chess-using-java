package testcases;

import chess.nmamit.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class KnightTest {

    static Board board;
    Cell cell;
    ArrayList<Coordinates> expected;

    @BeforeAll
    static void init() {
        board = new Board();
    }

    @BeforeEach
    void initTestCase() {
        expected = new ArrayList<Coordinates>();
    }

    @Test
    public void testCenter() {

        cell = board.cells[3][3];              //placing test piece at 3,3 (d5)

        expected.add(new Coordinates(2, 1));
        expected.add(new Coordinates(2, 5));

        expected.add(new Coordinates(1, 2));
        expected.add(new Coordinates(1, 4));

        expected.add(new Coordinates(4, 1));
        expected.add(new Coordinates(4, 5));

        expected.add(new Coordinates(5, 2));
        expected.add(new Coordinates(5, 4));

        cell.setPiece(Pieces.KNIGHT, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCorner() {
        cell = board.cells[0][0];

        expected.add(new Coordinates(1, 2));
        expected.add(new Coordinates(2, 1));

        cell.setPiece(Pieces.KNIGHT, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }
}
