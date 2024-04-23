package br.com.ekan.avaliacao.model.dto.entrada;

import br.com.ekan.avaliacao.controller.validation.documento.DocumentoCadastrar;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@DocumentoCadastrar
public class DocumentoCadastrarDTO {

	@NotNull(message = "O tipo documento é obrigatório!")
	private String tipoDocumento;
	
	@NotNull(message = "A descricao é obrigatório")
	@Size(min = 5, max = 20, message = "A descricao deve possuir entre {min} e {max} caracteres")
	private String descricao;

}
