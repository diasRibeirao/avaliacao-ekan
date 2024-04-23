package br.com.ekan.avaliacao.model.dto.saida;

import lombok.Builder;

import java.util.List;

@Builder
public record UsuarioDTO(
		Long id, 
		String nome,
		String login,
		List<UsuarioRoleDTO> roles
) {}
