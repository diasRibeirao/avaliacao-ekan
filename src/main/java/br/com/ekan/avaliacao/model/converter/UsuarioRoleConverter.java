package br.com.ekan.avaliacao.model.converter;

import br.com.ekan.avaliacao.model.Usuario;
import br.com.ekan.avaliacao.model.UsuarioRole;
import br.com.ekan.avaliacao.model.dto.entrada.UsuarioRoleCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.saida.UsuarioRoleDTO;
import br.com.ekan.avaliacao.model.enums.Role;
import br.com.ekan.avaliacao.utils.EkanUtils;
import java.util.Collections;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioRoleConverter {
    public UsuarioRoleDTO parse(UsuarioRole origin) {
        if (origin == null)
            return null;

        return new UsuarioRoleDTO(
                origin.getId(),
                origin.getRole()
        );
    }

    public List<UsuarioRoleDTO> parse(List<UsuarioRole> origin) {
        if (origin == null)
            return Collections.emptyList();

        return origin.stream().map(this::parse).collect(Collectors.toList());
    }


    public UsuarioRole parseRoleCadastrarDTO(UsuarioRoleCadastrarDTO origin, Usuario usuario) {
        if (origin == null)
            return null;

        var usuarioRole = new UsuarioRole(
                usuario,
                Role.toEnum(origin.getRole())
        );
        usuarioRole.setDataInclusao(EkanUtils.dataAtual());
        return usuarioRole;
    }

    public List<UsuarioRole> parseRoleCadastrarDTO(List<UsuarioRoleCadastrarDTO> origin, Usuario usuario) {
        return origin.stream().map(obj -> parseRoleCadastrarDTO(obj, usuario))
                .collect(Collectors.toList());
    }
}
