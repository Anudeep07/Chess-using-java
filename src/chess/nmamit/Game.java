package chess.nmamit;

import javax.swing.*;
import java.awt.*;

/**
 * This class is used to create a chess game.
 * Object of chess.nmamit.Game() class will enable you to play chess.
 *
 * @author anudeep
 */
public class Game {

    JFrame gameframe;


    Game() {

        gameframe = new JFrame("Chess local");

        gameframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameframe.setResizable(false);

        Board board = new Board();

        gameframe.add(board.boardpanel, BorderLayout.CENTER);

        gameframe.setVisible(true);
    }

}
