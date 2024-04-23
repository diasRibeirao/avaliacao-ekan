package br.com.ekan.avaliacao.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME", length = 80)
	private String nome;

	@Column(name = "LOGIN", length = 20)
	private String login;

	@Column(name = "SENHA", length = 120)
	private String senha;

	@Column(name = "DATA_INCLUSAO", updatable = false)
	private LocalDateTime dataInclusao;

	@Column(name = "DATA_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USUARIO_ID")
	private List<UsuarioRole> roles;

	public Usuario() {

	}

	public Usuario(Long id, String nome, String login, String senha) {
		this(nome, login, senha);
		this.id = id;
	}

	public Usuario(String nome, String login, String senha) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}

}
