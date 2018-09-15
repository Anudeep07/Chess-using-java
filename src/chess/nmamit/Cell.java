package chess.nmamit;

import chess.nmamit.pieces.Pawn;
import chess.nmamit.pieces.Piece;
import chess.nmamit.pieces.Rook;

import javax.swing.*;
import java.awt.*;

/*
 *This cell represents each cell in the chess board
 * Each cell is a JButton
 */


public class Cell {

    JButton cellbutton;
    Piece cellpiece;
    Coordinates cellposition;

    Cell(int row, int column) {
        cellbutton = new JButton();
        cellpiece = null;
        cellposition = new Coordinates(row,column);

        cellbutton.setFocusPainted(false);              //this won't show a border around the icon of the button when its pressed.
    }


    void setBackgroundColour(int r, int g, int b) {

        cellbutton.setBackground(new Color(r, g, b));
    }

    void addToJPanel(JPanel jp) {
        jp.add(cellbutton);
    }

    void setPiece(Pieces P, Colour c) {

            switch(P) {
                case PAWN:

                    cellpiece = new Pawn(c);
                    setImage("P");
                    break;

                case ROOK:

                    cellpiece = new Rook(c);
                    setImage("R");
                    break;

            }
    }

    void setImage(String S) {

        if(cellpiece.getPieceImage() != null)
            cellbutton.setIcon(cellpiece.getPieceImage());
        else
            cellbutton.setText(S);
    }
}
