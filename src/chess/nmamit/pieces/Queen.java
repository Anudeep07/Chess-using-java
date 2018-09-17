package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(Colour c) {

        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackQueen.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
        }
        else {
            pieceimage = createImageIcon("img/WhiteQueen.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }
    }

    @Override
    public ArrayList<Coordinates> possibleMoves(Cell c) {

        return null;
    }
}
