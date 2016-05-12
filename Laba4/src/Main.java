import java.util.*;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        new Main();
    }


    int countOfClasses, countOfObjects, countOfFeatures;
    Scanner in = new Scanner(System.in);

    public Main() {
        do {
            countOfClasses = check("Введите количество классов:");
            countOfObjects = check("Введите количество обучающих объектов:");
            countOfFeatures = check("Введите количество признаков:");
            Perseptron p = calculate();
            boolean t = false;
            do {
                int c;
                c = check("Для проверки объекта нажмите 1, для новой классификации любую другую цифру");
                switch (c) {
                    case 1:
                        List<Integer> x = new ArrayList<Integer>();
                        System.out.println("Введите данные объекта: ");
                        for (int i = 0; i < countOfFeatures; i++) {
                            int x1;
                            x1 = check("Введите x" + (i + 1) + ":");
                            x.add(x1);
                        }
                        System.out.format("Объект принадлежит классу  %d\n", p.calculateClassNumber(x));
                        break;
                    default:
                        t = true;
                        break;

                }
            } while (!t);
        } while (true);
    }

    private Perseptron calculate() {
        List<ClassInfo> info = new ArrayList<ClassInfo>();
        for (int i = 0; i < countOfClasses; i++) {
            info.add(new ClassInfo(countOfObjects, countOfFeatures + 1));
        }
        soutClasses(info);
        Perseptron p = new Perseptron(countOfClasses, countOfFeatures + 1, info);
        p.calculateSteps();
        return p;
    }

    private int check(String message) {
        boolean t = true;
        int res = 0;
        do {
            System.out.println(message);
            try {
                String str = in.nextLine();
                res = Integer.valueOf(str);
                t = true;
            } catch (Exception ex) {
                System.out.println("Введите целое число");
                t = false;
            }
        } while (!t);
        return res;
    }

    private void soutClasses(List<ClassInfo> classes) {
        System.out.println("\nСозданная выборка:");
        for (int i = 0; i < countOfClasses; i++) {
            System.out.println("Класс " + (i + 1));
            System.out.println(classes.get(i).toString());
        }
    }

}
