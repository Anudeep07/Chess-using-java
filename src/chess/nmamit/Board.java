package chess.nmamit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 *This class generates a board
 *
 * | Rk Kt Bi Qu Ki Bi Kt Rk | i=0
 * | P  P  P  P  P  P  P  P  | i=1
 * |                         | i=2
 * |                         | i=3
 * |                         | i=4
 * |                         | i=5
 * | P  P  P  P  P  P  P  P  | i=6
 * | Rk Kt Bi Qu Ki Bi Kt Rk | i=7
 */


public class Board {
    JPanel boardpanel;
    static boolean highlighted;
    static JButton highlightedbutton;
    static Color originalcellcolour;



    Board() {
        boardpanel = new JPanel();
        highlighted = false;
        highlightedbutton = null;

        boardpanel.setSize(800, 800);
        boardpanel.setLayout(new GridLayout(8, 8));

        Cell cells[][] = new Cell[8][8];

        drawBoardAndAddToPanel(cells);

        boardpanel.setVisible(true);
    }

    void drawBoardAndAddToPanel(Cell cells[][]) {
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                cells[i][j] = new Cell(i,j);              //creating cell object

                if (cells[i][j].cellcolour == Colour.WHITE) {
                    cells[i][j].setBackgroundColour(240, 250, 250);
                } else {
                    cells[i][j].setBackgroundColour(74, 163, 230);
                }

                cells[i][j].addToJPanel(boardpanel);
            }

            if(i==0 || i==7) {
                setMajorPieces(cells,i);
            }
            else if(i==1 || i==6) {
                setPawns(cells,i);
            }
        }
    }

    void setMajorPieces(Cell cells[][],int row) {

        Colour c;
        if(row==0) {
            c = Colour.BLACK;
        } else {
            c = Colour.WHITE;
        }

        cells[row][0].setPiece(Pieces.ROOK,c);
        cells[row][1].setPiece(Pieces.KNIGHT,c);
        cells[row][2].setPiece(Pieces.BISHOP,c);

        cells[row][3].setPiece(Pieces.QUEEN,c);
        cells[row][4].setPiece(Pieces.KING,c);

        cells[row][5].setPiece(Pieces.BISHOP,c);
        cells[row][6].setPiece(Pieces.KNIGHT,c);
        cells[row][7].setPiece(Pieces.ROOK,c);
    }

    void setPawns(Cell cells[][],int row) {

        if(row==1) {

            //creates 8 black pawns
            for(int i=0 ; i<8 ; i++) {
                cells[row][i].setPiece(Pieces.PAWN,Colour.BLACK);
            }


        } else {

            //creates 8 white pawns
            for(int i=0 ; i<8 ; i++) {
                cells[row][i].setPiece(Pieces.PAWN,Colour.WHITE);
            }
        }
    }

    static void makeSelectedCellHighlighted(Cell c) {

        if(!highlighted) {
            highlightedbutton = c.cellbutton;                                       //Cell's button generated the event.
            originalcellcolour = highlightedbutton.getBackground();
            highlightedbutton.setBackground(new Color(237, 253, 153));

            highlighted = true;

        } else {

            /*
             *This occurs when user had already clicked a button and he clicks some other button.
             */

            highlightedbutton.setBackground(originalcellcolour);    //change previously selected button to the original cell colour

            highlightedbutton = c.cellbutton;  //now highlightedbutton contains the new selected button



            originalcellcolour = highlightedbutton.getBackground();
            highlightedbutton.setBackground(new Color(237, 253, 153));

            highlighted = true;
        }
    }

}
