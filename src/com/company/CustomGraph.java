package com.company;

import java.io.*;
import java.lang.reflect.Array;


public class CustomGraph {


    private final static String ILLEGAL_FUNC = "IllegalFunction";
    private final static String ILLEGAL_POINT = "IllegalPoint";

    //Создаём метод для перевода функции-строки в арифметическое выражение
    public static String translation(String func) throws IOException, InterruptedException {
        //Создаём экземпляр класса для запуска сторонней программы
        //указываем, что надо запустить программу python
        //с параметрами python\\translation.py, func

        ProcessBuilder pb = new ProcessBuilder("python", "python\\translation.py",
                func);


        //Запуск процесса
        Process p = pb.start();

        //Перенаправляем в поток с чтением данных
        InputStreamConsumerThread inputConsumer =
                new InputStreamConsumerThread(p.getInputStream(), true);
        InputStreamConsumerThread errorConsumer =
                new InputStreamConsumerThread(p.getErrorStream(), true);

        inputConsumer.start();
        errorConsumer.start();

        try {
            p.waitFor();
        } catch (InterruptedException exception) {
            System.out.println(exception.getLocalizedMessage());
            throw exception;
        }


        if (!errorConsumer.getOutput().isEmpty()) throw new IllegalArgumentException(inputConsumer.getOutput());

        //Результат работы – переведённая функция
        return inputConsumer.getOutput();
    }

    //Перегрузка метода buildLevelLines
    //Первый метод - изображение линий уровня функции
    //Метод запускает стороннюю питоновскую программу для изображения линий
    //Запуск аналогичен методу translation
    public static void buildLevelLines(String func) throws IOException {

        ProcessBuilder pb = new ProcessBuilder("python", "python\\levels.py",
                func);


        Process p = pb.start();


        InputStreamConsumerThread errorConsumer =
                new InputStreamConsumerThread(p.getErrorStream(), true);

        errorConsumer.start();


        try {
            p.waitFor();

        } catch (InterruptedException exception) {
            System.out.println("LevelLines: !!! " + exception.getLocalizedMessage());
        }

        if (errorConsumer.getOutput().contains(ILLEGAL_FUNC))
            throw new IllegalArgumentException(func + "is illegal function for build level lines algorithm");

    }

    //Второй метод - добавление к линиям уровня градиента и производной по направлению
    public static void buildLevelLines(String func, double x, double y, double xDir, double yDir) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python", "python\\levels_with_grad.py",
                func,
                Double.toString(x),
                Double.toString(y),
                Double.toString(xDir),
                Double.toString(yDir),
                grad(func, x, y));

        Process p = pb.start();

        InputStreamConsumerThread errorConsumer =
                new InputStreamConsumerThread(p.getErrorStream(), true);


        errorConsumer.start();

        try {
            p.waitFor();
        } catch (InterruptedException exception) {
            System.out.println(exception.getLocalizedMessage());
        }

        if (!errorConsumer.getOutput().isEmpty()) throw new IllegalArgumentException(func);
    }

    //Метод вычисления градиента
    public static String grad(String func, Double x, Double y) throws IOException {

        //Составление частной производной по х(у - параметр)
        String xDerivative = partialDerivative(func, "x", "y");
        //Составление частной производной по у(х - параметр)
        String yDerivative = partialDerivative(func, "y", "x");


        Double xDerivativeValue = 0.;
        Double yDerivativeValue = 0.;
        try {
            //вычисляем значение производной по х в введённой точке
            xDerivativeValue = evaluate(xDerivative, x.toString(), y.toString());
            //вычисляем значение производной по у в введённой точке
            yDerivativeValue = evaluate(yDerivative, y.toString(), x.toString());
        } catch (Exception e) {
            System.out.println("CustomGraph.GradEvaluate :" + e.getLocalizedMessage());
        }
        //результат - строка вида {****; ****}
        return "{" + round(xDerivativeValue, 3) + ";" + round(yDerivativeValue, 3) + "}";

    }

    //вычисление производной по направлению
    public static Double evaluateDirDerivative(String func, Double x, Double y, Double xDir, Double yDir) throws IOException {
        //Составление формул для расчёта
        String xDerivative = partialDerivative(func, "x", "y");
        String yDerivative = partialDerivative(func, "y", "x");

        double xDerivativeValue = 0.;
        double yDerivativeValue = 0.;
        try {
            //Вычисление частных производных
            xDerivativeValue = evaluate(xDerivative, x.toString(), y.toString());
            yDerivativeValue = evaluate(yDerivative, y.toString(), x.toString());
        } catch (Exception e) {
            System.out.println("CustomGraph.evaluateDirDerivative :" + e.getLocalizedMessage());
        }

        //Вычисляем координаты вектора
        double xDirVec = xDir - x;
        double yDirVec = yDir - y;
        //вычисляем длину вектора
        double dirLength = Math.sqrt(xDirVec * xDirVec + yDirVec * yDirVec);
        double alpha = xDir / dirLength, beta = yDir / dirLength;

        //результат - округлённое значение производной по направлению
        return round(xDerivativeValue * alpha + beta * yDerivativeValue, 2);

    }

    //метод округления
    private static Double round(double a, int scale) {
        a *= Math.pow(10, scale);
        a = Math.floor(a);
        a /= Math.pow(10, scale);
        return a;
    }

    //метод вычисления значения переданной функции
    private static double evaluate(String func, String x, String y) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("python", "python\\eval.py", func, x, y);

        Process p = pb.start();

        InputStreamConsumerThread inputConsumer =
                new InputStreamConsumerThread(p.getInputStream(), true);
        InputStreamConsumerThread errorConsumer =
                new InputStreamConsumerThread(p.getErrorStream(), true);


        inputConsumer.start();
        errorConsumer.start();

        try {
            p.waitFor();
        } catch (InterruptedException exception) {
            System.out.println(exception.getLocalizedMessage());
        }

        if (!errorConsumer.getOutput().isEmpty()) throw new IllegalArgumentException(func + "is unable to evaluate");

        return Double.parseDouble(inputConsumer.getOutput());
    }

    //метод составления частной производной
    private static String partialDerivative(String func, String paramToDiff, String param2) throws IOException {

        //запускаем питоновский код
        ProcessBuilder pb = new ProcessBuilder("python", "python\\partial_derivative.py",
                func, paramToDiff, param2);


        Process p = pb.start();

        InputStreamConsumerThread inputConsumer =
                new InputStreamConsumerThread(p.getInputStream(), true);
        InputStreamConsumerThread errorConsumer =
                new InputStreamConsumerThread(p.getErrorStream(), true);


        inputConsumer.start();
        errorConsumer.start();

        try {
            p.waitFor();
        } catch (InterruptedException exception) {
            System.out.println(exception.getLocalizedMessage());
        }

        if (!errorConsumer.getOutput().isEmpty()) throw new IllegalArgumentException(func);


        return inputConsumer.getOutput().replaceAll("\s", "");


    }

}