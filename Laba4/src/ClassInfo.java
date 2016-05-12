import java.util.ArrayList;
import java.util.List;

public class ClassInfo {

    class Object{
        int[] features;
        Object(int countOfFeatures){
            features = new int[countOfFeatures];
            for(int i = 0; i < countOfFeatures - 1; i++){
                features[i] = (int)(Math.random()*3) - 1;
            }
            features[countOfFeatures - 1] = 1;
        }
    }

    List<Object> objects;

    public ClassInfo(int countOfClasses, int countOFFeatures){
        objects = new ArrayList<Object>();
        for(int i = 0; i < countOfClasses; i++){
            objects.add(new Object(countOFFeatures));
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < objects.size(); i++){
            str.append("Объект" + (i + 1) + " (");
            for(int j = 0; j < objects.get(i).features.length; j++){
                str.append(objects.get(i).features[j]);
                if(j + 1 != objects.get(i).features.length){
                    str.append(", ");
                }
            }
            str.append(");\n");
        }
        return str.toString();
    }
}
