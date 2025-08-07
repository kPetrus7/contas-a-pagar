package view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;

import controller.LancamentosController;
import controller.service.FrameManager;
import controller.service.SessionManager;
import controller.service.Utilitarios;
import model.Conta;
import model.ListaContas;
import model.tables.TableConta;
import view.Formats.Filtros;
import view.Formats.Formatos;
import view.Interfaces.Componentes;
import view.Interfaces.IconUpdatable;

public class LancamentosViewer extends JPanel implements IconUpdatable {

	private static final long serialVersionUID = 1L;

	private FrameManager frameManager;
	private SessionManager sessionManager;

	private JPanel panel;
	private JPanel fieldsPane;
	private JPanel sidePane;
	private JPanel editPane;
	private JPanel configPane;
	private JPanel actionPane;
	private JTableHeader tabelaHeader;
	private JScrollPane tabelaScrollPane;
	private JTable tabela_contas;
	private JButton btnConfiguracoes;
	private JButton btnUsuarios;
	private JButton btnVoltar;
	private JButton btnNovo;
	private JButton btnPesquisar;
	private JButton btnBaixar;
	private JButton btnEditar;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnImprimir;
	private JLabel lblOrigem;
	private JLabel lblDescricao;
	private JLabel lblEntrada;
	private JLabel lblVencimento;
	private JLabel lblRateio;
	private JLabel lblValor;
	private JLabel lblStatus;
	private JTextField fieldOrigem;
	private JTextField fieldDescricao;
	private JTextField fieldEntrada;
	private JTextField fieldVencimento;
	private JTextField fieldRateio;
	private JTextField fieldValor;
	private JComboBox<String> comboEstado;

	private ListaContas lista_contas;

	private LancamentosController controlador;
	private UsuariosViwer tela_usuarios;
	private ConfiguracoesViewer tela_configuracoes;
	private ImpressaoViewer tela_impressao;

	private Component foco;

	private String configIconPath;

	private int linha_selecionada;
	private int altura;
	private int largura;

	private boolean usuarios_visibilidade;
	private boolean configuracoes_visibilidade;
	private boolean impressao_visibilidade;
	private boolean administrador;

	public LancamentosViewer() {

		Utilitarios.arrastarPainel(this);
		
		frameManager = FrameManager.getInstance();
		sessionManager = SessionManager.getInstance();
		lista_contas = ListaContas.getInstance();
		controlador = new LancamentosController();

		configIconPath = "/images/settings.png";

		usuarios_visibilidade = false;
		configuracoes_visibilidade = false;
		impressao_visibilidade = false;

		largura = 884;
		altura = 479;

		int x = (frameManager.getScreenSize().width - largura)/2;
		int y = (frameManager.getScreenSize().height - altura)/4;


		if (sessionManager.getUsuarioPrioridade() == 1) {
			administrador = true;
		} else administrador = false;



		// =============================================================================//
		// Painéis Principais //
		// =============================================================================//

		setBounds(x, y, largura, altura);
		setBackground(new Color(41, 79, 125));
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 10, 864, 459);
		panel.setLayout(null);
		add(panel);

		// =============================================================================//
		// Painel dos Campos de lançamento //
		// =============================================================================//
		fieldsPane = new JPanel();
		fieldsPane.setBounds(0, 0, 748, 80);
		fieldsPane.setLayout(null);
		panel.add(fieldsPane);

		int labels_altura = 30;
		int fields_altura = labels_altura + 20;

		// ==============================================================================
		lblOrigem = view.Interfaces.Componentes.placeLabel("Origem", 10, labels_altura, 86, 15);
		fieldsPane.add(lblOrigem);

		fieldOrigem = view.Interfaces.Componentes.placeTextField("Origem", 10, fields_altura, 86, 22);
		fieldsPane.add(fieldOrigem);
		// ==============================================================================
		lblDescricao = Componentes.placeLabel("Descrição", 106, labels_altura, 86, 15);
		fieldsPane.add(lblDescricao);

		fieldDescricao = view.Interfaces.Componentes.placeTextField("Descrição", 106, fields_altura, 174, 22);
		fieldsPane.add(fieldDescricao);
		// ==============================================================================
		lblEntrada = view.Interfaces.Componentes.placeLabel("Entrada", 290, labels_altura, 86, 15);
		fieldsPane.add(lblEntrada);

		fieldEntrada = view.Interfaces.Componentes.placeTextField("Entrada", 290, fields_altura, 86, 22);
		((AbstractDocument) fieldEntrada.getDocument()).setDocumentFilter(new Filtros.FilterDate());

		fieldEntrada.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent e) {
				((AbstractDocument) fieldEntrada.getDocument())
						.setDocumentFilter(new Filtros.FilterNumberValorNotFocused());
				String text = Formatos.padraoData(fieldEntrada.getText());
				fieldEntrada.setText("");
				fieldEntrada.setText(text);
				((AbstractDocument) fieldEntrada.getDocument()).setDocumentFilter(new Filtros.FilterDate());
			}
		});
		fieldsPane.add(fieldEntrada);
		// ==============================================================================
		lblVencimento = view.Interfaces.Componentes.placeLabel("Vencimento", 386, labels_altura, 86, 15);
		fieldsPane.add(lblVencimento);

		fieldVencimento = view.Interfaces.Componentes.placeTextField("Vencimento", 386, fields_altura, 86, 22);
		((AbstractDocument) fieldVencimento.getDocument()).setDocumentFilter(new Filtros.FilterDate());

		fieldVencimento.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent e) {
				((AbstractDocument) fieldVencimento.getDocument())
						.setDocumentFilter(new Filtros.FilterNumberValorNotFocused());
				String text = Formatos.padraoData(fieldVencimento.getText());
				fieldVencimento.setText("");
				fieldVencimento.setText(text);
				((AbstractDocument) fieldVencimento.getDocument()).setDocumentFilter(new Filtros.FilterDate());
			}
		});
		fieldsPane.add(fieldVencimento);
		// ==============================================================================
		lblRateio = view.Interfaces.Componentes.placeLabel("Rateio", 482, labels_altura, 71, 15);
		fieldsPane.add(lblRateio);

		fieldRateio = view.Interfaces.Componentes.placeTextField("Rateio", 482, fields_altura, 71, 22);
		fieldsPane.add(fieldRateio);
		// ==============================================================================
		lblValor = view.Interfaces.Componentes.placeLabel("Valor (R$)", 563, labels_altura, 80, 15);
		fieldsPane.add(lblValor);

		fieldValor = view.Interfaces.Componentes.placeTextField("Valor", 563, fields_altura, 90, 22);
		fieldValor.setHorizontalAlignment(SwingConstants.RIGHT);
		fieldsPane.add(fieldValor);
		fieldValor.setText("0,00");

		fieldValor.addFocusListener(new FocusAdapter() {

			public void focusGained(FocusEvent e) {
				((AbstractDocument) fieldValor.getDocument())
						.setDocumentFilter(new Filtros.FilterNumberValorFocused());
			}

			public void focusLost(FocusEvent e) {
				((AbstractDocument) fieldValor.getDocument())
						.setDocumentFilter(new Filtros.FilterNumberValorNotFocused());
				String text = Formatos.padraMonetario(fieldValor.getText());
				System.out.println("Valor: " + text);
				fieldValor.setText("0,00");
				fieldValor.setText(text);
			}
		});
		// ==============================================================================
		lblStatus = view.Interfaces.Componentes.placeLabel("Estado", 663, labels_altura, 47, 15);
		fieldsPane.add(lblStatus);

		String[] estados = new String[] { "Aberto", "Pago", "Vencido", "" };
		comboEstado = new JComboBox<>(estados);
		comboEstado.setBounds(663, fields_altura, 80, 20);
		fieldsPane.add(comboEstado);

		// =============================================================================//
		// Painel Lateral //
		// =============================================================================//
		sidePane = new JPanel();
		sidePane.setBounds(749, 0, 116, 80);
		sidePane.setLayout(null);
		panel.add(sidePane);

		// ==============================================================================
		btnNovo = view.Interfaces.Componentes.placeButton("Novo", 13, 10, 90, 25);
		sidePane.add(btnNovo);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (Utilitarios.contasFieldsVerifyer()) {
					try {
						controlador.novo();
					} catch (AWTException saveError) {
						saveError.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					fieldOrigem.requestFocus();
					((AbstractTableModel) tabela_contas.getModel()).fireTableDataChanged();
				}
			}
		});
		// ==============================================================================
		btnPesquisar = view.Interfaces.Componentes.placeButton("Pesquisa", 13, 45, 90, 25);
		sidePane.add(btnPesquisar);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.pesquisar();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				((AbstractTableModel) tabela_contas.getModel()).fireTableDataChanged();

				setPanelEnabled(fieldsPane, true);
				setPanelEnabled(sidePane, true);
				setPanelEnabled(editPane, false);
			}
		});

		// =============================================================================//
		// Tabela de Contas //
		// =============================================================================//
		tabela_contas = new JTable();
		tabela_contas.setModel(new TableConta());
		tabela_contas.setFocusable(true);
		tabela_contas.setShowGrid(true);
		tabela_contas.setName("tabela_contas");

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

		// Id
		tabela_contas.getColumnModel().getColumn(0).setPreferredWidth(20);
		tabela_contas.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		// Origem
		tabela_contas.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela_contas.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);

		// Descrição
		tabela_contas.getColumnModel().getColumn(2).setPreferredWidth(120);
		tabela_contas.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);

		// Entrada
		tabela_contas.getColumnModel().getColumn(3).setPreferredWidth(40);
		tabela_contas.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

		// Vencimento
		tabela_contas.getColumnModel().getColumn(4).setPreferredWidth(40);
		tabela_contas.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

		// Rateio
		tabela_contas.getColumnModel().getColumn(5).setPreferredWidth(65);
		tabela_contas.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		// Valor
		tabela_contas.getColumnModel().getColumn(6).setPreferredWidth(65);
		tabela_contas.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

		// Estado
		tabela_contas.getColumnModel().getColumn(7).setPreferredWidth(65);
		tabela_contas.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

		tabelaHeader = tabela_contas.getTableHeader();
		tabelaHeader.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
		tabelaHeader.setBorder(new MatteBorder(0, 0, 1, 0, new Color(180, 180, 180)));

		tabelaScrollPane = new JScrollPane(tabela_contas);
		tabelaScrollPane.setBounds(10, 82, 844, 332);
		tabelaScrollPane.setBorder(new LineBorder(new java.awt.Color(180, 180, 180)));
		panel.add(tabelaScrollPane);

		InputMap tableFocus = tabela_contas.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = tabela_contas.getActionMap();

		tabela_contas.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				setPanelEnabled(fieldsPane, false);
				setPanelEnabled(editPane, true);
				btnSalvar.setEnabled(false);
			}
		});

		tabela_contas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				LancamentosViewer.this.linha_selecionada = tabela_contas.getSelectedRow();
				System.out.println("Linha selecionada: " + linha_selecionada);

				setPanelEnabled(fieldsPane, false);
				setPanelEnabled(sidePane, false);
				setPanelEnabled(editPane, true);

				btnSalvar.setEnabled(false);
			}
		});

		tableFocus.put(KeyStroke.getKeyStroke("ESCAPE"), "click");
		actionMap.put("click", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				btnUsuarios.requestFocusInWindow();
				setPanelEnabled(fieldsPane, true);
				setPanelEnabled(sidePane, true);
				setPanelEnabled(editPane, false);
			}
		});

		// =============================================================================//
		// Painel de Edição //
		// =============================================================================//
		editPane = new JPanel();
		editPane.setBounds(0, 416, 288, 43);
		editPane.setLayout(null);
		panel.add(editPane);

		// ==============================================================================
		btnBaixar = view.Interfaces.Componentes.placeButton("Baixar", 10, 9, 75, 25);
		btnBaixar.setEnabled(false);
		editPane.add(btnBaixar);
		btnBaixar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.baixar();
				((AbstractTableModel) tabela_contas.getModel()).fireTableDataChanged();

				setPanelEnabled(fieldsPane, true);
				setPanelEnabled(sidePane, true);
				setPanelEnabled(editPane, false);
			}
		});

		// ==============================================================================
		btnEditar = view.Interfaces.Componentes.placeButton("Editar", 95, 9, 75, 25);
		btnEditar.setEnabled(false);
		editPane.add(btnEditar);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (controlador.existingContaId()) {
					controlador.editar();

					setPanelEnabled(fieldsPane, true);
					setPanelEnabled(sidePane, false);
					setPanelEnabled(editPane, false);

					tabela_contas.setEnabled(false);

					btnEditar.setEnabled(false);
					btnEditar.setVisible(false);

					btnSalvar.setEnabled(true);
					btnSalvar.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null, "Lançamento excluído!", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					controlador.updateContas();
					((AbstractTableModel) tabela_contas.getModel()).fireTableDataChanged();
				}
			}
		});
		// ==============================================================================
		btnSalvar = view.Interfaces.Componentes.placeButton("Salvar", 95, 9, 75, 25);
		btnSalvar.setEnabled(false);
		btnSalvar.setVisible(false);
		editPane.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (Utilitarios.contasFieldsVerifyer()) {

					controlador.salvar();
					((AbstractTableModel) tabela_contas.getModel()).fireTableDataChanged();
					clearFields();

					setPanelEnabled(fieldsPane, true);
					setPanelEnabled(sidePane, true);
					setPanelEnabled(editPane, false);

					tabela_contas.setEnabled(true);

					btnEditar.setVisible(true);

					btnSalvar.setVisible(false);
					btnSalvar.setEnabled(false);

					fieldOrigem.requestFocus();
				}
			}
		});
		// ==============================================================================
		btnExcluir = view.Interfaces.Componentes.placeButton("Excluir", 180, 9, 75, 25);
		btnExcluir.setEnabled(false);
		editPane.add(btnExcluir);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				controlador.excluir();
				((AbstractTableModel) tabela_contas.getModel()).fireTableDataChanged();
				tabela_contas.requestFocus();

				btnUsuarios.requestFocus();
				setPanelEnabled(fieldsPane, true);
				setPanelEnabled(editPane, false);

				if (tabela_contas.getRowCount() == 0) {

					btnUsuarios.requestFocus();
					btnEditar.setEnabled(false);
				}
			}
		});

		// =============================================================================//
		// Painel de Configurações //
		// =============================================================================//
		configPane = new JPanel();
		configPane.setBounds(289, 416, 288, 43);
		configPane.setLayout(null);
		panel.add(configPane);

		btnConfiguracoes = view.Interfaces.Componentes.placeIconButton("Configurações", 134, 11, 21, 21,
				configIconPath);
		configPane.add(btnConfiguracoes);
		btnConfiguracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.configuracoes(configuracoes_visibilidade);
				configuracoes_visibilidade = !configuracoes_visibilidade;
			}
		});

		UpdateIcons();

		// =============================================================================//
		// Painel de ações //
		// =============================================================================//

		actionPane = new JPanel();
		actionPane.setBounds(577, 416, 288, 43);
		actionPane.setLayout(null);
		panel.add(actionPane);

		// ==============================================================================
		btnUsuarios = view.Interfaces.Componentes.placeButton("Usuários", 2, 9, 85, 25);
		btnUsuarios.setEnabled(administrador);
		actionPane.add(btnUsuarios);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.usuarios(usuarios_visibilidade);
				usuarios_visibilidade = !usuarios_visibilidade;
			}
		});

		btnImprimir = view.Interfaces.Componentes.placeButton("Imprimir", 97, 9, 85, 25);
		actionPane.add(btnImprimir);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.imprimir(impressao_visibilidade);
				impressao_visibilidade = !impressao_visibilidade;
			}
		});
		// ==============================================================================
		btnVoltar = new JButton();
		btnVoltar = view.Interfaces.Componentes.placeButton("Voltar", 192, 9, 85, 25);
		actionPane.add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.voltar();

			}
		});

	}

	public void setPanelEnabled(JComponent panel, boolean setEnabled) {
		panel.setEnabled(setEnabled);
		for (Component comp : panel.getComponents()) {
			comp.setEnabled(setEnabled);
		}
	}

	public void UpdateIcons() {

		int ui = frameManager.getUIManager();

		switch (ui) {
			case 0:
				configIconPath = "/images/settings.png";
				break;
			case 1:
				configIconPath = "/images/settings.png";
				break;
			case 2:
				configIconPath = "/images/settings_darktheme.png";
				break;
			default:
				configIconPath = "/images/settings.png";
				;
				break;
		}

		btnConfiguracoes.setIcon(controlador.getIcone("Configurações", configIconPath, 21, 21));
	}

	public void clearFields() {

		fieldOrigem.setText("");
		fieldDescricao.setText("");
		fieldEntrada.setText("");
		fieldVencimento.setText("");
		fieldRateio.setText("");
		fieldValor.setText("0,00");
		comboEstado.setSelectedIndex(0);
	}

	public void contaToFields(ArrayList<Conta> contas, int linha) {

		fieldOrigem.setText(contas.get(linha).getOrigem());
		fieldDescricao.setText(contas.get(linha).getDescricao());
		fieldEntrada.setText(Formatos.padraoData(contas.get(linha).getEntrada()));
		fieldVencimento.setText(Formatos.padraoData(contas.get(linha).getVencimento()));
		fieldRateio.setText(contas.get(linha).getRateio());
		fieldValor.setText(Formatos.padraMonetario(String.valueOf(contas.get(linha).getValor())));
		comboEstado.setSelectedIndex(contas.get(linha).getEstado());

	}

	public int getSelectedLine() {
		return this.linha_selecionada;
	}

	public String getTextOrigem() {
		return this.fieldOrigem.getText();
	}

	public String getTextDescricao() {
		return this.fieldDescricao.getText();
	}

	public String getTextEntrada() {
		return this.fieldEntrada.getText();
	}

	public String getTextVencimento() {
		return this.fieldVencimento.getText();
	}

	public String getTextRateio() {
		return this.fieldRateio.getText();
	}

	public String getTextValor() {
		if (this.fieldValor.getText().equals("0,00")) {
			return "0";
		} else
			return this.fieldValor.getText();
	}

	public String getTextEstado() {
		return (String) this.comboEstado.getSelectedItem();
	}

	public int getEstadoIndex() {
		return this.comboEstado.getSelectedIndex();
	}

	public JTable getTableContas() {
		return tabela_contas;
	}
}