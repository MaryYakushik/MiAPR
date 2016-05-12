package my;

import java.io.*;

public class Web {
    table t = new table();
    public String name;
    public int[][] mul; // Тут будем хранить отмасштабированные сигналы
    public int[][] weight; // Массив для хранения весов
    public int[][] input; // Входная информация
    public int limit = 50; // Порог - выбран экспериментально, для быстрого обучения
    public int sum; // Тут сохраним сумму масштабированных сигналов

    public Web(int sizex, int sizey, String n) // Задаем свойства при создании объекта
    {
        name = n;
        weight = new int[sizex][sizey]; // Определяемся с размером массива (число входов)
        mul = new int[sizex][sizey];



    }

    public void mul_w(int[][] inP) {

        input = new int[16][8];
        input = inP; // Получаем входные данные

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 8; y++) // Пробегаем по каждому аксону
            {
                mul[x][y] = input[x][y] * weight[x][y]; // Умножаем его сигнал (0 или 1) на его собственный вес и сохраняем в массив.
            }
        }
    }

    public void Sum()
    {
        sum = 0;
        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                sum += mul[x][y];
            }
        }
    }

    public boolean Rez()
    {
        if (sum >= limit)
            return true;
        else return false;
    }

    public void incW(int[][] array,String n){
        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                weight[x][y] += array[x][y];
            }
        }
      writeFile(n);
    }




    public void decW(int[][] array,String n){
        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                weight[x][y] -= array[x][y];
            }
        }
        writeFile(n);
    }

    private void writeFile(String n){
        try(FileWriter writer = new FileWriter(n, false))
        {
            writer.write("");

            for(int i = 0; i < weight.length; i++){
                for(int j = 0; j < weight[0].length; j++) {
                    writer.append(weight[i][j] + " ");
                }
                writer.append('\n');
            }
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
