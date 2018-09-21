package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;
import chess.nmamit.Pieces;

import java.util.ArrayList;

import static chess.nmamit.Board.isKingAttackedIfPieceRemoved;
import static chess.nmamit.Board.sameColourPiece;

public class Knight extends Piece {

    public Knight(Colour c) {

        piecename = Pieces.KNIGHT;
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
    public ArrayList<Coordinates> possibleMoves(Cell c) {

        ArrayList<Coordinates> possiblecoordinates = new ArrayList<Coordinates>();
        int cellrow = c.getCellRow();
        int cellcol = c.getCellCol();

        if(cellrow-1 >= 0) {
            if(cellcol-2 >= 0 && !sameColourPiece(c,cellrow-1,cellcol-2)) {
                possiblecoordinates.add(new Coordinates(cellrow-1,cellcol-2));
            }

            if(cellcol+2 <= 7 && !sameColourPiece(c,cellrow-1,cellcol+2)) {
                possiblecoordinates.add(new Coordinates(cellrow-1,cellcol+2));
            }
        }

        if(cellrow-2 >= 0) {
            if(cellcol-1 >= 0 && !sameColourPiece(c,cellrow-2,cellcol-1)) {
                possiblecoordinates.add(new Coordinates(cellrow-2,cellcol-1));
            }

            if(cellcol+1 <= 7 && !sameColourPiece(c,cellrow-2,cellcol+1)) {
                possiblecoordinates.add(new Coordinates(cellrow-2, cellcol+1));
            }
        }

        if(cellrow+1 <= 7) {
            if(cellcol-2 >= 0 && !sameColourPiece(c,cellrow+1,cellcol-2)) {
                possiblecoordinates.add(new Coordinates(cellrow+1,cellcol-2));
            }

            if(cellcol+2 <= 7 && !sameColourPiece(c,cellrow+1,cellcol+2)) {
                possiblecoordinates.add(new Coordinates(cellrow+1,cellcol+2));
            }
        }

        if(cellrow+2 <= 7) {
            if(cellcol-1 >= 0 && !sameColourPiece(c,cellrow+2,cellcol-1)) {
                possiblecoordinates.add(new Coordinates(cellrow+2,cellcol-1));
            }

            if(cellcol+1 <= 7 && !sameColourPiece(c,cellrow+2,cellcol+1)) {
                possiblecoordinates.add(new Coordinates(cellrow+2, cellcol+1));
            }
        }
        ArrayList<Coordinates> pinnedcoordinates = isKingAttackedIfPieceRemoved(c);

        if(pinnedcoordinates != null) {

            return intersection(pinnedcoordinates,possiblecoordinates);
            //fill this
        }

        return possiblecoordinates;
    }
}
