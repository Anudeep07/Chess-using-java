package testcases;

import chess.nmamit.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static chess.nmamit.Board.cells;

public class KingTest {

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

        for(int i=0 ; i<3 ; i++)
            for(int j=0 ; j<3 ; j++)
                if(i==1 && j==1)
                    continue;
                else
                    expected.add(new Coordinates(2+i, 2+j));

        cell.setPiece(Pieces.KING, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCorner() {
        cell = board.cells[0][0];

        expected.add(new Coordinates(0, 1));
        expected.add(new Coordinates(1, 0));
        expected.add(new Coordinates(1, 1));

        cell.setPiece(Pieces.KING, Colour.WHITE);
        ArrayList<Coordinates> actual = cell.cellpiece.possibleMoves(cell);
        Assert.assertEquals(expected, actual);
    }

}
