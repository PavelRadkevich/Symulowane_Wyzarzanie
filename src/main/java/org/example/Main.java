package org.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> result = generate_Start(x -> f3(2));
    }

    public interface function {
        int apply(int x);
    }

    public static int f3(int x) {
        if (x > -105 && x < -95) {
            return 2 * Math.abs(x + 100) + 10;
        } else if (x > 95 && x < 105) {
            return 2 * Math.abs(x - 100) + 11;
        }
        return 0;
    }

    //Generates: [x-start,
    public static ArrayList<Integer> generate_Start(function f) {
        List<Integer> start = new ArrayList<Integer>(2);
        start.set(0, f.apply((int) (Math.random() * 300 - 150)));
        return (ArrayList<Integer>) start;
    }
}

