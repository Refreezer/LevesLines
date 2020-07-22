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


    public static JFreeChart oneParamFuncBuildChart(String func,
                                                    float xStart, float xStop, int xSteps) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python", "python\\plot.py",
                func, Float.toString(xStart), Float.toString(xStop), Float.toString(xSteps));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        XYSeries series = new XYSeries("func");
        for (int i = 0; i < xSteps; i++) {
            try {
                float y = Float.parseFloat(in.readLine());
                series.add(xStart + ((xStop - xStart) /xSteps) * i, y);
                series.add(xStart + ((xStop - xStart) /xSteps) * i, -y);
                System.out.println(xStart + ((xStop - xStart) /xSteps) * i + " " + y);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }



        XYDataset dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("y = " + func, "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, true, true);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(100, 100, 1000, 800);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(800,800);
        frame.setVisible(true);

        return chart;
    }

    public static String buildLevelLines(String func) throws IOException {


        ProcessBuilder pb = new ProcessBuilder("python", "python\\levels.py",
                func);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String imgName = in.readLine();

        if(!imgName.contains(".png"))
            do {
                imgName = in.readLine();
            }while(!imgName.contains(".png"));

        var frame = new JFrame("Levels");

        System.out.println(func);
        frame.getContentPane().add(new ImagePanel(imgName));
        frame.setSize(900,900);
        frame.setVisible(true);
//        System.out.println(imgName);
        System.out.println(Arrays.deepToString(in.lines().toArray()));
//        return imgName;
        return Arrays.deepToString(in.lines().toArray());
    }





    public static String partialDerivative(String func, String paramToDiff, String param2) throws IOException {
        try {


            ProcessBuilder pb = new ProcessBuilder("python", "python\\partial_derivative.py",
                    func, paramToDiff, param2);

            pb.redirectErrorStream(true);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            List<String> output = new ArrayList<>();
            String s = in.readLine();
            if(s.contains("Traceback")) throw new IllegalArgumentException();
            return in.readLine();
//            return Arrays.deepToString(in.lines().toArray());


        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            throw e;
        }

    }


    public static void main(String[] args) throws IOException {
        System.out.println(buildLevelLines("sinc(x)*cos(y)*sqrt(x/y)"));
    }
}
