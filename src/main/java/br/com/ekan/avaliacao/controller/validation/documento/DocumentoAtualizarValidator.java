package br.com.ekan.avaliacao.controller.validation.documento;


import br.com.ekan.avaliacao.controller.exceptions.FieldMessage;
import br.com.ekan.avaliacao.model.Documento;
import br.com.ekan.avaliacao.model.dto.entrada.DocumentoAtualizarDTO;
import br.com.ekan.avaliacao.model.enums.TipoDocumento;
import br.com.ekan.avaliacao.repository.DocumentoRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import br.com.ekan.avaliacao.utils.EkanUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DocumentoAtualizarValidator implements ConstraintValidator<DocumentoAtualizar, DocumentoAtualizarDTO> {

	private static final String DESCRICAO = "descricao";

	private HttpServletRequest request;

	private DocumentoRepository repository;

	public DocumentoAtualizarValidator(HttpServletRequest request, DocumentoRepository repository) {
		this.request = request;
		this.repository = repository;
	}

	@Override
	public void initialize(DocumentoAtualizar ann) {
		// TODO document why this method is empty
	}

	@Override
	public boolean isValid(DocumentoAtualizarDTO documentoAtualizarDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		TipoDocumento tipo = null;
		try {
			tipo = TipoDocumento.toEnum(documentoAtualizarDTO.getTipoDocumento());
		} catch (ObjectNotFoundException e) {
			list.add(new FieldMessage("tipoDocumento", "Tipo de documento inválido"));
		}
		
		if (tipo != null) {
			if (tipo == TipoDocumento.CPF && !EkanUtils.isCpfValido(documentoAtualizarDTO.getDescricao())) {
				list.add(new FieldMessage(DESCRICAO, "CPF inválido"));
			}
			
			if (tipo == TipoDocumento.CNPJ && !EkanUtils.isCnpjValido(documentoAtualizarDTO.getDescricao())) {
				list.add(new FieldMessage(DESCRICAO, "CNPJ inválido"));
			}

			if (list.isEmpty()) {
				Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				Long uriId = Long.parseLong(map.get("id"));

				Optional<Documento> aux = repository.findByTipoDocumentoAndDescricao(
						tipo,
						documentoAtualizarDTO.getDescricao()
				);

				if (aux.isPresent() && !aux.get().getId().equals(uriId)) {
					list.add(new FieldMessage(DESCRICAO, tipo + " já cadastrado"));
				}
			}
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
					.addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}

}
