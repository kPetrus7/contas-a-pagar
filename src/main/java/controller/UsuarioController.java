package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.service.FrameManager;
import controller.service.Utilitarios;
import model.Usuario;
import model.dao.UsuarioDAO;
import view.UsuariosViwer;

public class UsuarioController {

	private FrameManager frameManager;
    private ArrayList<Usuario> usuarios_lista;
    private UsuariosViwer tela_usuarios;
    private UsuarioDAO dao;

    public UsuarioController() {

		frameManager = FrameManager.getInstance();

        usuarios_lista = new ArrayList<Usuario>();
        
        try {
			this.dao = new UsuarioDAO();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
			usuarios_lista = dao.searchUsuarios();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void novo() throws SQLException {
	
		tela_usuarios = (UsuariosViwer) frameManager.getPane("tela_usuarios");

		int user_id = dao.getNextUsuarioId();

		if (Utilitarios.userFieldsVerifyer(tela_usuarios)) {
			if (dao.loginVerify(tela_usuarios.getTextLogin())) {
				JOptionPane.showMessageDialog(null, "Este usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
				Utilitarios.userCleanFields(tela_usuarios);
				return;
			}
			try {
				dao.createNewUsuario(Utilitarios.usuariosToDB(tela_usuarios, user_id));
				} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		updateUsuarios();
		Utilitarios.userCleanFields(tela_usuarios);
    }

	public void excluir() throws SQLException {

		tela_usuarios = (UsuariosViwer) frameManager.getPane("tela_usuarios");

		int linha_selecionada = tela_usuarios.getSelectedLine();
		int user_id = usuarios_lista.get(linha_selecionada).getId();
		
		if (dao.usuariosAdmCount() > 1 || !dao.isAdm(dao.getLogin(user_id))) {
			Object[] opcoes = { "Sim", "Não" };

			
			int opcao = JOptionPane.showOptionDialog(
				null,
				"Deseja realmente excluir o usuário?",
				"Confirmação",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				opcoes, opcoes[0]);

			if (opcao == JOptionPane.YES_OPTION) dao.usuarioDelete(user_id);
		} else JOptionPane.showMessageDialog(null, "Deve haver pelo menos UM Administrador", "Erro", JOptionPane.ERROR_MESSAGE);

		updateUsuarios();
	}

    public void updateUsuarios() {
		
		ArrayList<Usuario> usuariosUpdated = new ArrayList<>();

		try {
			usuariosUpdated = dao.searchUsuarios();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		usuarios_lista.clear();
		usuarios_lista.addAll(usuariosUpdated);
	}

	public ArrayList<Usuario> getListaUsuarios() {
		return usuarios_lista;
	}
}
