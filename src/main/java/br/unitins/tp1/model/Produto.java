package br.unitins.tp1.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class Produto extends DefaultEntity {

    @Column(name = "textura_id", nullable = false)
    private Long texturaId;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToMany
    @JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    @JsonManagedReference
    private List<Categoria> categorias = new ArrayList<>();

    private EspecificacaoTecnica especificacaoTecnica;

    @Transient
    public Textura getTextura() {
        return Textura.valueOf(texturaId);
    }
    
    @Transient
    public void setTextura(Textura textura) {
        this.texturaId = (textura != null) ? textura.ID : null;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public EspecificacaoTecnica getEspecificacaoTecnica() {
        return especificacaoTecnica;
    }

    public void setEspecificacaoTecnica(EspecificacaoTecnica especificacaoTecnica) {
        this.especificacaoTecnica = especificacaoTecnica;
    }

}
