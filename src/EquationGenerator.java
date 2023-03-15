public class EquationGenerator {
    private int maxY;
    private int maxM;
    private int maxC;
    private int minY;
    private int minM;
    private int minC;

    public EquationGenerator() {
        this(1, 10);
    }

    public EquationGenerator(int min, int max) {
        this(min,min, min, max, max, max);
    }

    public EquationGenerator(int minY, int minM, int minC, int maxY, int maxM, int maxC) {
        this.maxY = maxY;
        this.maxM = maxM;
        this.maxC = maxC;
        this.minY = minY;
        this.minM = minM;
        this.minC = minC;
    }

    public Equation generateLinearEquation() {
        int y = Tools.randomIntBetween(minY, maxY);
        int m = Tools.randomIntBetween(minM, maxM);
        int c = Tools.randomIntBetween(minC, maxC);

        return new Equation(y, m, c);
    }
}