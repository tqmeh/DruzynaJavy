package com.example.Javaproject1;
import javax.swing.*;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class LimitowanyText extends JTextField {
    int MaxDlugosc;
    boolean onlyDigits;

    public LimitowanyText(int maxDlugosc, boolean onlyDigits) {
        this.MaxDlugosc = maxDlugosc;
        this.onlyDigits = onlyDigits;

        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Akceptuj cyfry i myślniki
                if (onlyDigits && !string.matches("[0-9-]+")) {
                    return; // Blokuj wprowadzanie znaków inne niż cyfry i myślniki
                }

                if ((fb.getDocument().getLength() + string.length()) <= maxDlugosc) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Akceptuj cyfry i myślniki
                if (onlyDigits && !text.matches("[0-9-]+")) {
                    return; // Blokuj wprowadzanie znaków inne niż cyfry i myślniki
                }

                if ((fb.getDocument().getLength() + text.length() - length) <= maxDlugosc) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

        });

    }
}



