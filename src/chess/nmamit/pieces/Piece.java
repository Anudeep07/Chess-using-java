package chess.nmamit.pieces;

import chess.nmamit.Cell;
import chess.nmamit.Colour;
import chess.nmamit.Coordinates;
import chess.nmamit.Pieces;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static chess.nmamit.Cell.contains;


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

    public ArrayList<Coordinates> intersection(ArrayList<Coordinates> list1, ArrayList<Coordinates> list2) {

        ArrayList<Coordinates> list = new ArrayList<Coordinates>();

        for(Coordinates c : list1) {
            if(contains(list2,c)) {
                list.add(c);
            }
        }

        return list;
    }

    public ImageIcon getPieceImage() {
        return pieceimage;
    }
}
