package br.com.ekan.avaliacao.controller;

import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioAtualizarDTO;
import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.entrada.DocumentoAtualizarDTO;
import br.com.ekan.avaliacao.model.dto.entrada.DocumentoCadastrarDTO;
import br.com.ekan.avaliacao.model.enums.TipoDocumento;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "emerson.dias", password = "asd123qwe!", roles = "ADMIN")
class BeneficiariosControllerIntegrationTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void listarComPaginacaoNaoEncontradoTest() throws Exception {
        mockMvc.perform(get("/v1/beneficiarios?page=0&size=1&sort=nome")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(2)
    public void cadastrarTest() throws Exception {
        DocumentoCadastrarDTO documentoDTO = new DocumentoCadastrarDTO();
        documentoDTO.setTipoDocumento(TipoDocumento.RG.name());
        documentoDTO.setDescricao("33333333");

        List<DocumentoCadastrarDTO> documentosDTO = new ArrayList<>();
        documentosDTO.add(documentoDTO);

        var cadastrarDTO = new BeneficiarioCadastrarDTO();
        cadastrarDTO.setNome("Miguel Oliveira");
        cadastrarDTO.setDataNascimento("15/05/1990");
        cadastrarDTO.setTelefone("(99) 9999-9999");
        cadastrarDTO.setDocumentos(documentosDTO);

        mockMvc.perform(post("/v1/beneficiarios")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(cadastrarDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void listarComPaginacaoTest() throws Exception {
        mockMvc.perform(get("/v1/beneficiarios?page=0&size=1&sort=nome")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].nome").exists())
                .andExpect(jsonPath("$[0].telefone").exists());
    }

    @Test
    @Order(4)
    public void buscarPeloIdTest() throws Exception {
        mockMvc.perform(get("/v1/beneficiarios/1")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.telefone").exists());
    }

    @Test
    @Order(5)
    public void listarDocumentosPeloIdBeneficiarioTest() throws Exception {
        mockMvc.perform(get("/v1/beneficiarios/1/documentos")
                 .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].tipoDocumento").exists())
                .andExpect(jsonPath("$[0].descricao").exists());
    }



    @Test
    @Order(6)
    public void atualizarTest() throws Exception {
        DocumentoAtualizarDTO documentoDTO = new DocumentoAtualizarDTO();
        documentoDTO.setId(1L);
        documentoDTO.setTipoDocumento(TipoDocumento.RG.name());
        documentoDTO.setDescricao("33333333");

        List<DocumentoAtualizarDTO> documentosDTO = new ArrayList<>();
        documentosDTO.add(documentoDTO);

        var cadastrarDTO = new BeneficiarioAtualizarDTO();
        cadastrarDTO.setNome("Miguel Oliveira");
        cadastrarDTO.setDataNascimento("15/05/1990");
        cadastrarDTO.setTelefone("(99) 9999-9999");
        cadastrarDTO.setDocumentos(documentosDTO);

        mockMvc.perform(put("/v1/beneficiarios/1")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(cadastrarDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(7)
    public void removerBeneficiarioTest() throws Exception {
        mockMvc.perform(delete("/v1/beneficiarios/1")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    public void listarDocumentosPeloIdBeneficiarioNaoEncontradoTest() throws Exception {
        mockMvc.perform(get("/v1/beneficiarios/1/documentos")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    public void buscarPeloIdNaoEncontradoTest() throws Exception {
        mockMvc.perform(get("/v1/beneficiarios/1")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

}