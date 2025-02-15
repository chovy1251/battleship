package edu.duke.yx304.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {

    T myData;
    T onHit;

    public SimpleShipDisplayInfo(T t1,T t2){
        this.myData = t1;
        this.onHit = t2;
    }

    @Override
    public T getInfo(Coordinate where, boolean hit) {
        if (hit) {
            return this.onHit;
        }
        else{
            return this.myData;
        }
    }

}
