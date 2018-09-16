package chess.nmamit.pieces;

import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

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
    Coordinates[] possibleMoves(Coordinates c) {
        return new Coordinates[0];
    }
}
