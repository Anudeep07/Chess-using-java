package testcases;

import chess.nmamit.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PawnTest {
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

        expected.add(new Coordinates(2, 3));

        cell.setPiece(Pieces.PAWN, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCorner() {
        cell = board.cells[2][0];

        expected.add(new Coordinates(1, 1));

        cell.setPiece(Pieces.PAWN, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testStart() {
        cell = board.cells[6][4];

        expected.add(new Coordinates(4, 4));
        expected.add(new Coordinates(5, 4));

        //cell.setPiece(Pieces.PAWN, Colour.WHITE); since at 6,4 there is already a white pawn
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }
}
