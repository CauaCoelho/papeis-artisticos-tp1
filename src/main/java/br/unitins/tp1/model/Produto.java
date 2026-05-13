package br.unitins.tp1.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class Produto extends DefaultEntity {

    @Column(name = "textura_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private Textura textura;

    @ManyToOne
    private Marca marca;

    @Embedded
    private EspecificacaoTecnica especificacaoTecnica;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VarianteProduto> varianteProdutos;

    private String nomeImagem;

    public Textura getTextura() {
        return textura;
    }

    public void setTextura(Textura textura) {
        this.textura = textura;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public EspecificacaoTecnica getEspecificacaoTecnica() {
        return especificacaoTecnica;
    }

    public void setEspecificacaoTecnica(EspecificacaoTecnica especificacaoTecnica) {
        this.especificacaoTecnica = especificacaoTecnica;
    }

    public List<VarianteProduto> getVarianteProdutos() {
        return varianteProdutos;
    }

    public void setVarianteProdutos(List<VarianteProduto> varianteProdutos) {
        this.varianteProdutos = varianteProdutos;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }
}
