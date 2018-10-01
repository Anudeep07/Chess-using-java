package chess.nmamit.network;

import chess.nmamit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static chess.nmamit.Board.updateBoardFromNetwork;
public class Host implements Game {

    JDialog boardframe;
    Player player;
    Board board;
    static Colour hostnetworkturn;

    ServerSocket server;
    Socket connection;
    ObjectOutputStream output;
    ObjectInputStream input;

    Host(String hostname) {
        Thread networkthread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(300);             //start running the server after 300ms, wait for the GUI to load
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startRunning();
            }
        });

        networkthread.start();
        createAndShowGUI(hostname);
    }

    private void createAndShowGUI(String hostname) {
        boardframe = new JDialog();
        hostnetworkturn = Colour.WHITE;

        boardframe.setTitle("Host");
        boardframe.setSize(800,850);
        boardframe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        boardframe.setModal(true);
        boardframe.setResizable(false);

        player = new Player(hostname, Colour.WHITE,this);
        //board = new Board(output);

        boardframe.add(player.playerpanel, BorderLayout.PAGE_END);
//        boardframe.add(board.boardpanel, BorderLayout.CENTER);
        boardframe.setVisible(true);
    }

    public void startRunning() {
        try {
            server = new ServerSocket(6789);

            while (true) {
                createConnection();
                createStreams();
                playGame();
            }

        } catch (IOException e) {
            //i/o error when opening socket
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void createConnection() throws IOException{
      /*  JDialog createconn = new JDialog();

        createconn.setTitle("Waiting");
        createconn.setSize(200,100);
        createconn.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        createconn.setModal(true);
        createconn.setResizable(false);

        JLabel message = new JLabel("Waiting for connection...");

        createconn.add(message, BorderLayout.CENTER);

        createconn.setVisible(true);
*/

        JOptionPane.showMessageDialog(boardframe,"SERVER: Waiting for connection...");
        connection = server.accept();
        JOptionPane.showMessageDialog(boardframe,"SERVER: Successfully connected to " + connection.getInetAddress().getHostAddress());
      //  createconn.dispose();

    }

    public void createStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        createBoard();

        input = new ObjectInputStream(connection.getInputStream());
    }

    private void createBoard() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                board = new Board(output,true);
                boardframe.add(board.boardpanel, BorderLayout.CENTER);
                boardframe.validate();
            }
        });

    }

    public static void modifyHostNetworkTurn(Colour c) {
        hostnetworkturn = c;
    }

    public void playGame() throws IOException {
        ArrayList<Coordinates> movedcells = new ArrayList<Coordinates>();
        do {

           SwingUtilities.isEventDispatchThread();      //no idea why i have to include this line
                                                        //if i remove above line only keep the if condition, everything acts weird

           if(hostnetworkturn == Colour.BLACK) {
                //need to read from client

               //disable the board first
               setEnableRec(boardframe,false);

                try {
                    movedcells = (ArrayList<Coordinates>) input.readObject();
                    updateBoard(movedcells);

                } catch (ClassNotFoundException classnotfoundexception) {
                    classnotfoundexception.printStackTrace();
                } catch (EOFException eofexception) {
                    //this is normal execution (game has been terminated on the host side) this happens if host won the game
                    JOptionPane.showMessageDialog(boardframe, "You lost! Please try again!");

                    closeConnections();
                    System.exit(0);         //because someone has won, no need to continue the game

                }
                //enable the board now, since client has played and the board is updated here
                setEnableRec(boardframe,true);
                //change turncolour back to white
               hostnetworkturn = Colour.WHITE;
            }

        } while(true);    //condition just for now (game is terminated when king is dead)
    }

    private void setEnableRec(Component container, boolean enable){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                container.setEnabled(enable);

                try {
                    Component[] components= ((Container) container).getComponents();
                    for (int i = 0; i < components.length; i++) {
                        setEnableRec(components[i], enable);
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //what to do when resign and draw is pressed
    }

    private void updateBoard(ArrayList<Coordinates> movedcells) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Coordinates originalcoordinates = movedcells.get(0);
                Coordinates newcoordinates = movedcells.get(1);

                Colour won = updateBoardFromNetwork(originalcoordinates,newcoordinates);

                if(won != Colour.NONE) {

                    if(won == Colour.WHITE)
                        JOptionPane.showMessageDialog(null, "Congratulations! You(white) win!");
                    else
                        JOptionPane.showMessageDialog(null,"Congratulations! You(black) win!");

                    try {
                        output.writeObject(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    closeConnections();
                    System.exit(0);         //because someone has won, no need to continue the game
                }

            }
        });
    }

    private void closeConnections() {
        //close streams
        try {
            output.close();
            input.close();
            connection.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//will send Host object and receive Connect object, only change board