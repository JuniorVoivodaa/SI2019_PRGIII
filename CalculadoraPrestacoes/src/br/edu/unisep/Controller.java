package br.edu.unisep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;

public class Controller {

    @FXML private TextField txtValor;
    @FXML private TextField txtTaxa;
    @FXML private TextField txtParcelas;

    @FXML private RadioButton rdJurosSimples;

    @FXML private Label lblResultado;


    public void calcular(ActionEvent event) {

        var valor = Double.parseDouble(txtValor.getText());
        var taxa = Double.parseDouble(txtTaxa.getText());
        var parc = Integer.parseInt(txtParcelas.getText());

        var result = 0d;
        if (rdJurosSimples.isSelected()) {
            result = (valor * (1 + (taxa / 100d * parc))) / parc;
        } else {
            result = (valor * Math.pow(1 + taxa / 100d, parc)) / parc;
        }

        var df = new DecimalFormat("#,##0.00");
        lblResultado.setText("R$ " + df.format(result));

    }

    public void limpar(ActionEvent event) {
        txtValor.clear();
        txtParcelas.clear();
        txtTaxa.clear();

        lblResultado.setText("R$ 0,00");
        rdJurosSimples.setSelected(true);

        txtValor.requestFocus();
    }

}
