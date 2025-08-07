package view.Interfaces;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Componentes {

	public String font = "Segoe UI Emoji";

	public int uiManager = -1;

	public int[] fontColor = {0, 0, 0};
	public int[] pane_backgroundColor = {0, 114, 252};

	public Componentes() {
	
	}

	public Componentes(String font, int uiManager, int[] fontColor, int[] pane_backgroundColor) {
		this.font = font;
		this.uiManager = uiManager;
		this.fontColor = fontColor;
		this.pane_backgroundColor = pane_backgroundColor;
	}

	public static JLabel placeLabel(String text, int x, int y, int width, int height) {
		
		JLabel label = new JLabel(text);
		label.setName("lbl" + text);
		label.setBounds(x, y, width, height);
		return label;
	}

	public static JLabel placeLblTitle(String text, int x, int y, int width, int height) {
		
		JLabel label = new JLabel(text);
		label.setName("title" + text);
		label.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(x, y, width, height);
		return label;
	}

	public static JTextField placeTextField(String name, int x, int y, int width, int height) {
		
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
		textField.setName("field" + name);

		InputMap inputmap = textField.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = textField.getActionMap();

		inputmap.put(KeyStroke.getKeyStroke("ENTER"), "click");
		actionMap.put("click", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField.transferFocus();
		    }
		});

		textField.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent e) {
		    	textField.selectAll();
		    }
		});
		return textField;
	}

	public static JTextField placeKeyField(String name, int x, int y, int width, int height) {
		
		JPasswordField textKeyField = new JPasswordField();
		textKeyField.setBounds(x, y, width, height);
		textKeyField.setName("keyField" + name);

		InputMap inputmap = textKeyField.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = textKeyField.getActionMap();

		inputmap.put(KeyStroke.getKeyStroke("ENTER"), "click");
		actionMap.put("click", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textKeyField.transferFocus();
		    }
		});

		textKeyField.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent e) {
		    	textKeyField.selectAll();
		    }
		});
		
		return textKeyField;
	}
	public static JButton placeButton(String text, int x, int y, int width, int height) {
		
		JButton button = new JButton(text);
		button.setName("btn" + text);
		button.setSelectedIcon(null);
		button.setBounds(x, y, width, height);
		button.setFocusable(true);

		InputMap inputmap = button.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = button.getActionMap();
		
		inputmap.put(KeyStroke.getKeyStroke("ENTER"), "click");
		actionMap.put("click", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        button.doClick();
		    }
		});
		return button;
	}

	public static JButton placeIconButton(String name, int x, int y, int width, int height, String iconPath) {

		JButton button = new JButton();
		button.setName("btn" + name);
		button.setSelectedIcon(null); 
		button.setBounds(x, y, width, height);
		button.setFocusable(true);

		InputMap inputmap = button.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = button.getActionMap();
			
		inputmap.put(KeyStroke.getKeyStroke("ENTER"), "click");
		actionMap.put("click", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button.doClick();
			}
		});

		try{

			BufferedImage Image = ImageIO.read(Componentes.class.getResource(iconPath));
			java.awt.Image redimImage = Image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(redimImage);
		
			button.setIcon(icon);
    	    button.setBorderPainted(false);
	        button.setFocusPainted(false);
        	button.setContentAreaFilled(false);

		} catch (Exception e) {
			button.setText(name);
			System.err.println("(" + name + ") Erro ao carregar o ícone: " + e.getMessage());
		}
		return button;
	}

	public static JCheckBox placeChekBox(String text, int x, int y, int width, int height) {

		JCheckBox chkBox = new JCheckBox(text);
		chkBox.setBounds(x, y, width, height);
		chkBox.setName("chk" + text);
		chkBox.setBorder(new LineBorder(new java.awt.Color(192, 192, 192)));

		InputMap inputmap = chkBox.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = chkBox.getActionMap();
		
		inputmap.put(KeyStroke.getKeyStroke("ENTER"), "click");
		actionMap.put("click", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        chkBox.doClick();
		    }
		});
		return chkBox;
	}
}
