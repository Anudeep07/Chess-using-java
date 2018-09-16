package chess.nmamit.pieces;

import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

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
    Coordinates[] possibleMoves(Coordinates c) {
        return new Coordinates[0];
    }
}
