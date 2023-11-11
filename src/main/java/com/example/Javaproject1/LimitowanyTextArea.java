package com.example.Javaproject1;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class LimitowanyTextArea extends JTextArea {
    public LimitowanyTextArea(int maxCharacters) {
        super();

        // Pobierz dokument powiązany z JTextArea
        AbstractDocument doc = (AbstractDocument) this.getDocument();

        // Ustaw filtr dokumentu, aby ograniczyć liczbę znaków
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            @SuppressWarnings("unused")
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength());
               // string += text;
                if ((fb.getDocument().getLength() + text.length()) <= maxCharacters) {
                    super.replace(fb, offset, length, text, attrs); // Zezwól na dodanie tekstu
                } else {
                    Toolkit.getDefaultToolkit().beep(); // Emituj dźwięk, gdy limit zostanie przekroczony
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= maxCharacters)
                    super.insertString(fb, offset, string, attr);
                else
                    Toolkit.getDefaultToolkit().beep(); // Emituj dźwięk, gdy limit zostanie przekroczony
            }
        });
    }
}