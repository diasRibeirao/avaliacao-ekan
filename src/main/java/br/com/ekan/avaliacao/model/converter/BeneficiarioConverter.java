package br.com.ekan.avaliacao.model.converter;

import br.com.ekan.avaliacao.model.Beneficiario;
import br.com.ekan.avaliacao.model.Documento;
import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioAtualizarDTO;
import br.com.ekan.avaliacao.model.dto.saida.BeneficiarioDTO;
import br.com.ekan.avaliacao.model.dto.saida.DocumentoDTO;
import br.com.ekan.avaliacao.utils.EkanUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeneficiarioConverter {

    private DocumentoConverter documentoConverter;

    public BeneficiarioConverter(DocumentoConverter documentoConverter) {
        this.documentoConverter = documentoConverter;
    }

    public BeneficiarioDTO parse(Beneficiario origin) {
        if (origin == null)
            return null;

        return new BeneficiarioDTO(
                origin.getId(),
                origin.getNome(),
                origin.getTelefone(),
                origin.getDataNascimento(),
                documentoConverter.parse(origin.getDocumentos())
        );
    }

    public List<BeneficiarioDTO> parse(List<Beneficiario> origin) {
        if (origin == null)
            return Collections.emptyList();

        return origin.stream().map(this::parse).collect(Collectors.toList());
    }

    public Beneficiario parseBeneficiarioCadastrarDTO(BeneficiarioCadastrarDTO origin) {
        if (origin == null)
            return null;

        var beneficiario = new Beneficiario(
                origin.getNome(),
                origin.getTelefoneSomenteNumeros(),
                getDataNascimento(origin.getDataNascimento())
        );
        beneficiario.setDocumentos(documentoConverter.parseDocumentoCadastrarDTO(
                origin.getDocumentos(), beneficiario)
        );
        beneficiario.setDataInclusao(EkanUtils.dataAtual());
        return beneficiario;
    }

    public Beneficiario parseBeneficiarioAtualizarDTO(BeneficiarioAtualizarDTO origin) {
        if (origin == null)
            return null;

        var beneficiario = new Beneficiario(
                origin.getNome(),
                origin.getTelefoneSomenteNumeros(),
                getDataNascimento(origin.getDataNascimento())
        );
        beneficiario.setDocumentos(documentoConverter.parseDocumentoAtualizarDTO(origin.getDocumentos(), beneficiario));
        beneficiario.setDataAtualizacao(EkanUtils.dataAtual());
        return beneficiario;
    }

    public List<DocumentoDTO> parseDocumento(List<Documento> documentos) {
        return documentoConverter.parse(documentos);
    }

    private LocalDate getDataNascimento(String dataNascimento) {
        try {
            return EkanUtils.stringToLocalDate(dataNascimento);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data de Nascimento Inválida: " + dataNascimento, dataNascimento, 0);
        }
    }
}
