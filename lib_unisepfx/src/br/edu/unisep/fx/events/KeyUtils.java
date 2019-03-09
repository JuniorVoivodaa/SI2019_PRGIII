package br.edu.unisep.fx.events;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class KeyUtils {

	public static void checkInteger(KeyEvent event) {
		validarEntradaNumerica(event, false, 0, true);
	}

	public static void checkDecimal(KeyEvent event) {
		validarEntradaNumerica(event, true, 0, true);
	}
	
	public static void checkDecimal(KeyEvent event, int countDecimal) {
		validarEntradaNumerica(event, true, countDecimal, true);
	}
	
	public static void checkIntegerPositive(KeyEvent event) {
		validarEntradaNumerica(event, false, 0, false);
	}

	public static void checkDecimalPositive(KeyEvent event) {
		validarEntradaNumerica(event, true, 0, false);
	}
	
	public static void checkDecimalPositive(KeyEvent event, int countDecimal) {
		validarEntradaNumerica(event, true, countDecimal, false);
	}

	public static void checkMoney(KeyEvent event) {
		validarEntradaNumerica(event, true, 2, false);
	}
	
	
	private static void validarEntradaNumerica(KeyEvent event, 
			boolean decimal, int casasDecimais, boolean negativo) {

		if (!event.getCharacter().equals("")) {
			char c = event.getCharacter().charAt(0);
			TextField txt = (TextField) event.getSource();

			if (decimal && c == '.') {

				if (txt.getText().contains(".")) {
					event.consume();
				}

			} else if (negativo && c == '-') {
			
				if (!txt.getText().equals("")) {
					event.consume();
				}
			} else if (!Character.isDigit(c)) {
				event.consume();
			} else if (decimal && casasDecimais > 0) {

				int posPonto = txt.getText().indexOf('.');
				if (posPonto != -1) {
					String dec = txt.getText().substring(posPonto + 1);

					if (dec.length() == casasDecimais) {
						event.consume();
					}
				}
			}
		}
	}
	
	
}