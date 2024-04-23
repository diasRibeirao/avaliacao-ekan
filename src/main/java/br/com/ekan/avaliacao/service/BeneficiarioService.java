package br.com.ekan.avaliacao.service;

import br.com.ekan.avaliacao.model.Beneficiario;
import br.com.ekan.avaliacao.model.Documento;
import br.com.ekan.avaliacao.repository.BeneficiarioRepository;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiarioService {

    private BeneficiarioRepository repository;

    public BeneficiarioService(BeneficiarioRepository repository) {
        this.repository = repository;
    }

    public List<Beneficiario> listarComPaginacao(Pageable paginacao) {
        return repository.findAll(paginacao).getContent();
    }

    public Beneficiario buscarPeloId(Long id) {
        Optional<Beneficiario> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Beneficiário não encontrado! Id: " + id));
    }

    public List<Documento> listarDocumentosPeloIdBeneficiario(Long id) {
        return repository.listarDocumentosPeloIdBeneficiario(id);
    }

    @Transactional
    public Beneficiario cadastrar(Beneficiario beneficiario) {
        return repository.save(beneficiario);
    }

    @Transactional
    public Beneficiario atualizar(Beneficiario beneficiario) {
        buscarPeloId(beneficiario.getId());
        return repository.save(beneficiario);
    }

    public void remover(Long id) {
        buscarPeloId(id);
        repository.deleteById(id);
    }

}
