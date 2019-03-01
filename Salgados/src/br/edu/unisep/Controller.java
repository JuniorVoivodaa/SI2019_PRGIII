package br.edu.unisep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;

public class Controller {

    @FXML private TextField txtCoxinhas;
    @FXML private TextField txtRizolis;
    @FXML private TextField txtKibe;
    @FXML private TextField txtMiniPizzas;
    @FXML private TextField txtMiniBurger;
    @FXML private TextField txtHotDog;

    @FXML private DatePicker txtEntrega;

    @FXML private CheckBox chkDecoracao;

    @FXML private Label lblValor;


    public void calcular(ActionEvent event) {
        var coxinha = Integer.parseInt(txtCoxinhas.getText());
        var rizolis = Integer.parseInt(txtRizolis.getText());
        var kibe = Integer.parseInt(txtKibe.getText());
        var miniPizza = Integer.parseInt(txtMiniPizzas.getText());
        var miniBurger = Integer.parseInt(txtMiniBurger.getText());
        var hotDog = Integer.parseInt(txtHotDog.getText());

        var valor = (coxinha * 0.1) + (rizolis * 0.1) + (kibe * 0.15) +
                (miniPizza * 0.2) + (miniBurger * 0.18) + (hotDog * 0.22);

        var df = new DecimalFormat("#,##0.00");
        lblValor.setText("R$ " + df.format(valor));
    }


    public void limpar(ActionEvent event) {
        txtCoxinhas.clear();
        txtRizolis.clear();
        txtKibe.clear();
        txtMiniBurger.clear();
        txtMiniPizzas.clear();
        txtHotDog.clear();

        chkDecoracao.setSelected(false);
        lblValor.setText("R$ 0,00");

        txtCoxinhas.requestFocus();
    }
}
