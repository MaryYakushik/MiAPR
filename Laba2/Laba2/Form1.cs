using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Laba2
{
    public partial class Form1 : Form
    {
        Graphics g;
        BufferedGraphicsContext bufferedGraphicsContext;
        BufferedGraphics bufferedGraphics;
        List<int> numbersOfCenters;

        public Form1()
        {
            InitializeComponent();
            g = pictureBox1.CreateGraphics();
            bufferedGraphicsContext = new BufferedGraphicsContext();
            bufferedGraphics = bufferedGraphicsContext.Allocate(g,
                new Rectangle(0, 0, pictureBox1.Width, pictureBox1.Height));
        }

        class Point
        {
            public int x;
            public int y;
            public int numberOfCenter;

        }

        List<Point> points;
        List<Point> centers;
        Random r = new Random();

        private void acceptButton_Click(object sender, EventArgs e)
        {
            points = new List<Point>();
            try
            {
                int countOfPoints = int.Parse(pointsCountBox.Text);
                for (int i = 0; i < countOfPoints; i++)
                {
                    Point p = new Point();
                    p.x = r.Next(pictureBox1.Size.Width);
                    p.y = r.Next(pictureBox1.Size.Height);
                    points.Add(p);
                    bufferedGraphics.Graphics.FillRectangle(new SolidBrush(Color.White),
                       p.x, p.y, 2, 2);
                }
                bufferedGraphics.Render();
            }
            catch { }
        }

        private void findClassesButton_Click(object sender, EventArgs e)
        {
            numbersOfCenters = new List<int>();
            centers = new List<Point>();
            setRandomCenter();
            findMaxDistance(0);
            spreadPoints();
            repaint();
            
        }

        public void setRandomCenter()
        {
            int randomNumber = r.Next(points.Count);
            Point p = new Point();
            p.x = points[randomNumber].x;
            p.y = points[randomNumber].y;
            p.numberOfCenter = randomNumber;
            centers.Add(p);
            numbersOfCenters.Add(randomNumber);
        }


        private void findMaxDistance(int number)
        {
            double maxDistance = -1;
            int numberOfPoint = -1;
            for (int i = 0; i < points.Count; i++)
            {
                if (!numbersOfCenters.Contains(i))
                {
                    if (maxDistance < 0)
                    {
                        maxDistance = Math.Pow(centers[number].x - points[i].x, 2) + Math.Pow(centers[number].y - points[i].y, 2);
                        numberOfPoint = i;
                    }
                    else
                    {
                        double distance = Math.Pow(centers[number].x - points[i].x, 2) + Math.Pow(centers[number].y - points[i].y, 2);
                        if (distance > maxDistance)
                        {
                            maxDistance = distance;
                            numberOfPoint = i;
                        }
                    }
                }
            }
            Point p = new Point();
            p.x = points[numberOfPoint].x;
            p.y = points[numberOfPoint].y;
            p.numberOfCenter = numberOfPoint;
            centers.Add(p);
        }

        private void spreadPoints()
        {
            for (int i = 0; i < points.Count; i++)
            {
                double maxdistance = Math.Pow(centers[0].x - points[i].x, 2) + Math.Pow(centers[0].y - points[i].y, 2);
                int numberOfMaxdistance = 0;
                for (int j = 1; j < centers.Count; j++)
                {
                    double distance = Math.Pow(centers[j].x - points[i].x, 2) + Math.Pow(centers[j].y - points[i].y, 2);
                    if (distance < maxdistance)
                    {
                        maxdistance = distance;
                        numberOfMaxdistance = j;
                    }
                }
                points[i].numberOfCenter = numberOfMaxdistance;
            }
        }

        private void repaint()
        {
            bufferedGraphics.Graphics.Clear(pictureBox1.BackColor);
            bufferedGraphics.Render();
            Random r = new Random();
            for (int i = 0; i < centers.Count; i++)
            {
                Color c = Color.FromArgb(r.Next(255), r.Next(255), r.Next(255));
                bufferedGraphics.Graphics.FillEllipse(new SolidBrush(c),
                           centers[i].x, centers[i].y, 6, 6);
                for (int j = 0; j < points.Count; j++)
                {
                    if (points[j].numberOfCenter == i)
                    {
                        bufferedGraphics.Graphics.FillRectangle(new SolidBrush(c),
                           points[j].x, points[j].y, 2, 2);
                    }
                }
            }
            bufferedGraphics.Render();
        }

    }
}
