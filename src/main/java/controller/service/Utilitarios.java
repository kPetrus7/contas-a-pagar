package controller.service;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Point;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Conta;
import model.Usuario;
import model.Formats.FormatosDB;
import view.LancamentosViewer;
import view.UsuariosViwer;

public class Utilitarios {

	public static Conta contasToArray(int conta_id) {

		LancamentosViewer tela_lancamento = (LancamentosViewer) FrameManager.getInstance().getPane("tela_lancamento");
		Conta conta = new Conta();

		conta.setId(conta_id);
		conta.setOrigem(tela_lancamento.getTextOrigem());
		conta.setDescricao(tela_lancamento.getTextDescricao());
		conta.setEntrada(tela_lancamento.getTextEntrada());
		conta.setVencimento(tela_lancamento.getTextVencimento());
		conta.setRateio(tela_lancamento.getTextRateio());
		conta.setValor(FormatosDB.valorForDB(tela_lancamento.getTextValor()));
		conta.setEstado(tela_lancamento.getEstadoIndex());

		return conta;
	}

	public static Conta contasToDB(int conta_id) {

		LancamentosViewer tela_lancamento = (LancamentosViewer) FrameManager.getInstance().getPane("tela_lancamento");

		Conta conta = new Conta();

		conta.setId(conta_id);
		conta.setOrigem(tela_lancamento.getTextOrigem());
		conta.setDescricao(tela_lancamento.getTextDescricao());
		conta.setEntrada(FormatosDB.dateForDB(tela_lancamento.getTextEntrada()));
		conta.setVencimento(FormatosDB.dateForDB(tela_lancamento.getTextVencimento()));
		conta.setRateio(tela_lancamento.getTextRateio());
		conta.setValor(FormatosDB.valorForDB(tela_lancamento.getTextValor()));
		conta.setEstado(tela_lancamento.getEstadoIndex());

		conta.printConta();
		return conta;
	}

	public static boolean contasFieldsVerifyer() {

		LancamentosViewer tela_lancamento = (LancamentosViewer) FrameManager.getInstance().getPane("tela_lancamento");
		boolean campos = true;

		if (tela_lancamento.getTextEstado() == null || tela_lancamento.getTextEstado().trim().isEmpty())
			campos = false;
		if (tela_lancamento.getTextOrigem() == null || tela_lancamento.getTextOrigem().trim().isEmpty())
			campos = false;
		if (tela_lancamento.getTextDescricao() == null || tela_lancamento.getTextDescricao().trim().isEmpty())
			campos = false;
		if (tela_lancamento.getTextEntrada() == null || tela_lancamento.getTextEntrada().trim().isEmpty())
			campos = false;
		if (tela_lancamento.getTextVencimento() == null || tela_lancamento.getTextVencimento().trim().isEmpty())
			campos = false;
		if (tela_lancamento.getTextRateio() == null || tela_lancamento.getTextRateio().trim().isEmpty())
			campos = false;
		if (tela_lancamento.getTextValor() == null || tela_lancamento.getTextValor().trim().isEmpty())
			campos = false;
		if (tela_lancamento.getTextEstado() == null || tela_lancamento.getTextEstado().trim().isEmpty())
			campos = false;

		if (campos) {
			if (tela_lancamento.getTextValor().equals("0")) {
				JOptionPane.showMessageDialog(tela_lancamento, "O valor do lançamento está zerado!", "Atenção",
						JOptionPane.WARNING_MESSAGE);
			}
			return campos;
		} else {
			JOptionPane.showMessageDialog(null, "Todos os campos precisam ser preenchidos!", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return campos;
		}
	}

	public static Usuario usuariosToDB(UsuariosViwer tela_usuarios, int user_id) {

		Usuario user = new Usuario();

		user.setId(user_id);
		user.setPrioridade(tela_usuarios.getPrioridade());
		user.setLogin(tela_usuarios.getTextLogin());
		user.setSenha(tela_usuarios.getTextSenha());

		return user;
	}

	public static boolean userFieldsVerifyer(UsuariosViwer tela_usuarios) {

		if (tela_usuarios.getTextLogin() == null || tela_usuarios.getTextLogin().trim().isEmpty())
			return false;
		if (tela_usuarios.getTextSenha() == null || tela_usuarios.getTextSenha().trim().isEmpty())
			return false;

		return true;
	}

	public static void userCleanFields(UsuariosViwer tela_usuarios) {

		tela_usuarios.setTextLogin("");
		tela_usuarios.setTextSenha("");
	}

	public static void arrastarPainel(JPanel panel) {
		
		final Point[] clickPoint = { null };

		panel.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				clickPoint[0] = e.getPoint();
			}
		});

		panel.addMouseMotionListener(new MouseMotionAdapter() {
		
			public void mouseDragged(MouseEvent e) {
		
				Point currentScreenLocation = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(currentScreenLocation, panel.getParent());
				panel.setLocation(currentScreenLocation.x - clickPoint[0].x, currentScreenLocation.y - clickPoint[0].y);
			}
		});
	}
}
