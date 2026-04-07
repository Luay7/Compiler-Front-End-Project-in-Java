import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class RecursiveDescentParser {
    private static StringTokenizer tokenizer;
    private static String currentSymbol;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String expression;
            while ((expression = reader.readLine()) != null) {
                tokenizer = new StringTokenizer(expression, " ");
                currentSymbol = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
                if (processE() && currentSymbol != null && currentSymbol.equals("$")) {
                    System.out.println("Correct Syntax");
                } else {
                    System.out.println("Syntax Error");
                }
            }
        } catch (IOException exception) {
            System.out.println("An error occurred while reading the file: " + exception.getMessage());
        }
    }

    private static boolean processE() {
        return processT() && processEPrime();
    }

    private static boolean processEPrime() {
        if (currentSymbol != null && (currentSymbol.equals("+") || currentSymbol.equals("-"))) {
            currentSymbol = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
            return processT() && processEPrime();
        }
        return true; 
    }

    private static boolean processT() {
        return processF() && processTPrime();
    }

    private static boolean processTPrime() {
        if (currentSymbol != null && (currentSymbol.equals("*") || currentSymbol.equals("/"))) {
            currentSymbol = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
            return processF() && processTPrime();
        }
        return true; 
    }

    private static boolean processF() {
        if (currentSymbol != null) {
            if (currentSymbol.equals("(")) {
                currentSymbol = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
                if (processE() && currentSymbol != null && currentSymbol.equals(")")) {
                    currentSymbol = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
                    return true;
                }
                return false;
            } else if (currentSymbol.equals("id")) {
                currentSymbol = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
                return true;
            }
        }
        return false;
    }
}