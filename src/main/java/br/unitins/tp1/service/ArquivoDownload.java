package br.unitins.tp1.service;

public record ArquivoDownload(
        byte[] content,
        String contentType,
        String filename) {
}
