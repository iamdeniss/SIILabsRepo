package khpi.artificialintelligence.lab3;

import java.util.List;
import java.util.Map;

public class Teacher {
    private static final int COUNT_OF_CORRECT_RECOGNITIONS = 8;

    public static void teach(double[][] w, double[] w0, Map<List<Double>, List<Integer>> startValues) {
        int correctRecognitions = 0;
        int counterForTeachingSteps = 0;
        double[] errorsVector = new double[2];
        while(correctRecognitions != COUNT_OF_CORRECT_RECOGNITIONS) {
            correctRecognitions = 0;
            for(Map.Entry<List<Double>, List<Integer>> XY : startValues.entrySet()) {
                int[] y = Main.getRealY(w, w0, XY.getKey());
                for(int j = 0; j < 2; j++) {
                    errorsVector[j] = XY.getValue().get(j) - y[j];
                }
                if (errorsVector[0] == 0 & errorsVector[1] == 0) correctRecognitions++;
                else correctRecognitions = 0;
                newW(getDeltaW(errorsVector, XY.getKey()), w);
                newW0(w0, errorsVector);
            }
            counterForTeachingSteps++;
            System.out.println("Number of current step: " + counterForTeachingSteps
                                + ", correct recognitions: " + correctRecognitions);
        }
    }

    private static void newW(double[][] dW, double[][] w) {
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[0].length; j++) {
                w[i][j] += dW[i][j];
            }
        }
    }

    private static void newW0(double[] w0, double[] dW0) {
        for (int i = 0; i < dW0.length; i++) {
            w0[i] += dW0[i];
        }
    }

    private static double[][] getDeltaW(double[] error, List<Double> values)
    {
        double[][] dW = new double[error.length][values.size()];
        for (int i = 0; i < dW.length; i++)
            for (int j = 0; j < dW[0].length; j++)
                dW[i][j] = error[i] * values.get(j);
        return dW;
    }
}
