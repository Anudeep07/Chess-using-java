package chess.nmamit.network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkGame {

    JDialog networkdialog;
    JPanel hostpanel;
    JPanel connectpanel;
    JButton host;
    JButton connect;
    Host h;
    Connect c;

    public NetworkGame() {
        networkdialog = new JDialog();

        networkdialog.setTitle("Play on a network");
        networkdialog.setLayout(new BoxLayout(networkdialog.getContentPane(),BoxLayout.Y_AXIS));
        networkdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        networkdialog.setModal(true);

        createAndAddButtons();


        networkdialog.pack();
        networkdialog.setVisible(true);
    }

    private void createAndAddButtons() {
        hostpanel = new JPanel();
        connectpanel = new JPanel();

        host = new JButton("Host");
        connect = new JButton("Connect");

        host.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                hostPressed();
            }
        });
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                connectPressed();
            }
        });

        hostpanel.add(host);
        connectpanel.add(connect);

        networkdialog.add(Box.createRigidArea(new Dimension(250, 50)));
        networkdialog.add(hostpanel);
        networkdialog.add(Box.createRigidArea(new Dimension(250, 50)));
        networkdialog.add(connectpanel);
        networkdialog.add(Box.createRigidArea(new Dimension(250, 50)));
    }

    private void hostPressed() {
        Host h = new Host("Anudeep");             //by default white for now
    }

    private void connectPressed() {
        Connect c = new Connect("Akshay");        //by default black for now
    }
}
