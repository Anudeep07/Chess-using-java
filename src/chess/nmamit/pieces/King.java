package chess.nmamit.pieces;

import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

public class King extends Piece {
    public King(Colour c) {

        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackKing.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
        }
        else {
            pieceimage = createImageIcon("img/WhiteKing.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }
    }

    @Override
    Coordinates[] possibleMoves(Coordinates c) {
        return new Coordinates[0];
    }
}
