package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Prioridade {
    ALTA(1l, "Prioridade de Administrador"),
    COMUM(2l, "Prioridade de usuário comum");

    
    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("descricao")
    public final String DESCRICAO;

    
    Prioridade(Long id, String descricao) {
        this.ID = id;
        this.DESCRICAO = descricao;
    }

    public static Prioridade valueOf(Long id){
        for (Prioridade prioridade : Prioridade.values()) {
            if(id == prioridade.ID)
                return prioridade;
        }
        return null;
    }
}
