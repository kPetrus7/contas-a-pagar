package controller;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.service.FrameManager;
import controller.service.Utilitarios;
import model.Conta;
import model.Formats.FormatosDB;
import model.ListaContas;
import model.dao.LancamentosDAO;
import view.ConfiguracoesViewer;
import view.Formats.Formatos;
import view.ImpressaoViewer;
import view.Interfaces.Componentes;
import view.LancamentosViewer;
import view.UsuariosViwer;

public class LancamentosController {

	private FrameManager frameManager;

	private LancamentosDAO dao;
	private ListaContas lista_contas;
	private LancamentosViewer tela_lancamento;
	private UsuariosViwer tela_usuarios;
	private ConfiguracoesViewer tela_configuracoes;
	private ImpressaoViewer tela_impressao;

	private int linha_temp;

	public LancamentosController() {

		frameManager = FrameManager.getInstance();
		lista_contas = ListaContas.getInstance();

		try {
			this.dao = new LancamentosDAO();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			lista_contas.setContas(dao.getContas());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		linha_temp = -1;
	}

	public void novo() throws AWTException, SQLException {
		
		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");
		int conta_id = dao.getNextContaId();

		try {
			dao.createNewConta(Utilitarios.contasToDB(conta_id));
			updateContas();
			tela_lancamento.clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		updateContas();
	}

	public void pesquisar() throws SQLException {

		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");

		String[] textos = new String[7];
		boolean[] campos = new boolean[7];
		boolean reset = true;

		lista_contas.setContas(dao.getContas());
		ArrayList<Conta> lista_temp = lista_contas.getContas();

		textos[0] = tela_lancamento.getTextOrigem();
		textos[1] = tela_lancamento.getTextDescricao();
		textos[2] = FormatosDB.dateForDB(tela_lancamento.getTextEntrada());
		textos[3] = FormatosDB.dateForDB(tela_lancamento.getTextVencimento());
		textos[4] = tela_lancamento.getTextRateio();
		textos[5] = tela_lancamento.getTextValor();
		textos[6] = tela_lancamento.getTextEstado();

		if (textos[5].trim() == "0") {
			textos[5] = "";
		}

		for (int i = 0; i <= textos.length - 1; i++) {

			if (textos[i].trim().isEmpty() || textos[i].equals("")) {
				campos[i] = false;

			} else {
				
				campos[i] = true;
				reset = false;

				switch (i) {
					case 0:
						lista_temp.removeIf(e -> !e.getOrigem().equals(tela_lancamento.getTextOrigem()));
						break;
					case 1:
						lista_temp.removeIf(e -> !e.getDescricao().equals(tela_lancamento.getTextDescricao()));
						break;
					case 2:
						lista_temp.removeIf(
								e -> !e.getEntrada().equals(FormatosDB.dateForDB(tela_lancamento.getTextEntrada())));
						break;
					case 3:
						lista_temp.removeIf(
								e -> !e.getVencimento()
										.equals(FormatosDB.dateForDB(tela_lancamento.getTextVencimento())));
						break;
					case 4:
						lista_temp.removeIf(e -> !e.getRateio().equals(tela_lancamento.getTextRateio()));
						break;
					case 5:
						lista_temp.removeIf(
								e -> !Formatos.padraMonetario(e.getStringValor())
										.equals(tela_lancamento.getTextValor()));
						break;
					case 6:
						lista_temp.removeIf(
								e -> !e.getStringEstado().equals(String.valueOf(tela_lancamento.getEstadoIndex())));
						break;
				}
			}
			System.out.println("Campo " + i + ": " + textos[i]);
			System.out.println("Condição: " + campos[i] + "\n");
		}

		lista_contas.setContas(lista_temp);
		System.out.println(lista_contas );

		if (reset == true) {
			System.out.println("Pesquisa em branco");
			lista_contas.setContas(dao.getContas());
		}
	}

	public void baixar() {

		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");
		if (tela_lancamento.getSelectedLine() == -1)
			return;

		int linha_selecionada = tela_lancamento.getSelectedLine();
		int conta_id = lista_contas.getContas().get(linha_selecionada).getId();

		try {
			dao.contaSetEstado(conta_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateContas();
	}

	public void editar() {

		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");

		int linha_selecionada = tela_lancamento.getSelectedLine();
		int conta_id = lista_contas.getContas().get(linha_selecionada).getId();

		tela_lancamento.contaToFields(lista_contas.getContas(), linha_selecionada);
		Utilitarios.contasToArray(conta_id);
		linha_temp = linha_selecionada;
	}

	public void salvar() {

		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");
		int conta_id = lista_contas.getContas().get(linha_temp).getId();

		try {
			dao.saveUpdatedConta(Utilitarios.contasToDB(conta_id), (conta_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateContas();
		linha_temp = -1;
	}

	public void excluir() {

		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");
		int linha_selecionada = tela_lancamento.getSelectedLine();

		if (linha_selecionada > -1) {

			int conta_id = lista_contas.getContas().get(linha_selecionada).getId();

			Object[] opcoes = { "Sim", "Não" };

			int opcao = JOptionPane.showOptionDialog(
					null,
					"Deseja realmente excluir a conta?",
					"Confirmação",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					opcoes, opcoes[0]);

			if (opcao == JOptionPane.YES_OPTION) {

				dao.contaDelete(conta_id);
			}
			updateContas();
		}
	}

	public void configuracoes(boolean configuracoes_visibilidade) {
		frameManager.showPane("tela_configuracoes", !configuracoes_visibilidade);
	}

	public void usuarios(boolean usuarios_visibilidade) {
		frameManager.showPane("tela_usuarios", !usuarios_visibilidade);
	}

	public void imprimir(boolean impressao_visible) {

		ImpressaoController controle_impressao = new ImpressaoController();
		controle_impressao.imprimir();
	
	}

	public void voltar() {

		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");
		Object[] opcoes = { "Sim", "Não" };

		int opcao = JOptionPane.showOptionDialog(
				null,
				"Deseja realmente sair?",
				"Confirmação",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				opcoes, opcoes[0]);

		if (opcao == JOptionPane.YES_OPTION) {
			
			frameManager.showPane("tela_login", true);
			frameManager.closePane("tela_lancamento");
			frameManager.closePane("tela_usuarios");
			frameManager.closePane("tela_configuracoes");
			frameManager.closePane("tela_impressao");
		}
	}

	public boolean existingContaId() {

		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");

		int linha_selecionada = tela_lancamento.getSelectedLine();
		int conta_id = lista_contas.getContas().get(linha_selecionada).getId();

		return (dao.existingContaId(conta_id));
	}

	public void updateContas() {
		try {
			lista_contas.setContas(dao.getContas());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ImageIcon getIcone(String name, String iconPath, int width, int height) {

		try {

			BufferedImage Image = ImageIO.read(Componentes.class.getResource(iconPath));
			java.awt.Image redimImage = Image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(redimImage);

			return icon;

		} catch (Exception e) {
			System.err.println("(" + name + ") Erro ao carregar o ícone: " + e.getMessage());
		}
		return null;
	}
}