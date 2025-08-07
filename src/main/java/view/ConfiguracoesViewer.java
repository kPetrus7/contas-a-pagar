package view;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.Point;

import controller.ConfiguracoesController;
import controller.service.FrameManager;
import controller.service.Utilitarios;
import view.Interfaces.Componentes;

public class ConfiguracoesViewer extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel lblTema;
	private JButton btnSalvar;
	private JComboBox<String> comboTema; 

	private FrameManager frameManager;
	private ConfiguracoesController controlador;
	
	private LancamentosViewer tela_lancamento;
	private int linha_selecionada;
	private int largura;
	private int altura;

	public ConfiguracoesViewer() {

		Utilitarios.arrastarPainel(this);

		controlador = new ConfiguracoesController();
		frameManager = FrameManager.getInstance();
		tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");

		largura = 214;
		altura = 500;

		Point contas_posicao = tela_lancamento.getLocation();
		
		int x = contas_posicao.x + tela_lancamento.getWidth() + 7;
		int y = contas_posicao.y;

		setBounds(x, y, largura, altura);
		setBackground(new Color(41, 79, 125));
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 10, 194, 480);
		panel.setLayout(null);
		add(panel);

		lblTema = Componentes.placeLabel("Tema", 10, 10, 174, 16);
		panel.add(lblTema);

		String[] temas = { "Legado", "FlatLightLaf", "FlatDarkLaf" };
		comboTema = new JComboBox<>(temas);
		comboTema.setSelectedIndex(controlador.setTema());
		comboTema.setBounds(10, 31, 174, 22);
		panel.add(comboTema);

		btnSalvar = view.Interfaces.Componentes.placeButton("Salvar", 10, 445, 174, 25);
		panel.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.saveConfiguracoes();
			}
		});
	}
	
	public int getSelectedTheme() {
		return comboTema.getSelectedIndex();
	}
}
