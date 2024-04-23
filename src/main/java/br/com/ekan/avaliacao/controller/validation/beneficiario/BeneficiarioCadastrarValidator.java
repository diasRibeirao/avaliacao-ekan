package br.com.ekan.avaliacao.controller.validation.beneficiario;

import br.com.ekan.avaliacao.controller.exceptions.FieldMessage;
import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioCadastrarDTO;
import br.com.ekan.avaliacao.repository.BeneficiarioRepository;
import br.com.ekan.avaliacao.utils.EkanUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeneficiarioCadastrarValidator
		implements ConstraintValidator<BeneficiarioCadastrar, BeneficiarioCadastrarDTO> {

	private BeneficiarioRepository repository;

	public BeneficiarioCadastrarValidator(BeneficiarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public void initialize(BeneficiarioCadastrar ann) {

	}

	@Override
	public boolean isValid(BeneficiarioCadastrarDTO beneficiarioCadastrarDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (!EkanUtils.isTelefoneValido(beneficiarioCadastrarDTO.getTelefoneSomenteNumeros())) {
			list.add(new FieldMessage("telefone", "Telefone inválido"));
		} else {
			var aux = repository.findByTelefone(beneficiarioCadastrarDTO.getTelefoneSomenteNumeros());
			if (aux != null) {
				list.add(new FieldMessage("telefone", "Telefone já cadastrado"));
			}
		}

		Set<String> tipoDocumentos = new HashSet<>();
		for (int i = 0; i < beneficiarioCadastrarDTO.getDocumentos().size(); i++) {
			String tipoDocumento = beneficiarioCadastrarDTO.getDocumentos().get(i).getTipoDocumento();
			if (!tipoDocumentos.add(tipoDocumento)) {
				list.add(new FieldMessage(
						"documentos[" + i + "].tipoDocumento",
						tipoDocumento + " não deve repetir")
				);
			}
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}

}
