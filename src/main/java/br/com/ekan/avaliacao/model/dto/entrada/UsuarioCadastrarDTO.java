package br.com.ekan.avaliacao.model.dto.entrada;

import br.com.ekan.avaliacao.controller.validation.usuario.UsuarioCadastrar;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@UsuarioCadastrar
public class UsuarioCadastrarDTO {

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 5, max = 80, message = "O nome deve possuir entre {min} e {max} caracteres")
    private String nome;

    @NotNull(message = "O login é obrigatório")
    @Size(min = 3, max = 20, message = "O login deve possuir entre {min} e {max} caracteres")
    private String login;

    @NotNull(message = "A senha é obrigatória")
    @Size(min = 6, max = 40, message = "A senha deve possuir entre {min} e {max} caracteres")
    private String senha;

    @NotEmpty(message = "Ao menos uma role é obrigatória")
    private List<UsuarioRoleCadastrarDTO> roles;

}
