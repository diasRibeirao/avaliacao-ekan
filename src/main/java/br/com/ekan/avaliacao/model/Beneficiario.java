package br.com.ekan.avaliacao.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "BENEFICIARIOS")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Beneficiario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME", length = 80)
	private String nome;

	@Column(name = "TELEFONE", length = 11)
	private String telefone;

	@Column(name = "DATA_NASCIMENTO")
	private LocalDate dataNascimento;

	@Column(name = "DATA_INCLUSAO", updatable = false)
	private LocalDateTime dataInclusao;

	@Column(name = "DATA_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "BENEFICIARIO_ID")
	private List<Documento> documentos;

	public Beneficiario() {
		
	}

	public Beneficiario(Long id, String nome, String telefone, LocalDate dataNascimento) {
		this(nome, telefone, dataNascimento);
		this.id = id;
	}
	
	public Beneficiario(String nome, String telefone, LocalDate dataNascimento) {
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
	}

}
