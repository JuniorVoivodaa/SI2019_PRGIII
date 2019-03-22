package br.edu.unisep.model.dao;

import br.edu.unisep.model.vo.MedicaoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicaoDAO {

    public void salvar(MedicaoVO med) {

        try {
            var con = obterConexao();

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
            var con = obterConexao();
            var ps = con.prepareStatement(
                    "select * from registro_medicao order by dt_medicao");

            // Executa a consulta no banco de dados, recebendo como retorno
            // um objeto ResultSet
            var rs = ps.executeQuery();

            // Percorre o resultado da consulta, para gerar os objetos MedicaoVO
            while (rs.next()) {
                var m = new MedicaoVO();
                m.setId( rs.getInt("id_medicao") );
                m.setSistolica( rs.getInt("vl_sist") );
                m.setDiastolica( rs.getInt("vl_diast") );
                m.setResultado( rs.getInt("tp_resultado") );

                var dt = rs.getDate("dt_medicao");
                m.setData( dt.toLocalDate() );

                retorno.add(m);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public void excluir(Integer id) {

        try {
            var con = obterConexao();

            var ps = con.prepareStatement(
                    "delete from registro_medicao where id_medicao = ?");

            ps.setInt(1, id);

            ps.execute();

            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void alterar(MedicaoVO medicao) {

        try {
            var con = obterConexao();
            var ps = con.prepareStatement("update registro_medicao " +
                    "set dt_medicao = ?, " +
                    "vl_sist = ?, " +
                    "vl_diast = ?," +
                    "tp_resultado = ? " +
                    "where id_medicao = ?");

            ps.setDate(1, Date.valueOf(medicao.getData()));
            ps.setInt(2, medicao.getSistolica());
            ps.setInt(3, medicao.getDiastolica());
            ps.setInt(4, medicao.getResultado());
            ps.setInt(5, medicao.getId());

            ps.execute();

            ps.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    private Connection obterConexao() throws ClassNotFoundException, SQLException {
        // Carrega na memória a classe do Driver de conexão com o banco
        Class.forName("org.postgresql.Driver");

        // Estabelece uma conexão com o banco de dados
        var con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/controle_pressao",
                "postgres", "admin");

        return con;
    }

}
