package br.unitins.tp1.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "usuario")
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "cliente")
    private List<Compra> compras;

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
}
