package br.unitins.tp1.model;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Compra {
    FormaDePagamento formaDePagamento;
    List<Produto> produto;
    Cliente cliente;
}
