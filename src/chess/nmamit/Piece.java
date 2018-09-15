package chess.nmamit;

import javax.swing.*;



public abstract class Piece {

    ImageIcon pieceimage;
    Colour piececolour;
    boolean alivestatus;


    abstract Coordinates[] possibleMoves(Coordinates c);


}
