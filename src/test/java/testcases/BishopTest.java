package testcases;

import chess.nmamit.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BishopTest {

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
    public void testCenterWhiteSquare() {

        cell = board.cells[3][3];              //placing test piece at 3,3 (d5) which is a white square

        expected.add(new Coordinates(2, 2));
        expected.add(new Coordinates(1, 1));

        expected.add(new Coordinates(4, 2));
        expected.add(new Coordinates(5, 1));

        expected.add(new Coordinates(2, 4));
        expected.add(new Coordinates(1, 5));

        expected.add(new Coordinates(4, 4));
        expected.add(new Coordinates(5, 5));

        cell.setPiece(Pieces.BISHOP, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCenterBlackSquare() {

        cell = board.cells[3][4];              //placing test piece at 3,3 (d5) which is a white square

        expected.add(new Coordinates(2, 3));
        expected.add(new Coordinates(1, 2));

        expected.add(new Coordinates(4, 3));
        expected.add(new Coordinates(5, 2));

        expected.add(new Coordinates(2, 5));
        expected.add(new Coordinates(1, 6));

        expected.add(new Coordinates(4, 5));
        expected.add(new Coordinates(5, 6));

        cell.setPiece(Pieces.BISHOP, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCornerWhiteSquare() {

        cell = board.cells[2][0];

        expected.add(new Coordinates(1, 1));

        for(int i=1 ; i<=3 ; i++)
            expected.add(new Coordinates(2+i, 0+i));

        cell.setPiece(Pieces.BISHOP, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCornerBlackSquare() {

        cell = board.cells[2][7];

        expected.add(new Coordinates(1, 6));

        for(int i=1 ; i<=3 ; i++)
            expected.add(new Coordinates(2+i, 7-i));

        cell.setPiece(Pieces.BISHOP, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }
}
