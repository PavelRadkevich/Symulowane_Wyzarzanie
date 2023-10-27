package org.example;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            double x_max = SW(Main::f32);
            System.out.println(x_max);
        }
        //f32(2, 2);
    }

    public static double SW(function f) {
        boolean rememberMax = true;
        ArrayList<Double> startArray = generate_Start();
        int era = 0;
        double[] xy = new double[] {startArray.get(0), startArray.get(1)};
        double[] xy_new = new double[2];
        double[] xy_max = xy.clone();
        int M = 0; //Liczba iteracji
        do {
            for (int i = 0; i < startArray.get(5); i++) {
                xy_new[0] = randomNumber(xy[0], startArray.get(2));
                xy_new[1] = randomNumber(xy[1], startArray.get(2));
                if (f.count(xy_new) > f.count(xy)) {
                    xy = xy_new.clone();
                    if (rememberMax) {
                        if (f.count(xy) > f.count(xy_max)) {
                            xy_max = xy.clone();
                        }
                    }
                } else {
                    xy = chooseX(startArray.get(2), xy, xy_new, f, startArray.get(6)).clone();
                }
                M++;
                if (M < startArray.get(4)) break;      //Plus 1, ponieważ liczba iteracji jest typem Double
            }
            startArray.set(1, startArray.get(1) * startArray.get(3));
        } while (M < startArray.get(4));
        if (rememberMax) {
            return f.count(xy_max);
        }else {
            return f.count(xy);
        }
    }

    public static double[] chooseX(double T, double[] xy,  double[] xy_new, function f, double B) {
        if (f.count(xy_new) == 0) return xy;
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

    public static double f32(double[] xy) {
        int[] nr = new int[10];
        for (int i = 0; i < 10; i++) {
            nr[i] = i;
        }
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

    //Generates: [0. x-start - początkowa wartość x,
    // 1. y_start - początkowa wartość y dla dwuwymiarowych,
    // 2. T-start - startowa temperatura,
    // 3. alpha - współczynnik stygnięcia w %,
    // 4. M_max - maksymalna liczba iteracji,
    // 5. L-liczba kroków w epoce
    public static ArrayList<Double> generate_Start() {
        ArrayList<Double> start = new ArrayList<>(6);
        double min = -550;
        double max = 550;
        start.add(0, (min + Math.random() * (max - min))); //x_start
        start.add(1, (min + Math.random() * (max - min))); //y_start
        start.add(2, 1400.0); //T
        start.add(3, 0.999); //alpha
        start.add(4, 5000.0); //M_max
        start.add(5, 1.0); //kroki w epoce
        start.add(6, 0.1); //Stała Boltzmana ?
        return start;
    }

    public static double randomNumber(double x, double T) {
        double random = Math.random();
        double min = x - 2 * T;
        double max = x + 2 * T;
        double rand = min + random * (max - min);
        //System.out.println("x: " + x + " Random: " + random + " min: " + min + " max: " + max + " rand " + rand  + " T: " + T);
        return rand;
    }
}

