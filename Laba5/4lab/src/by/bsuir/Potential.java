package by.bsuir;

/**
 * Created by User on 28.03.2016.
 */
public class Potential {
    Object o1;
    Object o2;
    Object o3;
    Object o4;
    int K0;
    int number;

    public Potential(Object i1, Object i2, Object i3, Object i4){
        o1 = i1;
        o2 = i2;
        o3 = i3;
        o4 = i4;

        int x1, x2;
        double K1 = 0, K2 = 0, K3 = 0, K4;
        K0 = 0;
        K1 = K1_f(o2.x1, o2.x2); //потенциальная функция o2 - второй объект первого класса
        K2 = K2_f(o3.x1, o3.x2); // первый объект 2 класса
        K3 = K3_f(o4.x1, o4.x2); // второй объект 2 класса
        K4 = K3_f(o1.x1, o1.x2); // первый объект 1 класса

        if(K1_f(o1.x1, o1.x2) < 0 && K1_f(o2.x1, o2.x2) < 0 && K1_f(o3.x1, o3.x2) >= 0 && K1_f(o4.x1, o4.x2) >= 0){
            number = 1;
        }
        if(K1_f(o1.x1, o1.x2) >= 0 && K1_f(o2.x1, o2.x2) >= 0 && K1_f(o3.x1, o3.x2) < 0 && K1_f(o4.x1, o4.x2) < 0){
            number = 1;
        }
        if(K2_f(o1.x1, o1.x2) < 0 && K2_f(o2.x1, o2.x2) < 0 && K2_f(o3.x1, o3.x2) >= 0 && K2_f(o4.x1, o4.x2) >= 0){
            number = 2;
        }
        if(K2_f(o1.x1, o1.x2) >= 0 && K2_f(o2.x1, o2.x2) >= 0 && K2_f(o3.x1, o3.x2) < 0 && K2_f(o4.x1, o4.x2) < 0){
            number = 2;
        }
        if(K3_f(o1.x1, o1.x2) < 0 && K3_f(o2.x1, o2.x2) < 0 && K3_f(o3.x1, o3.x2) >= 0 && K3_f(o4.x1, o4.x2) >= 0){
            number = 3;
        }
        if(K3_f(o1.x1, o1.x2) >= 0 && K3_f(o2.x1, o2.x2) >= 0 && K3_f(o3.x1, o3.x2) < 0 && K3_f(o4.x1, o4.x2) < 0){
            number = 3;
        }
        if(K4 == 0){
            number = 4;
        }
        System.out.println(K1);
        System.out.println(K2);
        System.out.println(K3);
        System.out.println(number);
    }

    private double K1_f(double x1, double x2){
        return K0 + 1 + 4 * x1 * o1.x1 + 4 * x2 * o1.x2 + 16 * x1 * x2 * o1.x1 * o1.x2;
    }
    private double K2_f(double x1, double x2){
        return K1_f(x1, x2) + 1 + 4 * x1 * o2.x1 + 4 * x2 * o2.x2 + 16 * x1 * x2 * o2.x1 * o2.x2;
    }
    private double K3_f(double x1, double x2){
        return K2_f(x1, x2) - (1 + 4 * x1 * o3.x1 + 4 * x2 * o3.x2 + 16 * x1 * x2 * o3.x1 * o3.x2);
    }

    public double graphic(double x1){
        double ch;
        double zn;
        double d = 0;
        switch (number){
            case 1: ch = - (1 + 4 * x1 * o1.x1);
                    zn = 4 * o1.x2 + 16 * x1 * o1.x1 * o2.x2;
                    d = ch / zn;
                break;
            case 2: ch = - (2 + 4 * x1 * (o1.x1 + o2.x1));
                    zn = 4 * (o1.x2 + o2.x2) + 16 * x1 * (o1.x1 * o1.x2 + o2.x1 * o2.x2);
                    d = ch / zn;
                break;
            case 3: ch =  - (1 + 4 * x1 * ( o1.x1 + o2.x1 - o3.x1));
                    zn = 4 * (o1.x2 + o2.x2 - o3.x2) + 16 * x1 *(o1.x1 * o1.x2 + o2.x1 * o2.x2 - o3.x1 * o3.x2);
                    d = ch / zn;
                break;
            case 4:
                ch =  - (1 + 4 * x1 * ( o1.x1 + o2.x1 - o3.x1));
                zn = 4 * (o1.x2 + o2.x2 - o3.x2) + 16 * x1 *(o1.x1 * o1.x2 + o2.x1 * o2.x2 - o3.x1 * o3.x2);
                d = ch / zn;
                break;
            default: break;
        }
        return d;
    }

    public double calculate(double x, double y){
        double d = 0; //результат функции
        switch (number){
            case 1: d = K1_f(x, y);
                break;
            case 2: d = K2_f(x, y);
                break;
            case 3: d = K3_f(x, y);
                break;
            case 4: d = K3_f(x,y);
                break;
        }
        return d;
    }
}
