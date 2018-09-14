package chess.nmamit;

import javax.swing.*;
import java.awt.*;

/*
 *This class generates a board
 */
public class Board {
    JPanel boardpanel;

    void drawBoardAndAddToFrame(Cell cells[][]) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell();           //creating cell object

                if ((i + j) % 2 == 0) {
                    cells[i][j].setBackgroundColour(255, 255, 255);
                } else {
                    cells[i][j].setBackgroundColour(120, 180, 240);
                }

                cells[i][j].addToJPanel(boardpanel);
            }
        }
    }


    Board() {
        boardpanel = new JPanel();

        boardpanel.setSize(800, 800);
        boardpanel.setLayout(new GridLayout(8, 8));


        Cell cells[][] = new Cell[8][8];

        drawBoardAndAddToFrame(cells);

        boardpanel.setVisible(true);
    }
}
