package chess.nmamit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


import static chess.nmamit.network.Connect.modifyClientNetworkTurn;
import static chess.nmamit.network.Host.modifyHostNetworkTurn;
import static java.lang.Math.abs;

/*
 *This class generates a board
 *
 * | Rk Kt Bi Qu Ki Bi Kt Rk | i=0
 * | P  P  P  P  P  P  P  P  | i=1
 * |                         | i=2
 * |                         | i=3
 * |                         | i=4
 * |                         | i=5
 * | P  P  P  P  P  P  P  P  | i=6
 * | Rk Kt Bi Qu Ki Bi Kt Rk | i=7
 */


public class Board {
    public static Board boardref;
    public JPanel boardpanel;
    static Colour turn;
    static boolean highlighted;
    static JButton highlightedbutton;
    static Cell highlightedcell;
    static Color originalcellcolour;
    public static Cell[][] cells;
    static Cell whitekingcell;
    static Cell blackkingcell;
    public static Colour kingdead;
    public static boolean networkmode;
    public static boolean ishost;
    ObjectOutputStream output;
    ObjectInputStream input;


    public Board() {
        boardpanel = new JPanel();
        highlighted = false;
        highlightedbutton = null;
        originalcellcolour = null;
        turn = Colour.WHITE;
        kingdead = Colour.NONE;
        boardref = this;
        networkmode = false;    //will be overwritten to true if its networking since Board() is called in Board(ObjectOutputStream)



        boardpanel.setSize(800, 800);
        boardpanel.setLayout(new GridLayout(8, 8));

        cells = new Cell[8][8];

        drawBoardAndAddToPanel(cells);

        boardpanel.setVisible(true);
    }

    public Board(ObjectInputStream input, ObjectOutputStream output, boolean host) {
        this();
        this.input = input;
        this.output = output;
        networkmode = true;

        if(host) {
            ishost = true;
            turn = Colour.WHITE;
        } else {
            ishost = false;
            turn = Colour.BLACK;
        }
    }

    public static void sendMoveOnNetwork(ArrayList<Coordinates> movedcells, Pieces promotedpiece) {
        try {
            boardref.output.flush();
            boardref.output.writeObject(movedcells);
            boardref.output.writeObject(promotedpiece);

            if(ishost) {
                modifyHostNetworkTurn(Colour.BLACK);
            } else {
                modifyClientNetworkTurn(Colour.WHITE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMoveOnNetwork(ArrayList<Coordinates> movedcells) {
        try {
            boardref.output.flush();
            boardref.output.writeObject(movedcells);

            if(ishost) {
                modifyHostNetworkTurn(Colour.BLACK);
            } else {
                modifyClientNetworkTurn(Colour.WHITE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isPromo(Cell start) {
        if (start.cellpiece.piecename == Pieces.PAWN) {
            if (start.cellpiece.piececolour == Colour.WHITE)
                if (start.cellposition.x == 1)
                    return true;
                else
                    return false;

            else if (start.cellposition.x == 6)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public static Colour updateBoardFromNetwork(Coordinates originalcoordinates, Coordinates newcoordinates) {

        Cell originalcell = cells[originalcoordinates.x][originalcoordinates.y];
        Cell newcell = cells[newcoordinates.x][newcoordinates.y];

        if(isPromo(originalcell)) {
            try {
                Pieces promotedpiece = (Pieces) boardref.input.readObject();

                originalcell.cellbutton.setIcon(null);   //removes icon from highlightedcell
                if(newcell.cellpiece != null && newcell.cellpiece.piecename == Pieces.KING ) {
                    newcell.cellbutton.setIcon(originalcell.cellpiece.getPieceImage());
                    if(newcell.cellpiece.piececolour == Colour.WHITE)
                        return Colour.BLACK;
                    else
                        return Colour.WHITE;

                }

                originalcell.possiblecoordinates = null;

                newcell.setPiece(promotedpiece, originalcell.cellpiece.piececolour);    //sets piece in selected cell
                newcell.possiblecoordinates = newcell.cellpiece.possibleMoves(newcell);

                originalcell.cellpiece = null;

                return Colour.NONE;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            originalcell.cellbutton.setIcon(null);   //removes icon from highlightedcell

            //if selected cell is king (some piece removes king), then return colour that won the game
            if(newcell.cellpiece != null && newcell.cellpiece.piecename == Pieces.KING ) {
                newcell.cellbutton.setIcon(originalcell.cellpiece.getPieceImage());
                if(newcell.cellpiece.piececolour == Colour.WHITE)
                    return Colour.BLACK;
                else
                    return Colour.WHITE;

            }

            originalcell.possiblecoordinates = null;

            newcell.setPiece(originalcell.cellpiece.piecename, originalcell.cellpiece.piececolour);    //sets piece in selected cell
            newcell.possiblecoordinates = newcell.cellpiece.possibleMoves(newcell);

            originalcell.cellpiece = null;

            return Colour.NONE;
        }


        return Colour.NONE;

    }

    void drawBoardAndAddToPanel(Cell cells[][]) {

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                cells[i][j] = new Cell(i, j);              //creating cell object


                if (cells[i][j].cellcolour == Colour.WHITE) {
                    cells[i][j].setBackgroundColour(240, 250, 250);
                } else {
                    cells[i][j].setBackgroundColour(74, 163, 230);
                }

                cells[i][j].addToJPanel(boardpanel);
            }

            if (i == 0 || i == 7) {
                setMajorPieces( i);
            } else if (i == 1 || i == 6) {
                setPawns(i);
            }
        }

        whitekingcell = cells[7][4];
        blackkingcell = cells[0][4];
    }
    void setMajorPieces(int row) {

        Colour c;
        if(row==0) {
            c = Colour.BLACK;
        } else {
            c = Colour.WHITE;
        }

        cells[row][0].setPiece(Pieces.ROOK,c);
        cells[row][1].setPiece(Pieces.KNIGHT,c);
        cells[row][2].setPiece(Pieces.BISHOP,c);

        cells[row][3].setPiece(Pieces.QUEEN,c);
        cells[row][4].setPiece(Pieces.KING,c);



        cells[row][5].setPiece(Pieces.BISHOP,c);
        cells[row][6].setPiece(Pieces.KNIGHT,c);
        cells[row][7].setPiece(Pieces.ROOK,c);
    }

    void setPawns(int row) {

        if(row==1) {

            //creates 8 black pawns
            for(int i=0 ; i<8 ; i++) {
                cells[row][i].setPiece(Pieces.PAWN,Colour.BLACK);
            }


        } else {

            //creates 8 white pawns
            for(int i=0 ; i<8 ; i++) {
                cells[row][i].setPiece(Pieces.PAWN,Colour.WHITE);
            }
        }
    }



    static void unhighlightPreviousPressed(Cell c) {

        //possible moves' background is removed here
        if(highlightedcell.possiblecoordinates != null)
            for(Coordinates coordinates : highlightedcell.possiblecoordinates) {
                cells[coordinates.x][coordinates.y].cellbutton.setBorder(BorderFactory.createEmptyBorder());
            }

        highlightedbutton.setBackground(originalcellcolour);
        highlightedcell = null;
        highlightedbutton = null;
        originalcellcolour = null;
        highlighted = false;
    }

    static void makeSelectedCellHighlighted(Cell c) {

            if(c.cellpiece != null && correctColour(c)) {
                c.possiblecoordinates = c.cellpiece.possibleMoves(c);
                highlightedcell = c;
                highlightedbutton = c.cellbutton;                                       //Cell's button generated the event.
                originalcellcolour = highlightedbutton.getBackground();
                highlightedbutton.setBackground(new Color(228, 230, 11));

                highlighted = true;



                //all possible moves' background is updated here
                if(c.possiblecoordinates != null)
                    for(Coordinates coordinates : c.possiblecoordinates) {
                        cells[coordinates.x][coordinates.y].cellbutton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,new Color(228, 230, 11)));
                    }
                //if he makes a move, change turn
            }
    }

    static boolean correctColour(Cell c) {
        if(turn == c.cellpiece.piececolour)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(int row, int col) {
        if(cells[row][col].cellpiece == null)
            return true;
        else
            return false;
    }

    public static boolean sameColourPiece(Cell c, int newrow,int newcol) {

        if(isEmpty(newrow,newcol))
            return false;

        Colour originalpiececolour = c.cellpiece.piececolour;
        Colour newpiececolour = cells[newrow][newcol].cellpiece.piececolour;

        if(originalpiececolour == newpiececolour) {
            return true;
        } else {
            return false;
        }
    }

    public static void changeTurn() {
        if(turn == Colour.BLACK)
            turn = Colour.WHITE;
        else
            turn = Colour.BLACK;
    }

    public static ArrayList<Coordinates> isKingAttackedIfPieceRemoved(Cell originalcell) {

        Coordinates path;
        ArrayList<Coordinates> possiblecoordinateswhenpinned = new ArrayList<Coordinates>();

        if(originalcell.cellpiece.piececolour == Colour.WHITE) {

            //piece is white
            path = originalcell.subtract(whitekingcell);
        } else {
            //piece is black
            path = originalcell.subtract(blackkingcell);
        }


        int row = path.x;
        int col = path.y;

        int originalrow = originalcell.getCellRow();
        int originalcol = originalcell.getCellCol();
        Colour originalpiececolour = originalcell.getPieceColour();

        if(row == 0 || col == 0 || (abs(row) == abs(col))){

            if(row == 0) {
                if (col < 0) {
                    //(0,-x)
                    for (int j = originalcol+1 ; j < 8; j++) {

                        if (cells[originalrow][j].getPieceColour() == originalpiececolour)
                            return null;
                        if (cells[originalrow][j].getPieceName() == Pieces.ROOK || cells[originalrow][j].getPieceName() == Pieces.QUEEN)
                        {
                            possiblecoordinateswhenpinned.add(new Coordinates(originalrow,j));// attacking piece cell is also possible
                            //found a attacking piece
                            //should return true if there is nothing blocking the originalpiece and the king
                            //if there is some piece(any colour) between the originalpiece and the king, then return false

                            for(int k = originalcol-1 ; k>=0 ; k--) {
                                if(cells[originalrow][k].getPieceName() == Pieces.KING && cells[originalrow][k].getPieceColour() == originalpiececolour)
                                    return possiblecoordinateswhenpinned;
                                else{
                                    if(cells[originalrow][k].cellpiece != null)
                                        return null;
                                }
                                possiblecoordinateswhenpinned.add(new Coordinates(originalrow,k));
                            }
                            return null;
                        }

                        possiblecoordinateswhenpinned.add(new Coordinates(originalrow,j));

                    }
                    return null;
                }

                if (col > 0) {
                    for (int j = originalcol-1 ; j >= 0; j--) {

                        if (cells[originalrow][j].getPieceColour() == originalpiececolour)
                            return null;
                        if (cells[originalrow][j].getPieceName() == Pieces.ROOK  || cells[originalrow][j].getPieceName() == Pieces.QUEEN)
                        {
                            possiblecoordinateswhenpinned.add(new Coordinates(originalrow,j));
                            //found a attacking piece
                            //should return true if there is nothing blocking the originalpiece and the king
                            //if there is some piece(any colour) between the originalpiece and the king, then return false

                            for(int k = originalcol+1 ; k<8 ; k++) {
                                if(cells[originalrow][k].getPieceName() == Pieces.KING && cells[originalrow][k].getPieceColour() == originalpiececolour)
                                    return possiblecoordinateswhenpinned;
                                else{
                                    if(cells[originalrow][k].cellpiece != null)
                                        return null;
                                }
                                possiblecoordinateswhenpinned.add(new Coordinates(originalrow,k));
                            }
                            return null;
                        }
                        possiblecoordinateswhenpinned.add(new Coordinates(originalrow,j));

                    }
                    return null;
                }
            }

            if(col == 0) {
                if (row < 0) {
                    for (int j = originalrow+1 ; j < 8 ; j++) {

                        if (cells[j][originalcol].getPieceColour() == originalpiececolour)
                            return null;
                        if (cells[j][originalcol].getPieceName() == Pieces.ROOK  || cells[j][originalcol].getPieceName() == Pieces.QUEEN)
                        {
                            possiblecoordinateswhenpinned.add(new Coordinates(j,originalcol));
                            //found a attacking piece
                            //should return true if there is nothing blocking the originalpiece and the king
                            //if there is some piece(any colour) between the originalpiece and the king, then return false

                            for(int k = originalrow-1 ; k>=0 ; k--) {
                                if(cells[k][originalcol].getPieceName() == Pieces.KING && cells[k][originalcol].getPieceColour() == originalpiececolour)
                                    return possiblecoordinateswhenpinned;
                                else{
                                    if(cells[k][originalcol].cellpiece != null)
                                        return null;
                                }
                                possiblecoordinateswhenpinned.add(new Coordinates(k,originalcol));
                            }
                            return null;
                        }

                        possiblecoordinateswhenpinned.add(new Coordinates(j,originalcol));
                    }
                    return null;
                }

                if (row > 0) {
                    for (int j = originalrow-1 ; j >= 0 ; j--) {

                        if (cells[j][originalcol].getPieceColour() == originalpiececolour)
                            return null;
                        if (cells[j][originalcol].getPieceName() == Pieces.ROOK  || cells[j][originalcol].getPieceName() == Pieces.QUEEN)
                        {
                            possiblecoordinateswhenpinned.add(new Coordinates(j,originalcol));
                            //found a attacking piece
                            //should return true if there is nothing blocking the originalpiece and the king
                            //if there is some piece(any colour) between the originalpiece and the king, then return false

                            for(int k = originalrow+1 ; k<8 ; k++) {
                                if(cells[k][originalcol].getPieceName() == Pieces.KING && cells[k][originalcol].getPieceColour() == originalpiececolour)
                                    return possiblecoordinateswhenpinned;
                                else{
                                    if(cells[k][originalcol].cellpiece != null)
                                        return null;
                                }
                                possiblecoordinateswhenpinned.add(new Coordinates(k,originalcol));
                            }
                            return null;
                        }
                        possiblecoordinateswhenpinned.add(new Coordinates(j,originalcol));
                    }
                    return null;
                }
            }

            //abs(row) == abs(col)

            if(row > col) {
                //(x,-x)

                for(int i=originalrow-1, j=originalcol+1 ; i>=0 && j<8 ; i--,j++) {

                    if(cells[i][j].getPieceColour() == originalpiececolour)
                        return null;
                    if(cells[i][j].getPieceName() == Pieces.BISHOP || cells[i][j].getPieceName() ==Pieces.QUEEN)
                    {
                        possiblecoordinateswhenpinned.add(new Coordinates(i,j));
                        //found a attacking piece
                        //should return true if there is nothing blocking the originalpiece and the king
                        //if there is some piece(any colour) between the originalpiece and the king, then return false

                        for(int k = originalrow+1, l=originalcol-1 ; k<8 && l>=0 ; k++,l--) {
                            if(cells[k][l].getPieceName() == Pieces.KING && cells[k][l].getPieceColour() == originalpiececolour)
                                return possiblecoordinateswhenpinned;
                            else{
                                if(cells[k][l].cellpiece != null)
                                    return null;
                            }
                            possiblecoordinateswhenpinned.add(new Coordinates(k,l));
                        }
                        return null;
                    }

                    possiblecoordinateswhenpinned.add(new Coordinates(i,j));

                }

                return null;
            } else {
                if(col > row) {
                    //(-x,x)

                    for(int i=originalrow+1, j=originalcol-1 ; i<8 && j>=0 ; i++,j--) {

                        if(cells[i][j].getPieceColour() == originalpiececolour)
                            return null;
                        if(cells[i][j].getPieceName() == Pieces.BISHOP || cells[i][j].getPieceName() == Pieces.QUEEN)
                        {
                            possiblecoordinateswhenpinned.add(new Coordinates(i,j));

                            //found a attacking piece
                            //should return true if there is nothing blocking the originalpiece and the king
                            //if there is some piece(any colour) between the originalpiece and the king, then return false

                            for(int k = originalrow-1, l=originalcol+1 ; k>=0 && l<8 ; k--,l++) {
                                if(cells[k][l].getPieceName() == Pieces.KING && cells[k][l].getPieceColour() == originalpiececolour)
                                    return possiblecoordinateswhenpinned;
                                else{
                                    if(cells[k][l].cellpiece != null)
                                        return null;
                                }
                                possiblecoordinateswhenpinned.add(new Coordinates(k,l));
                            }
                            return null;
                        }
                        possiblecoordinateswhenpinned.add(new Coordinates(i,j));
                    }

                    return null;
                } else {
                    //(x,x) or (-x,-x)
                    if(row < 0) {
                        //(-x,-x)

                        for(int i=originalrow+1,j=originalcol+1 ; i<8 && j<8 ; i++,j++) {

                            if(cells[i][j].getPieceColour() == originalpiececolour)
                                return null;
                            if(cells[i][j].getPieceName() == Pieces.BISHOP || cells[i][j].getPieceName() == Pieces.QUEEN)
                            {
                                possiblecoordinateswhenpinned.add(new Coordinates(i,j));
                                //found a attacking piece
                                //should return true if there is nothing blocking the originalpiece and the king
                                //if there is some piece(any colour) between the originalpiece and the king, then return false

                                for(int k = originalrow-1, l=originalcol-1 ; k>=0 && l>=0 ; k--,l--) {
                                    if(cells[k][l].getPieceName() == Pieces.KING && cells[k][l].getPieceColour() == originalpiececolour)
                                        return possiblecoordinateswhenpinned;
                                    else{
                                        if(cells[k][l].cellpiece != null)
                                            return null;
                                    }
                                    possiblecoordinateswhenpinned.add(new Coordinates(k,l));
                                }
                                return null;
                            }
                            possiblecoordinateswhenpinned.add(new Coordinates(i,j));

                        }
                        return null;

                    } else {
                        //(x,x)

                        for(int i=originalrow-1,j=originalcol-1 ; i>=0 && j>=0 ; i--,j--) {

                            if(cells[i][j].getPieceColour() == originalpiececolour)
                                return null;
                            if(cells[i][j].getPieceName() == Pieces.BISHOP || cells[i][j].getPieceName() == Pieces.QUEEN)
                            {
                                possiblecoordinateswhenpinned.add(new Coordinates(i,j));
                                //found a attacking piece
                                //should return true if there is nothing blocking the originalpiece and the king
                                //if there is some piece(any colour) between the originalpiece and the king, then return false

                                for(int k = originalrow+1, l=originalcol+1 ; k<8 && l<8 ; k++,l++) {
                                    if(cells[k][l].getPieceName() == Pieces.KING && cells[k][l].getPieceColour() == originalpiececolour)
                                        return possiblecoordinateswhenpinned;
                                    else{
                                        if(cells[k][l].cellpiece != null)
                                            return null;
                                    }
                                    possiblecoordinateswhenpinned.add(new Coordinates(k,l));
                                }
                                return null;
                            }

                            possiblecoordinateswhenpinned.add(new Coordinates(i,j));
                        }
                        return null;

                    }
                }
            }


        } else {
            return null;
        }

    }



}
