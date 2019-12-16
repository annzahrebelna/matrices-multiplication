package matrix;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public final class Matrix {
    private static final Supplier<Byte> byteSupplier = () -> Math.random() < 0.5 ? (byte)0 : (byte)1;

    public static Matrix getSquareMatrix(short n) {
        return new Matrix(n, n);
    }

    public static Matrix getSquareMatrix(short n, Supplier<Byte> byteSupplier) {
        return new Matrix(n, n, byteSupplier);
    }

    private final short n;
    private final short m;
    private final byte[][] data;

    public Matrix(short n, short m, byte[][] data) {
        this.n = n;
        this.m = m;
        this.data = copyArray(data);
    }

    public Matrix(short n, short m) {
        this.n = n;
        this.m = m;
        data = new byte[n][m];
        initialize(byteSupplier);
    }

    public Matrix(short n, short m, Supplier<Byte> byteSupplier) {
        this.n = n;
        this.m = m;
        data = new byte[n][m];
        initialize(byteSupplier);
    }

    public byte[][] getData() {
        return copyArray(data);
    }

    public byte[][] getTransposedData() {
        byte[][] transposedData = new byte[m][n];
        for (short i=0; i<n; i++) {
            for (short j=0; j<m; j++) {
                transposedData[i][j] = data[j][i];
            }
        }
        return transposedData;
    }

    public byte[] getRow(short i) {
        return data[i].clone();
    }

    public byte[] getColumn(short j) {
        byte[] column = new byte[m];
        for (short i=0; i<n; i++) {
            column[i] = data[i][j];
        }
        return column;
    }

    public boolean canBeMultiplied(Matrix matrix) {
        return Short.valueOf(this.getM()).equals(matrix.getN());
    }

    private byte[][] copyArray(byte[][] data) {
        int length = data.length;
        byte[][] dataCopy = new byte[length][data[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(data[i], 0, dataCopy[i], 0, data[i].length);
        }
        return dataCopy;
    }

    private void initialize(Supplier<Byte> byteSupplier) {
        for (short i=0; i<n; i++) {
            for (short j=0; j<m; j++) {
                data[i][j] = byteSupplier.get();
            }
        }
    }
}
