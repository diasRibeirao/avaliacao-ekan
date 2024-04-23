package br.com.ekan.avaliacao.model.dto.saida;


import br.com.ekan.avaliacao.model.enums.Role;

public record UsuarioRoleDTO(
        Long id,
        Role role
) {}
