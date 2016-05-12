package by.bsuir;

import sun.plugin2.message.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main extends JFrame{

    JTable table;
    JTextField countField;
    int count;
    JRadioButton rb1;
    JRadioButton rb2;
    Group group;
    JPanel mainPanel;
    public Main(){

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        add(mainPanel);

        JLabel label = new JLabel("Count: ");
        label.setBounds(10, 10, 50, 20);
        mainPanel.add(label);

        countField = new JTextField("10");
        countField.setBounds(70, 10, 50, 20);
        mainPanel.add(countField);

        ButtonGroup bG = new ButtonGroup();

        JLabel lrb1 = new JLabel("Min: ");
        lrb1.setBounds(130, 10, 30, 20);
        mainPanel.add(lrb1);

        rb1 = new JRadioButton();
        rb1.setBounds(155, 10, 20, 20);
        mainPanel.add(rb1);
        rb1.setSelected(true);
        bG.add(rb1);

        JLabel lrb2 = new JLabel("Max: ");
        lrb2.setBounds(180, 10, 30, 20);
        mainPanel.add(lrb2);

        rb2 = new JRadioButton();
        rb2.setBounds(210, 10, 20, 20);
        mainPanel.add(rb2);
        bG.add(rb2);

        JButton button = new JButton("Ok");
        button.setBounds(40, 40, 50, 20);
        mainPanel.add(button);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    count = Integer.parseInt(countField.getText());
                    if(count <= 10 && count > 1) {
                        group = new Group(count);
                        String[] columnsNames = new String[count + 1];
                        for (int i = 1; i < count + 2; i++) {
                            columnsNames[i - 1] = "x" + i;
                        }
                        table = new JTable(group.returnGroup(), columnsNames);
                        table.setPreferredScrollableViewportSize(new Dimension(550, 70));
                        table.setBounds(0, 70, 550, 180);
                        mainPanel.add(table);
                        table.validate();
                        Graphics2D gr = (Graphics2D) getGraphics();
                        paintGraphic(gr);
                    }
                    else
                        System.out.println("Enter value > 1 and <= 10");
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

    }
    int y1, x1;
    public void paintGraphic(Graphics2D gr){
        gr.setStroke(new BasicStroke(2.0f));
        gr.setColor(Color.white);
        gr.fillRect(0, 200, 550, 550);
        gr.setColor(Color.black);
        for(int i = 0; i < count + 2; i++) {
            gr.setColor(Color.black);
            y1 = (getWidth() - 350) /(count + 1);
            gr.drawLine(25, 300 + i * y1, 519, 300 + i * y1);
            x1 = (getWidth() - 50) / (count + 1);
            gr.drawLine(25 + i * x1, 300, 25 + x1 * i, 498);
        }
        ArrayList<Group.strct> list;
        if(rb1.isSelected()) // проверка на check
            list = group.findMin();
        else
            list = group.findMax();

        Set<Integer> set = new HashSet<Integer>();
        int k = 1;
        int newRangecount = 0;
        int min = 0;
        gr.setStroke(new BasicStroke(5.0f));
        Random r = new Random();
        for( int i = 0; i < list.size(); i++){
            gr.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
            for(int j = 0; j < list.get(i).list.size(); j++){
                gr.drawLine(25 + k * x1, 498 - list.get(i).distance * y1, 25 + k * x1, 498);
                gr.drawString("x" + list.get(i).list.get(j), 25 + k * x1, 498 + 15);
                if(j != 0){
                    gr.drawLine(25 + (k - 1) * x1, 498 - list.get(i).distance * y1, 25 + (k) * x1, 498 - list.get(i).distance * y1);
                }
                else{
                    if(i != 0){
                        int x = 0;
                        if(list.get(i - 1).list.size() > 1) {
                            x = (int) ((k - 1 - (list.get(i - 1).list.size() - 1) * 1.0 / 2) * x1);
                        }
                        else
                            x = (int) ((k - 1 - list.get(i - 1).list.size() * 1.0 / 2) * x1);
                        gr.drawLine(25 + x, 498 - list.get(i).distance * y1, 25 + x, 498 - list.get(i - 1).distance * y1);
                        gr.drawLine(25 + x, 498 - list.get(i).distance * y1, 25 + (k) * x1, 498 - list.get(i).distance * y1);
                    }
                }
                set.add(list.get(i).list.get(j));
                k++;
            }
        }

    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setBounds(300, 100, 550, 550);
        frame.setTitle("laba 6");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}
