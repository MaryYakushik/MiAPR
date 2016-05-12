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
            bufferedGraphics.Graphics.Clear(pictureBox1.BackColor);
            bufferedGraphics.Render();
            points = new List<Point>();
            try
            {
                int countOfPoints = int.Parse(pointsCountBox.Text);
                if (countOfPoints >= 1000 && countOfPoints <= 100000)
                {
                    for (int i = 0; i < countOfPoints; i++)
                    {
                        Point p = new Point();
                        p.x = r.Next(pictureBox1.Size.Width);
                        p.y = r.Next(pictureBox1.Size.Height);
                        points.Add(p);
                        bufferedGraphics.Graphics.FillRectangle(new SolidBrush(Color.Black),
                           p.x, p.y, 2, 2);
                    }
                    bufferedGraphics.Render();
                }
                else
                {
                    MessageBox.Show("Введите значение в диапазоне от 1000 до 100000");
                }
            }
            catch 
            {
                MessageBox.Show("Введите значение в диапазоне от 1000 до 100000");
            }
        }

        bool t;
        private void findClassesButton_Click(object sender, EventArgs e)
        {
            numbersOfCenters = new List<int>();
            centers = new List<Point>();
            setRandomCenter();
            findMaxDistance(0);
            t = true;
            while (t)
            {
                spreadPoints();
                findmaxDistanceInClasses();
            }
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
            numbersOfCenters.Add(numberOfPoint);
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

        SortedList<Double, int> maxDistances;
        Double averageDistance;

        private void findmaxDistanceInClasses()
        {
            averageDistance = 0;
            maxDistances = new SortedList<Double, int>();           
            Parallel.For(0, centers.Count, functionForThreads);
            Parallel.For(0, centers.Count, Average);
            Double element = maxDistances.ElementAt(maxDistances.Count - 1).Key;
            if(element > averageDistance / (2 * centers.Count))
            {
                int numberOfElement = maxDistances.ElementAt(maxDistances.Count - 1).Value;
                Point p = new Point();
                p.x = points[numberOfElement].x;
                p.y = points[numberOfElement].y;
                p.numberOfCenter = numberOfElement;
                centers.Add(p);
                numbersOfCenters.Add(numberOfElement);
            }
            else
            {
                t = false;
            }
        }

        private void functionForThreads( int i)
        {
            double maxdistance = -1;
            int numberOfMaxDistance = -1;
            for (int j = 0; j < points.Count; j++)
            {
                if (!numbersOfCenters.Contains(j))
                {
                    if (points[j].numberOfCenter == i)
                    {
                        if (maxdistance < 0)
                        {
                            maxdistance = Math.Pow(centers[i].x - points[j].x, 2) + Math.Pow(centers[i].y - points[j].y, 2);
                            numberOfMaxDistance = j;
                        }
                        else
                        {
                            double distance = Math.Pow(centers[i].x - points[j].x, 2) + Math.Pow(centers[i].y - points[j].y, 2);
                            if (distance > maxdistance)
                            {
                                maxdistance = distance;
                                numberOfMaxDistance = j;
                            }
                        }
                    }
                }
                
            }
            maxDistances.Add(maxdistance, numberOfMaxDistance);
        }

        private void Average(int numberOfCurrentCenter)
        {
            for (int i = numberOfCurrentCenter + 1; i < centers.Count; i++)
            {
                double distance = Math.Pow(centers[numberOfCurrentCenter].x - centers[i].x, 2) + Math.Pow(centers[numberOfCurrentCenter].y - centers[i].y, 2);
                averageDistance += distance;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            bufferedGraphics.Graphics.Clear(pictureBox1.BackColor);
            bufferedGraphics.Render();
        }
    }
}
