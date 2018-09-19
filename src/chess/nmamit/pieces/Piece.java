package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;
import chess.nmamit.Pieces;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;


public abstract class Piece {

    ImageIcon pieceimage;
    public Pieces piecename;
    public Colour piececolour;
    boolean alivestatus;



    public abstract ArrayList<Coordinates> possibleMoves(Cell c);

    ImageIcon createImageIcon(String path) {

        if(new File(path).exists()) {
            return new ImageIcon(path);
        } else {
            return null;
        }
    }

    public ImageIcon getPieceImage() {
        return pieceimage;
    }
}
