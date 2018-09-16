package chess.nmamit.pieces;

import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

public class Knight extends Piece {

    public Knight(Colour c) {

        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackKnight.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
        }
        else {
            pieceimage = createImageIcon("img/WhiteKnight.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }
    }

    @Override
    Coordinates[] possibleMoves(Coordinates c) {
        return new Coordinates[0];
    }
}
