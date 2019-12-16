package benchmark;

import matrix.ParallelMatricesMultiplier;
import matrix.SequentialMatricesMultiplier;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class MatrixMultiplicationBenchmarking {

    private static final int DEFAULT_FORKS = 2;
    private static final int DEFAULT_WARMUP_ITERATIONS = 2;
    private static final int DEFAULT_MEASUREMENT_ITERATIONS = 5;

    public static void main(String[] args) throws Exception {
        short n = getValidSize(args);

        ChainedOptionsBuilder builder = new OptionsBuilder()
                .include(MatrixMultiplicationBenchmarking.class.getSimpleName())
                .mode(Mode.AverageTime)
                .forks(DEFAULT_FORKS)
                .warmupIterations(DEFAULT_WARMUP_ITERATIONS)
                .measurementIterations(DEFAULT_MEASUREMENT_ITERATIONS)
                .timeUnit(TimeUnit.SECONDS)
                .param("matrixSize", String.valueOf(n));

        new Runner(builder.build()).run();
    }

    private static short getValidSize(String[] args) {
        int n;
        if (args.length == 1) {
            n = Integer.parseUnsignedInt(args[0]);
            if (n<1 || n>10000) {
                throw new IllegalArgumentException("Size must be in the range [1..10000]");
            }
        } else {
            throw new IllegalArgumentException("Please enter ONE number for size");
        }
        return (short) n;
    }

    @Benchmark
    public Object sequentialMatrixMultiplication(MatrixProvider matrixProvider) {
        return new SequentialMatricesMultiplier()
                .multiply(matrixProvider.getMatrix1(), matrixProvider.getMatrix2());
    }

    @Benchmark
    public Object parallelMatrixMultiplication(MatrixProvider matrixProvider) {
        return new ParallelMatricesMultiplier()
                .multiply(matrixProvider.getMatrix1(), matrixProvider.getMatrix2());
    }

}
