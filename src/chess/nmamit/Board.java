package chess.nmamit;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    JPanel boardpanel;
    static Colour turn;
    static boolean highlighted;
    static JButton highlightedbutton;
    static Cell highlightedcell;
    static Color originalcellcolour;
    static Cell cells[][];
    static Cell whitekingcell;
    static Cell blackkingcell;

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

        whitekingcell = cells[7][4];
        blackkingcell = cells[0][4];

        generateAllPossibleMoves();

    }

    void generateAllPossibleMoves() {
        for(int i=0 ; i<2 ; i++) {
            for(int j=0 ; j<8 ; j++) {
                cells[i][j].possiblecoordinates = cells[i][j].cellpiece.possibleMoves(cells[i][j]);
            }
        }

        for(int i=6 ; i<8 ; i++) {
            for(int j=0 ; j<8 ; j++) {
                cells[i][j].possiblecoordinates = cells[i][j].cellpiece.possibleMoves(cells[i][j]);
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

    public static boolean isKingAttackedIfPieceRemoved(Cell originalcell) {

        Coordinates path;

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
                    for (int j = originalcol; j < 8; j++) {

                        if (cells[originalrow][j].getPieceColour() == originalpiececolour)
                            return false;
                        if (cells[originalrow][j].getPieceName() == "rook" || cells[originalrow][j].getPieceName() == "queen")
                            return true;


                    }
                    return false;
                }

                if (col > 0) {
                    for (int j = originalcol; j >= 0; j--) {

                        if (cells[originalrow][j].getPieceColour() == originalpiececolour)
                            return false;
                        if (cells[originalrow][j].getPieceName() == "rook" || cells[originalrow][j].getPieceName() == "queen")
                            return true;

                    }
                    return false;
                }
            }

            if(col == 0) {
                if (row < 0) {
                    for (int j = originalrow; j >= 0 ; j--) {

                        if (cells[j][originalcol].getPieceColour() == originalpiececolour)
                            return false;
                        if (cells[j][originalcol].getPieceName() == "rook" || cells[j][originalcol].getPieceName() == "queen")
                            return true;


                    }
                    return false;
                }

                if (row > 0) {
                    for (int j = originalrow ; j < 8 ; j++) {

                        if (cells[j][originalcol].getPieceColour() == originalpiececolour)
                            return false;
                        if (cells[j][originalcol].getPieceName() == "rook" || cells[j][originalcol].getPieceName() == "queen")
                            return true;

                    }
                    return false;
                }
            }

            //abs(row) == abs(col)

            if(row > col) {
                //(x,-x)

                for(int i=originalrow, j=originalcol ; i<8 && j>=0 ; i++,j--) {

                    if(cells[i][j].getPieceColour() == originalpiececolour)
                        return false;
                    if(cells[i][j].getPieceName() == "bishop" || cells[i][j].getPieceName() == "queen")
                        return true;

                }

                return false;
            } else {
                if(col > row) {
                    //(-x,x)

                    for(int i=originalrow, j=originalcol ; i>=0 && j<8 ; i--,j++) {

                        if(cells[i][j].getPieceColour() == originalpiececolour)
                            return false;
                        if(cells[i][j].getPieceName() == "bishop" || cells[i][j].getPieceName() == "queen")
                            return true;

                    }

                    return false;
                } else {
                    //(x,x) or (-x,-x)
                    if(row < 0) {
                        //(-x,-x)

                        for(int i=originalrow,j=originalcol ; i>=0 && j>=0 ; i--,j--) {

                            if(cells[i][j].getPieceColour() == originalpiececolour)
                                return false;
                            if(cells[i][j].getPieceName() == "bishop" || cells[i][j].getPieceName() == "queen")
                                return true;

                        }
                        return false;

                    } else {
                        //(x,x)

                        for(int i=originalrow,j=originalcol ; i<8 && j<8 ; i++,j++) {

                            if(cells[i][j].getPieceColour() == originalpiececolour)
                                return false;
                            if(cells[i][j].getPieceName() == "bishop" || cells[i][j].getPieceName() == "queen")
                                return true;

                        }
                        return false;

                    }
                }
            }


        } else {
            return false;
        }

    }



}
