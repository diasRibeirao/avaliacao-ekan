package br.com.ekan.avaliacao.service;

import br.com.ekan.avaliacao.model.Usuario;
import br.com.ekan.avaliacao.model.dto.saida.TokenDTO;
import br.com.ekan.avaliacao.repository.UsuarioRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import br.com.ekan.avaliacao.utils.TokenUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;

    private UsuarioRepository usuarioRepository;

    private TokenUtils tokenUtils;

    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioRepository usuarioRepository,
                       TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.tokenUtils = tokenUtils;
    }

    public TokenDTO login(String login, String senha) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(login, senha)
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Instant dataExpiracao = tokenUtils.calcularDataExpiracao();

            return TokenDTO
                    .builder()
                    .accessToken(tokenUtils.gerarToken(authentication, dataExpiracao))
                    .tokenType("Bearer")
                    .expiresIn(tokenUtils.formatarDataExpiracao(dataExpiracao))
                    .build();
        } catch (BadCredentialsException e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Transactional
    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPeloId(Long id) {
        Optional<Usuario> obj = usuarioRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! Id: " + id));
    }

}
