package br.com.ekan.avaliacao.model.dto.entrada;

import br.com.ekan.avaliacao.controller.validation.usuario.UsuarioRoleCadastrar;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@UsuarioRoleCadastrar
public class UsuarioRoleCadastrarDTO {

	@NotNull(message = "A role é obrigatória!")
	@Schema(type = "string", example = "ROLE_ADMIN")
	private String role;

}
