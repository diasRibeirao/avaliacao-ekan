package br.com.ekan.avaliacao.service;

import br.com.ekan.avaliacao.model.Usuario;
import br.com.ekan.avaliacao.model.security.UserDetailsImpl;
import br.com.ekan.avaliacao.repository.UsuarioRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

        return UserDetailsImpl.build(usuario);
    }

}
