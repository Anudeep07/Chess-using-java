package chess.nmamit;

import javax.swing.*;
import java.awt.*;

/**
 * This class is used to create a chess game.
 * Object of chess.nmamit.Game() class will enable you to play chess.
 */
public class Game {

    JFrame boardframe;
    Player player1;
    Player player2;
    Board board;

    Game(String p1, String p2) {
        boardframe = new JFrame("Play chess");

        boardframe.setSize(800,900);
        boardframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        boardframe.setResizable(false);

        player1 = new Player(p1, Colour.WHITE);
        player2 = new Player(p2,Colour.BLACK);

        board = new Board();

        boardframe.add(player1.playerpanel, BorderLayout.PAGE_START);
        boardframe.add(board.boardpanel, BorderLayout.CENTER);
        boardframe.add(player2.playerpanel,BorderLayout.PAGE_END);

        boardframe.setVisible(true);
    }



}
