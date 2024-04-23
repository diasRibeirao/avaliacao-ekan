package br.com.ekan.avaliacao.repository;

import br.com.ekan.avaliacao.model.enums.Role;
import br.com.ekan.avaliacao.model.UsuarioRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UsuarioRole, Long> {

    @Transactional(readOnly = true)
    Optional<UsuarioRole> findByRole(Role role);

}