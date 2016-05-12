import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Perseptron {

    List<int[]> wvectors;
    List<Integer> corrections;
    final List<ClassInfo> classes;
    List<Integer> forCorrections;

    public Perseptron(int countOfClasses, int countOfFeatures, List<ClassInfo> _classes) {
        wvectors = new ArrayList<int[]>();
        classes = _classes;
        for (int i = 0; i < countOfClasses; i++) {
            wvectors.add(new int[countOfFeatures]);
        }
    }

    public void calculateSteps() {
        System.out.println("Итерация 1 для класса 1 объекта 1:");
        for (int i = 0; i < wvectors.size(); i++) {
            for (int j = 0; j < wvectors.get(i).length; j++) {
                if (i != 0) {
                    wvectors.get(i)[j] -= classes.get(0).objects.get(0).features[j];
                } else {
                    wvectors.get(i)[j] += classes.get(0).objects.get(0).features[j];
                }

            }
        }
        tring();
        boolean t = false;
        int classnumber = 0;
        int objectNumber = 0;
        int iteration = 1;
        int countOfIterations = 1;
        boolean endit = false;
        do {
            iteration++;
            if(iteration > 1000){
                break;
            }
            if (objectNumber + 1 == classes.get(0).objects.size()) {
                classnumber = (classnumber + 1) % classes.size();
            }
            objectNumber = (objectNumber + 1) % classes.get(0).objects.size();
            if (calculateSummary(classnumber, objectNumber)) {
                System.out.format("Итерация %d для класса %d объекта %d:\n", iteration, classnumber + 1, objectNumber + 1);
                for (int i = 0; i < wvectors.size(); i++) {
                    for (int j = 0; j < wvectors.get(i).length; j++) {
                        if (!forCorrections.contains(i)) {
                            wvectors.get(i)[j] -= classes.get(classnumber).objects.get(objectNumber).features[j];
                        } else {
                            wvectors.get(i)[j] += classes.get(classnumber).objects.get(objectNumber).features[j];
                        }

                    }
                }
                tring();
                t = true;
            }
            else{
                System.out.format("Итерация %d для класса %d объекта %d не требует коррекции.\n", iteration, classnumber + 1, objectNumber + 1);
            }
            countOfIterations++;
            if(countOfIterations == classes.size()*classes.get(0).objects.size()){
                countOfIterations = 0;
                if(!t){
                    endit = true;
                }
                t = false;
            }
        } while (!endit);
        System.out.println("\nОкончательный результат: ");
        result();
        System.out.println();
    }

    public void tring() {
        for (int i = 0; i < wvectors.size(); i++) {
            for (int j = 0; j < wvectors.get(i).length; j++) {
                System.out.print(wvectors.get(i)[j] + " ");
            }
            System.out.println();
        }
    }

    public void result() {
        for (int i = 0; i < wvectors.size(); i++) {
            System.out.format("d%d(x)= ", (i + 1));
            boolean t = false;
            for (int j = 0; j < wvectors.get(i).length; j++) {
                if (wvectors.get(i)[j] != 0) {
                    if (t) {
                        System.out.print(" + ");
                    }
                    if (j + 1 != wvectors.get(i).length) {
                        System.out.format("%d*x%d", wvectors.get(i)[j], (j + 1));
                    } else {
                        System.out.print(wvectors.get(i)[j]);
                    }
                    t = true;
                }
            }
            System.out.println();
        }
    }

    private boolean calculateSummary(int number, int objectNumber) {
        corrections = new ArrayList<Integer>();
        forCorrections = new ArrayList<Integer>();
        System.out.format("\nКоррекции для класса %d объекта %d:\n", (number + 1), (objectNumber + 1));
        for (int i = 0; i < wvectors.size(); i++) {
            int result = 0;
            for (int j = 0; j < wvectors.get(i).length; j++) {
                result += wvectors.get(i)[j] * classes.get(number).objects.get(objectNumber).features[j];
            }
            System.out.print(result + " ");
            corrections.add(result);
        }
        System.out.println();
        int forCompare = corrections.get(number);
        for(int i = 0; i < corrections.size(); i++){
            if(forCompare > corrections.get(i)){
                forCorrections.add(i);
            }
        }

        forCorrections.add(number);

        Collections.sort(corrections);
        if (forCompare <= corrections.get(0)) {
            System.out.println(forCompare + " <= остальных элементов\n");
            return true;
        } else {
            System.out.println(forCompare + " > остальных элементов\n");
            return false;
        }
    }

    public int calculateClassNumber(List<Integer> list) {
        int res = 0;
        int classnumber = 0;
        for (int i = 0; i < wvectors.size(); i++) {
            int max = 0;
            for (int j = 0; j < list.size(); j++) {
                max += wvectors.get(i)[j] * list.get(j);
            }
            if(i == 0){
                res = max;
                classnumber = i;
            }
            else
            {
                if(res < max){
                    res = max;
                    classnumber = i;
                }
            }
        }

        return classnumber + 1;
    }
}
