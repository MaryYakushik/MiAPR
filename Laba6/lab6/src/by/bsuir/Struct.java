package by.bsuir;

import org.omg.CORBA.Object;

/**
 * Created by User on 29.03.2016.
 */
public class Struct implements  Comparable{
    Integer x1;
    Integer x2;
    int distance;

    public Struct(Integer x1, Integer x2, int distance){
        this.x1 = x1;
        this.x2 = x2;
        this.distance = distance;
    }

    @Override
    public int compareTo(java.lang.Object o) {
        Struct st = (Struct)o;
        if(distance < st.distance)
            return -1;
        else{
            if(distance > st.distance)
                return  1;
            else
                return 0;
        }

    }

    @Override
    public String toString(){
        return distance + " " + x1 + " " + x2 + "\n";
    }
}
