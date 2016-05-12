package my;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;


public class Work1 extends JFrame {


    table t = new table();
    table t2 = new table();
    private JPanel panelNew;
    int[][] array = new int[16][16];
    int[][] minArray = new int[16][8];
    int[][] tableArray = new int[256][128];

    public Work1() {


        super("My");
        panelNew = new JPanel();
        add(panelNew);
        panelNew.setLayout(null);

        panelNew.setBackground(Color.white);


        JButton btn = new JButton("Class 1");
        JButton btn2 = new JButton("Class 2");
        JButton btn3 = new JButton("Choose picture");
        JButton btn4 = new JButton("Result");
        JButton btn5 = new JButton("Weight");
        JButton btn6 = new JButton("Wrong");
        JButton btn7 = new JButton("Wrong");
        JButton btn8 = new JButton("TableWeight1");
        JButton btn9 = new JButton("TableWeight2");

        JTextField field = new JTextField(10);
        JTextField field2 = new JTextField(10);


        panelNew.add(btn);
        panelNew.add(btn2);
        panelNew.add(btn3);
        panelNew.add(btn4);
        panelNew.add(btn5);
        panelNew.add(btn6);
        panelNew.add(btn7);
        panelNew.add(field);
        panelNew.add(field2);
        panelNew.add(btn8);
        panelNew.add(btn9);

        btn.setBounds(0, 0, 150, 30);
        btn2.setBounds(160, 0, 150, 30);
        btn3.setBounds(320, 0, 150, 30);
        btn4.setBounds(480, 0, 150, 30);
        btn5.setBounds(0, 40, 150, 30);
        btn6.setBounds(120, 220, 100, 20);
        btn7.setBounds(380, 220, 100, 20);
        btn8.setBounds(160, 40, 150, 30);
        btn9.setBounds(320, 40, 150, 30);
        field.setBounds(120, 185, 100, 20);
        field2.setBounds(380, 185, 100, 20);


        Web NW1 = new Web(16, 8, "1");
        Web NW2 = new Web(16, 8, "6");

        btn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {

                try {
                    BufferedImage myPicture1 = ImageIO.read(new File("D:\\1.png"));
                    JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
                    panelNew.add(picLabel1);
                    picLabel1.setBounds(160, 150, 16, 16);
                } catch (IOException ex) {

                }
            }
        });

        btn2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                try

                {
                    BufferedImage myPicture1 = ImageIO.read(new File("D:\\6.png"));
                    JLabel picLabel = new JLabel(new ImageIcon(myPicture1));
                    panelNew.add(picLabel);
                    picLabel.setBounds(420, 150, 16, 16);

                } catch (IOException ex) {

                }
            }
        });

        btn3.addMouseListener(new MouseAdapter() {
            JLabel picLabel;

            public void mousePressed(MouseEvent me) {
                JFileChooser openFile = new JFileChooser("C:\\");
                int result = openFile.showOpenDialog(null);//отобрааем пользователю

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = openFile.getSelectedFile();
                    try {
                        BufferedImage myPicture = ImageIO.read(selectedFile);

                        if (picLabel != null) {
                            panelNew.remove(picLabel);
                        }
                        picLabel = new JLabel(new ImageIcon(myPicture));
                        panelNew.add(picLabel);
                        panelNew.repaint();
                        picLabel.setBounds(300, 200, 16, 16);
                        for (int i = 0; i < 16; i++) {
                            for (int j = 0; j < 16; j++) {
                                int c = myPicture.getRGB(j, i);
                                int red = (c & 0x00ff0000) >> 16;
                                int green = (c & 0x0000ff00) >> 8;
                                int blue = c & 0x000000ff;
                                if (red == 255 && green == 255 && blue == 255)
                                    array[i][j] = 0;
                                else
                                    array[i][j] = 1;
                            }
                        }
                        minArray = functionNewArray(array);
                    } catch (IOException ex) {

                    }
                }
            }
        });

        btn4.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                NW1.mul_w(array);
                NW1.Sum();
                if (NW1.Rez()) field.setText("true");
                else field.setText("false");

                NW2.mul_w(array);
                NW2.Sum();
                if (NW2.Rez()) field2.setText("true");
                else field2.setText("false");

            }
        });

        btn5.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                try {
                    Weight(NW1, "src/my/class1Weight.txt", t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Weight(NW2, "src/my/class2Weight.txt", t2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btn6.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (field.getText().compareTo("true") != 0) {
                    NW1.incW(array, "src/my/class1Weight.txt");
                } else {
                    NW1.decW(array, "src/my/class1Weight.txt");
                }
            }
        });

        btn7.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (field2.getText().compareTo("true") != 0)
                    NW2.incW(array, "src/my/class2Weight.txt");
                else NW2.decW(array, "src/my/class2Weight.txt");
            }
        });

        btn8.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                t.fer();
            }
        });

        btn9.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                t2.fer();
            }
        });
    }

    public int[][] functionNewArray(int[][] mass2) {
        int lengthRow = mass2.length;
        int lengthColumns = mass2[0].length;

        int[][] newarray = new int[lengthRow][lengthColumns / 2];

        boolean[][] checkmass = new boolean[lengthRow][lengthColumns];

        for (int i = 0; i < lengthRow; i++) {
            for (int j = 0; j < lengthColumns; j++) {
                checkmass[i][j] = true;
            }
        }

        newarray = fastMath(newarray, 0, 0, 4, 2, checkmass, 4, 4, mass2, 0, 0);
        newarray = fastMath(newarray, 0, 2, 4, 4, checkmass, 4, 4, mass2, 0, 4);
        newarray = fastMath(newarray, 0, 4, 4, 6, checkmass, 4, 4, mass2, 0, 8);
        newarray = fastMath(newarray, 0, 6, 4, 8, checkmass, 4, 4, mass2, 0, 12);

        newarray = fastMath(newarray, 4, 0, 8, 2, checkmass, 4, 4, mass2, 4, 0);
        newarray = fastMath(newarray, 4, 2, 8, 4, checkmass, 4, 4, mass2, 4, 4);
        newarray = fastMath(newarray, 4, 4, 8, 6, checkmass, 4, 4, mass2, 4, 8);
        newarray = fastMath(newarray, 4, 6, 8, 8, checkmass, 4, 4, mass2, 4, 12);

        newarray = fastMath(newarray, 8, 0, 12, 2, checkmass, 4, 4, mass2, 8, 0);
        newarray = fastMath(newarray, 8, 2, 12, 4, checkmass, 4, 4, mass2, 8, 4);
        newarray = fastMath(newarray, 8, 4, 12, 6, checkmass, 4, 4, mass2, 8, 8);
        newarray = fastMath(newarray, 8, 6, 12, 8, checkmass, 4, 4, mass2, 8, 12);

        newarray = fastMath(newarray, 12, 0, 16, 2, checkmass, 4, 4, mass2, 12, 0);
        newarray = fastMath(newarray, 12, 2, 16, 4, checkmass, 4, 4, mass2, 12, 4);
        newarray = fastMath(newarray, 12, 4, 16, 6, checkmass, 4, 4, mass2, 12, 8);
        newarray = fastMath(newarray, 12, 6, 16, 8, checkmass, 4, 4, mass2, 12, 12);

        for(int i = 0; i < tableArray.length; i++){
            for(int j = 0; j < tableArray[0].length; j++){
                if(tableArray[i][j] == 1)
                    t.add(tableArray[i][j],i,j);
            }
        }
        return newarray;
    }

    public int[][] fastMath(int[][] array, int first, int second, int row, int columns, boolean[][] checkmass, int randI, int randJ, int[][] mass2, int diaposon, int diaposon2) {
        Random randNumber = new Random();
        int sum = 0;
        int randomx1 = 0;
        int randomy2 = 0;
        boolean t;
        for (int i = first; i < row; i++) {
            for (int j = second; j < columns; j++) {
                for (int k = 0; k < 2; k++) {
                    do {
                        t = false;
                        randomx1 = randNumber.nextInt(randI);
                        randomx1 += diaposon;
                        randomy2 = randNumber.nextInt(randJ);
                        randomy2 += diaposon2;

                        if (checkmass[randomx1][randomy2] == true) {
                            tableArray[randomx1 * 16 + randomy2][i * 8 + j] = 1; // для большой слева - 1 / 16 = i ; остаток = j ; для мелкой вверх -1 / 8 = i ; остаток j;
                            t = true;
                            sum += mass2[randomx1][randomy2];
                            checkmass[randomx1][randomy2] = false;
                        }
                    }
                    while (!t);
                }
                if (sum > 0) {
                    sum = 1;
                    array[i][j] = sum;

                } else {
                    sum = 0;
                    array[i][j] = sum;
                }
            }
        }
        return array;
    }

    public void Weight(Web NW, String n, table t) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(n));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int k = 0;
        String line;
        String split[];

        try {
            while ((line = reader.readLine()) != null) {
                split = line.split(" ");

                for (int i = 0; i < split.length; i++) {
                    NW.weight[k][i] = Integer.parseInt(split[i]);
                    t.add(NW.weight[k][i], k, i);
                }
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.close();
    }

    public static void main(String[] args) {

        Work1 go1 = new Work1();
        go1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        go1.setBounds(350, 100, 640, 600);
        go1.setResizable(false);
        go1.setVisible(true);

    }


}
