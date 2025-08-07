package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class LoginDAO {

    private Connection conn;

    public LoginDAO() throws SQLException {
        
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
    
    public String getEncryptedSenha(String user_login) {

        getConnection();

        String sql = "SELECT encryptedSenha FROM usuarios WHERE login = ? LIMIT 1";

        try  {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user_login); 
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("encryptedSenha");  
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

    public Usuario getUsuario(String user_login) {

        Usuario usuario = new Usuario();
        usuario.setLogin(user_login);

        getConnection();

        String sql = "SELECT id, prioridade FROM usuarios WHERE login = ? LIMIT 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user_login);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setPrioridade(rs.getInt("prioridade"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }

        return usuario;
    }

    public boolean test() {

        getConnection();

        if (conn == null) {
            closeConnection();
            return false;
        } else {
            closeConnection();
            return true;
        }
    }

}
