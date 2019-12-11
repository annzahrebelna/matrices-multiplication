public class Main {

    public static void main(String[] args) {
        short n = getValidSize(args);

        SquareMatrix matrix1 = new SquareMatrix(n);
        matrix1.initialize();
        SquareMatrix matrix2 = new SquareMatrix(n);
        matrix2.initialize();

        AbstractMatricesMultiplier sequentialMultiplier = new SequentialMatricesMultiplier();
        System.out.println("Sequential multiplication is running...");
        sequentialMultiplier.multiply(matrix1, matrix2);
        System.out.println("Finished: " + sequentialMultiplier.getTimeSpent() + "ms");

        AbstractMatricesMultiplier parallelMultiplier = new ParallelMatricesMultiplier();
        System.out.println("Parallel multiplication is running...");
        parallelMultiplier.multiply(matrix1, matrix2);
        System.out.println("Finished: " + parallelMultiplier.getTimeSpent() + "ms");
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
}
