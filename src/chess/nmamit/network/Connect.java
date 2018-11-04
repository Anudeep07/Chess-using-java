package chess.nmamit.network;

import chess.nmamit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static chess.nmamit.Board.updateBoardFromNetwork;

public class Connect implements Game {

    JDialog boardframe;
    Player player;
    Board board;
    public static Colour clientnetworkturn;

    Socket connection;
    ObjectOutputStream output;
    ObjectInputStream input;
    String serverip;

    Connect(String clientname) {

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
        createAndShowGUI(clientname);

    }

    private void createAndShowGUI(String clientname) {
        boardframe = new JDialog();
        clientnetworkturn = Colour.WHITE;                                                                     //turn = white means, its white's turn to play and not turn of client

        boardframe.setTitle("Client");
        boardframe.setSize(800,850);
        boardframe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        boardframe.setModal(true);
        boardframe.setResizable(false);

        player = new Player(clientname, Colour.BLACK,this);                             //this indicates client's piece colour is black
        // board = new Board();
        serverip = "127.0.0.1";

        boardframe.add(player.playerpanel, BorderLayout.PAGE_START);
        // boardframe.add(board.boardpanel, BorderLayout.CENTER);

        boardframe.setVisible(true);
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

    public void startRunning() {
        //since client is always black, we disable the board in the beginning
        //when we receive an object, we change token and then we enable the board
        try {
            createConnection();
            createStreams();
            playGame();

        } catch (IOException e) {
            //i/o error when opening socket
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public static void modifyClientNetworkTurn(Colour c) {
        clientnetworkturn = c;
    }

    public void createConnection() throws IOException {

        connection = new Socket(serverip,6789);
        JOptionPane.showMessageDialog(boardframe,"CLIENT: Successfully connected to " + connection.getInetAddress().getHostAddress());
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
                board = new Board(output,false);
                boardframe.add(board.boardpanel, BorderLayout.CENTER);
                boardframe.validate();
            }
        });

    }

    public void playGame() throws IOException {
        ArrayList<Coordinates> movedcells = new ArrayList<Coordinates>();
        do {
            SwingUtilities.isEventDispatchThread();     //no idea why i have to include this line
                                                        //if i remove above line only keep the if condition, everything acts weird

            if(clientnetworkturn == Colour.WHITE) {
                //need to read from server

                //disable the board first
                setEnableRec(boardframe,false);

                try {

                    movedcells = (ArrayList<Coordinates>) input.readObject();

                    if(movedcells == null)
                    {
                        JOptionPane.showMessageDialog(boardframe, "You lost! Please try again!");

                        closeConnections();
                        System.exit(0);         //because someone has won, no need to continue the game
                    }

                    updateBoard(movedcells);


                } catch (ClassNotFoundException classnotfoundexception) {
                    classnotfoundexception.printStackTrace();
                } catch (EOFException eofexception) {
                    //this is normal execution (game has been terminated on the host side) this happens if host won the game
                    JOptionPane.showMessageDialog(boardframe, "You lost! Please try again!");

                    closeConnections();
                    System.exit(0);         //because someone has won, no need to continue the game

                }
                //enable the board now, since host has played and the board is updated here
                setEnableRec(boardframe,true);
                //change turncolour back to black
                clientnetworkturn = Colour.BLACK;
            }

        } while(true);    //condition just for now
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
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //what to do when resign and draw is pressed
    }
}
