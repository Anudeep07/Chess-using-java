package chess.nmamit;

/*
 *This class is used to denote the coordinates of a board. x=0,y=0 is a8, x=7,y=7 is h1
 */

import java.io.Serializable;

public class Coordinates implements Serializable {
    public int x,y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Colour getCoordinateColour() {
        if((x+y)%2 == 0) {
            return Colour.WHITE;
        } else {
            return Colour.BLACK;
        }
    }



    @Override
    public boolean equals(Object obj) {

        if(obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Coordinates coordinates = (Coordinates) obj;
        return this.x == coordinates.x && this.y == coordinates.y;
    }
}
