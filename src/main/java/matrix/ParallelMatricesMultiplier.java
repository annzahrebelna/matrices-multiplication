package matrix;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.*;

import static utils.MyCollectors.toByteArray;

public class ParallelMatricesMultiplier extends AbstractMatricesMultiplier {

    private ArraysMultiplier arraysMultiplier;
    private byte[][] result;

    public ParallelMatricesMultiplier() {
        arraysMultiplier = new Modulo2ArraysMultiplier();
    }

    @Override
    protected Matrix multiplyMatrices(Matrix m1, Matrix m2) {
        ExecutorService executorService = Executors.newWorkStealingPool();
        short n = m1.getN();
        short m = m2.getM();
        result = new byte[n][m];
        byte[][] transposedM2 = m2.getTransposedData();
        for (int i=0; i<n; i++) {
            Runnable task = new MultiplyRowOnMatrixTask(i, n, m1.getRow(i), transposedM2);
            executorService.submit(task);
        }
        executorService.shutdown();
        waitForTasksBeingExecuted(executorService);
        return new Matrix(n, m, result);
    }

    private void waitForTasksBeingExecuted(ExecutorService executorService) {
        boolean isDone;
        try {
            isDone = executorService.awaitTermination(1, TimeUnit.HOURS);
            if (!isDone) {
                throw new RuntimeException("Execution was running more than 1 Hour, so it was interrupted.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Execution was interrupted.");
        }
    }

    @Data
    @AllArgsConstructor
    private class MultiplyRowOnMatrixTask implements Runnable {

        private int rowIndex;
        private short rowSize;
        private byte[] row;
        private byte[][] transposedMatrix;

        @Override
        public void run() {
            byte[] resultRow = Arrays.stream(transposedMatrix)
                    .map(row2 -> arraysMultiplier.multiply(rowSize, row, row2))
                    .collect(toByteArray());
            result[rowIndex] = resultRow;
        }
    }
}
