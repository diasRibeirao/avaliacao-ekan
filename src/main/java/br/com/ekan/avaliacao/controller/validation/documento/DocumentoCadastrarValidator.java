package br.com.ekan.avaliacao.controller.validation.documento;


import br.com.ekan.avaliacao.controller.exceptions.FieldMessage;
import br.com.ekan.avaliacao.model.Documento;
import br.com.ekan.avaliacao.model.dto.entrada.DocumentoCadastrarDTO;
import br.com.ekan.avaliacao.model.enums.TipoDocumento;
import br.com.ekan.avaliacao.repository.DocumentoRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import br.com.ekan.avaliacao.utils.EkanUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentoCadastrarValidator implements ConstraintValidator<DocumentoCadastrar, DocumentoCadastrarDTO> {

    private DocumentoRepository repository;

    public DocumentoCadastrarValidator(DocumentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(DocumentoCadastrar ann) {
    }

    @Override
    public boolean isValid(DocumentoCadastrarDTO documentoCadastrarDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        TipoDocumento tipo = null;
        try {
            tipo = TipoDocumento.toEnum(documentoCadastrarDTO.getTipoDocumento());
        } catch (ObjectNotFoundException e) {
            list.add(new FieldMessage("tipoDocumento", "Tipo de documento inválido"));
        }

        if (tipo != null) {
            if (tipo == TipoDocumento.CPF && !EkanUtils.isCpfValido(documentoCadastrarDTO.getDescricao())) {
                list.add(new FieldMessage("descricao", "CPF inválido"));
            }

            if (tipo == TipoDocumento.CNPJ && !EkanUtils.isCnpjValido(documentoCadastrarDTO.getDescricao())) {
                list.add(new FieldMessage("descricao", "CNPJ inválido"));
            }

            if (list.isEmpty()) {
                Optional<Documento> aux = repository.findByTipoDocumentoAndDescricao(
                        tipo,
                        documentoCadastrarDTO.getDescricao()
                );

                if (aux.isPresent()) {
                    list.add(new FieldMessage("descricao", tipo + " já cadastrado"));
                }
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
