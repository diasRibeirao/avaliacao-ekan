package br.com.ekan.avaliacao.model.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    @NotNull(message = "O login é obrigatório")
    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotNull(message = "A senha é obrigatória")
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

}
