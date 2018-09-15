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
enum Colour {
    BLACK,WHITE;
}

enum Pieces {
    PAWN,KNIGHT,BISHOP,ROOK,QUEEN,KING,NONE
}

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

    }

    void setPawns(Cell cells[][],int row) {

        if(row==1) {

            //creates 8 black pawns
            for(int i=0 ; i<8 ; i++) {
                cells[row][i].setPiece(Pieces.PAWN,Colour.BLACK);
            }


        }
        else {

            //creates 8 white pawns
            for(int i=0 ; i<8 ; i++) {
                cells[row][i].setPiece(Pieces.PAWN,Colour.WHITE);
            }
        }
    }
}
