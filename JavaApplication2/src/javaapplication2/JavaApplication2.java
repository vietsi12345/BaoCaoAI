/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author LENOVO
 */
public class JavaApplication2 {

    final static int maxN = 100;
    static int m = 0, n = 0, p = 0;
    static int[][] R = new int[maxN][maxN];
    static int[][] T = new int[maxN][maxN];
    static int[][] C = new int[maxN][maxN];
    static int[][] D = new int[maxN][maxN];
    static int[][] checkD = new int[maxN][maxN];
    static ArrayList<int[]> dinh = new ArrayList<>();
    static int num_dinh = 0;
    static int[] list_tiet = new int[maxN];
    static boolean kq = false;
    static int dg1 = 1;
    static int dg2 = 10;
    static int dg3 = 100;
    static Random random = new Random();

    static int ham_muc_tieu() {
        kq = true;
        int tong = 0;
        for (int i = 1; i <= num_dinh; i++) {
            int u = dinh.get(i - 1)[0];
            int v = dinh.get(i - 1)[1];
            int tiet = list_tiet[i];
            // kiêm tra ràng buộc có thể có 
            for (int j = 1; j <= num_dinh; j++) {
                if ((dinh.get(i - 1)[0] == dinh.get(j - 1)[0] || dinh.get(i - 1)[1] == dinh.get(j - 1)[1]) && list_tiet[i] == list_tiet[j] && i != j) {
                    tong = tong - dg1;
                    kq = false;
                }
                if ((dinh.get(i - 1)[0] == dinh.get(j - 1)[0] || dinh.get(i - 1)[1] == dinh.get(j - 1)[1]) && list_tiet[i] != list_tiet[j] && i != j) {
                    tong = tong + dg1;
                }
            }
            // kiểm tra ràng buộc của ma trận T
            if (T[v][tiet] == 1) {
                tong = tong + dg2;
            } else {
                tong = tong - dg2;
                kq = false;
            }
            // kiểm tra ràng buộc của ma trận C 
            if (C[u][tiet] == 1) {
                tong = tong + dg2;
            } else {
                tong = tong - dg2;
                kq = false;
            }
        }
        // kiểm tra ràng buộc của ma trận D 
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= p; j++) {
                checkD[i][j] = 0;
            }
        }
        for (int i = 1; i <= num_dinh; i++) {
            int u = dinh.get(i - 1)[0];
            int tiet = list_tiet[i];
            checkD[u][tiet]=1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= p; j++) {
                if (D[i][j] == 1 && checkD[i][j] == 1) {
                    tong += dg3;
                }
                if (D[i][j] == 1 && checkD[i][j] != 1) {
                    tong -= dg3;
                    kq = false;
                }
            }
        }
        return tong;
    }

    /**
     * @param args the command line arguments
     */
    static int dem = 0;

    static void hill_climbing() {
        dem++;
        int mi = ham_muc_tieu();
        if (kq == true ) {
            System.out.println("Hoàn thành");
            return;
        } 
        for (int i = 1; i <= num_dinh; i++) {
            int tiet = list_tiet[i];
            for (int j = 1; j <= p; j++) {
                list_tiet[i] = j;
                int m = ham_muc_tieu();
                if (m > mi) {
                    hill_climbing();
                    return;
                } else {
                    list_tiet[i] = tiet;
                }
            }
        }
        list_tiet[random.nextInt(num_dinh) + 1] = random.nextInt(p) + 1;
        hill_climbing();
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("input1.txt"));
            m = sc.nextInt();
            n = sc.nextInt();
            p = sc.nextInt();
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    R[i][j] = sc.nextInt();
                }
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= p; j++) {
                    T[i][j] = sc.nextInt();
                }
            }
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= p; j++) {
                    C[i][j] = sc.nextInt();
                }
            }
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= p; j++) {
                    D[i][j] = sc.nextInt();
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int z = 1; z <= R[i][j]; z++) {
                    int[] pair = new int[]{i, j};
                    dinh.add(pair);
                }
            }
        }
        num_dinh = dinh.size();
        // random giá trị các tiết để gán cho các cặp đỉnh Lớp - GV 
        for (int i = 1; i <= num_dinh; i++) {
            list_tiet[i] = (int) (Math.random() * p + 1);
        }
        hill_climbing();
        for (int i = 1; i <= p; i++) {
            System.out.print("Tiet: " + i + ":");
            for (int j = 1; j <= num_dinh; j++) {
                if (list_tiet[j] == i) {
                    System.out.print("(LOP " + dinh.get(j - 1)[0] + " - " + "GV " + dinh.get(j - 1)[1] + ") ");
                }
            }
            System.out.println();
        }
            // lưu kêt quả vào 1 ma trận TKB với kích thước p *m với các giá trị chính là giáo viên
            int[][] TKB = new int[maxN][maxN];
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j <= m; j++) {
                TKB[i][j] = 0;
            }
        }
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j <= num_dinh; j++) {
                if ((list_tiet[j] == i)) {
                    for (int k = 1; k <= m; k++) {
                        if (dinh.get(j - 1)[0] == k) {
                            TKB[i][k] = dinh.get(j - 1)[1];
                        }
                    }
                }
            }
        }
        

        // Xây dựng Đồ họa
        JFrame frame = new JFrame("Thời khóa biểu");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(p + 1, m + 1));
        JTextField[][] table = new JTextField[p][m];
         for (int i = 0; i < p; i++) {
            for (int j = 0; j < m; j++) {
                table[i][j] = new JTextField();
            }
        }
        // Thêm các ô trống vào góc trên trái
        panel.add(new JLabel());
        for (int i = 1; i <= m; i++) {
            panel.add(new JLabel("Lớp " + i));
        }
        // Thêm các tiêu đề cột
        for (int i = 1; i <= p; i++) {
            panel.add(new JLabel("Tiết " + i));
            for (int j = 1; j <= m; j++) {
                JTextField textField = new JTextField();
                panel.add(textField);
                table[i - 1][j - 1] = textField;
            }
        }
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j <= m; j++) {
                if (TKB[i][j]!=0){
                    table[i - 1][j - 1].setText("Giáo Viên "+String.valueOf(TKB[i][j]));
                }
                else {
                    table[i - 1][j - 1].setText("X");
                }
            }
        }
        // Thêm các thành phần vào khung giao diện
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
