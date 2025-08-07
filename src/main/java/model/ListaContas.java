package model;

import java.util.ArrayList;

public class ListaContas {

    private static ListaContas instance;
    private ArrayList<Conta> contas;

    private ListaContas() {
        contas = new ArrayList<>();
    }

    public static ListaContas getInstance() {
        if (instance == null) {
            instance = new ListaContas();
        }
        return instance;
    }

    public void setContas(ArrayList<Conta> contas) {
        this.contas = contas;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public void clear() {
        contas.clear();
    }
}
