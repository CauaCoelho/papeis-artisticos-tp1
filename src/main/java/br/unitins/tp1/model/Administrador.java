package br.unitins.tp1.model;

public class Administrador extends DefaultEntity {
private String nome;
private String login;
private String senha;
private Prioridade prioridade;

public String getNome() {
    return nome;
}
public void setNome(String nome) {
    this.nome = nome;
}
public String getLogin() {
    return login;
}
public void setLogin(String login) {
    this.login = login;
}
public String getSenha() {
    return senha;
}
public void setSenha(String senha) {
    this.senha = senha;
}
public Prioridade getPrioridade() {
    return prioridade;
}
public void setPrioridade(Prioridade prioridade) {
    this.prioridade = prioridade;
}


}
