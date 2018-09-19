package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;
import chess.nmamit.Pieces;

import java.util.ArrayList;

import static chess.nmamit.Board.isEmpty;
import static chess.nmamit.Board.isKingAttackedIfPieceRemoved;
import static chess.nmamit.Board.sameColourPiece;

public class Rook extends Piece {

    public Rook(Colour c) {

        piecename = Pieces.ROOK;
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
    public ArrayList<Coordinates> possibleMoves(Cell c) {
        if(isKingAttackedIfPieceRemoved(c)) {
            //the piece is pinned
            return null;
        }

        ArrayList<Coordinates> possiblecoordinates = new ArrayList<Coordinates>();
        int cellrow = c.getCellRow();
        int cellcol = c.getCellCol();

        int rowptr;
        int colptr;

        colptr = cellcol - 1;

        while (colptr >= 0) {

            if (isEmpty(cellrow, colptr)) {
                possiblecoordinates.add(new Coordinates(cellrow, colptr));
                colptr--;
            } else if (!sameColourPiece(c, cellrow, colptr)) {
                possiblecoordinates.add(new Coordinates(cellrow, colptr));
                break;
            } else
                break;
        }
        colptr = cellcol + 1;
        while (colptr <= 7 && isEmpty(cellrow, colptr)) {
            if (isEmpty(cellrow, colptr)) {
                possiblecoordinates.add(new Coordinates(cellrow, colptr));
                colptr++;
            } else if (!sameColourPiece(c, cellrow, colptr)) {
                possiblecoordinates.add(new Coordinates(cellrow, colptr));
                break;
            } else
                break;
        }

        rowptr = cellrow + 1;
        while (rowptr <= 7) {
            if (isEmpty(rowptr, cellcol)) {
                possiblecoordinates.add(new Coordinates(rowptr, cellcol));
                rowptr++;
            } else if (!sameColourPiece(c, rowptr, cellcol)) {
                possiblecoordinates.add(new Coordinates(rowptr, cellcol));
                break;
            } else
                break;
        }

        rowptr = cellrow - 1;
        while (rowptr >= 0) {
            if (isEmpty(rowptr, cellcol)) {
                possiblecoordinates.add(new Coordinates(rowptr, cellcol));
                rowptr--;
            } else if (!sameColourPiece(c, rowptr, cellcol)) {
                possiblecoordinates.add(new Coordinates(rowptr, cellcol));
                break;
            } else
                break;
        }
        return possiblecoordinates;
    }
}
