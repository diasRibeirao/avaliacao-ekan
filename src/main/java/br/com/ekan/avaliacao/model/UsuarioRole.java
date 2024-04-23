package br.com.ekan.avaliacao.model;

import br.com.ekan.avaliacao.model.enums.Role;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIOS_ROLE")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UsuarioRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USUARIO_ID")
	private Usuario usuario;

	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "DATA_INCLUSAO", updatable = false)
	private LocalDateTime dataInclusao;

	@Column(name = "DATA_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	public UsuarioRole() {

	}

	public UsuarioRole(Long id, Usuario usuario, Role role) {
		this(usuario, role);
		this.id = id;
	}

	public UsuarioRole(Usuario usuario, Role role) {
		this.usuario = usuario;
		this.role = role;
	}
}
