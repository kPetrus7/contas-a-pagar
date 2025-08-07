package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

import controller.LoginController;
import controller.service.FrameManager;
import controller.service.Utilitarios;
import view.Interfaces.Componentes;

public class LoginViewer extends JPanel {

	private static final long serialVersionUID = 1L;

	private LoginController controlador;
	private FrameManager frameManager;

	private JPanel panel;
	private JButton btnLogin;
	private JLabel lblTitle;
	private JLabel lblLogin;
	private JLabel lblSenha;
	private JTextField fieldLogin;
	private JTextField fieldSenha;
	private JCheckBox chkSenha;

	private int largura;
	private int altura;

	private boolean senhaVisible = false;

	public LoginViewer() {
		
		Utilitarios.arrastarPainel(this);

		controlador = new LoginController();
		frameManager = FrameManager.getInstance();

		largura = 254;
		altura = 260;

		int x = (frameManager.getScreenSize().width - largura)/2;
		int y = (frameManager.getScreenSize().height - altura)/3;

		setBounds(x, y, largura, altura);
		setBackground(new Color(41, 79, 125));
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 10, 234, 240);
		panel.setLayout(null);
		add(panel);

		lblTitle = Componentes.placeLblTitle("CONTAS A PAGAR", 32, 10, 170, 26);
		panel.add(lblTitle);

		lblLogin = Componentes.placeLabel("Login:", 20, 51, 46, 15);
		panel.add(lblLogin);

		fieldLogin = Componentes.placeTextField("Login", 20, 70, 180, 22);
		panel.add(fieldLogin);

		lblSenha = Componentes.placeLabel("Senha:", 20, 112, 46, 15);
		panel.add(lblSenha);

		fieldSenha = Componentes.placeKeyField("Senha", 20, 131, 180, 22);
		((javax.swing.JPasswordField) fieldSenha).setEchoChar('*');
		panel.add(fieldSenha);

		chkSenha = Componentes.placeChekBox("Visualizar senha", 20, 158, 180, 25);
		panel.add(chkSenha);

		chkSenha.addActionListener(e -> {
			senhaVisible = !senhaVisible;
			setSenhaVisible(senhaVisible);
		});

		btnLogin = Componentes.placeButton("Entrar", 81, 205, 75, 25);
		panel.add(btnLogin);

		btnLogin.addActionListener(e -> {
			controlador.login();
			setFocusSenha();
		});
	}

	private void setSenhaVisible(boolean status) {

		if (!status)
			((javax.swing.JPasswordField) fieldSenha).setEchoChar('*');
		else if (status)
			((javax.swing.JPasswordField) fieldSenha).setEchoChar((char) 0);
	}

	public String getTextLogin() {
		String login = this.fieldLogin.getText();
		return login;
	}

	public String getTextSenha() {
		String senha = this.fieldSenha.getText();
		return senha;
	}

	private void setFocusSenha() {
		this.fieldSenha.requestFocusInWindow();
		this.fieldSenha.setText("");
	}
}