package controller;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.swing.JTable;

import controller.service.FrameManager;
import model.Conta;
import model.ListaContas;
import view.Formats.Formatos;
import view.ImpressaoViewer;
import view.LancamentosViewer;

public class ImpressaoController {

    private LancamentosViewer tela_lancamento;
    private ImpressaoViewer tela_impressao;
    private FrameManager frameManager;
    private ListaContas contas;
    private JTable tabela_impressao;
    private MessageFormat header;
    private MessageFormat footer;


    public ImpressaoController() {

        frameManager = FrameManager.getInstance();
        contas = ListaContas.getInstance();

    }

    public void imprimir() {
        
        ArrayList<Conta> lista_contas = contas.getContas();
        double soma_valores = 0.0;

        header = new MessageFormat("Contas a Pagar");        
        tela_lancamento = (LancamentosViewer) frameManager.getPane("tela_lancamento");
        tela_impressao = (ImpressaoViewer) frameManager.getPane("tela_impressao");
        tabela_impressao = tela_lancamento.getTableContas();

        for (Conta c : lista_contas) {
            soma_valores = soma_valores + c.getValor();
        }

        footer = new MessageFormat("Total: R$ " + Formatos.padraMonetario(String.valueOf(soma_valores)));

        try {
            tabela_impressao.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException e) {
            System.err.println(e);
        }


    }
}
