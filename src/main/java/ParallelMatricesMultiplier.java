import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        byte[][] transposed = m2.getTransposedData();
        for (short i=0; i<n; i++) {
            Runnable task = new MultiplyRowOnMatrixTask(i, n, m1.getRow(i), transposed);
            executorService.submit(task);
        }
        executorService.shutdown();
        boolean isDone;
        try {
            isDone = executorService.awaitTermination(1, TimeUnit.HOURS);
            if (!isDone) {
                throw new RuntimeException("Execution was running more than 1 Hour, so it was interrupted.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Execution was interrupted.");
        }
        return new Matrix(n, m, result);
    }

    @Data
    @AllArgsConstructor
    private class MultiplyRowOnMatrixTask implements Runnable {

        private short rowIndex;
        private short rowSize;
        private byte[] row;
        private byte[][] matrix;

        @Override
        public void run() {
            byte[] resultRow = new byte[rowSize];
            for (int i=0; i<rowSize; i++) {
                resultRow[i] = arraysMultiplier.multiply(rowSize, row, matrix[i]);
            }
            result[rowIndex] = resultRow;
        }
    }
}
