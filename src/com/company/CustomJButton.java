package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/***
 * упрощает сощдание кнокпки
 */

public class CustomJButton extends  JButton{

    ActionListener defaultListener = e -> fillTextField((JButton) e.getSource());


    public enum ButtonMode {FUNC, NUM_OR_OPS}


    public final static int FUNC_WIDTH = 93;
    public final static int FUNC_HEIGHT = 23;
    public final static int NUM_OR_OPS_WIDTH = 68;
    public final static int NUM_OR_OPS_HEIGHT = 23;

    public CustomJButton(String name, int x, int y, int width, int height, ActionListener listener) {
        super(name);

        super.addActionListener(listener);
        super.setBounds(x, y, width, height);
        super.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));


    }

    public CustomJButton(String name, int x, int y, ActionListener listener, ButtonMode mode) {
        super(name);
        super.addActionListener(listener);
        switch (mode) {
            case FUNC -> {
                super.setBounds(x, y, FUNC_WIDTH, FUNC_HEIGHT);
            }
            case NUM_OR_OPS -> {
                super.setBounds(x, y, NUM_OR_OPS_WIDTH, NUM_OR_OPS_HEIGHT);
            }
        }
        super.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));

    }


    private void fillTextField(JButton source) {
        String str = source.getText();
        String curInput = Jfrm.getInstance().textField.getText();
        Jfrm.getInstance().textField.setText("" + curInput + str);


    }

}
