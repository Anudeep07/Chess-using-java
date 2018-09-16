package chess.nmamit;

import chess.nmamit.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static chess.nmamit.Board.makeSelectedCellHighlighted;

/*
 *This cell represents each cell in the chess board
 * Each cell is a JButton
 */


public class Cell implements ActionListener {

    JButton cellbutton;
    Piece cellpiece;
    Coordinates cellposition;
    Colour cellcolour;

    Cell(int row, int column) {
        cellbutton = new JButton();
        cellpiece = null;
        cellposition = new Coordinates(row,column);
        cellcolour = cellposition.getCoordinateColour();

        cellbutton.setFocusPainted(false);              //this won't show a border around the icon of the button when its pressed.
        cellbutton.addActionListener(this);
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

                case KNIGHT:

                    cellpiece = new Knight(c);
                    setImage("Kt");
                    break;

                case BISHOP:

                    cellpiece = new Bishop(c);
                    setImage("B");
                    break;

                case QUEEN:

                    cellpiece = new Queen(c);
                    setImage("Q");
                    break;

                case KING:

                    cellpiece = new King(c);
                    setImage("K");
                    break;
            }
    }

    void setImage(String S) {

        if(cellpiece.getPieceImage() != null)
            cellbutton.setIcon(cellpiece.getPieceImage());
        else
            cellbutton.setText(S);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        makeSelectedCellHighlighted(this);

    }

}
