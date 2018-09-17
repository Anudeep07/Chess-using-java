package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(Colour c) {

        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackRook.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
        }
        else {
            pieceimage = createImageIcon("img/WhiteRook.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }
    }

    @Override
    public ArrayList<Coordinates> possibleMoves(Cell c) {

        return null;
    }
}
