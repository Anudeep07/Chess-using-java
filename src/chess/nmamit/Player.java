package chess.nmamit;

import javax.swing.*;
import java.awt.*;

public class Player {
    String name;
    JPanel playerpanel;
    JLabel playername;
    JButton issuedraw;
    JButton resign;

    public Player(String pname) {
        name = pname;

        createPlayerPanel();
    }

    void createPlayerPanel() {

        playerpanel = new JPanel();
        playerpanel.setPreferredSize(new Dimension(800,50));

        playername = new JLabel(name);
        playerpanel.add(playername);

        issuedraw = new JButton("Draw");
        resign = new JButton("Resign");

        playerpanel.add(issuedraw);
        playerpanel.add(resign);

        playerpanel.setVisible(true);
    }
}
