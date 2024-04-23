package br.com.ekan.avaliacao.repository;

import br.com.ekan.avaliacao.model.Documento;
import br.com.ekan.avaliacao.model.enums.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    @Transactional(readOnly = true)
    Optional<Documento> findByTipoDocumentoAndDescricao(TipoDocumento tipo, String descricao);

}