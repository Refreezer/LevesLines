package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Set;

public class Jfrm extends JFrame {

    public JTextField textFieldForFunc;
    public JTextField textFieldForGrad;
    public JTextField textFieldForDer;
    public JTextField xTextfield;
    public JTextField yTextfield;


    private static volatile Jfrm instance;

    //TODO--split various strings to different collections for button generation
    private final Set<String> validInputs = Set.of(
            "9", "8", "7", "6", "5", "4", "3", "2", "1", "0",
            ".", "(", ")", "+", "-", "/", "*", "x^y", "|",
            "sin(x)", "cos(x)", "tan(x)", "ctg(x)", "asin(x)",
            "acos(x)", "atan(x)", "actg(x)", "log(a, b)",
            "sqrt(x)", "^");

    //TODO -- add json-like net grid for buttons (maybe xml)

    //Singleton
    public static Jfrm getInstance() {
        Jfrm localInstance = instance;
        if (localInstance == null) {
            synchronized (Jfrm.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Jfrm();
                }
            }
        }
        return localInstance;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Jfrm frame = new Jfrm();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Create the frame.
     */
    public Jfrm() {

        setTitle("App.exe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 803);

        JPanel contentPanel = new JPanel();
        contentPanel.setForeground(Color.WHITE);
        contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        CustomJPanel mainCustomPanel = new CustomJPanel(10, 469, 1244, 285);
        mainCustomPanel.setBackground(Color.LIGHT_GRAY);
        mainCustomPanel.setBorder(new LineBorder(Color.GRAY, 3, true));
        contentPanel.add(mainCustomPanel);


        JLabel lblUxY = new JLabel("U(x, y) = ");
        lblUxY.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        lblUxY.setBounds(62, 13, 84, 25);
        mainCustomPanel.add(lblUxY);

        textFieldForFunc = new JTextField();
        textFieldForFunc.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        textFieldForFunc.setBounds(156, 15, 553, 21);
        mainCustomPanel.add(textFieldForFunc);
        textFieldForFunc.setColumns(10);


        JLabel labelGrad = new JLabel("Grad = ");
        labelGrad.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        labelGrad.setBounds(900, 100, 84, 25);
        mainCustomPanel.add(labelGrad);

        textFieldForGrad = new JTextField();
        textFieldForGrad.setEditable(false);
        textFieldForGrad.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        textFieldForGrad.setBounds(1000, 100, 100, 21);
        mainCustomPanel.add(textFieldForGrad);
        textFieldForGrad.setColumns(10);

        JLabel xyLabel = new JLabel("Input x, y -> ");
        xyLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        xyLabel.setBounds(900, 60, 84, 25);
        mainCustomPanel.add(xyLabel);

        yTextfield = new JTextField();
        yTextfield.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        yTextfield.setBounds(1060, 60, 50, 21);
        mainCustomPanel.add(yTextfield);
        yTextfield.setColumns(10);

        xTextfield = new JTextField();
        xTextfield.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        xTextfield.setBounds(1000, 60, 50, 21);
        mainCustomPanel.add(xTextfield);
        xTextfield.setColumns(10);


        JButton startDrawbutton = new JButton("\u041D\u0430\u0440\u0438\u0441\u043E\u0432\u0430\u0442\u044C!");
        startDrawbutton.addActionListener(e -> {
            String str = textFieldForFunc.getText().trim();
            //TODO -- текст на месте каретки

            if (str.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Empty input!");
            } else {
                try {

                    String translatedFunction = CustomGraph.translation(str);
                    CustomGraph.buildLevelLines(translatedFunction);
                    System.out.println("processing draw for " + translatedFunction);

                    if (!xTextfield.getText().isEmpty() && !yTextfield.getText().isEmpty()) {
                        String gradResult = CustomGraph.gradEvaluate(translatedFunction,
                                Double.parseDouble(xTextfield.getText()),
                                Double.parseDouble(yTextfield.getText()));

                        textFieldForGrad.setText(gradResult);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input.\nAdd operations and\n parenthesis: " + str);
                }
            }
        });
        startDrawbutton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        startDrawbutton.setBounds(719, 12, 145, 23);
        mainCustomPanel.add(startDrawbutton);

        CustomJPanel funcPanel = new CustomJPanel(10, 47, 421, 227);
        funcPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        mainCustomPanel.add(funcPanel);
        funcPanel.setLayout(null);

        JLabel label = new JLabel("\u041D\u0435\u043A\u043E\u0442\u043E\u0440\u044B\u0435 \u0444\u0443\u043D\u043A\u0446\u0438\u0438");
        label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        label.setBounds(10, 11, 220, 19);
        funcPanel.add(label);

        CustomJPanel numsAndOpsPanel = new CustomJPanel(441, 47, 360, 227);
        numsAndOpsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        numsAndOpsPanel.setBounds(441, 47, 360, 227);
        mainCustomPanel.add(numsAndOpsPanel);


        //TODO -- temporary decision to set buttons manually
        ActionListener defaultListener = e -> btnClick((JButton) e.getSource());
        funcPanel.addButton(new CustomJButton("sin(x)", 10, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("cos(x)", 110, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("tan(x)", 210, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("ctg(x)", 310, 41, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("asin(x)", 10, 75, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("acos(x)", 110, 75, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("atan(x)", 210, 75, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("actg(x)", 310, 76, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("log(a, b)", 10, 109, defaultListener, CustomJButton.ButtonMode.FUNC));
        funcPanel.addButton(new CustomJButton("sqrt(x)", 124, 109, defaultListener, CustomJButton.ButtonMode.FUNC));


        numsAndOpsPanel.addButton(new CustomJButton("7", 10, 11, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("8", 88, 13, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("9", 166, 13, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("4", 10, 45, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("5", 88, 47, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("6", 166, 47, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("3", 166, 81, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("2", 88, 81, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("1", 10, 79, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("0", 10, 113, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));

        numsAndOpsPanel.addButton(new CustomJButton(".", 282, 115, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("+", 282, 13, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("-", 282, 45, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("*", 282, 81, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("/", 282, 115, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));

        numsAndOpsPanel.addButton(new CustomJButton("(", 10, 149, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton(")", 88, 149, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("|", 282, 183, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));
        numsAndOpsPanel.addButton(new CustomJButton("^", 282, 149, defaultListener, CustomJButton.ButtonMode.NUM_OR_OPS));

        numsAndOpsPanel.addButton(new CustomJButton("C", 166, 149, e -> textFieldForFunc.setText(""), CustomJButton.ButtonMode.NUM_OR_OPS));


    }

    private void btnClick(JButton source) {
        String str = source.getText();
        if (validInputs.contains(str)) {
            textFieldForFunc.setText(textFieldForFunc.getText() + str);
        } else if (str.equals("C")) {
            textFieldForFunc.setText("");
        }
    }
}