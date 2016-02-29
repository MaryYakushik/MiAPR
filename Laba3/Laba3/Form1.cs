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

                if(PC1 + PC2 - 1 < 0.00000000001)
                {
                    Random r = new Random();
                    double mu1 = 0;
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
                        sigma1 += Math.Pow(points1[i] - mu1, 2);
                        sigma2 += Math.Pow(points2[i] - mu2, 2);
                    }
                    sigma1 = Math.Sqrt(sigma1 / pointsCount);
                    sigma2 = Math.Sqrt(sigma2 / pointsCount);

                    double p1 = 0;
                    double p2 = 0;
                    Color c1 = Color.FromArgb(r.Next(255), r.Next(255), r.Next(255));
                    Color c2 = Color.FromArgb(r.Next(255), r.Next(255), r.Next(255));
                    for(int i = 0; i < pointsCount; i++)
                    {
                        p1 = Math.Exp(-0.5 * Math.Pow((i - mu1) / sigma1, 2)) /
                            (sigma1 * Math.Sqrt(2 * Math.PI));
                        p2 = Math.Exp(-0.5 * Math.Pow((i - mu2) / sigma2, 2)) /
                             (sigma2 * Math.Sqrt(2 * Math.PI));
                        bufferedGraphics.Graphics.FillRectangle(new SolidBrush(c1),
                             i, pictureBox1.Height - (int) (p1 * PC1 * 150000), 3, 3);
                        bufferedGraphics.Graphics.FillRectangle(new SolidBrush(c2),
                             i, pictureBox1.Height - (int) (p2 * PC2 * 150000), 3, 3);
                    }
                    bufferedGraphics.Render();
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
