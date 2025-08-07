package controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.service.FrameManager;
import controller.service.SessionManager;
import model.Chave;
import model.dao.LoginDAO;
import view.ConfiguracoesViewer;
import view.LancamentosViewer;
import view.ImpressaoViewer;
import view.LoginViewer;
import view.UsuariosViwer;

public class LoginController {

	private FrameManager frameManager;
	private SessionManager sessionManager;
	private LoginDAO dao;
	private LoginViewer tela_login;

	public LoginController() {

		frameManager = FrameManager.getInstance();
		sessionManager = SessionManager.getInstance();

		try {
			this.dao = new LoginDAO();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void login() {

		tela_login = (LoginViewer) frameManager.getPane("tela_login");

		String login = tela_login.getTextLogin();
		String senha = tela_login.getTextSenha();
		String senhaCripto = new Chave(senha).Encrypt();

		if (!(login == null || login.trim().isEmpty())) {
			if (senhaCripto.equals(dao.getEncryptedSenha(login))) {

				sessionManager.setUsuario(dao.getUsuario(login));

				frameManager.addPane("tela_lancamento", new LancamentosViewer());
				frameManager.addPane("tela_usuarios", new UsuariosViwer());
				frameManager.addPane("tela_configuracoes", new ConfiguracoesViewer());
				frameManager.addPane("tela_impressao", new ImpressaoViewer());

				frameManager.showPane("tela_lancamento", true);
				frameManager.showPane("tela_usuarios", false);
				frameManager.showPane("tela_configuracoes", false);
				frameManager.showPane("tela_impressao", false);				

				frameManager.showPane("tela_login", false);

			} else {

				JOptionPane.showMessageDialog(null, "Login ou senha inválidos!", "Aviso",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}