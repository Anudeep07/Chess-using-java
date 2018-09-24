package chess.nmamit;

import javax.swing.*;
import java.awt.*;

public class Player {
    String name;
    JPanel playerpanel;
    JLabel playername;
    JButton issuedraw;
    JButton resign;
    Colour c;

    public Player(String pname, Colour c, LocalGame game) {
        name = pname;
        this.c = c;

        createPlayerPanel(game);
    }

    void createPlayerPanel(LocalGame game) {

        playerpanel = new JPanel();
        playerpanel.setPreferredSize(new Dimension(800,50));

        playername = new JLabel(name);
        playerpanel.add(playername);

        issuedraw = new JButton("Draw");
        resign = new JButton("Resign");

        issuedraw.setActionCommand(name+" Draw");
        resign.setActionCommand(name+" Resign");

        resign.addActionListener(game);
        issuedraw.addActionListener(game);

        playerpanel.add(issuedraw);
        playerpanel.add(resign);

        playerpanel.setVisible(true);
    }


}
