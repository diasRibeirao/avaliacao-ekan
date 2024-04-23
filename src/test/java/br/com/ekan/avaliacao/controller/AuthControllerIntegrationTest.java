package br.com.ekan.avaliacao.controller;

import br.com.ekan.avaliacao.model.dto.entrada.LoginDTO;
import br.com.ekan.avaliacao.model.dto.entrada.UsuarioCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.entrada.UsuarioRoleCadastrarDTO;
import br.com.ekan.avaliacao.utils.TestUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerIntegrationTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void registrarTest() throws Exception {
        var role = new UsuarioRoleCadastrarDTO();
        role.setRole("ROLE_ADMIN");
        var roles = new ArrayList<UsuarioRoleCadastrarDTO>();
        roles.add(role);

        var cadastrarDTO = new UsuarioCadastrarDTO();
        cadastrarDTO.setNome("Emerson Dias de Oliveira");
        cadastrarDTO.setLogin("emerson.dias");
        cadastrarDTO.setSenha("asd123qwe!");
        cadastrarDTO.setRoles(roles);

        mockMvc.perform(post("/v1/auth/registrar")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(cadastrarDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensagem").value("Usuário registrado com sucesso!"));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "emerson.dias", password = "asd123qwe!", roles = "ADMIN")
    public void buscarPeloIdTest() throws Exception {
        mockMvc.perform(get("/v1/auth/1/usuarios")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Emerson Dias de Oliveira"))
                .andExpect(jsonPath("$.login").value("emerson.dias"));
    }

    @Test
    @Order(3)
    public void buscarPeloIdNaoEncontratoTest() throws Exception {
        mockMvc.perform(get("/v1/auth/999/usuarios")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void loginTest() throws Exception {
        var loginDTO = LoginDTO
                .builder()
                .login("emerson.dias")
                .senha("asd123qwe!")
                .build();

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    @Order(5)
    public void loginErroTest() throws Exception {
        var loginDTO = LoginDTO
                .builder()
                .login("emerson.dois")
                .senha("asd123qwe!")
                .build();

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(loginDTO)))
                .andExpect(status().isNotFound());
    }

}
