package view.Formats;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Filtros {

    public static class FilterNumberValorFocused extends DocumentFilter {

		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
 
			if (string == null)
				return;
			if (string.matches("[0-9,]+")) {
				super.insertString(fb, offset, string, attr);
			}
		}

		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {

			String textoAtual = fb.getDocument().getText(0, fb.getDocument().getLength());
			int totalChar = fb.getDocument().getLength() - length + text.length();
			int posVirgula = textoAtual.indexOf(',');

			if (text == null || charCount(',', textoAtual) > 0 && text.equals(",")) return;

			if (posVirgula != -1 && offset > posVirgula + 2) {
    			super.insertString(fb, offset, "", null);
    			return;
			}
			if (totalChar == 6 && charCount(',', textoAtual) < 1 && !text.equals(",")) {
				super.insertString(fb, offset, ",", null);
			}
			if (((text.matches("[0-9,]+")) || text.equals("")) && totalChar <= 9) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}

	public static class FilterNumberValorNotFocused extends DocumentFilter {

		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
 
			if (string == null)
				return;
			if (string.matches("[0-9.,]+")) {
				super.insertString(fb, offset, string, attr);
			}
		}
	}
	
	public static class FilterDate extends DocumentFilter {

		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
			if (string == null)
				return;
			if (string.matches("[0-9/]+")) {
				super.insertString(fb, offset, string, attr);
			}
		}

		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {

			int totalChar = fb.getDocument().getLength() - length + text.length();
			String textoAtual = fb.getDocument().getText(0, fb.getDocument().getLength());

			if (text == null || charCount('/', textoAtual) > 2 && text.equals("/"))
				return;
			if (totalChar == 2 && charCount('/', textoAtual) == 0 && !text.equals("/")) {
				super.insertString(fb, offset, "/", null);
			}
			if (totalChar == 5 && charCount('/', textoAtual) == 1 && !text.equals("/")) {
				super.insertString(fb, offset, "/", null);
			}
		
			if (((text.matches("[0-9/]+")) || text.equals("")) && totalChar <= 10) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}

	public static int charCount(char chr, String texto) {
		int contador = 0;
		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) == chr) {
				contador++;
			}
		}
		return contador;
	}

}
