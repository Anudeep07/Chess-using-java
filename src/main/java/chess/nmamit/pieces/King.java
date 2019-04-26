package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;
import chess.nmamit.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import static chess.nmamit.Board.isEmpty;
import static chess.nmamit.Board.sameColourPiece;

public class King extends Piece implements Serializable {
    public King(Colour c) {

        piecename = Pieces.KING;
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
    public ArrayList<Coordinates> possibleMoves(Cell c) {

        ArrayList<Coordinates> possiblecoordinates = new ArrayList<Coordinates>();

        int cellrow = c.getCellRow();
        int cellcol = c.getCellCol();

        if(cellrow-1 >= 0) {
            //row above king
            if(cellcol-1 >= 0) {
                if(isEmpty(cellrow-1,cellcol-1) || !sameColourPiece(c,cellrow-1,cellcol-1))
                    possiblecoordinates.add(new Coordinates(cellrow-1,cellcol-1));
            }

            if(isEmpty(cellrow-1,cellcol) || !sameColourPiece(c,cellrow-1,cellcol))
                possiblecoordinates.add(new Coordinates(cellrow-1,cellcol));

            if(cellcol+1 < 8) {
                if(isEmpty(cellrow-1,cellcol+1) || !sameColourPiece(c,cellrow-1,cellcol+1))
                    possiblecoordinates.add(new Coordinates(cellrow-1,cellcol+1));
            }
        }

        //same row as king

        if(cellcol-1 >= 0) {
            if(isEmpty(cellrow,cellcol-1) || !sameColourPiece(c,cellrow,cellcol-1))
                possiblecoordinates.add(new Coordinates(cellrow,cellcol-1));
        }

        if(cellcol+1 < 8) {
            if(isEmpty(cellrow,cellcol+1) || !sameColourPiece(c,cellrow,cellcol+1))
                possiblecoordinates.add(new Coordinates(cellrow,cellcol+1));
        }



        if(cellrow+1 < 8) {
            //row below king
            if(cellcol-1 >= 0) {
                if(isEmpty(cellrow+1,cellcol-1) || !sameColourPiece(c,cellrow+1,cellcol-1))
                    possiblecoordinates.add(new Coordinates(cellrow+1,cellcol-1));
            }

            if(isEmpty(cellrow+1,cellcol) || !sameColourPiece(c,cellrow+1,cellcol))
                possiblecoordinates.add(new Coordinates(cellrow+1,cellcol));

            if(cellcol+1 < 8) {
                if(isEmpty(cellrow+1,cellcol+1) || !sameColourPiece(c,cellrow+1,cellcol+1))
                    possiblecoordinates.add(new Coordinates(cellrow+1,cellcol+1));
            }
        }



        //from all these possible moves, check if move results in a check.

        return possiblecoordinates;
    }
}
