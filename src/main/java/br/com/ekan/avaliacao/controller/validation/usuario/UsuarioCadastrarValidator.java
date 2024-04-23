package br.com.ekan.avaliacao.controller.validation.usuario;

import br.com.ekan.avaliacao.controller.exceptions.FieldMessage;
import br.com.ekan.avaliacao.model.dto.entrada.UsuarioCadastrarDTO;
import br.com.ekan.avaliacao.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsuarioCadastrarValidator
        implements ConstraintValidator<UsuarioCadastrar, UsuarioCadastrarDTO> {

    private UsuarioRepository repository;

    public UsuarioCadastrarValidator(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UsuarioCadastrar ann) {
        // TODO document why this method is empty
    }

    @Override
    public boolean isValid(UsuarioCadastrarDTO usuarioCadastrarDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        var aux = repository.findByLogin(usuarioCadastrarDTO.getLogin());
        if (aux.isPresent()) {
            list.add(new FieldMessage("login", "Login já cadastrado"));
        }

        Set<String> usuarioRoles = new HashSet<>();
        for (int i = 0; i < usuarioCadastrarDTO.getRoles().size(); i++) {
            String role = usuarioCadastrarDTO.getRoles().get(i).getRole();
            if (!usuarioRoles.add(role)) {
                list.add(new FieldMessage(
                        "roles[" + i + "].role",
                        "Role não deve repetir")
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
