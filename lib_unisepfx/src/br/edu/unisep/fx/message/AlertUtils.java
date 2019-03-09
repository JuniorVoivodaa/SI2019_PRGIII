package br.edu.unisep.fx.message;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AlertUtils {

	public static void exibirErro(String mensagem) {
		exibirAlerta(AlertType.ERROR, null, mensagem);
	}

	public static void exibirErro(String cabec, String mensagem) {
		exibirAlerta(AlertType.ERROR, cabec, mensagem);
	}

	public static void exibirInfo(String mensagem) {
		exibirAlerta(AlertType.INFORMATION, null, mensagem);
	}

	public static void exibirInfo(String cabec, String mensagem) {
		exibirAlerta(AlertType.INFORMATION, cabec, mensagem);
	}

	public static void exibirWarning(String mensagem) {
		exibirAlerta(AlertType.WARNING, null, mensagem);
	}

	public static void exibirWarning(String cabec, String mensagem) {
		exibirAlerta(AlertType.WARNING, cabec, mensagem);
	}

	private static void exibirAlerta(AlertType tipo, String cabec, String mensagem) {
		Alert msg = new Alert(tipo);
		msg.setHeaderText(cabec);
		
		Stage stage = (Stage) msg.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		stage.toFront();
		
		msg.setContentText(mensagem);
		msg.showAndWait();
	}

}