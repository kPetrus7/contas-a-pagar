package controller.service;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import view.Interfaces.IconUpdatable;

public class FrameManager {

    private static FrameManager instance;

    private int uiManager;

    private JFrame janela = new JFrame();
    private Dimension screenSize;

    private HashMap<String, JPanel> telas = new HashMap<>();

    private FrameManager() {
    }

    public static FrameManager getInstance() {
        if (instance == null) {
            instance = new FrameManager();
        }
        return instance;
    }

    public void addFrame(JFrame janela) {
        this.janela = janela;
        janela.setVisible(true);
    }

    public void setScreenSize(Dimension screenSize) {
        this.screenSize = screenSize;
    }

    public Dimension getScreenSize() {
        return this.screenSize;
    }

    public void addPane(String nome, JPanel tela) {
        telas.put(nome, tela);
        janela.getContentPane().add(tela);
        janela.revalidate();
        janela.repaint();
    }

    public JPanel getPane(String nome) {
        return telas.get(nome);
    }

    public void removePane(String nome) {
        telas.remove(nome);
    }

    public void showPane(String nome, boolean visible) {
        JPanel tela = telas.get(nome);
        if (tela != null) {
            tela.setVisible(visible);
        }
    }

    public void closePane(String nome) {
        JPanel tela = telas.get(nome);
        if (tela != null) {
            tela.setVisible(false);
            telas.remove(nome);
        }
    }

    public void closeAllPane() {
        for (String nome : telas.keySet()) {
            JPanel tela = telas.get(nome);
            if (tela != null) {
                tela.setVisible(false);
            }
        }
        telas.clear();
    }

    public void printPanes() {

        for (String nome : telas.keySet()) {
            System.out.println(nome);
        }
    }

    public void setUIManager(int uiManager) {

        if (this.uiManager == uiManager) {
            return;
        }

        this.uiManager = uiManager;

        try {
            switch (uiManager) {
                case 0:
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    break;
                case 1:
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case 2:
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                default:
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateUIFrames();
    }

    public int getUIManager() {
        return uiManager;
    }

    public void updateUIFrames() {

        SwingUtilities.updateComponentTreeUI(janela);

        for (JPanel tela : telas.values()) {
            SwingUtilities.updateComponentTreeUI(tela);

            if (tela instanceof IconUpdatable) {
                ((IconUpdatable) tela).UpdateIcons();
            }
        }

    }
}
