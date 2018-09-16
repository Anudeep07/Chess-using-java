package chess.nmamit.pieces;

import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

public class Bishop extends Piece {
    public Bishop(Colour c) {

        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackBishop.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
        }
        else {
            pieceimage = createImageIcon("img/WhiteBishop.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }
    }

    @Override
    Coordinates[] possibleMoves(Coordinates c) {
        return new Coordinates[0];
    }
}
