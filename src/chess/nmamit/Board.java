package chess.nmamit;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    JPanel boardpanel;
    static Colour turn;
    static boolean highlighted;
    static JButton highlightedbutton;
    static Cell highlightedcell;
    static Color originalcellcolour;
    static Cell cells[][];



    Board() {
        boardpanel = new JPanel();
        highlighted = false;
        highlightedbutton = null;
        originalcellcolour = null;
        turn = Colour.WHITE;


        boardpanel.setSize(800, 800);
        boardpanel.setLayout(new GridLayout(8, 8));

        cells = new Cell[8][8];

        drawBoardAndAddToPanel(cells);

        boardpanel.setVisible(true);
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

        generateAllPossibleMoves();
    }

    void generateAllPossibleMoves() {
        for(int i=0 ; i<2 ; i++) {
            for(int j=0 ; j<8 ; j++) {
                cells[i][j].cellpiece.possiblecoordinates = cells[i][j].cellpiece.possibleMoves(cells[i][j]);
            }
        }

        for(int i=6 ; i<8 ; i++) {
            for(int j=0 ; j<8 ; j++) {
                cells[i][j].cellpiece.possiblecoordinates = cells[i][j].cellpiece.possibleMoves(cells[i][j]);
            }
        }
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

        //if condition only for now till we implement all the possiblemoves
        if(highlightedcell.cellpiece.possiblecoordinates != null)
            for(Coordinates coordinates : highlightedcell.cellpiece.possiblecoordinates) {
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
                highlightedcell = c;
                highlightedbutton = c.cellbutton;                                       //Cell's button generated the event.
                originalcellcolour = highlightedbutton.getBackground();
                highlightedbutton.setBackground(new Color(228, 230, 11));

                highlighted = true;

                //if condition only for now till we implement all the possiblemoves
                if(c.cellpiece.possiblecoordinates != null)
                    for(Coordinates coordinates : c.cellpiece.possiblecoordinates) {
                        cells[coordinates.x][coordinates.y].cellbutton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,new Color(228, 230, 11)));
                    }

                //need to call possiblemoves
                //if he makes a move, change turn
            }
/*
        else {

            if(c.cellpiece == null) {

                //a square containing a piece is highlighted and we clicked on a square with no piece, we have 2 options now: can select a square
                //                                                                                                              that belongs to the
                //                                                                                                              set of coordinates
                //                                                                or can select some other square

                // if i selected a square that doesn't belong to set of coordinates that piece can go to, then unhighlight the highlighted square

                highlightedbutton.setBackground(originalcellcolour);

                highlightedbutton = null;
                originalcellcolour = null;
                highlighted = false;

            } else {

                if(correctColour(c)) {


                    highlightedbutton.setBackground(originalcellcolour);    //change previously selected button to the original cell colour

                    highlightedbutton = c.cellbutton;  //now highlightedbutton contains the new selected button



                    originalcellcolour = highlightedbutton.getBackground();
                    highlightedbutton.setBackground(new Color(237, 253, 153));

                    highlighted = true;

                    //need to call possiblemoves
                } else {
                    //we clicked on a piece of opposite colour
                    highlightedbutton.setBackground(originalcellcolour);

                    highlightedbutton = null;
                    originalcellcolour = null;
                    highlighted = false;

                }
            }


        }*/
    }

    static boolean correctColour(Cell c) {
        if(turn == c.cellpiece.piececolour)
            return true;
        else
            return false;
    }

    public static boolean sameColourPiece(Cell c, int newrow,int newcol) {

        if(cells[newrow][newcol].cellpiece == null)
            return false;

        Colour originalpiececolour = c.cellpiece.piececolour;
        Colour newpiececolour = cells[newrow][newcol].cellpiece.piececolour;

        if(originalpiececolour == newpiececolour) {
            return true;
        } else {
            return false;
        }
    }



}
