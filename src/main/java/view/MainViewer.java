package view;

import javax.swing.JFrame;

import java.awt.*;

import controller.service.FrameManager;
import controller.service.SessionManager;

public class MainViewer extends JFrame {

    private FrameManager frameManager;
    private SessionManager sessionManager;
    private Dimension screenSize;

    int largura;
    int altura;

    public MainViewer() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameManager = FrameManager.getInstance();
        frameManager.setScreenSize(screenSize);

        largura = screenSize.width;
        altura = screenSize.height;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(254, 254, 254));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setSize(largura, altura);
        
        getContentPane().setLayout(null);
    }
}
