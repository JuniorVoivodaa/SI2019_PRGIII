package br.edu.unisep;

import br.edu.unisep.fx.annotation.OnlyNumber;
import br.edu.unisep.fx.annotation.Required;
import br.edu.unisep.fx.controller.ModalController;
import br.edu.unisep.model.dao.AutorDAO;
import br.edu.unisep.model.dao.LivroDAO;
import br.edu.unisep.model.vo.AutorVO;
import br.edu.unisep.model.vo.LivroVO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NovoLivroController extends ModalController {

    @Required(campo="título")
    @FXML private TextField txtTitulo;

    @Required(campo="autor")
    @FXML private ChoiceBox<AutorVO> cmbAutor;

    @Required(campo="editora")
    @FXML private TextField txtEditora;

    @Required(campo="páginas")
    @OnlyNumber
    @FXML private TextField txtPaginas;

    @Required(campo="sinopse")
    @FXML private TextArea txtSinopse;

    @FXML private Label lblTitulo;

    private LivroVO livro = new LivroVO();

    @Override
    public void onModalInit(){
        listarAutores();

        if (params.length != 0) {
            var livroSel = (LivroVO) params[0];
            exibirDadosAlteracao(livroSel);
        }

    }


    public void exibirDadosAlteracao(LivroVO livroSel) {
        livro.setId(livroSel.getId());
        livro.setStatus(livroSel.getStatus());

        txtTitulo.setText(livroSel.getTitulo());
        txtEditora.setText(livroSel.getEditora());
        txtPaginas.setText(livroSel.getPaginas().toString());
        txtSinopse.setText(livroSel.getSinopse());
        cmbAutor.setValue(livroSel.getAutor());

        lblTitulo.setText("Alterar Livro");
    }


    private void listarAutores() {
        var dao = new AutorDAO();
        var lista = dao.listar();

        cmbAutor.setItems(FXCollections.observableList(lista));
    }


    public void salvar(ActionEvent event) {

        if (validate()) {

            livro.setTitulo(txtTitulo.getText().trim());
            livro.setPaginas(Integer.valueOf(txtPaginas.getText().trim()));
            livro.setEditora(txtEditora.getText().trim());
            livro.setSinopse(txtSinopse.getText().trim());
            livro.setStatus(1);

            // Obtém o objeto AutorVO selecionado na combo de autores
            // e valoriza o atributo autor do livro
            livro.setAutor(cmbAutor.getValue());

            var dao = new LivroDAO();

            if (livro.getId() != null) {
                dao.alterar(livro);
            } else {
                dao.salvar(livro);
            }

            closeModal();
        }
    }

    public void cancelar(ActionEvent event) {
        closeModal();
    }

}
