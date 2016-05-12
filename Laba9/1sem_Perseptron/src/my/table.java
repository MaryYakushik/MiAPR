package my;

import javax.swing.*;
import java.awt.*;

public class table extends  JFrame{
    String[] columnNames = new String[129];

    String[][] data;

    public table()
    {

        for(int i =0; i < 129; i++)
        {
            columnNames[i] = " ";
        }
        data=new String[257][129];
        data[0][0]="X/A";
        for(int i=1; i < data.length;i++){
            data[i][0] = "      "+String.valueOf(i)+")";
        }
        for(int j = 1; j < data[0].length; j++){
            data[0][j] = "      "+String.valueOf(j)+")";
        }

    }


    public void add(double d ,int i,int j){
        data[i+1][j+1] = String.valueOf(d);

    }

    public void fer()
    {
        JFrame frame = new JFrame("Table");
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(650, 300));
        frame.pack();//упоковка фрейма. устанавливает размер (мин) которого хватит для отображения всех компонентов
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
