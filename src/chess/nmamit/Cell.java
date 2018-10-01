package chess.nmamit;

import chess.nmamit.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import static chess.nmamit.Board.*;

/*
 *This cell represents each cell in the chess board
 * Each cell is a JButton
 */


public class Cell implements ActionListener, Serializable {

    public JButton cellbutton;
    public Piece cellpiece;
    Coordinates cellposition;
    Colour cellcolour;
    public ArrayList<Coordinates> possiblecoordinates;

    Cell(int row, int column) {
        cellbutton = new JButton();
        cellpiece = null;
        cellposition = new Coordinates(row,column);
        cellcolour = cellposition.getCoordinateColour();
        possiblecoordinates = null;

        cellbutton.setFocusPainted(false);              //this won't show a border around the icon of the button when its pressed.
        cellbutton.addActionListener(this);
    }


    void setBackgroundColour(int r, int g, int b) {

        cellbutton.setBackground(new Color(r, g, b));
    }

    void addToJPanel(JPanel jp) {
        jp.add(cellbutton);
    }

    public void setPiece(Pieces P, Colour c) {

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

        if(cellpiece.getPieceImage() != null) {
            cellbutton.setIcon(cellpiece.getPieceImage());
            cellbutton.setDisabledIcon(cellpiece.getPieceImage());
        }

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (!highlighted) {
          /*  if(cellpiece != null)
                possiblecoordinates = cellpiece.possibleMoves(this);
          */  makeSelectedCellHighlighted(this);
        } else {
            /*
             *This occurs when user had already clicked a button and he clicks some other button.
             *
             * 1. he presses a button within the possible coordinates
             * 2. he presses a button other than possible coordinates
             *      a. he presses a button of same colour piece, then unhighlight previous pressed and highlight new one
             *      b. he presses some other invalid button, unhighlight the previous pressed button
             */


            //other than possible coordinates

            if(withinPossibleCoordinates()) {

                removePieceAndAdd();

                if(kingdead != Colour.NONE) {
                    endGame();
                }

                if(!networkmode)    //change turn only if its not network mode
                    changeTurn();


            } else {

                unhighlightPreviousPressed(this);

                if (cellpiece != null && correctColour(this)) {
                    makeSelectedCellHighlighted(this);
                }
            }
        }
    }

    public int getCellRow() {
        return cellposition.x;
    }

    public int getCellCol() {
        return cellposition.y;
    }

    Coordinates subtract(Cell c1) {
        int row = c1.getCellRow() - this.getCellRow();
        int col = c1.getCellCol() - this.getCellCol();

        return new Coordinates(row,col);
    }

    public Pieces getPieceName() {
        if(cellpiece == null)
            return null;
        else
            return cellpiece.piecename;
    }

    public Colour getPieceColour() {
        if(cellpiece == null)
            return Colour.NONE;
        else
            return cellpiece.piececolour;
    }

    public static boolean contains(ArrayList<Coordinates> C, Coordinates position) {       //we can't use highlightedcell.possiblecoordinates.contains(cellposition) because it compares the objects and not object.x & object.y

        if(C != null)
            for(Coordinates cord : C) {
                if(cord.x == position.x && cord.y == position.y)
                    return true;
            }

        return false;
    }

    boolean withinPossibleCoordinates() {
        if(contains(highlightedcell.possiblecoordinates, cellposition))     //if highlightedcell.possiblecoordinates contains cellposition
            return true;
        else
            return false;
    }

    void removePieceAndAdd() {   //removes piece from highlighted cell and adds it to selected cell

        //removing piece from highlightedcell
        ArrayList<Coordinates> movedcells = new ArrayList<Coordinates>();

        movedcells.add(highlightedcell.cellposition);
        movedcells.add(this.cellposition);

        if(networkmode)
            sendMoveOnNetwork(movedcells);

        highlightedcell.cellbutton.setIcon(null);   //removes icon from highlightedcell

        //if selected cell is king (some piece removes king), then set respective colour king as dead
        if(this.cellpiece != null && this.cellpiece.piecename == Pieces.KING ) {
            if(this.cellpiece.piececolour == Colour.WHITE)
                kingdead = Colour.WHITE;
            else
                kingdead = Colour.BLACK;
        }

        for(Coordinates coordinates : highlightedcell.possiblecoordinates) {    //this will remove possiblecells' background
            cells[coordinates.x][coordinates.y].cellbutton.setBorder(BorderFactory.createEmptyBorder());
        }
        highlightedcell.possiblecoordinates = null;

        setPiece(highlightedcell.cellpiece.piecename, turn);    //sets piece in selected cell
        possiblecoordinates = cellpiece.possibleMoves(this);

        highlightedcell.cellpiece = null;

        unhighlightPreviousPressed(this);
    }

    void endGame() {
        if(kingdead == Colour.WHITE) {
            JOptionPane.showMessageDialog(null,"Black wins!");


        } else {
            JOptionPane.showMessageDialog(null, "White wins!");
        }
        System.exit(0);
    }
}
