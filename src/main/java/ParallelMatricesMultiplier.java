import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.*;

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

        for (short i=0; i<n; i++) {
            Runnable task = new MultiplyRowOnMatrixTask(i, n, m1.getRow(i), m2);
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
        private Matrix matrix;

        @Override
        public void run() {
            for (short j=0; j<matrix.getM(); j++) {
                byte value = arraysMultiplier.multiply(rowSize, row, matrix.getColumn(j));
                result[rowIndex][j] = value;
            }
        }
    }
}
