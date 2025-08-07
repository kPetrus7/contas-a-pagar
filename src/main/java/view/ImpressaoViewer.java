package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.ImpressaoController;
import controller.service.FrameManager;
import controller.service.Utilitarios;
import view.Interfaces.Componentes;

public class ImpressaoViewer extends JPanel {

	private static final long serialVersionUID = 1L;

	private FrameManager frameManager;
	private LancamentosViewer tela_lancamento;
	private ImpressaoController controlador;

	private JPanel panel;
	private JCheckBox chkOrigem;
	private JCheckBox chkDescricao;
	private JCheckBox chkEntrada;
	private JCheckBox chkVencimento;
	private JCheckBox chkRateio;
	private JCheckBox chkValor;
	private JCheckBox chkEstado;
	private JCheckBox chkId;
	private JButton btnOk;

	private int largura;
	private int altura;

	public ImpressaoViewer() {

		Utilitarios.arrastarPainel(this);

		largura = 380;
		altura = 120;

		controlador = new ImpressaoController();
		frameManager = FrameManager.getInstance();
		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");

		Point contas_posicao = tela_lancamento.getLocation();

		int x = contas_posicao.x + tela_lancamento.getWidth() - largura;
		int y = contas_posicao.y + tela_lancamento.getHeight() + 7;

		setBounds(x, y, largura, altura);
		setBackground(new Color(41, 79, 125));
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 10, 360, 100);
		panel.setLayout(null);
		add(panel);

		chkOrigem = Componentes.placeChekBox("Origem", 5, 5, 90, 25);
		chkOrigem.setSelected(true);
		panel.add(chkOrigem);

		chkDescricao = Componentes.placeChekBox("Descrição", 5, 35, 90, 25);
		chkDescricao.setSelected(true);
		panel.add(chkDescricao);

		chkRateio = Componentes.placeChekBox("Rateio", 5, 65, 90, 25);
		chkRateio.setSelected(true);
		panel.add(chkRateio);

		chkEntrada = Componentes.placeChekBox("Entrada", 130, 5, 90, 25);
		chkEntrada.setSelected(true);
		panel.add(chkEntrada);

		chkVencimento = Componentes.placeChekBox("Vencimento", 130, 35, 90, 25);
		chkVencimento.setSelected(true);
		panel.add(chkVencimento);

		chkValor = Componentes.placeChekBox("Valor", 130, 65, 90, 25);
		chkValor.setSelected(true);
		panel.add(chkValor);

		chkEstado = Componentes.placeChekBox("Estado", 255, 5, 90, 25);
		chkEstado.setSelected(true);
		panel.add(chkEstado);

		chkId = Componentes.placeChekBox("ID", 255, 35, 90, 25);
		chkId.setSelected(true);
		panel.add(chkId);

		btnOk = Componentes.placeButton("Ok", 255, 65, 90, 25);
		panel.add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.imprimir();
			}
		});
	}

	public ArrayList<Boolean> getElementos() {

		ArrayList<Boolean> elementos = new ArrayList<>();

		elementos.add(chkId.isSelected());
		elementos.add(chkOrigem.isSelected());
		elementos.add(chkDescricao.isSelected());
		elementos.add(chkEntrada.isSelected());
		elementos.add(chkVencimento.isSelected());
		elementos.add(chkRateio.isSelected());
		elementos.add(chkValor.isSelected());
		elementos.add(chkEstado.isSelected());

		return elementos;
	}
}