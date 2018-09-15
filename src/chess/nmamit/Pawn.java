package chess.nmamit;

import javax.swing.*;
import java.io.File;

public class Pawn extends Piece {

    Pawn(Colour c) {

        //black pawn
        if(c == Colour.BLACK) {
            pieceimage = createImageIcon("img/BlackPawn.png");
            //pieceimage = new ImageIcon("../../../img/BlackPawn.png");
            piececolour = Colour.BLACK;
            alivestatus = true;
        }
        else {  //white pawn
            pieceimage = createImageIcon("img/WhitePawn.png");
           // pieceimage = new ImageIcon("img/WhitePawn.png");
            piececolour = Colour.WHITE;
            alivestatus = true;
        }

    }

    Coordinates[] possibleMoves(Coordinates c) {

        Coordinates[] x = new Coordinates[2];
        return x;
    }

    private ImageIcon createImageIcon(String path) {

        if(new File(path).exists()) {
            return new ImageIcon(path);
        } else {
            return null;
        }
    }
}
