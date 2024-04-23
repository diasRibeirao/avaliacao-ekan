package br.com.ekan.avaliacao.service;

import br.com.ekan.avaliacao.model.Beneficiario;
import br.com.ekan.avaliacao.model.Documento;
import br.com.ekan.avaliacao.repository.BeneficiarioRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BeneficiarioServiceTest {

    @InjectMocks
    private BeneficiarioService beneficiarioService;

    @Mock
    private BeneficiarioRepository beneficiarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listarComPaginacaoTest() {
        List<Beneficiario> beneficiarios = new ArrayList<>();
        PageImpl<Beneficiario> page = new PageImpl<>(beneficiarios);
        Pageable paginacao = mock(Pageable.class);

        when(beneficiarioRepository.findAll(paginacao)).thenReturn(page);

        List<Beneficiario> result = beneficiarioService.listarComPaginacao(paginacao);

        assertEquals(beneficiarios, result);
    }

    @Test
    public void buscarPeloIdBeneficiarioEncontradoTest() {
        Long id = 1L;
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setId(id);

        when(beneficiarioRepository.findById(id)).thenReturn(Optional.of(beneficiario));

        Beneficiario result = beneficiarioService.buscarPeloId(id);

        assertEquals(beneficiario, result);
    }

    @Test
    public void BuscarPeloIdBeneficiarioNaoEncontradoTest() {
        Long id = 1L;

        when(beneficiarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> beneficiarioService.buscarPeloId(id));
    }

    @Test
    public void listarDocumentosPeloIdBeneficiarioTest() {
        Long idBeneficiario = 1L;
        List<Documento> documentos = new ArrayList<>();

        when(beneficiarioRepository.listarDocumentosPeloIdBeneficiario(idBeneficiario)).thenReturn(documentos);

        List<Documento> result = beneficiarioService.listarDocumentosPeloIdBeneficiario(idBeneficiario);

        assertEquals(documentos, result);
    }

    @Test
    public void cadastrarTest() {
        Beneficiario beneficiario = new Beneficiario();

        when(beneficiarioRepository.save(beneficiario)).thenReturn(beneficiario);

        Beneficiario result = beneficiarioService.cadastrar(beneficiario);

        assertEquals(beneficiario, result);
    }

    @Test
    public void atualizarBeneficiarioEncontradoTest() {
        Long id = 1L;
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setId(id);

        when(beneficiarioRepository.findById(id)).thenReturn(Optional.of(beneficiario));
        when(beneficiarioRepository.save(beneficiario)).thenReturn(beneficiario);

        Beneficiario result = beneficiarioService.atualizar(beneficiario);

        assertEquals(beneficiario, result);
    }

    @Test
    public void atualizarBeneficiarioNaoEncontradoTest() {
        Long id = 1L;
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setId(id);

        when(beneficiarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> beneficiarioService.atualizar(beneficiario));
    }

    @Test
    public void removerBeneficiarioEncontradoTest() {
        Long id = 1L;
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setId(id);

        when(beneficiarioRepository.findById(id)).thenReturn(Optional.of(beneficiario));

        beneficiarioService.remover(id);

        verify(beneficiarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void removerBeneficiarioNaoEncontradoTest() {
        Long id = 1L;

        when(beneficiarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> beneficiarioService.remover(id));
    }
}
