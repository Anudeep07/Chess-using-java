package chess.nmamit;

import javax.swing.*;
import java.awt.*;

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


    Board() {
        boardpanel = new JPanel();

        boardpanel.setSize(800, 800);
        boardpanel.setLayout(new GridLayout(8, 8));


        Cell cells[][] = new Cell[8][8];

        drawBoardAndAddToPanel(cells);

        boardpanel.setVisible(true);
    }

    void drawBoardAndAddToPanel(Cell cells[][]) {
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell(i,j);           //creating cell object



                if ((i + j) % 2 == 0) {
                    cells[i][j].setBackgroundColour(255, 255, 255);
                } else {
                    cells[i][j].setBackgroundColour(120, 180, 240);
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
}
