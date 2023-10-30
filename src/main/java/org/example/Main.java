package org.example;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //ArrayList<Double> max = new ArrayList<>(1000);
        for (int i = 0; i < 10; i++) {
            //System.out.println("Iteracja nr: " + i + "\n");
            double x_max = SW(Main::f45);
            System.out.println(x_max + "\n");
            //max.add(i, x_max);
        }
        //System.out.println("AVG: " + max.stream().mapToDouble(a -> a).average());
    }

    public static double SW(function f) {
        ArrayList<Double> startArray = generateStart();
        double[] xy = new double[] {startArray.get(0), startArray.get(1)};
        double[] xy_new = new double[2];
        double[] xy_max = xy.clone();
        /*ArrayList<String> history = new ArrayList<>(1);
        ArrayList<String> historyWorse = new ArrayList<>(1);*/
        int M = 0; //Liczba iteracji
        do {
            for (int i = 0; i < Starting_Parameters.L; i++) {
                xy_new[0] = randomNumberX(xy[0], startArray.get(2));
                xy_new[1] = randomNumberY(xy[1], startArray.get(2));
                if (f.count(xy_new) > f.count(xy)) {
                    xy = xy_new.clone();
                    //history.add(history.size(), "nr: " + history.size() + " " + Arrays.toString(xy) + " f(x,y): " + f.count(xy) + "\n");
                    if (Starting_Parameters.REMEMBER_MAX) {
                        if (f.count(xy) > f.count(xy_max)) {
                            xy_max = xy.clone();
                        }
                    }
                } else {
                    //double lastRezult = f.count(xy);
                    xy = chooseXY(startArray.get(2), xy, xy_new, f, Starting_Parameters.BOLTZMANN).clone();
                    /*if (xy[0] == xy_new[0] && xy[1] == xy_new[1] && lastRezult > f.count(xy_new)) {
                        historyWorse.add(historyWorse.size(), "GORSZE nr: " + historyWorse.size() + " " + Arrays.toString(xy) + " f(x,y): " + f.count(xy) + "\n");
                    }*/
                }
                M++;
                if (M < Starting_Parameters.M_MAX) break;
            }
            startArray.set(1, startArray.get(1) * Starting_Parameters.ALPHA);
        } while (M < Starting_Parameters.M_MAX);
        //System.out.println("Ilość zmian: " + history.size() + "\nIlość gorszych: " + historyWorse.size() + "\n" + "\nOstatnie: " + f.count(xy));
        if (Starting_Parameters.REMEMBER_MAX) {
            return f.count(xy_max);
        }else {
            return f.count(xy);
        }
    }

    public static double[] chooseXY(double T, double[] xy, double[] xy_new, function f, double B) {
        double prawd1 = Math.exp(-(f.count(xy) - f.count(xy_new))/(T * B));
        double prawd2 = Math.random();
        if (prawd1 > prawd2) return xy_new;
        else return xy;
    }

    public interface function {
        double count(double[] xy);
    }

    public static double f31(double[] xy) {
        if (xy[0] > -105 && xy[0] < -95) {
            return -2 * Math.abs(xy[0] + 100) + 10;
        } else if (xy[0] > 95 && xy[0] < 105) {
            return -2.2 * Math.abs(xy[0] - 100) + 11;
        }
        return 0;
    }

    public static double f33(double[] xy) {
        int[] h = new int[10];
        for (int i = 0; i < 10; i++) {
            h[i] = 10 - i;
        }
        double[] xs = new double[]{0.0, 293.8926, 475.5283, 475.5283,
                293.8926, 0.0, -293.8926, -475.5283, -475.5283, -293.8926};
        double[] ys = new double[]{500.0, 404.5085, 154.5085, -154.5085,
                -404.5085, -500.0, -404.5085, -154.5085, 154.5085, 404.5085};
        for (int i = 0; i < 10; i++) {
            double result = -((double) h[i] / 25) * Math.sqrt(Math.pow(xy[0] - xs[i], 2) + Math.pow(xy[1] - ys[i], 2)) + h[i];
            if (result > 0) return result;
        }
        return 0;
    }

    public static double f45(double[] xy) {
        return 21.5 + xy[0] * Math.sin(4 * Math.PI * xy[0]) + xy[1] * Math.sin(20 * Math.PI * xy[1]);
    }

    //Generates: [0. x-start - początkowa wartość x,
    // 1. y_start - początkowa wartość y dla dwuwymiarowych,
    // 2. T-start - startowa temperatura,
    // 3. alpha - współczynnik stygnięcia,
    // 4. M_max - maksymalna liczba iteracji,
    // 5. L-liczba kroków w epoce
    public static ArrayList<Double> generateStart() {
        ArrayList<Double> start = new ArrayList<>(6);
        start.add(0, (Starting_Parameters.MIN_X + Math.random() * (Starting_Parameters.MAX_X - Starting_Parameters.MIN_X)));
        start.add(1, (Starting_Parameters.MIN_Y + Math.random() * (Starting_Parameters.MAX_Y - Starting_Parameters.MIN_Y)));
        start.add(2, Starting_Parameters.T_START);
        return start;
    }

    public static double randomNumberX(double x, double T) {
        double random = Math.random();
        double min = x - T * Starting_Parameters.T_MNOZ;
        if (min < Starting_Parameters.MIN_X) min = Starting_Parameters.MIN_X;
        double max = x + T * Starting_Parameters.T_MNOZ;
        if (max > Starting_Parameters.MAX_X) max = Starting_Parameters.MAX_X;
        return min + random * (max - min);
    }

    public static double randomNumberY(double y, double T) {
        double random = Math.random();
        double min = y - T * Starting_Parameters.T_MNOZ;
        if (min < Starting_Parameters.MIN_Y) min = Starting_Parameters.MIN_Y;
        double max = y + T * Starting_Parameters.T_MNOZ;
        if (max > Starting_Parameters.MAX_Y) max = Starting_Parameters.MAX_Y;
        return min + random * (max - min);
    }
}

