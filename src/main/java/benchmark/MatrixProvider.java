package benchmark;

import matrix.Matrix;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.BenchmarkParams;

@State(Scope.Benchmark)
public class MatrixProvider {
    @Param({})
    private short matrixSize;
    private Matrix matrix1;
    private Matrix matrix2;

    public MatrixProvider() {}

    @Setup
    public void setup(BenchmarkParams parameters) {
        matrix1 = Matrix.getSquareMatrix(matrixSize);
        matrix2 = Matrix.getSquareMatrix(matrixSize);
    }

    public Matrix getMatrix1() {
        return matrix1;
    }

    public Matrix getMatrix2() {
        return matrix2;
    }
}
