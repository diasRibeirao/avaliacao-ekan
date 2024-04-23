package br.com.ekan.avaliacao.model;

import br.com.ekan.avaliacao.model.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "DOCUMENTOS")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "BENEFICIARIO_ID")
	private Beneficiario beneficiario;

	@Column(name = "TIPO_DOCUMENTO")
	@Enumerated(EnumType.STRING)
	private TipoDocumento tipoDocumento;

	@Column(name = "DESCRICAO", length = 20)
	private String descricao;

	@Column(name = "DATA_INCLUSAO", updatable = false)
	private LocalDateTime dataInclusao;

	@Column(name = "DATA_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	public Documento() {

	}

	public Documento(Long id, Beneficiario beneficiario, TipoDocumento tipoDocumento, String descricao) {
		this(beneficiario, tipoDocumento, descricao);
		this.id = id;
	}
	
	public Documento(Beneficiario beneficiario, TipoDocumento tipoDocumento, String descricao) {
		this.beneficiario = beneficiario;
		this.tipoDocumento = tipoDocumento;
		this.descricao = descricao;
	}
}
