package controller;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import controller.service.FrameManager;
import model.dao.ConfiguracoesDAO;
import view.ConfiguracoesViewer;

public class ConfiguracoesController {

    private FrameManager frameManager;
    private ConfiguracoesViewer tela_configuracoes;
    private ConfiguracoesDAO dao;
    private int tema;

    public ConfiguracoesController() {

        frameManager = FrameManager.getInstance();

        try {
            this.dao = new ConfiguracoesDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int setTema() {

        tela_configuracoes = (ConfiguracoesViewer) frameManager.getPane("tela_configuracoes");
        tema = dao.getTema();

        if (tema == -1) {
            tema = 0;
        }
        return tema;
    }

    public void saveConfiguracoes() {
        
        tela_configuracoes = (ConfiguracoesViewer) frameManager.getPane("tela_configuracoes");
        tema = tela_configuracoes.getSelectedTheme();
        dao.updateTema(tema);
        frameManager.setUIManager(tema);
    }

    public void switchTema() {

        tela_configuracoes = (ConfiguracoesViewer) frameManager.getPane("tela_configuracoes");
        tema = tela_configuracoes.getSelectedTheme();

        try{
            switch (tema) {
                case 1: UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case 2: UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
                    UIManager.put("Component.focusWidth", 0.8);
                    UIManager.put("Component.focusColor", new Color(2, 53, 115));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
