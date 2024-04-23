package br.com.ekan.avaliacao.controller.validation.beneficiario;

import br.com.ekan.avaliacao.controller.exceptions.FieldMessage;
import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioAtualizarDTO;
import br.com.ekan.avaliacao.repository.BeneficiarioRepository;
import br.com.ekan.avaliacao.utils.EkanUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.*;

public class BeneficiarioAtualizarValidator implements ConstraintValidator<BeneficiarioAtualizar, BeneficiarioAtualizarDTO> {

    private HttpServletRequest request;

    private BeneficiarioRepository repository;

    public BeneficiarioAtualizarValidator(HttpServletRequest request, BeneficiarioRepository repository) {
        this.request = request;
        this.repository = repository;
    }

    @Override
    public void initialize(BeneficiarioAtualizar constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BeneficiarioAtualizarDTO beneficiarioAtualizarDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (!EkanUtils.isTelefoneValido(beneficiarioAtualizarDTO.getTelefoneSomenteNumeros())) {
            list.add(new FieldMessage("telefone", "Telefone inválido"));
        } else {
            Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            Long uriId = Long.parseLong(map.get("id"));

            var aux = repository.findByTelefone(beneficiarioAtualizarDTO.getTelefoneSomenteNumeros());
            if (aux != null && !aux.getId().equals(uriId)) {
                list.add(new FieldMessage("telefone", "Telefone já cadastrado"));
            }
        }

        Set<String> tipoDocumentos = new HashSet<>();
        for (int i = 0; i < beneficiarioAtualizarDTO.getDocumentos().size(); i++) {
            String tipoDocumento = beneficiarioAtualizarDTO.getDocumentos().get(i).getTipoDocumento();
            if (!tipoDocumentos.add(tipoDocumento)) {
                list.add(new FieldMessage(
                        "documentos[" + i + "].tipoDocumento",
                        tipoDocumento + " não deve repetir")
                );
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
