using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Laba3
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            graphics = pictureBox1.CreateGraphics();
            bufferedGraphicsContext = new BufferedGraphicsContext();
            bufferedGraphics = bufferedGraphicsContext.Allocate(graphics,
                new Rectangle(0, 0, pictureBox1.Width, pictureBox1.Height));

            bufferedGraphics.Graphics.Clear(pictureBox1.BackColor);
        }


        Graphics graphics;
        BufferedGraphicsContext bufferedGraphicsContext;
        BufferedGraphics bufferedGraphics;

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                bufferedGraphics.Graphics.Clear(pictureBox1.BackColor);
                bufferedGraphics.Render();
                int pointsCount = 1000;
                double PC1 = double.Parse(textBox1.Text);
                double PC2 = double.Parse(textBox2.Text);

                List<int> points1 = new List<int>();
                List<int> points2 = new List<int>();

                if(PC1 + PC2 - 1 < 0.00000000001 && PC1 + PC2 - 1 >= 0 )
                {
                    Random r = new Random();
                    double mu1 = 0; // математическое ожидание 
                    double mu2 = 0;
                    for (int i = 0; i < pointsCount; i++)
                    {
                        points1.Add(r.Next(pictureBox1.Width) - 100);
                        points2.Add(r.Next(pictureBox1.Width) + 100);
                        mu1 += points1[i];
                        mu2 += points2[i]; 
                    }
                    mu1 /= pointsCount;
                    mu2 /= pointsCount;

                    double sigma1 = 0;
                    double sigma2 = 0;
                    for (int i = 0; i < pointsCount; i++)
                    {
                        sigma1 += Math.Pow(points1[i] - mu1, 2); // среднеквадратичное отклонение
                        sigma2 += Math.Pow(points2[i] - mu2, 2);
                    }
                    sigma1 = Math.Sqrt(sigma1 / pointsCount);
                    sigma2 = Math.Sqrt(sigma2 / pointsCount);

                    double p1 = 0;
                    double p2 = 0;
                    Point point = new Point();
                    Color c1 = Color.FromArgb(r.Next(255), r.Next(255), r.Next(255));
                    Color c2 = Color.FromArgb(r.Next(255), r.Next(255), r.Next(255));
                    bool t = false;
                    for(int i = 0; i < pointsCount; i++)
                    {
                        p1 = Math.Exp(-0.5 * Math.Pow((i - mu1) / sigma1, 2)) / //апостериорная плотность вероятности 
                            (sigma1 * Math.Sqrt(2 * Math.PI));
                        p2 = Math.Exp(-0.5 * Math.Pow((i - mu2) / sigma2, 2)) /
                             (sigma2 * Math.Sqrt(2 * Math.PI));
                        bufferedGraphics.Graphics.FillRectangle(new SolidBrush(c1),
                             i, pictureBox1.Height - (int) (p1 * PC1 * 150000), 3, 3);
                        bufferedGraphics.Graphics.FillRectangle(new SolidBrush(c2),
                             i, pictureBox1.Height - (int) (p2 * PC2 * 150000), 3, 3);
                        if ((int)(p1 * PC1 * 150000) == (int)(p2 * PC2 * 150000) && !t)
                        {
                            point.X = i;
                            point.Y = (int)(p1 * PC1 * 150000);
                            Point point2 = new Point();
                            point2.X = i;
                            point2.Y = 0;
                            bufferedGraphics.Graphics.FillRectangle(new SolidBrush(Color.Black),
                            i, 0, 2, pictureBox1.Height);
                            t = true;
                        }

                    }
                    bufferedGraphics.Render();


                    double falseAlarmError = 0; // вероятность ложной тревоги

                    double x = -100;
                    p1 = 1;
                    p2 = 0;
                    if (PC2 != 0)
                        while (p2 < p1)
                        {
                            p1 = PC1 * Math.Exp(-0.5 * Math.Pow((x - mu1) / sigma1, 2)) /
                                (sigma1 * Math.Sqrt(2 * Math.PI));
                            p2 = PC2 * Math.Exp(-0.5 * Math.Pow((x - mu2) / sigma2, 2)) /
                                (sigma2 * Math.Sqrt(2 * Math.PI));
                            falseAlarmError += p2 * 0.001;  // вероятность ложной тревоги
                            x += 0.001;
                        }
                    double borderX = x;
                    double missingDetectingError = 0; // вероятность пропуска обнаружения ошибки
                    while (x < pictureBox1.Width + 100)
                    {
                        p1 = Math.Exp(-0.5 * Math.Pow((x - mu1) / sigma1, 2)) /
                            (sigma1 * Math.Sqrt(2 * Math.PI));
                        p2 = Math.Exp(-0.5 * Math.Pow((x - mu2) / sigma2, 2)) /
                            (sigma2 * Math.Sqrt(2 * Math.PI));
                        missingDetectingError += p1 * PC1 * 0.001; // вероятность пропуска обнаружения ошибки
                        x += 0.001;
                    }

                    bufferedGraphics.Graphics.DrawLine(new Pen(Color.Green, 3),
                        (int)(borderX), 0, (int)(borderX), pictureBox1.Height);

                    double totalClassificationError = 0; // вероятность суммарной ошибки классификации
                    if (PC1 == 0)
                    {
                        falseAlarmError = 1;
                        missingDetectingError = 0;
                        totalClassificationError = 1;
                    }
                    else
                    {
                        if (PC2 == 0)
                        {
                            falseAlarmError = 0;
                            missingDetectingError = 0;
                            totalClassificationError = 0;
                        }
                        else
                        {
                            falseAlarmError /= PC1;
                            missingDetectingError /= PC1;
                        }
                    }

                    totalClassificationError = falseAlarmError + missingDetectingError;
                    textBox3.Text = falseAlarmError.ToString();
                    textBox4.Text = missingDetectingError.ToString();
                    textBox5.Text = totalClassificationError.ToString();

                }
                else
                {
                    MessageBox.Show("Сумма вероятностей должна равняться 1");
                }
            }
            catch
            {

            }
        }
    }
}
