package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Colour c) {

        piecename = "pawn";
        //black pawn
        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackPawn.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
        }
        else {  //white pawn
            pieceimage = createImageIcon("img/WhitePawn.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }

    }

    @Override
    public ArrayList<Coordinates> possibleMoves(Cell c) {

        ArrayList<Coordinates> x = new ArrayList<Coordinates>();


        return x;
    }

}
