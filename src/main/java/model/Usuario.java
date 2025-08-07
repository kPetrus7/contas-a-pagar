package model;

public class Usuario {

    private int id;
    private int prioridade;
    private String login;
    private String senha;

    public Usuario() {
        
    }
    
    public Usuario(int id, int prioridade, String login, String senha) {

        this.id = id;
        this.prioridade = prioridade;
        this.login = login;
        this.senha = senha;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrioridade() {
        return this.prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
    	return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}