package br.com.ekan.avaliacao.repository;

import br.com.ekan.avaliacao.model.Beneficiario;
import br.com.ekan.avaliacao.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT d FROM Documento d WHERE d.beneficiario.id = :id ORDER BY d.id")
    List<Documento> listarDocumentosPeloIdBeneficiario(Long id);

    @Transactional(readOnly = true)
    Beneficiario findByTelefone(String telefone);

}