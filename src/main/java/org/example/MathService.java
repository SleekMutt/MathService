package org.example;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MathService {
    public static final Repository repository;
    public static final Scanner sc = new Scanner(System.in);

    static {
        try {
            repository = Repository.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasValidBrackets(String equation) {
        int i = 0;
        for (char c : equation.toCharArray()) {
            if (c == '(') {
                i++;
            }
            else if (c == ')') {
                if (i == 0) {
                    return false;
                }
                i--;
                if (i < 0) {
                    return false;
                }
            }
        }
        if(i != 0){
            System.out.println("Дужки розставлені некоректно");
        }
        return i == 0;
    }
    public static boolean hasValidSymbols(String equation){
        equation = equation.replaceAll("[()]", "");
        if(equation.charAt(0) == '*' || equation.charAt(0) == '/'){
            return false;
        }
        for (int i = 1; i < equation.length(); i++) {
            if(!Character.isDigit(equation.charAt(i)) && equation.charAt(i) != 'x'){
                boolean b = equation.charAt(i + 1) == '/' || equation.charAt(i + 1) == '*' || equation.charAt(i + 1) == '=';
                if(b){
                    System.out.println("Знаки розставлені некоректно");
                    return false;
                }
                if((equation.charAt(i) == '+' || equation.charAt(i) == '-') && (equation.charAt(i + 1) == '+' || equation.charAt(i + 1) == '-')){
                    System.out.println("Знаки розставлені некоректно");
                    return false;
                }
            }

        }
        return true;
    }
    public static String inputEquation() throws SQLException {
        String equation;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Введіть рівняння: ");
            equation = scanner.nextLine();
            if(hasValidSymbols(equation) && hasValidBrackets(equation)){
                System.out.println("Рівняння введене правильно. Йде збереження в бд.");
                repository.saveEquation(equation);
                return equation;
            }
            else{
                System.out.println("Equation is invalid. Try again");
            }
        }
    }
    public static boolean isSolution(String s, double x) throws SQLException {
        List<String> strings = Arrays.asList(s.split("="));
        String equation = strings.stream().filter(el -> el.contains("x")).findFirst().get();
        String result = strings.stream().filter(el -> !el.contains("x")).findFirst().get();

        DoubleEvaluator eval = new DoubleEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<>();
        variables.set("x", x);
        Double evaluate = eval.evaluate(equation, variables);
        if (evaluate == Double.parseDouble(result) || Math.abs(evaluate - Double.parseDouble(result)) <= 1.0e-9){
            System.out.println("Корінь є правильним");
            repository.addSolution(s, x);
            return true;
        }
        else{
            System.out.println("Корінь є неправильним");
            return false;
        }
    }
    public static List<Equation> findAllBySolution(double x) throws SQLException {
        return repository.findAllBySolution(x);
    }
}
