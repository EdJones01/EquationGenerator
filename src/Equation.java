import java.text.DecimalFormat;
import java.util.Arrays;

public class Equation {
    private double y;
    private double m;
    private double c;
    private double x;

    public Equation(double y, double m, double c) {
        this.y = y;
        this.m = m;
        this.c = c;

        x = (y - c) / m;
    }

    public String toString() {
        if(m == 1)
            return (int) y + " = x + " + (int) c;
        return (int) y + " = " + (int) m + "x + " + (int) c;
    }

    public String[] getSimplifiedAnswerStrings() {
        return simplifyFraction((int) (y - c) + "/" + (int) m);
    }

    public static String[] simplifyFraction(String fraction) {
        int numerator, denominator;
        String[] result;

        String[] parts = fraction.split("/");
        numerator = Integer.parseInt(parts[0]);
        denominator = Integer.parseInt(parts[1]);

        if (numerator == 0 || denominator == 0) {
            result = new String[]{"0"};
            return result;
        }

        int gcd = Math.abs(findGCD(numerator, denominator));

        numerator /= gcd;
        denominator /= gcd;

        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        if (denominator == 1) {
            result = new String[]{Integer.toString(numerator)};
            return result;
        }

        result = new String[]{numerator + "/" + denominator, ""};


        if (numerator < 0) {
            result = Arrays.copyOf(result, 3);
            result[1] = (-numerator) + "/" + (- denominator);
        }

        result[result.length-1] = round((((double) numerator/ (double) denominator)), 3)
                .replaceAll("()\\.0+$|(\\..+?)0+$", "$2");

        return result;
    }

    public static int findGCD(int num1, int num2) {
        if (num2 == 0) {
            return num1;
        }
        return findGCD(num2, num1 % num2);
    }

    public static String round(double value, int decimalPlaces) {
        String pattern = "0.";
        for (int i = 0; i < decimalPlaces; i++) {
            pattern += "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public double getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}