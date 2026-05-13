package br.unitins.tp1.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import br.unitins.tp1.model.Produto;
import br.unitins.tp1.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ProdutoFileServiceImpl implements FileService {

    // Diretório base (poderia vir de configuração)
    private static final Path PRODUTO_UPLOAD_DIR = Paths.get(
            System.getProperty("user.home"),
            "quarkus",
            "images",
            "produto");

    // Tipos permitidos
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif");

    // Tamanho mínimo e máximo (exemplo)
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024; // 5 MB
    private static final long MIN_FILE_SIZE = 1L * 1024; // 1 KB

    @Inject
    ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public void salvar(Long id, FileUpload file) throws IOException {
        Produto produto = produtoRepository.findById(id);

        String novoNomeImagem = salvarImagem(file);
        produto.setNomeImagem(novoNomeImagem);
    }

    private String salvarImagem(FileUpload file) {
        if (file == null || file.uploadedFile() == null) {
            throw new WebApplicationException(
                    "Arquivo de imagem não informado.",
                    Response.Status.BAD_REQUEST);
        }
        try {
            validarTamanho(file);
            validarExtensao(file);

            // Garante que o diretório existe
            Files.createDirectories(PRODUTO_UPLOAD_DIR);
            
            // Gera nome aleatório seguro
            String novoNome = gerarNomeAleatorio(file.fileName());
            Path destino = PRODUTO_UPLOAD_DIR.resolve(novoNome);

            // Salva o arquivo
            Files.copy(file.uploadedFile(), destino, StandardCopyOption.REPLACE_EXISTING);

            return novoNome;

        } catch (IOException e) {
            throw new WebApplicationException(
                    "Erro ao salvar a imagem.",
                    e,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private void validarTamanho(FileUpload file) {
        long size = file.size();

        if (size <= 0) {
            throw new WebApplicationException(
                    "Arquivo vazio.",
                    Response.Status.BAD_REQUEST);
        }

        if (size < MIN_FILE_SIZE) {
            throw new WebApplicationException(
                    "Arquivo muito pequeno para ser considerado uma imagem válida.",
                    Response.Status.BAD_REQUEST);
        }

        if (size > MAX_FILE_SIZE) {
            throw new WebApplicationException(
                    String.format("Arquivo muito grande. Tamanho máximo permitido: %d bytes.", MAX_FILE_SIZE),
                    Response.Status.BAD_REQUEST);
        }
    }

    private void validarExtensao(FileUpload file) {
        String ext = getExtension(file.fileName());

        if (ext == null || !ALLOWED_EXTENSIONS.contains(ext.toLowerCase(Locale.ROOT))) {
            throw new WebApplicationException(
                    "Extensão de arquivo não suportada.",
                    Response.Status.BAD_REQUEST);
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null)
            return null;
        String onlyName = Paths.get(fileName).getFileName().toString();
        int idx = onlyName.lastIndexOf('.');
        if (idx == -1 || idx == onlyName.length() - 1) {
            return null;
        }
        return onlyName.substring(idx + 1);
    }

    private String gerarNomeAleatorio(String originalName) {
        // Garante que não vem caminho junto
        String onlyName = Paths.get(originalName).getFileName().toString();

        // Descobre extensão
        String ext = "";
        int idx = onlyName.lastIndexOf('.');
        if (idx != -1) {
            ext = onlyName.substring(idx).toLowerCase(Locale.ROOT);
        }

        // Gera UUID + extensão
        return UUID.randomUUID().toString() + ext;
    }

    @Override
    public File download(String nomeArquivo) {
        File file = PRODUTO_UPLOAD_DIR.resolve(nomeArquivo).toFile();
        return file;
    }
}
