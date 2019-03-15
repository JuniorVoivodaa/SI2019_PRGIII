package br.edu.unisep.model.dao;

import br.edu.unisep.model.vo.MedicaoVO;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicaoDAO {

    public void salvar(MedicaoVO med) {

        try {

            // Carrega na memória a classe do Driver de conexão com o banco
            Class.forName("org.postgresql.Driver");

            // Estabelece uma conexão com o banco de dados
            var con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/controle_pressao",
                        "postgres", "admin");


            // Define o comando SQL que será executado no banco de dados
            var ps = con.prepareStatement(
                    "insert into registro_medicao (" +
                            "dt_medicao, vl_sist, vl_diast, tp_resultado) " +
                            "values (?, ?, ?, ?)");

            // Define os valores dos parâmetros do comando SQL
            ps.setDate(1, Date.valueOf(med.getData()));
            ps.setInt(2, med.getSistolica());
            ps.setInt(3, med.getDiastolica());
            ps.setInt(4, med.getResultado());

            // Executa o comando sql no banco de dados
            ps.execute();

            ps.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public List<MedicaoVO> listar() {

        var retorno = new ArrayList<MedicaoVO>();

        try {

            Class.forName("org.postgresql.Driver");

            var con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/controle_pressao",
                    "postgres", "admin");


            var ps = con.prepareStatement("");


            ps.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

}
