package dev.otabek.components;

import com.intellij.ui.JBColor;

import javax.swing.*;

public class TruncatedLabel extends JLabel {

    public TruncatedLabel(String text, int maxLabelLength) {
        if (maxLabelLength <= 0) {
            throw new IllegalArgumentException("maxLabelLength must be greater than 0");
        }

        setForeground(JBColor.foreground().darker());
        setText(truncateText(text, maxLabelLength));
    }

    private static String truncateText(String text, int maxLength) {
        if (text == null) {
            return null;
        }
        int cutPoint = Math.min(text.length(), maxLength);
        String visibleText = text.trim().substring(0, cutPoint);
        if (cutPoint == maxLength) {
            visibleText = visibleText + "...";
        }

        return visibleText;
    }
}
