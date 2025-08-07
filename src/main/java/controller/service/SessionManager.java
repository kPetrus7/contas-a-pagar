package controller.service;

import model.Usuario;

public class SessionManager {
    
    private static SessionManager instance;
    private Usuario usuario;

    private SessionManager(){
    }

    public static SessionManager getInstance(){
        if (instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

        System.out.println("\n" + "Usuario definido: ");
        System.out.println("Nome: " + usuario.getLogin());
        System.out.println("Id: " + usuario.getId());
        System.out.println("Prioridade: " + usuario.getPrioridade());
    }

    public int getUsuarioId() {
        return usuario.getId();
    }
    
    public int getUsuarioPrioridade() {
        return usuario.getPrioridade();
    }

    public String getUsuarioNome() {
        return usuario.getLogin();
    }

}
