package chess.nmamit;

import javax.swing.*;
import java.awt.*;

/*
 *This class will enable the user to play locally.
 */
public class Local {

    JFrame localframe;

    Local() {
        localframe = new JFrame("Chess local");

        localframe.setSize(800,900);
        localframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        localframe.setResizable(false);

        Game game = new Game();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        localframe.add(player1.playerpanel, BorderLayout.PAGE_START);
        localframe.add(game.board.boardpanel, BorderLayout.CENTER);
        localframe.add(player2.playerpanel,BorderLayout.PAGE_END);

        localframe.setVisible(true);
        Game newgame = new Game();
        
    }

}
