package by.bsuir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//МЕТОДОМ ПОТЕНЦИАЛОВ

public class Main extends JFrame {

    JPanel panel;
    TextField o1x1;
    TextField o1x2;
    TextField o2x1;
    TextField o2x2;
    TextField o3x1;
    TextField o3x2;
    TextField o4x1;
    TextField o4x2;
    Potential p;
    Graphics gr;

    public Main( String str){
        super(str);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.setVisible(true);

        // class 1
        Label cl1 = new Label();
        cl1.setText("Class 1");
        cl1.setBounds(35, 0, 50, 20);
        add(cl1);
        o1x1 = new TextField();
        o1x1.setText("-1");
        o1x1.setBounds(15, 20, 40, 20);
        add(o1x1);
        Label l = new Label();
        l.setText("x1");
        l.setBounds(0, 20, 15, 20);
        add(l);
        o1x2 = new TextField();
        o1x2.setText("0");
        o1x2.setBounds(70, 20, 40, 20);
        add(o1x2);
        Label l1 = new Label();
        l1.setText("x2");
        l1.setBounds(55, 20, 15,20);
        add(l1);
        o2x1 = new TextField();
        o2x1.setText("1");
        o2x1.setBounds(15, 50, 40, 20);
        add(o2x1);
        Label l2 = new Label();
        l2.setText("x1");
        l2.setBounds(0, 50, 15, 20);
        add(l2);
        o2x2 = new TextField();
        o2x2.setText("1");
        o2x2.setBounds(70, 50, 40, 20);
        add(o2x2);
        Label l3 = new Label();
        l3.setText("x2");
        l3.setBounds(55, 50, 15, 20);
        add(l3);

        //class 2
        Label cl2 = new Label();
        cl2.setText("Class 2");
        cl2.setBounds(220, 0, 50, 20);
        add(cl2);
        o3x1 = new TextField();
        o3x1.setText("2");
        o3x1.setBounds(200, 20, 40, 20);
        add(o3x1);
        Label l4 = new Label();
        l4.setText("x1");
        l4.setBounds(185, 20, 15, 20);
        add(l4);
        o3x2 = new TextField();
        o3x2.setText("0");
        o3x2.setBounds(255, 20, 40, 20);
        add(o3x2);
        Label l5 = new Label();
        l5.setText("x2");
        l5.setBounds(240, 20, 40, 20);
        add(l5);
        o4x1 = new TextField();
        o4x1.setText("1");
        o4x1.setBounds(200, 50, 40, 20);
        add(o4x1);
        Label l6 = new Label();
        l6.setText("x1");
        l6.setBounds(185, 50, 15, 20);
        add(l6);
        o4x2 = new TextField();
        o4x2.setText("-2");
        o4x2.setBounds(255, 50, 40, 20);
        add(o4x2);
        Label l7 = new Label();
        l7.setText("x2");
        l7.setBounds(240, 50, 15, 20);
        add(l7);
        Button button = new Button();
        button.setLabel("OK");
        button.setBounds(330, 35, 50, 20);
        add(button);

        add(panel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                try{
                    pressed(e.getX(), e.getY());
                }catch(Exception ex){}

            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gr = getGraphics();
                gr.setColor(Color.WHITE);
                gr.fillRect(0, 0, getWidth(), getHeight());
                drawGraphics(gr);
            }
        });
    }
    public static void main(String[] args) {
        Main frame = new Main("МЕТОД ПОТЕНЦИАЛОВ");
        frame.setBounds(350, 100, 500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void drawGraphics(Graphics gr){
        try{
            gr.setColor(Color.BLACK);
            int x1 = Integer.parseInt(o1x1.getText());
            int x2 = Integer.parseInt(o1x2.getText());
            Object o1 = new Object(x1, x2);
            x1 = Integer.parseInt(o2x1.getText());
            x2 = Integer.parseInt(o2x2.getText());
            Object o2 = new Object(x1, x2);
            x1 = Integer.parseInt(o3x1.getText());
            x2 = Integer.parseInt(o3x2.getText());
            Object o3 = new Object(x1, x2);
            x1 = Integer.parseInt(o4x1.getText());
            x2 = Integer.parseInt(o4x2.getText());
            Object o4 = new Object(x1, x2);

            gr.drawLine(10, 305, 480, 305);
            gr.drawLine(245, 150, 245, 460);
            p = new Potential(o1, o2, o3, o4); // подсчет функции
            for(int i = 0; i < 5; i++){ // деления на прямой
                gr.drawLine(20 + i * 45, 303, 20 + i * 45, 307);
                gr.drawLine(20 + (i + 6) * 45, 303, 20 + (i + 6) * 45, 307);
            }
            for( int i =0; i < 3; i++){ // деления на прямой
                gr.drawLine(243, 170 + i * 45, 247, 170 + i * 45 );
                gr.drawLine(243, 170 + (i + 4) * 45, 247, 170 + (i + 4) * 45 );
            }
            if(p.number > 0) { // отрисовка графика
                for (int i = 0; i < 500; i++) {
                    double ii = i * 1.0 / 45;
                    int y = (int) (45 * p.graphic(-1 * ii));
                    int y1 = (int) (45 * p.graphic(ii));
                    gr.drawOval(245 - i, 305 - y, 1, 1);
                    gr.drawOval(i + 245, 305 - y1, 1, 1);
                }

                gr.setColor(Color.green);
                gr.fillOval(245 + o1.x1 * 45, 305 - o1.x2 * 45, 6, 6);
                gr.fillOval(245 + o2.x1 * 45, 305 - o2.x2 * 45, 6, 6);

                gr.setColor(Color.red);
                gr.fillOval(245 + o3.x1 * 45, 305 - o3.x2 * 45, 6, 6);
                gr.fillOval(245 + o4.x1 * 45, 305 - o4.x2 * 45, 6, 6);
            }
            else
                JOptionPane.showMessageDialog(null, "Невозможно разделить на 2 класса");
        }catch (Exception ex){

        }
    }

    public void pressed(int x, int y){
        double x1 = 1.0 * (x - 3 - 245) / 45;
        double y1 = -1.0 * (y - 3 - 305) / 45;

        if(p.calculate(x1, y1) > 0){
            System.out.println("1 class");
            gr.setColor(Color.green);
            gr.fillOval(x - 3, y - 3, 6, 6);
        }
        else
        {
            System.out.println("2 class");
            gr.setColor(Color.red);
            gr.fillOval(x - 3, y - 3, 6, 6);
        }
    };

}
