package chess.nmamit;

import chess.nmamit.network.NetworkGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 *This class is the main class. *
 */
public class Main {

    JFrame mainframe;
    JPanel localpanel;
    JPanel networkpanel;
    JPanel exitpanel;
    JButton playlocal;
    JButton playnetwork;
    JButton exit;

    Main() {
        mainframe = new JFrame("Chess");

        //mainframe.setSize(1000,1000);
        mainframe.setLayout(new BoxLayout(mainframe.getContentPane(), BoxLayout.Y_AXIS));
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);

        createAndAddButtons();

        playlocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Local newlocalgame = new Local();
            }
        });

        playnetwork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new NetworkGame();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object[] options = {
                        "Yes",
                        "No"
                };
                int option = JOptionPane.showOptionDialog(mainframe, "Are you sure you wish to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[1]);

                if (option == 0)
                    System.exit(0);
            }
        });

        mainframe.pack();
        mainframe.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    void createAndAddButtons() {

        localpanel = new JPanel();
        networkpanel = new JPanel();
        exitpanel = new JPanel();

        playlocal = new JButton("Play locally");
        playnetwork = new JButton("Play on a network");
        exit = new JButton("Exit");

        localpanel.add(playlocal);
        networkpanel.add(playnetwork);
        exitpanel.add(exit);

        mainframe.add(Box.createRigidArea(new Dimension(250, 50)));
        mainframe.add(localpanel);
        mainframe.add(Box.createRigidArea(new Dimension(250, 50)));
        mainframe.add(networkpanel);
        mainframe.add(Box.createRigidArea(new Dimension(250, 50)));
        mainframe.add(exitpanel);
        mainframe.add(Box.createRigidArea(new Dimension(250, 50)));
    }
}
