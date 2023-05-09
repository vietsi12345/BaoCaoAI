    using System;
    using System.Collections.Generic;
    using System.Collections.Specialized;
    using System.ComponentModel;
    using System.Data;
    using System.Drawing;
    using System.IO;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using System.Windows.Forms;
    using System.Windows.Media;
    using System.Windows.Media.Media3D;
    using System.Windows.Media.TextFormatting;
    using System.Xml.Linq;
    using LiveCharts.WinForms;
    namespace WindowsFormsApp1
    {
        public partial class Form1 : Form
        {

            List<int>[] adj = new List<int>[1000];
            int[] color = new int[1000];
            int n, m;

            public Form1()
            {
                InitializeComponent();
            }
            // kiểm tra xem có thể tô màu c cho đỉnh v hay không
            bool is_safe(int v, int c)
            {
                for (int i = 0; i < adj[v].LongCount(); i++)
                {
                    int u = adj[v][i];
                    if (color[u] == c)
                    {
                        return false;
                    }
                }
                return true;
            }

            // kiểm tra xem có bao nhiêu màu có thể gán cho đỉnh V
            int get_remaining_values(int v)
            {
                int remaining = 0;
                // khai báo 1 mảng 10 phần tử đều bằng false. Nếu bằng false nghĩa là còn
                // có thể tô được
                bool[] used_color = new bool[10];
                for (int i = 0; i < 10; i++)
                {
                    used_color[i] = false;
                }  
                for (int i = 0; i < adj[v].Count(); i++)
                {
                    int u = adj[v][i]; // lấy lần lượt từng đỉnh kề với đỉnh v
                    if (color[u] != -1) // chọn những đỉnh đã được tô màu
                    {
                        used_color[color[u]] = true; // nếu màu đó đã được tô thì chuyển trành true
                    }
                }
                // đếm số lượng màu có thể tô được cho đỉnh v
                for (int i = 0; i <= 9; i++)
                {
                    if (!used_color[i])
                    {
                        remaining++;
                    }
                }
                return remaining;
            }
            int tinh_bac(int v)
            {
                return adj[v].Count();
            }

        // sử dụng kĩ thuật cải tiến sắp xếp các các đỉnh theo bậc giảm dần nếu cùng bậc thì sắp xếp theo đỉnh
        // nào có ít màu có thể gán hơn thì xét trước       
        bool graph_coloring(int v)
            {
                if (v == n + 1 )
                {
                    return true;
                }

                List<int> nodes = new List<int>();  // tạo list chứa các đỉnh chưa được tô màu  
                for (int i = 1; i <= n; i++)
                {
                    if (color[i] == -1)
                    {
                        nodes.Add(i);
                    }
                }     
            for (int i=0;i<nodes.Count; i++)
            {
                for (int j =i+1;j<nodes.Count; j++)
                {
                    if (tinh_bac(nodes[j]) > tinh_bac(nodes[i]))
                    {
                        int temp = nodes[j];
                        nodes[j] = nodes[i];
                        nodes[i] = temp;
                    }
                    if (tinh_bac(nodes[j]) == tinh_bac(nodes[i]))
                    {
                        if (get_remaining_values(nodes[j]) < get_remaining_values(nodes[i]))
                        {
                            int temp = nodes[j];
                            nodes[j] = nodes[i];
                            nodes[i] = temp;
                        }

                    }
                }
            }
            for (int i = 0; i < nodes.Count; i++)
                {
                    int u = nodes[i];
                    for (int c = 0; c <= 9; c++)
                    {
                        if (is_safe(u, c))
                        {
                            color[u] = c;
                            if (graph_coloring(v + 1))
                            {
                                return true;
                            }
                            color[u] = -1; // backtrack
                        }
                    }
                }
                return false;
            }
            private void Form1_Load(object sender, EventArgs e)
            {
                string filePath = "D:/test.txt";

                // Đọc các dòng của tệp tin
                string[] lines = File.ReadAllLines(filePath);
                string[] firstLine = lines[0].Split(' ');
                n = int.Parse(firstLine[0]); // Số đỉnh
                m = int.Parse(firstLine[1]); // Số cạnh
                //List<int>[] adj = new List<int>[n+1];
                Console.WriteLine(n);
                Console.WriteLine(m);
                // khởi tạo List trong mảng
                for (int i = 1; i <= n; i++)
                {
                    adj[i] = new List<int>();
                }
                // Lặp qua từng dòng, bắt đầu từ dòng thứ hai
                for (int i = 1; i <= m; i++)
                {
                    // Tách các đỉnh của cạnh bằng khoảng trắng
                    string[] vertices = lines[i].Split(' ');

                    // Lấy ra 2 đỉnh và thêm vào danh sách kề
                    int vertex1 = int.Parse(vertices[0]);
                    int vertex2 = int.Parse(vertices[1]);
                    adj[vertex1].Add(vertex2);
                    adj[vertex2].Add(vertex1);
                }
                for (int i = 1; i <= n; i++)
                {
                    if (i == 20)
                    {
                        continue;
                    }
                    Console.Write("Danh sách kề của đỉnh {0}: ", i ); 
                    foreach (int j in adj[i])
                    {
                        Console.Write("{0} ", j );
                    }
                    Console.WriteLine();
                }
                for (int i = 1; i <= n; i++)
                {
                    color[i] = -1; // mac dinh cac dinh chua to.
                }
                if (graph_coloring(1))
                {
                    for (int i = 1; i <= n; i++)
                    {
                        Console.WriteLine("dinh {0} co mauu: {1}", i, color[i]);
                    }
                }
            GeoMap geoMap = new GeoMap();
                Random random = new Random();
                //gives value
                Dictionary<string, double> values = new Dictionary<string, double>();

                // creat province
                for (int i = 1; i < 65; i++)
                {
                    String x = i.ToString();
                    if (i < 10)
                    {
                        x = "0" + x;
                    }
                    values[x] = 0;
                }

                for (int i = 1; i < 65; i++)
                {
                    String x = i.ToString();
                    if (i < 10)
                    {
                        x = "0" + x;
                    }
                    values[x] = color[i];
                }
                //change default color
                //geoMap.DefaultLandFill = new SolidColorBrush(Colors.Transparent);

                //change heat color
                GradientStopCollection collection = new GradientStopCollection();
                collection.Add(new GradientStop() { Color = System.Windows.Media.Color.FromArgb(68, 128, 0, 0), Offset = 0 });
                collection.Add(new GradientStop() { Color = System.Windows.Media.Color.FromArgb(128, 128, 128, 0), Offset = 0.5 });
                collection.Add(new GradientStop() { Color = System.Windows.Media.Color.FromArgb(128, 0, 0, 255), Offset = 1 });

                geoMap.GradientStopCollection = collection;
                //geoMap.map
                //change boundary color
                //geoMap.LandStroke = new SolidColorBrush(Colors.Black);

                geoMap.HeatMap = values;
                geoMap.Hoverable = true;
                geoMap.Source = $"{Application.StartupPath}\\vietnam.xml";
                this.Controls.Add(geoMap);
                geoMap.Dock = DockStyle.Fill;
            }
        }
    }
