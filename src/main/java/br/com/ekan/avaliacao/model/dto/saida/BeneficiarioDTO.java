package br.com.ekan.avaliacao.model.dto.saida;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record BeneficiarioDTO(
		Long id, 
		String nome,
		String telefone,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
		@Schema(type = "string", pattern = "dd/MM/yyyy")
		LocalDate dataNascimento,
		List<DocumentoDTO> documentos
) {}
