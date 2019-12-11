import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class Matrix {
    private static final Supplier<Byte> byteSupplier = () -> Math.random() < 0.5 ? (byte)0 : (byte)1;

    private final short n;
    private final short m;

    private byte[][] data;

    public Matrix(short n, short m) {
        this.n = n;
        this.m = m;
        data = new byte[n][m];
    }

    public void initialize(Supplier<Byte> byteSupplier) {
        for (short i=0; i<n; i++) {
            for (short j=0; j<m; j++) {
                data[i][j] = byteSupplier.get();
            }
        }
    }

    public void initialize() {
        initialize(byteSupplier);
    }

    public byte[] getRow(short i) {
        return data[i];
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
}
