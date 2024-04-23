package br.com.ekan.avaliacao.model.converter;

import br.com.ekan.avaliacao.model.Usuario;
import br.com.ekan.avaliacao.model.dto.entrada.UsuarioCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.saida.UsuarioDTO;
import br.com.ekan.avaliacao.utils.EkanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {

    private UsuarioRoleConverter usuarioRoleConverter;

    private PasswordEncoder passwordEncoder;

    public UsuarioConverter(UsuarioRoleConverter usuarioRoleConverter,
                            PasswordEncoder passwordEncoder) {
        this.usuarioRoleConverter = usuarioRoleConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO parse(Usuario origin) {
        if (origin == null)
            return null;

        return new UsuarioDTO(
                origin.getId(),
                origin.getNome(),
                origin.getLogin(),
                usuarioRoleConverter.parse(origin.getRoles())
        );
    }

    public Usuario parseUsuarioCadastrarDTO(UsuarioCadastrarDTO origin) {
        if (origin == null)
            return null;

        var usuario = new Usuario(
                origin.getNome(),
                origin.getLogin(),
                passwordEncoder.encode(origin.getSenha())
        );
        usuario.setRoles(usuarioRoleConverter.parseRoleCadastrarDTO(
                origin.getRoles(), usuario)
        );
        usuario.setDataInclusao(EkanUtils.dataAtual());
        return usuario;
    }
}
