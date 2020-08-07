package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

public class Jfrm extends JFrame {

    public JTextField textFieldForFunc;
    public JTextField textFieldForGrad;
    public JTextField textFieldForDer;
    public JTextField xTextfield;
    public JTextField yTextfield;
    public JTextField xDirTextfield;
    public JTextField yDirTextfield;

    //Создаём набор строк для кнопок
    private final Set<String> validInputs = Set.of(
            "9", "8", "7", "6", "5", "4", "3", "2", "1", "0",
            ".", "(", ")", "+", "-", "/", "*", "abs()", "x", "y",
            "sin(x)", "cos(x)", "tan(x)", "ctg(x)", "asin(x)",
            "acos(x)", "atan(x)", "actg(x)", "log(a, b)",
            "sqrt(x)", "^", "pi", "e");

    //запуск приложения
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("env\\Scripts\\activate.bat");
        EventQueue.invokeLater(() -> {
            try {
                Jfrm frame = new Jfrm();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //настройка окна
    public Jfrm() {

        setTitle("Скалярное поле.экзе");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 400);
        //отключаем изменяемость размера окна
        setResizable(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setForeground(Color.WHITE);
        contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        //панель со всем интерфейсом
        CustomJPanel mainCustomPanel = new CustomJPanel(10, 10, 1244, 343);
        mainCustomPanel.setBackground(Color.LIGHT_GRAY);
        mainCustomPanel.setBorder(new LineBorder(Color.GRAY, 3, true));
        contentPanel.add(mainCustomPanel);

        //надпись U(x, y) =
        JLabel lblUxY = new JLabel("U(x, y) = ");
        lblUxY.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        lblUxY.setBounds(62, 13, 84, 25);
        mainCustomPanel.add(lblUxY);

        //текстовое поле для ввода функции
        textFieldForFunc = new JTextField();
        textFieldForFunc.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        textFieldForFunc.setBounds(156, 15, 553, 21);
        mainCustomPanel.add(textFieldForFunc);
        textFieldForFunc.setColumns(10);

        //снова надпись
        JLabel labelGrad = new JLabel("Градиент в точке = ");
        labelGrad.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        labelGrad.setBounds(840, 140, 180, 25);
        mainCustomPanel.add(labelGrad);

        //поле для вывода градиента
        textFieldForGrad = new JTextField();
        //запрещаем редактировать содержимое поля
        textFieldForGrad.setEditable(false);
        textFieldForGrad.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        textFieldForGrad.setBounds(1010, 140, 115, 21);
        mainCustomPanel.add(textFieldForGrad);
        textFieldForGrad.setColumns(10);

        //ещё одна надпись
        JLabel xyLabel = new JLabel("Введите х и у точки М: ");
        xyLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        xyLabel.setBounds(840, 55, 200, 25);
        mainCustomPanel.add(xyLabel);

        //поле для ввода х
        xTextfield = new JTextField();
        xTextfield.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        xTextfield.setBounds(1050, 60, 50, 21);
        mainCustomPanel.add(xTextfield);
        xTextfield.setColumns(10);

        //поле для ввода y
        yTextfield = new JTextField();
        yTextfield.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        yTextfield.setBounds(1110, 60, 50, 21);
        mainCustomPanel.add(yTextfield);
        yTextfield.setColumns(10);

        //и ещё одна надпись
        JLabel dirLable = new JLabel("Направление к точке М1: ");
        dirLable.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        dirLable.setBounds(840, 100, 250, 25);
        mainCustomPanel.add(dirLable);

        //координата х точки направления
        xDirTextfield = new JTextField();
        xDirTextfield.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        xDirTextfield.setBounds(1050, 100, 50, 21);
        mainCustomPanel.add(xDirTextfield);
        xDirTextfield.setColumns(10);

        //координата у точки направления
        yDirTextfield = new JTextField();
        yDirTextfield.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        yDirTextfield.setBounds(1110, 100, 50, 21);
        mainCustomPanel.add(yDirTextfield);
        yDirTextfield.setColumns(10);

        //очередная надпись
        JLabel labelDirDeriv = new JLabel("Производная по направлению = ");
        labelDirDeriv.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        labelDirDeriv.setBounds(840, 180, 260, 25);
        mainCustomPanel.add(labelDirDeriv);

        //поле для вывода посчитанной производной
        textFieldForDer = new JTextField();
        //поле не изменяется пользователем
        textFieldForDer.setEditable(false);
        textFieldForDer.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        textFieldForDer.setBounds(1100, 180, 100, 21);
        mainCustomPanel.add(textFieldForDer);
        textFieldForDer.setColumns(10);

        //настройка кнопки Нарисовать
        JButton startDrawbutton = new JButton("Нарисовать!");
        startDrawbutton.addActionListener(e -> textFieldListener());
        startDrawbutton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        startDrawbutton.setBounds(719, 12, 145, 23);
        mainCustomPanel.add(startDrawbutton);

        //панелька с кнопками-функциями
        CustomJPanel funcPanel = new CustomJPanel(10, 47, 421, 284);
        funcPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        mainCustomPanel.add(funcPanel);
        funcPanel.setLayout(null);

        //надпись
        JLabel label = new JLabel("Некоторые функции");
        label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        label.setBounds(10, 11, 220, 19);
        funcPanel.add(label);

        //панелька с цифрами и мат. операциями
        CustomJPanel numsAndOpsPanel = new CustomJPanel(441, 47, 360, 227);
        numsAndOpsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        numsAndOpsPanel.setBounds(441, 47, 360, 284);
        mainCustomPanel.add(numsAndOpsPanel);

        //слушатель, установленный для всех кнопок, кроме "Нарисовать"
        ActionListener defaultListener = e -> fillWithBtnText((JButton) e.getSource());

        //Добавляем все кнопки к панели функций
        funcPanel.addButton(new CustomJButton("sin(x)", 10, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("cos(x)", 110, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("tan(x)", 210, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("ctg(x)", 310, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("asin(x)", 10, 75, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("acos(x)", 110, 75, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("atan(x)", 210, 75, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("actg(x)", 310, 75, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("log(a, b)", 10, 109, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("sqrt(x)", 110, 109, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("pi", 210, 109, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("e", 310, 109, defaultListener, CustomJButton.ButtonMode.FUNC));

        //Добавляем все кнопки к панели цифр и мат. операций
        numsAndOpsPanel.addButton(new CustomJButton("7", 10, 11, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("8", 88, 11, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("9", 166, 11, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("4", 10, 45, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("5", 88, 45, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("6", 166, 45, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("3", 166, 79, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("2", 88, 79, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("1", 10, 79, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("0", 88, 113, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));

        numsAndOpsPanel.addButton(new CustomJButton(".", 282, 181, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("+", 282, 11, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("-", 282, 45, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("*", 282, 79, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("/", 282, 113, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));

        numsAndOpsPanel.addButton(new CustomJButton("(", 10, 113, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton(")", 166, 113, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("abs()", 282, 215, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("^", 282, 147, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("x", 10, 149, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("y", 88, 149, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));

        numsAndOpsPanel.addButton(new CustomJButton("C", 166, 149, e -> textFieldForFunc.setText(""), CustomJButton.ButtonMode.NUM_OR_OPS));
    }

    //метод заполнения текстового поля функции нажатием кнопок
    private void fillWithBtnText(JButton source) {

        //получаем текст с кнопки
        String strFromButton = source.getText();
        //StringBuilder создаёт расширяемую строку
        //помещаем в strFromTextField текст с кнопки
        StringBuilder strFromTextField = new StringBuilder(textFieldForFunc.getText());

        //если текст удовлетворяет 
        if (validInputs.contains(strFromButton)) {
            int caretPos = textFieldForFunc.getCaret().getDot();
            strFromTextField.ensureCapacity(strFromTextField.length() + strFromButton.length());
            strFromTextField.insert(caretPos, strFromButton);
            textFieldForFunc.setText(strFromTextField.toString());
        }
    }

    private void textFieldListener() {
        //удаляем начальные и конечные пробелы
        String str = textFieldForFunc.getText().trim();
        Runnable buildLevelsRunnable;

        Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread th, Throwable ex) {
                ex.printStackTrace();
                StringBuilder msg = new StringBuilder(ex.getMessage());
                int cnt = 0;
                for (int i = 0; i < msg.length(); i++) {
                    cnt++;
                    if(cnt == 50){
                        while(msg.charAt(i) != ' ')
                            i--;

                        cnt = 0;
                        msg.insert(i,"\n");
                    }
                }
                JOptionPane.showMessageDialog(null, msg);
            }
        };

        //если строка пустая
        if (str.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустой ввод!");
        } else {
            try {
                String translatedFunction = CustomGraph.translation(str);
                //если пользователь задал точку  градиента,
                //то считаем градиент
                if (!xTextfield.getText().isEmpty() && !yTextfield.getText().isEmpty()) {

                    Thread gradThread = new Thread(() -> {
                        String gradResult = null;
                        try {
                            gradResult = CustomGraph.grad(translatedFunction,
                                    Double.parseDouble(xTextfield.getText()),
                                    Double.parseDouble(yTextfield.getText()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                        //печатаем градиент
                        textFieldForGrad.setText(gradResult);
                    });

                    gradThread.setUncaughtExceptionHandler(h);
                    gradThread.start();

                    //если задано направление - считаем производную
                    if (!xDirTextfield.getText().isEmpty() && !yDirTextfield.getText().isEmpty()) {

                        Thread dirThread = new Thread(() -> {
                            try {
                                String dirDeriative = CustomGraph.evaluateDirDerivative(
                                        translatedFunction,
                                        Double.parseDouble(xTextfield.getText()),
                                        Double.parseDouble(yTextfield.getText()),
                                        Double.parseDouble(xDirTextfield.getText()),
                                        Double.parseDouble(yDirTextfield.getText())
                                ).toString();

                                textFieldForDer.setText(dirDeriative);
                            } catch (Exception e) {
                                e.printStackTrace();JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        });
                        dirThread.setUncaughtExceptionHandler(h);
                        dirThread.start();


                        //печатаем производную по направлению

                        //строим линии уровня
                        buildLevelsRunnable = () -> {
                            try {
                                CustomGraph.buildLevelLines(translatedFunction,
                                        Double.parseDouble(xTextfield.getText()),
                                        Double.parseDouble(yTextfield.getText()),
                                        Double.parseDouble(xDirTextfield.getText()),
                                        Double.parseDouble(yDirTextfield.getText()));
                            } catch (IOException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        };
                    } else {
                        //если не задано направление,
                        //то строим график
                        buildLevelsRunnable = () -> {
                            try {
                                CustomGraph.buildLevelLines(translatedFunction);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        };
                    }
                } else {
                    //если задана только функция
                    buildLevelsRunnable = () -> {
                        try {
                            CustomGraph.buildLevelLines(translatedFunction);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                }
                Thread linesThread = new Thread(buildLevelsRunnable);

                linesThread.setUncaughtExceptionHandler(h);
                linesThread.start();

            } catch (IllegalArgumentException | ArithmeticException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (Exception ex) {

                List<String> arr = Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
                StringBuilder msg = new StringBuilder();
                for (String el : arr) {
                    msg.append(el).append("\n");
                }
                JOptionPane.showMessageDialog(
                        null, "Invalid input.\nAdd operations and\n parenthesis: " + str + msg
                );
            }

        }
    }
}