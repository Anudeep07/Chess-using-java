package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;
import chess.nmamit.Pieces;

import java.util.ArrayList;

import static chess.nmamit.Board.isEmpty;
import static chess.nmamit.Board.isKingAttackedIfPieceRemoved;
import static chess.nmamit.Board.sameColourPiece;

public class Pawn extends Piece {

    public Pawn(Colour c) {

        piecename = Pieces.PAWN;
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

        ArrayList<Coordinates> possiblecoordinates = new ArrayList<Coordinates>();
        int cellrow = c.getCellRow();
        int cellcol = c.getCellCol();

        if (piececolour == Colour.WHITE)
        {
            if (isEmpty(cellrow-1,cellcol)) {

                if(cellrow ==6 && isEmpty(cellrow-2,cellcol))
                    possiblecoordinates.add(new Coordinates(cellrow-2,cellcol));

                possiblecoordinates.add(new Coordinates(cellrow - 1, cellcol));
            }
            if(cellcol > 0 && !sameColourPiece(c,cellrow-1,cellcol-1) && !isEmpty(cellrow-1,cellcol-1))
                possiblecoordinates.add(new Coordinates(cellrow-1,cellcol-1));

            if(cellcol < 7 && !sameColourPiece(c,cellrow-1,cellcol+1) && !isEmpty(cellrow-1,cellcol+1))
                possiblecoordinates.add(new Coordinates(cellrow-1,cellcol+1));

            //if(cellrow == 1)  pawn promotion
        }

        else
        {
            if (isEmpty(cellrow+1,cellcol)) {

                if(cellrow ==1 && isEmpty(cellrow+2,cellcol) )
                    possiblecoordinates.add(new Coordinates(cellrow+2,cellcol));

                possiblecoordinates.add(new Coordinates(cellrow + 1, cellcol));

            }

            if(cellcol > 0 && !sameColourPiece(c,cellrow+1,cellcol-1) && !isEmpty(cellrow+1,cellcol-1))
                possiblecoordinates.add(new Coordinates(cellrow+1,cellcol-1));

            if(cellcol < 7 && !sameColourPiece(c,cellrow+1,cellcol+1) && !isEmpty(cellrow+1,cellcol+1))
                possiblecoordinates.add(new Coordinates(cellrow+1,cellcol+1));

        }

        ArrayList<Coordinates> pinnedcoordinates = isKingAttackedIfPieceRemoved(c);

        if(pinnedcoordinates != null) {

            return intersection(pinnedcoordinates,possiblecoordinates);
            //fill this
        }

        return possiblecoordinates;
    }

}
