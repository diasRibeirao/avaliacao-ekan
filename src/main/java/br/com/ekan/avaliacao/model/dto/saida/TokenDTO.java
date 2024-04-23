package br.com.ekan.avaliacao.model.dto.saida;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

@Builder
public record TokenDTO(
        @JsonFormat(pattern = "access_token")
        String accessToken,
        @JsonFormat(pattern = "token_type")
        String tokenType,
        @JsonFormat(pattern = "expires_in")
        String expiresIn
) {
}
