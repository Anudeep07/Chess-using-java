package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;
import chess.nmamit.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import static chess.nmamit.Board.isEmpty;
import static chess.nmamit.Board.isKingAttackedIfPieceRemoved;
import static chess.nmamit.Board.sameColourPiece;

public class Bishop extends Piece implements Serializable {
    public Bishop(Colour c) {

        piecename = Pieces.BISHOP;
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
    public ArrayList<Coordinates> possibleMoves(Cell c) {

        ArrayList<Coordinates> possiblecoordinates = new ArrayList<Coordinates>();
        int cellrow = c.getCellRow();
        int cellcol = c.getCellCol();

        int rowptr;
        int colptr;

        rowptr = cellrow-1;
        colptr = cellcol-1;

        while(rowptr >= 0 && colptr >= 0){

            if(isEmpty(rowptr,colptr)) {
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                rowptr--;
                colptr--;
            }

            else if (!sameColourPiece(c,rowptr,colptr)){
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                break;
            }

            else
                break;
        }

        rowptr = cellrow+1;
        colptr = cellcol-1;

        while(rowptr <= 7 && colptr >= 0){

            if(isEmpty(rowptr,colptr)) {
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                rowptr++;
                colptr--;
            }

            else if (!sameColourPiece(c,rowptr,colptr)){
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                break;
            }

            else
                break;
        }

        rowptr = cellrow-1;
        colptr = cellcol+1;

        while(rowptr >= 0 && colptr <= 7){

            if(isEmpty(rowptr,colptr)) {
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                rowptr--;
                colptr++;
            }

            else if (!sameColourPiece(c,rowptr,colptr)){
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                break;
            }

            else
                break;
        }

        rowptr = cellrow+1;
        colptr = cellcol+1;

        while(rowptr <= 7 && colptr <= 7){

            if(isEmpty(rowptr,colptr)) {
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                rowptr++;
                colptr++;
            }

            else if (!sameColourPiece(c,rowptr,colptr)){
                possiblecoordinates.add(new Coordinates(rowptr, colptr));
                break;
            }

            else
                break;
        }

        ArrayList<Coordinates> pinnedcoordinates = isKingAttackedIfPieceRemoved(c);

        if(pinnedcoordinates != null) {

            return intersection(pinnedcoordinates,possiblecoordinates);
            //fill this
        }
        return possiblecoordinates;
    }
}
