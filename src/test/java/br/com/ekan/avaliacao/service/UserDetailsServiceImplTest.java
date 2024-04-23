package br.com.ekan.avaliacao.service;

import br.com.ekan.avaliacao.model.Usuario;
import br.com.ekan.avaliacao.model.UsuarioRole;
import br.com.ekan.avaliacao.model.enums.Role;
import br.com.ekan.avaliacao.repository.UsuarioRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDetailsService = new UserDetailsServiceImpl(usuarioRepository);
    }

    @Test
    public void loadUserByUsernameUsuarioEncontradoTest() {
        String login = "emerson.dias";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin(login);
        UsuarioRole usuarioRole = new UsuarioRole();
        usuarioRole.setRole(Role.ROLE_USER);

        usuario.setRoles(new ArrayList<>());
        usuario.getRoles().add(usuarioRole);

        when(usuarioRepository.findByLogin(login)).thenReturn(Optional.of(usuario));
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        assertEquals(usuario.getLogin(), userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameUsuarioNaoEncontradoTest() {
        String login = "nonExistentUser";
        when(usuarioRepository.findByLogin(login)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> userDetailsService.loadUserByUsername(login));
    }

    @Test
    public void loadUserByUsernameUsuarioComRolesNulasTest() {
        String login = "userWithNullRoles";
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setLogin(login);
        usuario.setRoles(null);

        when(usuarioRepository.findByLogin(login)).thenReturn(Optional.of(usuario));
        assertThrows(NullPointerException.class, () -> userDetailsService.loadUserByUsername(login));
    }
}