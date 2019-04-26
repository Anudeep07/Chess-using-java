package testcases;

import chess.nmamit.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class QueenTest {
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

        //diagonal left up
        expected.add(new Coordinates(2, 2));
        expected.add(new Coordinates(1, 1));

        //diagonal left down
        expected.add(new Coordinates(4, 2));
        expected.add(new Coordinates(5, 1));

        //diagonal right up
        expected.add(new Coordinates(2, 4));
        expected.add(new Coordinates(1, 5));

        //diagonal right down
        expected.add(new Coordinates(4, 4));
        expected.add(new Coordinates(5, 5));


        cell.setPiece(Pieces.QUEEN, Colour.WHITE);
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

        //diagonal right up
        expected.add(new Coordinates(1, 1));

        //diagonal right down
        for(int i=1 ; i<=3 ; i++)
            expected.add(new Coordinates(2+i, 0+i));

        cell.setPiece(Pieces.QUEEN, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }
}
