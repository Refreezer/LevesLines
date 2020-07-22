package math.graph.app;


import javax.swing.*;

import java.awt.*;
import java.util.HashMap;

/***
 * Дополняет класс Jpanel, упрощая доступ к кнопкам на панели
 */

public class CustomJPanel extends JPanel{

    private HashMap<String, CustomJButton> attachedButtons;


    public CustomJPanel(int xBound, int yBound, int width, int height) {
        super();
        attachedButtons = new HashMap<>();
        setBounds(xBound, yBound, width, height);
        setLayout(null);
    }


    public void addButton(CustomJButton newButton) {
        add(newButton);
        attachedButtons.put(newButton.getText(), newButton);
    }

    //TODO -- auto ButtonAdding


}
