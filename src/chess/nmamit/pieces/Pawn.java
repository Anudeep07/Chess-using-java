package chess.nmamit.pieces;

import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

public class Pawn extends Piece {

    public Pawn(Colour c) {

        //black pawn
        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackPawn.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
            possiblecoordinates = new Coordinates[4];   //1 up, 2 up, left diagonal, right diagonal
        }
        else {  //white pawn
            pieceimage = createImageIcon("img/WhitePawn.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }

    }

    @Override
    Coordinates[] possibleMoves(Coordinates c) {

        Coordinates[] x = new Coordinates[2];
        return x;
    }

}
