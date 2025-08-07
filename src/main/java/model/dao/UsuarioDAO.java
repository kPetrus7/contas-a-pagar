package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Chave;
import model.Usuario;

public class UsuarioDAO {

    private Connection conn;

    public UsuarioDAO() throws SQLException {
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
    
    public ArrayList<Usuario> searchUsuarios() throws SQLException {

        ArrayList<Usuario> users = new ArrayList<>();
        getConnection();

        try {
            String sql = "SELECT id, prioridade, login, encryptedSenha FROM usuarios ORDER BY id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario user = new Usuario(
                        rs.getInt("id"),
                        rs.getInt("prioridade"),
                        rs.getString("login"),
                        rs.getString("encryptedSenha"));
                users.add(user);
            }
            rs.close();
            stmt.close();
        } finally {
            closeConnection();
        }
        return users;
    }

    public void createNewUsuario(Usuario user) throws SQLException {

        getConnection();

        try {
            String sql = "INSERT INTO usuarios (id, prioridade, login, encryptedSenha) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());
            stmt.setInt(2, user.getPrioridade());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, new Chave(user.getSenha()).Encrypt());

            stmt.executeUpdate();
            stmt.close();
        } finally {
            closeConnection();
        }
    }

    public void usuarioDelete(int index_id) {

        getConnection();

        try {
            String sql = "DELETE FROM usuarios WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, index_id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeConnection();
        }

    }

    public int getNextUsuarioId() throws SQLException {

        int proximo_id = -1;

        getConnection();
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS proximo_id FROM usuarios";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                proximo_id = rs.getInt("proximo_id");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeConnection();
        }

        return proximo_id;
    }

    public String getLogin(int index_id) {
        getConnection();

        String sql = "SELECT login FROM usuarios WHERE id = ? LIMIT 1";

        try  {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, index_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("login");
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
    }

    public boolean loginVerify(String login) {

        getConnection();

        String sql = "SELECT * FROM usuarios WHERE login = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        } finally {
            closeConnection();
        }
    }

    public int usuariosAdmCount() {

        getConnection();

        String sql = "SELECT COUNT(*) AS total FROM usuarios WHERE prioridade = 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("total") : 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeConnection();
        }
    }

    public boolean isAdm(String login) {

        getConnection();

        String sql = "SELECT prioridade FROM usuarios WHERE login = ? LIMIT 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            System.out.println("prioridade: " + rs.getInt("prioridade"));
            if (rs.getInt("prioridade") == 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            closeConnection();
        }
    }

    public void test() {

        getConnection();
        if (conn == null) {
            System.out.println("usuario: SEM CONEXAO COM O BANCO");
        } else {
            System.out.println("usuario: CONEXAO OK");
        }
        closeConnection();
    }
}
