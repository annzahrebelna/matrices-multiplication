package matrix;

public abstract class AbstractMatricesMultiplier implements MatricesMultiplier {

    private long start;
    private long finish;

    @Override
    public Matrix multiply(Matrix m1, Matrix m2) {
        if (!m1.canBeMultiplied(m2)) {
            throw new IllegalArgumentException("Matrices can not be multiplied");
        }
        start = System.currentTimeMillis();
        Matrix result = multiplyMatrices(m1, m2);
        finish = System.currentTimeMillis();
        return result;
    }

    public long getTimeSpent() {
        return finish - start;
    }

    protected abstract Matrix multiplyMatrices(Matrix m1, Matrix m2);
}
