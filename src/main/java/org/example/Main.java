package org.example;

import java.util.Scanner;


public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
        String equation = null;
        while (true){
            System.out.println("1 - ввести рівняння\n" + "2 - підібрати корінь\n" + "3 - отримати всі рівняння по кореню");
            int s = sc.nextInt();
            switch (s) {
                case 1 -> equation = MathService.inputEquation();
                case 2 -> {
                    if(equation != null){
                        System.out.println("Введіть х для рівняння " + equation);
                        double x = sc.nextDouble();
                        MathService.isSolution(equation,  x);
                    }
                    else{
                        System.out.println("Введіть для початку рівняння");
                    }
                }
                case 3 ->{
                    System.out.println("Введіть х для пошуку рівнянь ");
                    double x = sc.nextDouble();
                    System.out.println(MathService.findAllBySolution(x));
                }
                default -> System.out.println("Такої команди не існує");
            }
        }


    }
}
