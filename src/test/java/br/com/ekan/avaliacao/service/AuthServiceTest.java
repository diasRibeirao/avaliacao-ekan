package br.com.ekan.avaliacao.service;

import br.com.ekan.avaliacao.model.Usuario;
import br.com.ekan.avaliacao.model.dto.saida.TokenDTO;
import br.com.ekan.avaliacao.repository.UsuarioRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import br.com.ekan.avaliacao.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TokenUtils tokenUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authService = new AuthService(authenticationManager, usuarioRepository, tokenUtils);
    }

    @Test
    public void loginTest() {
        String login = "emerson.dias";
        String senha = "asd123qwe!";
        Authentication authentication = mock(Authentication.class);
        Instant dataExpiracao = Instant.now();
        TokenDTO expectedTokenDTO = TokenDTO.builder()
                .accessToken("testAccessToken")
                .tokenType("Bearer")
                .expiresIn("testExpiration")
                .build();

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenUtils.calcularDataExpiracao()).thenReturn(dataExpiracao);
        when(tokenUtils.gerarToken(authentication, dataExpiracao)).thenReturn("testAccessToken");
        when(tokenUtils.formatarDataExpiracao(dataExpiracao)).thenReturn("testExpiration");

        TokenDTO tokenDTO = authService.login(login, senha);

        assertNotNull(tokenDTO);
        assertEquals(expectedTokenDTO, tokenDTO);
    }

    @Test
    void registrarTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("emerson.dias");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario savedUsuario = authService.registrar(usuario);

        assertNotNull(savedUsuario);
        assertEquals(usuario, savedUsuario);
    }

    @Test
    public void testBuscarPeloId_UsuarioEncontrado() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario foundUsuario = authService.buscarPeloId(id);

        assertNotNull(foundUsuario);
        assertEquals(usuario, foundUsuario);
    }

    @Test
    public void testBuscarPeloId_UsuarioNaoEncontrado() {
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> authService.buscarPeloId(id));
    }
}