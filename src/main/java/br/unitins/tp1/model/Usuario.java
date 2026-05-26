package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Usuario extends DefaultEntity {
    private String nome;
    
    @Column(unique = true)
    private String login;
    
    /**
     * ID do usuário no Keycloak (sub claim do JWT)
     * Gerenciado pelo Keycloak, não deve ser alterado
     */
    @Column(unique = true, nullable = false)
    private String sub;
    
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    private String username;
    
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

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
