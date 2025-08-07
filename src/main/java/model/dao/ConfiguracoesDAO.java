package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracoesDAO {

    private Connection conn;

    public ConfiguracoesDAO() throws SQLException {
        test();
    }

    private void getConnection() {

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:data.db");
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }
    }

    private void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTema(){

        getConnection();

        String sql = "SELECT valor FROM configuracoes WHERE chave = 'tema'";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {
                return rs.getInt("valor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return -1;
    }

    public void updateTema(int tema) {

        getConnection();

        String sql = "UPDATE configuracoes SET valor = ? WHERE chave = 'tema'";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tema);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void test() {

        getConnection();
        if (conn == null) {
            System.out.println("configurações: SEM CONEXAO COM O BANCO");
        } else
            System.out.println("configurações: CONEXAO OK");
        closeConnection();
    }
}
