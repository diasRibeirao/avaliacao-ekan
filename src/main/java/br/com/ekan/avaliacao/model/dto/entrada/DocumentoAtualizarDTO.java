package br.com.ekan.avaliacao.model.dto.entrada;


import br.com.ekan.avaliacao.controller.validation.documento.DocumentoAtualizar;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@DocumentoAtualizar
public class DocumentoAtualizarDTO {

	private Long id;

	@NotNull(message = "O tipo documento é obrigatório!")
	private String tipoDocumento;
	
	@NotNull(message = "A descricao é obrigatório")
	@Size(min = 5, max = 20, message = "A descricao deve possuir entre {min} e {max} caracteres")
	private String descricao;
}
