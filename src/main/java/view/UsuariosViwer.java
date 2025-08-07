package view;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import controller.UsuarioController;
import controller.service.FrameManager;
import controller.service.Utilitarios;
import model.tables.TableUser;
import view.Interfaces.Componentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JScrollPane;

public class UsuariosViwer extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable tabela_usuarios;
	private JButton btnExcluir;
	private JButton btnNovo;
	private JTextField fieldLogin;
	private JTextField fieldSenha;
	private JLabel lblLogin;
	private JLabel lblSenha;
	private JComboBox<String> comboTipo;
	private JCheckBox chkSenha;
	
	private LancamentosViewer tela_lancamento;
	private UsuarioController controlador;
	private FrameManager frameManager;
	
	private boolean senhaVisible;
	private int linha_selecionada;
	private int largura;
	private int altura;

	public UsuariosViwer() {

		Utilitarios.arrastarPainel(this);

		controlador = new UsuarioController();

		frameManager = FrameManager.getInstance();
		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");

		largura = 214;
		altura = 500;

		Point contas_posicao = tela_lancamento.getLocation();
		
		int x = contas_posicao.x - largura - 7;
		int y = contas_posicao.y;

		setBounds(x, y, largura, altura);
		setBackground(new Color(41, 79, 125));
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 10, 194, 480);
		panel.setLayout(null);
		add(panel);

		tabela_usuarios = new JTable();
		tabela_usuarios.setName("tabela_usuarios");
		tabela_usuarios.setModel(new TableUser(controlador.getListaUsuarios()));
		tabela_usuarios.setFocusable(true);
		tabela_usuarios.getColumnModel().getColumn(0).setPreferredWidth(24);
		tabela_usuarios.getColumnModel().getColumn(1).setPreferredWidth(65);
		tabela_usuarios.getColumnModel().getColumn(2).setPreferredWidth(85);

		scrollPane = new JScrollPane(tabela_usuarios);
		scrollPane.setViewportView(tabela_usuarios);
		scrollPane.setBounds(10, 156, 174, 244);
		panel.add(scrollPane);

		InputMap tableFocus = tabela_usuarios.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = tabela_usuarios.getActionMap();

		tabela_usuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				UsuariosViwer.this.linha_selecionada = tabela_usuarios.getSelectedRow();
			}
		});

		tableFocus.put(KeyStroke.getKeyStroke("ESCAPE"), "click");
		actionMap.put("click", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNovo.requestFocus();
			}
		});

		btnNovo = view.Interfaces.Componentes.placeButton("Novo", 10, 410, 174, 25);
		panel.add(btnNovo);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.novo();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				((AbstractTableModel) tabela_usuarios.getModel()).fireTableDataChanged();
			}
		});

		btnExcluir = view.Interfaces.Componentes.placeButton("Excluir", 10, 445, 174, 25);
		panel.add(btnExcluir);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.excluir();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				((AbstractTableModel) tabela_usuarios.getModel()).fireTableDataChanged();
			}
		});

		lblLogin = Componentes.placeLabel("Login:", 10, 10, 174, 16);
		panel.add(lblLogin);

		fieldLogin = Componentes.placeTextField("Login", 10, 30, 174, 22);
		panel.add(fieldLogin);

		lblSenha = Componentes.placeLabel("Senha:", 10, 55, 174, 16);
		panel.add(lblSenha);

		fieldSenha = Componentes.placeKeyField("Senha", 10, 75, 174, 22);
		panel.add(fieldSenha);

		chkSenha = Componentes.placeChekBox("Visualizar senha", 10, 129, 180, 22);
		panel.add(chkSenha);

		chkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				senhaVisible = !senhaVisible;
				setSenhaVisible(senhaVisible);
			}
		});

		String[] prioridades = new String[] { "Usuário", "Administrador" };
		comboTipo = new JComboBox<>(prioridades);
		comboTipo.setBounds(10, 102, 174, 22);
		panel.add(comboTipo);
	}

	private void setSenhaVisible(boolean status) {

		if (!status)
			((javax.swing.JPasswordField) fieldSenha).setEchoChar('*');
		else if (status)
			((javax.swing.JPasswordField) fieldSenha).setEchoChar((char) 0);
	}

	public int getSelectedLine() {
		return this.linha_selecionada;
	}

	public int getPrioridade() {
		return this.comboTipo.getSelectedIndex();
	}

	public String getTextLogin() {
		return this.fieldLogin.getText();
	}

	public String getTextSenha() {
		return this.fieldSenha.getText();
	}

	public void setTextLogin(String text) {
		fieldLogin.setText(text);
	}

	public void setTextSenha(String text) {
		fieldSenha.setText(text);
	}
}
