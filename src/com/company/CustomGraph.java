package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.swing.*;

public class CustomGraph {

    public static String translation(String func) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python", "python\\translation.py",
                func);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String funcTrans = in.readLine();
        if (funcTrans.contains("Traceback"))
            return Arrays.deepToString(in.lines().toArray());
        return funcTrans;
    }



    public static String buildLevelLines(String func) throws IOException {


        ProcessBuilder pb = new ProcessBuilder("python", "python\\levels.py",
                func);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String imgName = in.readLine();

        if (!imgName.contains(".png"))
            do {
                imgName = in.readLine();
            } while (!imgName.contains(".png"));

        var frame = new JFrame("Levels");

        System.out.println(func);
        frame.getContentPane().add(new ImagePanel(imgName));
        frame.setSize(900, 900);
        frame.setVisible(true);
//        System.out.println(imgName);
        System.out.println(Arrays.deepToString(in.lines().toArray()));
//        return imgName;
        return Arrays.deepToString(in.lines().toArray());
    }


    public static String grad(String func, Double x, Double y) throws IOException {

        String xDerivative = partialDerivative(func, "x", "y");
        String yDerivative = partialDerivative(func, "y", "x");

        System.out.println("xDer :" + xDerivative);
        System.out.println("yDer :" + yDerivative);

        Double xDerivativeValue =0.;
        Double yDerivativeValue = 0.;
        try {
            xDerivativeValue = evaluate(xDerivative, x.toString(), y.toString());
            yDerivativeValue = evaluate(yDerivative, y.toString(), x.toString());
        } catch (Exception e) {
            System.out.println("CustomGraph.GradEvaluate :" + e.getMessage());
        }
        return "{" +  round(xDerivativeValue, 3) + ";" + round(yDerivativeValue, 3) + "}";

    }
    public static Double evaluateDirDerivative(String func, Double x, Double y, Double xDir, Double yDir ) throws IOException {
        String xDerivative = partialDerivative(func, "x", "y");
        String yDerivative = partialDerivative(func, "y", "x");

        System.out.println("xDer :" + xDerivative);
        System.out.println("yDer :" + yDerivative);

        double xDerivativeValue =0.;
        double yDerivativeValue = 0.;
        try {
            xDerivativeValue = evaluate(xDerivative, x.toString(), y.toString());
            yDerivativeValue = evaluate(yDerivative, y.toString(), x.toString());
        } catch (Exception e) {
            System.out.println("CustomGraph.evaluateDirDerivative :" + e.getMessage());
        }
        double xDirVec = xDir - x;
        double yDirVec = yDir - y;
        double dirLength = Math.sqrt(xDirVec * xDirVec + yDirVec * yDirVec);
        double alpha =  xDir/dirLength, beta = yDir/dirLength;

        return round( xDerivativeValue * alpha + beta * yDerivativeValue, 2);

    }

    private static Double round (double a, int scale) {
        a *=  Math.pow(10, scale);
        a = Math.floor(a);
        a /=Math.pow(10, scale);
        return a;
    }

    private static double evaluate(String func, String x, String y) throws IOException {
        func = translation(func);
        System.out.println("Method.evaluate :" + func);
        ProcessBuilder pb = new ProcessBuilder("python", "python\\eval.py",
                func, x, y);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));


        try {
            return Double.parseDouble(in.readLine());
        } catch (Exception e) {
            System.out.println("Method: evaluate " + Arrays.deepToString(in.lines().toArray()));
            return 0;
        }
    }


    private static String partialDerivative(String func, String paramToDiff, String param2) throws IOException {
        try {


            ProcessBuilder pb = new ProcessBuilder("python", "python\\partial_derivative.py",
                    func, paramToDiff, param2);

            pb.redirectErrorStream(true);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            List<String> output = new ArrayList<>();
            String s = in.readLine();
            if (s.contains("Traceback")) throw new IllegalArgumentException();

            String result = in.readLine();
            if(result.contains("Traceback"))
                return Arrays.deepToString(in.lines().toArray());

            return result;
//            return Arrays.deepToString(in.lines().toArray());


        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            throw e;
        }

    }


    //TODO -- отображение на графике

    public static void main(String[] args) throws IOException {

    }
}
