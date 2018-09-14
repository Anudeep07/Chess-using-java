package chess.nmamit;

import javax.swing.*;
import java.awt.*;

/*
 *This cell represents each cell in the chess board
 * Each cell is a JButton
 */
public class Cell {

    JButton cellbutton;

    Cell() {
        cellbutton = new JButton();
    }

    void setBackgroundColour(int r, int g, int b) {

        cellbutton.setBackground(new Color(r, g, b));
    }

    void addToJPanel(JPanel jp) {
        jp.add(cellbutton);
    }

}
