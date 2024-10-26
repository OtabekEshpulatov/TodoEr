package dev.otabek.components.todo;

import com.intellij.ui.components.panels.HorizontalBox;

import javax.swing.*;
import java.awt.*;

public class TodoNoItemsToShowComponent extends HorizontalBox {


    public TodoNoItemsToShowComponent() {
        add(Box.createHorizontalGlue());
        add(new JLabel("No items to show"));
        add(Box.createHorizontalGlue());


        setPreferredSize(new Dimension(300, 200));
    }


}
