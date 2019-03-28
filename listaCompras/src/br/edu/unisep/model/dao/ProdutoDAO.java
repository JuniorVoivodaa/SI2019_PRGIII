package br.edu.unisep.model.dao;

import br.edu.unisep.model.vo.ProdutoVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void salvar(ProdutoVO prod) {
        try {

            var con = obterConexao();
            var ps = con.prepareStatement("insert into produtos " +
                    "(ds_produto, vl_mercadoa, vl_mercadob, vl_mercadoc) values " +
                    "(?, ?, ?, ?)");
            ps.setString(1, prod.getDescricao());
            ps.setDouble(2, prod.getMercadoA());
            ps.setDouble(3, prod.getMercadoB());
            ps.setDouble(4, prod.getMercadoC());

            ps.execute();

            ps.close();
            con.close();

        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProdutoVO> listar() {
        List<ProdutoVO> produtos = new ArrayList<>();

        try {

            var con = obterConexao();
            var ps = con.prepareStatement("select * from produtos");

            var rs = ps.executeQuery();
            while (rs.next()) {
                var p = new ProdutoVO();
                p.setId(rs.getInt("id_produto"));
                p.setDescricao( rs.getString("ds_produto"));
                p.setMercadoA( rs.getDouble("vl_mercadoa"));
                p.setMercadoB( rs.getDouble("vl_mercadob"));
                p.setMercadoC( rs.getDouble("vl_mercadoc"));

                produtos.add(p);
            }

            rs.close();
            ps.close();
            con.close();

        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public void excluir(Integer id) {
        try {

            var con = obterConexao();
            var ps = con.prepareStatement("delete from produtos where id_produto = ?");
            ps.setInt(1, id);

            ps.execute();

            ps.close();
            con.close();

        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterar(ProdutoVO prod) {
        try {

            var con = obterConexao();
            var ps = con.prepareStatement("update produtos set " +
                    "ds_produto = ?, " +
                    "vl_mercadoa = ?, " +
                    "vl_mercadob = ?, " +
                    "vl_mercadoc = ? " +
                    "where id_produto = ?");

            ps.setString(1, prod.getDescricao());
            ps.setDouble(2, prod.getMercadoA());
            ps.setDouble(3, prod.getMercadoB());
            ps.setDouble(4, prod.getMercadoC());
            ps.setInt(5, prod.getId());

            ps.execute();

            ps.close();
            con.close();

        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection obterConexao() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");

        var con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/supermercado",
                "postgres", "admin");
        return con;
    }

}
