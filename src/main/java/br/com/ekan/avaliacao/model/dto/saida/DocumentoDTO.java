package br.com.ekan.avaliacao.model.dto.saida;


import br.com.ekan.avaliacao.model.enums.TipoDocumento;

public record DocumentoDTO(
        Long id,
        TipoDocumento tipoDocumento,
        String descricao
) {}
