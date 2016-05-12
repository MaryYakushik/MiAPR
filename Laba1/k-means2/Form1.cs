using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Threading;
using System.Diagnostics;

namespace k_means2
{
    public partial class Form1 : Form
    {

        #region Points classes
        class Point
        {
            public int X;
            public int Y;
            public Color color;
        }

        class Center
        {
            public int X;
            public int Y;
            public Color color;
            public int distanceX;
            public int distanceY;
            public int countOfPoints;
        }
        #endregion

        List<Point> points;
        List<Center> centers;
        List<Boolean> list;
        BufferedGraphicsContext bufferedGraphicsContext;
        BufferedGraphics bufferedGraphics;

        Graphics g;

        public Form1()
        {
            InitializeComponent();
            g = pictureBox1.CreateGraphics();
            bufferedGraphicsContext = new BufferedGraphicsContext();
            bufferedGraphics = bufferedGraphicsContext.Allocate(g,
                new Rectangle(0, 0, pictureBox1.Width, pictureBox1.Height));
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            bufferedGraphics.Graphics.Clear(pictureBox1.BackColor);
            bufferedGraphics.Render();
            points = new List<Point>();
            try
            {
                int count = int.Parse(textBox1.Text);
                if (count < 1000 || count > 1000000)
                {
                    MessageBox.Show("Incorrect input. Enter value betveen 1000 and 1000000");
                }
                else
                {
                    Random r = new Random();
                    for (int i = 0; i < count; i++)
                    {
                        Point p = new Point();
                        p.X = r.Next(pictureBox1.Size.Width);
                        p.Y = r.Next(pictureBox1.Size.Height); ;
                        points.Add(p);
                        bufferedGraphics.Graphics.FillRectangle(new SolidBrush(Color.Black),
                        p.X, p.Y, 2, 2);
                    }
                    bufferedGraphics.Render();
                    MessageBox.Show("All points were set");
                }
            }
            catch
            {
                MessageBox.Show("Incorrect input. Enter numbers");
            }
        }


        public void SetRandomCenters(int count)
        {
            Random r = new Random();
            for (int i = 0; i < count; i++)
            {
                Center p = new Center();
                p.X = r.Next(pictureBox1.Size.Width); ;
                p.Y = r.Next(pictureBox1.Size.Height);;
                p.color = Color.FromArgb(r.Next(255), r.Next(255), r.Next(255));
                p.distanceX = 0;
                p.distanceY = 0;
                p.countOfPoints = 0;
                centers.Add(p);
                list.Add(false);
                g.DrawEllipse(new Pen(p.color, 3), p.X, p.Y, 4, 4);
            }
        }


        public void SetPointsToCenters()
        {
            Parallel.For(0, points.Count, PointstoCenters);
        }


        public void PointstoCenters(int i)
        {
            double distance = Math.Pow(points[i].X - centers[0].X, 2) + Math.Pow(points[i].Y - centers[0].Y, 2);
            int min = 0;
            for (int j = 1; j < centers.Count; j++)
            {
                double distance2 = Math.Pow(points[i].X - centers[j].X, 2) + Math.Pow(points[i].Y - centers[j].Y, 2);
                if ((int)distance2 < (int)distance)
                {
                    distance = distance2;
                    min = j;
                }
            }
            centers[min].distanceX += points[i].X;
            centers[min].distanceY += points[i].Y;
            centers[min].countOfPoints++;
            points[i].color = centers[min].color;
        }


        public void FindNewCenters()
        {
            for (int j = 0; j < centers.Count; j++)
            {
                int xc = (int)centers[j].distanceX / centers[j].countOfPoints;
                int yc = (int)centers[j].distanceY / centers[j].countOfPoints;
                if (Math.Abs(xc - centers[j].X) < 2  && Math.Abs(yc - centers[j].Y) < 2)
                {
                    list[j] = true;
                }
                else
                {
                    list[j] = false;
                    Center p = centers[j];
                    p.X = xc;
                    p.Y = yc;
                    centers[j] = p;
                }
                centers[j].distanceX = 0;
                centers[j].distanceY = 0;
                centers[j].countOfPoints = 0;
            }
        }


        public bool Check()
        {
            bool t = false;
            for( int i = 0; i < list.Count; i++)
            {
                if (!list[i])
                {
                    t = true;
                    break;
                }
            }
            return t;
        }


        public void Repaint()
        {
            g.Clear(Color.White);
            for (int i = 0; i < points.Count; i++)
            {
                bufferedGraphics.Graphics.FillRectangle(new SolidBrush(points[i].color),
                    points[i].X, points[i].Y, 2, 2);
            }

            for (int i = 0; i < centers.Count; i++)
            {
                bufferedGraphics.Graphics.FillEllipse(new SolidBrush(centers[i].color),
                    centers[i].X, centers[i].Y, 9, 9);
            }
            bufferedGraphics.Render();
        }


        private void button3_Click(object sender, EventArgs e)
        {
            centers = new List<Center>();
            list = new List<Boolean>();
            try
            {
                int count = int.Parse(textBox2.Text);
                if (count < 2 || count > 20)
                {
                    MessageBox.Show("Incorrect input. Enter value betveen 2 and 20");
                }
                else
                {
                    SetRandomCenters(count);
                    Stopwatch s = new Stopwatch();
                    s.Start();
                    do
                    {
                        SetPointsToCenters();
                        FindNewCenters();
                    } while (Check());
                    s.Stop();
                    MessageBox.Show("Time: " + s.Elapsed.ToString());
                    Repaint();
                }
            }
            catch
            {

            }
        }


        private void button2_Click(object sender, EventArgs e)
        {
            points.Clear();
            centers.Clear();
            list.Clear();
            bufferedGraphics.Graphics.Clear(pictureBox1.BackColor);
            bufferedGraphics.Render();
        }

    }
}
