package chess.nmamit.pieces;

import chess.nmamit.Colour;
import chess.nmamit.Coordinates;

import javax.swing.*;
import java.io.File;


public abstract class Piece {

    ImageIcon pieceimage;
    public Colour piececolour;
    boolean alivestatus;
    public Coordinates[] possiblecoordinates;


    abstract Coordinates[] possibleMoves(Coordinates c);

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
