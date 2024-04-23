package br.com.ekan.avaliacao.model.dto.saida;

import lombok.Builder;

@Builder
public record MensagemDTO (
    String mensagem
) {}
