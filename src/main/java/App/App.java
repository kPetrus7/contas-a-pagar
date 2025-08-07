package App;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.service.FrameManager;
import model.Chave;
import view.LoginViewer;
import view.MainViewer;

public class App {

    public static void main(String[] args) {

        createTables();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("permanentFocusOwner", evt -> {
			Component foco = (Component) evt.getNewValue();

            if (foco != null){
			if(foco.getName() != null) {
        		System.out.println("Foco atual: " + foco.getName()); 
			} else {
				System.out.println("Foco atual: " + foco.getClass().getSimpleName());
			}
		}
		});
    
        FrameManager frameManager = FrameManager.getInstance();

        frameManager.setUIManager(getTema());
        frameManager.updateUIFrames();

        frameManager.addFrame(new MainViewer());
        frameManager.addPane("tela_login", new LoginViewer());

    }

    private static void createTables() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection("jdbc:sqlite:data.db");
            
            Statement stmtSettings = conn.createStatement();
            stmtSettings.execute("CREATE TABLE IF NOT EXISTS configuracoes (" +
                    "chave TEXT PRIMARY KEY," +
                    "valor INTEGER" +
                    ");");

            stmtSettings.close();

            PreparedStatement pstmtSettings = conn.prepareStatement(
                    "INSERT OR IGNORE INTO configuracoes (chave, valor) VALUES (?, ?)");
            pstmtSettings.setString(1, "tema");
            pstmtSettings.setInt(2, 1);
            pstmtSettings.executeUpdate();
            pstmtSettings.close();
            //=========================================================================================
            Statement stmtConta = conn.createStatement();
            stmtConta.execute("CREATE TABLE IF NOT EXISTS contas ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "origem VARCHAR(100),"
                    + "descricao VARCHAR(255),"
                    + "entrada VARCHAR(20),"
                    + "vencimento VARCHAR(20),"
                    + "rateio VARCHAR(50),"
                    + "valor DOUBLE,"
                    + "estado INTEGER"
                    + ")");
            stmtConta.close();
            //=========================================================================================
            Statement stmtUser = conn.createStatement();
            stmtUser.execute("CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "prioridade INTEGER,"
                    + "Login VARCHAR(255),"
                    + "encryptedSenha VARCHAR(255)"
                    + ")");
            stmtUser.close();

            ResultSet rs = stmtUser.executeQuery("SELECT COUNT(*) AS total FROM usuarios");
            rs.next();

            int totalUsuarios = rs.getInt("total");

            if (totalUsuarios == 0) {
                PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO usuarios (prioridade, Login, encryptedSenha) VALUES (?, ?, ?)");

                insertStmt.setInt(1, 1);
                insertStmt.setString(2, "admin");
                insertStmt.setString(3, new Chave("admin").Encrypt());
                insertStmt.executeUpdate();
                insertStmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static int getTema(){

        Connection conn = null;
        String sql = "SELECT valor FROM configuracoes WHERE chave = 'tema'";

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:data.db");
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("valor");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
}
