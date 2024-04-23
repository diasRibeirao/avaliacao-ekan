package br.com.ekan.avaliacao.model.dto.entrada;


import br.com.ekan.avaliacao.controller.validation.beneficiario.BeneficiarioAtualizar;
import br.com.ekan.avaliacao.utils.EkanUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@BeneficiarioAtualizar
public class BeneficiarioAtualizarDTO {

	@NotNull(message = "O nome é obrigatório")
	@Size(min = 5, max = 80, message = "O nome deve possuir entre {min} e {max} caracteres")
	private String nome;

	@NotNull(message = "O telefone é obrigatório")
	@Schema(type = "string", pattern = "^(?:(?:\\+|00)55|)(?:\\s|-)?(?:\\(?0?\\d{2}\\)?\\s?)(?:\\s|-)?(?:9\\d{4})\\-?\\d{4}$", 
	example = "(XX) 9XXXX-XXXX ou (XX) XXXX-XXXX)")
	private String telefone;

	@NotNull(message = "A data de nascimento é obrigatória")
	@Schema(type = "string", pattern = "dd/MM/yyyy", example = "22/07/1983")
	private String dataNascimento;
	
	@NotEmpty(message = "Ao menos um documento é obrigatório")
	private @Valid List<DocumentoAtualizarDTO> documentos;
	
	@JsonIgnore
	public String getTelefoneSomenteNumeros() {
		return EkanUtils.somenteNumeros(this.telefone);
	}

}
