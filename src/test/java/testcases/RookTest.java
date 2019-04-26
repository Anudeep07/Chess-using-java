package testcases;

import chess.nmamit.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RookTest {
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

        //left
        for(int i=1 ; i<=3 ; i++)
            expected.add(new Coordinates(3, 3-i));

        //right
        for(int i=1 ; i<=4 ; i++)
            expected.add(new Coordinates(3, 3+i));

        //bottom
        for(int i=1 ; i<=2 ; i++)
            expected.add(new Coordinates(3+i, 3));

        //up
        for(int i=1 ; i<=2 ; i++)
            expected.add(new Coordinates(3-i, 3));

        cell.setPiece(Pieces.ROOK, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCorner() {
        cell = board.cells[2][0];

        //right
        for(int i=1 ; i<=7 ; i++)
            expected.add(new Coordinates(2, 0+i));

        //bottom
        for(int i=1 ; i<=3 ; i++)
            expected.add(new Coordinates(2+i, 0));

        //up
        expected.add(new Coordinates(1, 0));

        cell.setPiece(Pieces.ROOK, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }
}
