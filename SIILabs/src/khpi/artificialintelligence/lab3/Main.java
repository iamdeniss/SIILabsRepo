package khpi.artificialintelligence.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<List<Double>, List<Integer>> startValues = new LinkedHashMap<>();
        startValues.put(Arrays.asList(167d, 66d), Arrays.asList(1, 0));
        startValues.put(Arrays.asList(177d, 75d), Arrays.asList(1, 1));
        startValues.put(Arrays.asList(157d, 59d), Arrays.asList(0, 0));
        startValues.put(Arrays.asList(190d, 84d), Arrays.asList(1, 1));
        startValues.put(Arrays.asList(175d, 63d), Arrays.asList(0, 1));
        startValues.put(Arrays.asList(210d, 90d), Arrays.asList(1, 1));
        startValues.put(Arrays.asList(140d, 45d), Arrays.asList(0, 0));
        startValues.put(Arrays.asList(157d, 69d), Arrays.asList(1, 0));

        List<Double> maxXValues = findMaxX(startValues.keySet());
        normXValues(startValues, maxXValues);

        double[][] w = initWMatrix();
        double[] w0 = initW0Vector();

        Teacher.teach(w, w0, startValues);

        char isContinue = 'y';
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (isContinue == 'y') {
                List<Double> heightAndWeight = new ArrayList<>();
                System.out.println();
                System.out.print("Enter height: ");
                heightAndWeight.add(Double.parseDouble(bufferedReader.readLine()) / maxXValues.get(0));
                System.out.print("Enter weight: ");
                heightAndWeight.add(Double.parseDouble(bufferedReader.readLine()) / maxXValues.get(1));
                int[] y = getRealY(w, w0, heightAndWeight);

                if (y[0] == 0 & y[1] == 0)
                    System.out.println("This is a jockey!");
                else if (y[0] == 1 & y[1] == 1)
                    System.out.println("This is a football player!");
                else System.out.println("This is an athlete!");

                System.out.print("Continue? (y/n) ");
                isContinue = bufferedReader.readLine().charAt(0);
            }
        } catch (IOException e) {
            System.out.println("[EXCEPTION] " + e.getMessage());
        }
    }

    private static List<Double> findMaxX(Set<List<Double>> values) {
        double maxX1 = 0, maxX2 = 0;
        for(List<Double> xValues : values) {
            if(xValues.get(0) > maxX1)
                maxX1 = xValues.get(0);
            if(xValues.get(1) > maxX2)
                maxX2 = xValues.get(1);
        }
        return Arrays.asList(maxX1, maxX2);
    }

    private static void normXValues(Map<List<Double>, List<Integer>> startValues, List<Double> maxXValues) {
        for(List<Double> xValues : startValues.keySet()) {
            xValues.set(0, xValues.get(0) / maxXValues.get(0));
            xValues.set(1, xValues.get(1) / maxXValues.get(1));
        }
    }

    private static double[][] initWMatrix() {
        Random random = new Random();
        double[][] wMatrix = new double[2][2];
        for(int i = 0; i < wMatrix.length; i++) {
            for(int j = 0; j < wMatrix[0].length; j++) {
                wMatrix[i][j] = random.nextDouble() * 2 - 1;
            }
        }
        return wMatrix;
    }

    private static double[] initW0Vector() {
        Random random = new Random();
        double[] w0Vector = new double[2];
        for(int i = 0; i < w0Vector.length; i++) {
            w0Vector[i] = random.nextDouble() * 2 - 1;
        }
        return w0Vector;
    }

    static int[] getRealY(double[][] w, double[] w0, List<Double> values) {
        double[] y = new double[2];
        for(int i = 0; i < w.length; i++) {
            for(int j = 0; j < w[0].length; j++) {
                y[i] += w[i][j] * values.get(j);
            }
            y[i] += w0[i];
        }
        return normalization(y);
    }

    private static int[] normalization(double[] y) {
        int[] normY = new int[2];
        for(int i = 0; i < y.length; i++) {
            if (y[i] >= 0) normY[i] = 1;
            else normY[i] = 0;
        }
        return normY;
    }
}
