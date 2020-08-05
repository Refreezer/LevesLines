package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/***
 * упрощает сощдание кнокпки
 */

//класс CustomJButton наследуется от JButton
public class CustomJButton extends  JButton{

    //создаём словарь, в котором хранятся 2 типа кнопок
    public enum ButtonMode {FUNC, NUM_OR_OPS}

    //Неизменяемые константы, задающие размеры
    // кнопок для функций и операций и чисел
    public final static int FUNC_WIDTH = 93;
    public final static int FUNC_HEIGHT = 23;
    public final static int NUM_OR_OPS_WIDTH = 68;
    public final static int NUM_OR_OPS_HEIGHT = 23;

    //Перегрузка метода CustomJButton
    //В первом методе устанавливаем обработчик события listener
    public CustomJButton(String name, int x, int y, int width, int height, ActionListener listener) {
        super(name);
        super.addActionListener(listener);
        super.setBounds(x, y, width, height);
        super.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
    }

    //Во втором - размеры кнопки в зависимости от её типа
    public CustomJButton(String name, int x, int y, ActionListener listener, ButtonMode mode) {
        super(name);
        super.addActionListener(listener);
        //Выбираем тип кнопки
        //и устанавливаем размеры кнопки в соответствии с выбранным типом
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

}
