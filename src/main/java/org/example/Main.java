package org.example;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        double x_max = SW(Main::f3);
        System.out.println(x_max);
    }

    public static double SW(function f) {
        ArrayList<Double> startArray = generate_Start();
        int era = 0;
        double x = startArray.get(0);
        int M = 0; //Liczba iteracji
        do {
            for (int i = 0; i < startArray.get(4); i++) {
                double x_new = randomNumber(x, startArray.get(1));
                if (f.count(x_new) > f.count(x)) {
                    x = x_new;
                } else {
                    x = chooseX(startArray.get(1), x, x_new, f, startArray.get(5));
                }
                M++;
                if (M < startArray.get(3)) break;      //Plus 1, ponieważ liczba iteracji jest typem Double
            }
            startArray.set(1, startArray.get(1) * startArray.get(2));
        } while (M < startArray.get(3));

        System.out.println("M: " + M + "X: " + x);
        return f.count(x);
    }

    public static double chooseX(double T, double x, double x_new, function f, double B) {
        double prawd1 = Math.exp(-(f.count(x_new) - f.count(x))/(T * B));
        double prawd2 = Math.random();
        if (prawd1 > prawd2) return x_new;
        else return x;
    }

    public interface function {
        double count(double x);
    }

    public static double f3(double x) {
        if (x > -105 && x < -95) {
            return -2 * Math.abs(x + 100) + 10;
        } else if (x > 95 && x < 105) {
            return -2.2 * Math.abs(x - 100) + 11;
        }
        return 0;
    }

    //Generates: [0. x-start - początkowa wartość x, 1. T-start - startowa temperatura,
    // 2. alpha - współczynnik stygnięcia w %,
    // 3. M_max - maksymalna liczba iteracji,  4. L-liczba kroków w epoce]
    public static ArrayList<Double> generate_Start() {
        ArrayList<Double> start = new ArrayList<>(5);
        start.add(0, (Math.random() * 300 - 150)); //x_start
        start.add(1, 500.0); //T
        start.add(2, 0.999); //alpha
        start.add(3, 3000.0); //M_max
        start.add(4, 1.0); //kroki w epoce
        start.add(5, 0.1); //Stała Boltzmana ?
        return start;
    }

    public static double randomNumber(double x, double T) {
        double random = Math.random();
        double min = x - 2 * T;
        double max = x + 2 * T;
        double rand = min + random * (max - min);
        System.out.println("x: " + x + " Random: " + random + " min: " + min + " max: " + max + " rand " + rand  + " T: " + T);
        return rand;
    }
}

