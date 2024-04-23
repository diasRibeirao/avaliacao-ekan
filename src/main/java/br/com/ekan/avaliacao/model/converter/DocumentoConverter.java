package br.com.ekan.avaliacao.model.converter;

import br.com.ekan.avaliacao.model.Beneficiario;
import br.com.ekan.avaliacao.model.Documento;
import br.com.ekan.avaliacao.model.dto.entrada.DocumentoCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.entrada.DocumentoAtualizarDTO;
import br.com.ekan.avaliacao.model.dto.saida.DocumentoDTO;
import br.com.ekan.avaliacao.model.enums.TipoDocumento;
import br.com.ekan.avaliacao.utils.EkanUtils;
import java.util.Collections;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentoConverter {

    public DocumentoDTO parse(Documento origin) {
        if (origin == null)
            return null;

        return new DocumentoDTO(
                origin.getId(),
                origin.getTipoDocumento(),
                origin.getDescricao()
        );
    }

    public List<DocumentoDTO> parse(List<Documento> origin) {
        if (origin == null)
            return Collections.emptyList();

        return origin.stream().map(this::parse).collect(Collectors.toList());
    }

    public Documento parseDocumentoCadastrarDTO(DocumentoCadastrarDTO origin, Beneficiario beneficiario) {
        if (origin == null)
            return null;

        Documento documento = new Documento(
                beneficiario,
                TipoDocumento.toEnum(origin.getTipoDocumento()),
                origin.getDescricao()
        );
        documento.setDataInclusao(EkanUtils.dataAtual());
        return documento;
    }

    public List<Documento> parseDocumentoCadastrarDTO(List<DocumentoCadastrarDTO> origin, Beneficiario beneficiario) {
        return origin.stream().map(obj -> parseDocumentoCadastrarDTO(obj, beneficiario))
                .collect(Collectors.toList());
    }

    public List<Documento> parseDocumentoAtualizarDTO(List<DocumentoAtualizarDTO> origin, Beneficiario beneficiario) {
        return origin.stream().map(obj -> parseDocumentoAtualizarDTO(obj, beneficiario))
                .collect(Collectors.toList());
    }

    public Documento parseDocumentoAtualizarDTO(DocumentoAtualizarDTO origin, Beneficiario beneficiario) {
        if (origin == null)
            return null;

        Documento documento = new Documento(
                origin.getId(),
                beneficiario,
                TipoDocumento.toEnum(origin.getTipoDocumento()),
                origin.getDescricao()
        );
        if (documento.getId() == null || documento.getId().equals(0L)) {
            documento.setDataInclusao(EkanUtils.dataAtual());
        } else {
            documento.setDataAtualizacao(EkanUtils.dataAtual());
        }
        return documento;
    }
}
