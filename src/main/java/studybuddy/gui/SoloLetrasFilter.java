package studybuddy.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro que permite solo letras (y espacios) en un JTextField.
 * Útil para campos de nombre donde no se permiten números ni símbolos.
 */
public class SoloLetrasFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
            throws BadLocationException {
        if (string != null && string.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
            throws BadLocationException {
        if (text != null && text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}