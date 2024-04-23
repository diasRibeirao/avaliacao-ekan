package br.com.ekan.avaliacao.controller.validation.usuario;


import br.com.ekan.avaliacao.controller.exceptions.FieldMessage;
import br.com.ekan.avaliacao.model.dto.entrada.UsuarioRoleCadastrarDTO;
import br.com.ekan.avaliacao.model.enums.Role;
import br.com.ekan.avaliacao.repository.DocumentoRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRoleCadastrarValidator implements ConstraintValidator<UsuarioRoleCadastrar, UsuarioRoleCadastrarDTO> {

    private DocumentoRepository repository;

    public UsuarioRoleCadastrarValidator(DocumentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UsuarioRoleCadastrar ann) {
        // TODO document why this method is empty
    }

    @Override
    public boolean isValid(UsuarioRoleCadastrarDTO usuarioRoleCadastrarDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        try {
            Role.toEnum(usuarioRoleCadastrarDTO.getRole());
        } catch (ObjectNotFoundException e) {
            list.add(new FieldMessage("role", "Role " + usuarioRoleCadastrarDTO.getRole() + " inválida"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}
