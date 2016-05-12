package by.bsuir;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;
import java.lang.String;

/**
 * Created by User on 29.03.2016.
 */
public class Group {
    Integer[][] table;

    public class strct{
        int distance;
        public List<Integer> list = new ArrayList<Integer>();

        public strct(int idistance){
            distance = idistance;
        }
    }

    public Group(int count){
        Random r = new Random();
        table = new Integer[count][count];
        for(int i = 0; i < count; i++){
            table[i][i] = 0;
            for (int j = i + 1; j < count; j++){
                table[i][j] = r.nextInt(count) + 1;
                table[j][i] = table[i][j];
            }
        }
    }

    public String[][] returnGroup(){
        String[][] array = new String[table.length + 1][table.length + 1];
        for(int i = 0; i < table.length; i++){
            array[i + 1][0] = "x" + (i + 1);
            array[0][i + 1] = "x" + (i + 1);
        }
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table.length; j++){
                array[i + 1][j + 1] = String.valueOf(table[i][j]);
            }
        }
        return array;
    }

    public ArrayList<strct> findMin(){
        ArrayList<Struct> list = new ArrayList<Struct>();
        for(int i = 0; i < table.length; i++){
            for(int j = i + 1; j < table.length; j++){
                list.add(new Struct((i + 1), (j + 1), table[i][j]));
            }
        }
        Collections.sort(list);
        int minDist = 1;
        Set<Integer> newl = new HashSet<Integer>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).distance == minDist ){
                if(!newl.contains(list.get(i).x1) || !newl.contains(list.get(i).x2)){
                    newl.add(list.get(i).x1);
                    newl.add(list.get(i).x2);
                }
                else{
                    list.remove(i);
                    i--;
                }
            }
            else{
                if(newl.contains(list.get(i).x1) && newl.contains(list.get(i).x2)){
                    list.remove(i);
                    i--;
                }
                else{
                    if(newl.contains(list.get(i).x1) || newl.contains(list.get(i).x2)) {
                        minDist = list.get(i).distance;
                        newl.add(list.get(i).x1);
                        newl.add(list.get(i).x2);
                    }
                }
            }
        }
        System.out.println(list);
        return forDrawing(list);
    }

    public ArrayList<strct> forDrawing(ArrayList<Struct> list){ // отрисовка
        // для классификации по минимуму
        ArrayList<strct> draw = new ArrayList<strct>();
        int mind = 0;
        Set<Integer> s = new HashSet<Integer>();
        for(int i = 0; i < list.size(); i++){
            if (mind < list.get(i).distance){
                mind = list.get(i).distance;
                if(!s.contains(list.get(i).x1) || !s.contains(list.get(i).x2)) {
                    draw.add(new strct(mind));
                    if (!s.contains(list.get(i).x1)) {
                        draw.get(draw.size() - 1).list.add(list.get(i).x1);
                        s.add(list.get(i).x1);
                    }
                    if (!s.contains(list.get(i).x2)) {
                        s.add(list.get(i).x2);
                        draw.get(draw.size() - 1).list.add(list.get(i).x2);
                    }
                }
            }
            else{
                if(!s.contains(list.get(i).x1)) {
                    draw.get(draw.size() - 1).list.add(list.get(i).x1);
                    s.add(list.get(i).x1);
                }
                if(!s.contains(list.get(i).x2)) {
                    s.add(list.get(i).x2);
                    draw.get(draw.size() - 1).list.add(list.get(i).x2);
                }
            }
        }
        return draw;
    }

    public ArrayList<strct> findMax(){
        ArrayList<Struct> list = new ArrayList<Struct>();
        for(int i = 0; i < table.length; i++){
            for(int j = i + 1; j < table.length; j++){
                list.add(new Struct((i + 1), (j + 1), table[i][j]));
            }
        }
        Collections.sort(list);
        int maxDist = 10;
        Set<Integer> newl = new HashSet<Integer>();
        for(int i = list.size() - 1; i >= 0; i--){
            if(list.get(i).distance == maxDist ){
                if(!newl.contains(list.get(i).x1) || !newl.contains(list.get(i).x2)){
                    newl.add(list.get(i).x1);
                    newl.add(list.get(i).x2);
                }
                else{
                    list.remove(i);
                    i--;
                }
            }
            else{
                if(newl.contains(list.get(i).x1) && newl.contains(list.get(i).x2)){
                    list.remove(i);
                    i--;
                }
                else{
                    if(newl.contains(list.get(i).x1) || newl.contains(list.get(i).x2)) {
                        maxDist = list.get(i).distance;
                        newl.add(list.get(i).x1);
                        newl.add(list.get(i).x2);
                    }
                }
            }
        }
        System.out.println(list);
        return forDrawing1(list);
    }

    public ArrayList<strct> forDrawing1(ArrayList<Struct> list){
        // отрисовка для классификации по максимуму
        ArrayList<strct> draw = new ArrayList<strct>();
        int maxdist = 11;
        Set<Integer> s = new HashSet<Integer>();
        for(int i = list.size() - 1; i >= 0; i--){
            if (maxdist > list.get(i).distance){
                maxdist = list.get(i).distance;
                if(!s.contains(list.get(i).x1) || !s.contains(list.get(i).x2)) {
                    draw.add(new strct(maxdist));
                    if (!s.contains(list.get(i).x1)) {
                        draw.get(draw.size() - 1).list.add(list.get(i).x1);
                        s.add(list.get(i).x1);
                    }
                    if (!s.contains(list.get(i).x2)) {
                        s.add(list.get(i).x2);
                        draw.get(draw.size() - 1).list.add(list.get(i).x2);
                    }
                }
            }
            else{
                if(!s.contains(list.get(i).x1)) {
                    draw.get(draw.size() - 1).list.add(list.get(i).x1);
                    s.add(list.get(i).x1);
                }
                if(!s.contains(list.get(i).x2)) {
                    s.add(list.get(i).x2);
                    draw.get(draw.size() - 1).list.add(list.get(i).x2);
                }
            }
        }

        return draw;
    }
}
