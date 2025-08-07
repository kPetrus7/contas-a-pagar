package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Conta;

public class LancamentosDAO {

    private Connection conn;

    public LancamentosDAO() throws SQLException {
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
    
    public ArrayList<Conta> getContas() throws SQLException {

        ArrayList<Conta> contas = new ArrayList<>();
        getConnection();

        try {
            String sql = "SELECT id, origem, descricao, entrada, vencimento, rateio, valor, estado FROM contas ORDER BY id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Conta conta = new Conta(
                    rs.getInt("id"),
                    rs.getString("origem"),
                    rs.getString("descricao"),
                    rs.getString("entrada"),
                    rs.getString("vencimento"),
                    rs.getString("rateio"),
                    rs.getDouble("valor"),
                    rs.getInt("estado"));
                contas.add(conta);
            }
            rs.close();
            stmt.close();
        } finally {
            closeConnection();
        }
        return contas;
    }

    public void createNewConta(Conta conta) throws SQLException {

        getConnection();

        try {
            String sql = "INSERT INTO contas (origem, descricao, entrada, vencimento, rateio, valor, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, conta.getOrigem());
            stmt.setString(2, conta.getDescricao());
            stmt.setString(3, conta.getEntrada());
            stmt.setString(4, conta.getVencimento());
            stmt.setString(5, conta.getRateio());
            stmt.setDouble(6, conta.getValor());
            stmt.setInt(7, conta.getEstado());

            stmt.executeUpdate();
            stmt.close();
        } finally {
            closeConnection();
        }
    }

    public void saveUpdatedConta(Conta conta, int index_id) throws SQLException {
        
        getConnection();

        try {
            String sql = "UPDATE contas SET origem = ?, descricao = ?, entrada = ?, vencimento = ?, rateio = ?, valor = ?, estado = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, conta.getOrigem());
            stmt.setString(2, conta.getDescricao());
            stmt.setString(3, conta.getEntrada());
            stmt.setString(4, conta.getVencimento());
            stmt.setString(5, conta.getRateio());
            stmt.setDouble(6, conta.getValor());
            stmt.setInt(7, conta.getEstado());
            stmt.setInt(8, index_id);

            stmt.executeUpdate();
            stmt.close();
        } finally {
            closeConnection();
        }
    }

    public void contaSetEstado(int index_id) throws SQLException {

        getConnection();

        try {
            String sql = "UPDATE contas SET estado = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setInt(2, index_id);
            stmt.executeUpdate();
            stmt.close();

        } finally {
            closeConnection();
        }
    }

    public void contaDelete(int index_id) {
        
        getConnection();

        try {
            String sql = "DELETE FROM contas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, index_id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeConnection();
        }

    }

    public int getNextContaId() throws SQLException{

        int proximo_id = -1;

        getConnection();
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS proximo_id FROM contas";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                proximo_id = rs.getInt("proximo_id");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e ) {
            e.printStackTrace();

        } finally {
            closeConnection();
        }

        return proximo_id;
    }

    public boolean existingContaId(int index_id) {

        getConnection();

        try {

            String sql = "SELECT 1 FROM contas WHERE id = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, index_id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        } 
        return false;
    }

    public void test() {

        getConnection();
        if (conn == null) {
            System.out.println("contas: SEM CONEXAO COM O BANCO");
        } else System.out.println("contas: CONEXAO OK");
        closeConnection();
    }
}