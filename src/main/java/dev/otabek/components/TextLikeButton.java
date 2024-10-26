package dev.otabek.components;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class TextLikeButton extends JButton {

    public TextLikeButton(String text) {
        super(text);
        configure();
    }

    private void configure() {
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false); // Remove background
        this.setForeground(JBColor.BLUE); // Set text color to blue (like a hyperlink)
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
