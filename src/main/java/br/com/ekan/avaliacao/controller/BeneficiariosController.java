package br.com.ekan.avaliacao.controller;

import br.com.ekan.avaliacao.model.Beneficiario;
import br.com.ekan.avaliacao.model.converter.BeneficiarioConverter;
import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioAtualizarDTO;
import br.com.ekan.avaliacao.model.dto.entrada.BeneficiarioCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.saida.BeneficiarioDTO;
import br.com.ekan.avaliacao.model.dto.saida.DocumentoDTO;
import br.com.ekan.avaliacao.service.BeneficiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Beneficiarios")
@RequestMapping(path = "/v1/beneficiarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class BeneficiariosController {

    private BeneficiarioService service;

    private BeneficiarioConverter converter;

    public BeneficiariosController(BeneficiarioService service, BeneficiarioConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    @Operation(summary = "Listar todos os beneficiários cadastrados")
    public ResponseEntity<List<BeneficiarioDTO>> listarComPaginacao(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        log.info("Request para listar todos os beneficiários cadastrados");
        List<BeneficiarioDTO> list = converter.parse(service.listarComPaginacao(paginacao));

        if (list.isEmpty()) {
            log.info("Nenhum beneficiário encontrado");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Buscar um beneficiário pelo seu id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BeneficiarioDTO> buscarPeloId(@PathVariable Long id) {
        log.info("Request para buscar o beneficiário de id: {}", id);
        var beneficiario = converter.parse(service.buscarPeloId(id));
        return ResponseEntity.ok().body(beneficiario);
    }

    @Operation(summary = "Listar todos os documentos de um beneficiário a partir de seu id")
    @GetMapping(value = "/{id}/documentos")
    public ResponseEntity<List<DocumentoDTO>> listarDocumentosPeloIdBeneficiario(@PathVariable Long id) {
        log.info("Request para Listar todos os documentos de um beneficiário a partir de seu id: {}", id);
        List<DocumentoDTO> list = converter.parseDocumento(
                service.listarDocumentosPeloIdBeneficiario(id)
        );

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Cadastrar um beneficiário junto com seus documentos")
    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody BeneficiarioCadastrarDTO cadastrar) {
        log.info("Request para Cadastrar um beneficiário junto com seus documentos: {}", cadastrar.toString());
        var beneficiario = converter.parseBeneficiarioCadastrarDTO(cadastrar);
        beneficiario = service.cadastrar(beneficiario);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(beneficiario.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualizar os dados cadastrais de um beneficiário")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar(@Valid @PathVariable Long id, @Valid @RequestBody BeneficiarioAtualizarDTO atualizar) {
        log.info("Request para Aatualizar os dados cadastrais de um beneficiário de id : {} - {}", atualizar.toString(), id);
        var beneficiarioSalvo = service.buscarPeloId(id);
        Beneficiario beneficiario = converter.parseBeneficiarioAtualizarDTO(atualizar);
        beneficiario.setId(beneficiarioSalvo.getId());
        service.atualizar(beneficiario);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover um beneficiário")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        log.info("Request para remover o beneficiário de id : {}", id);
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
