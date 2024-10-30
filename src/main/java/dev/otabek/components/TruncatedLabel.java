package dev.otabek.components;

import com.intellij.ui.JBColor;

import javax.swing.*;

public class TruncatedLabel extends JLabel {

    public TruncatedLabel(String text, int maxLabelLength) {
        setForeground(JBColor.foreground().darker());
        setText(truncateText(text, maxLabelLength));
    }

    private static String truncateText(String text, int maxLength) {
        if (text == null) {
            return null;
        }
        if (maxLength == -1) {
            return text;
        }

        int cutPoint = Math.min(text.length(), maxLength);
        String visibleText = text.trim().substring(0, cutPoint);
        if (cutPoint == maxLength) {
            visibleText = visibleText + "...";
        }

        return visibleText;
    }
}
