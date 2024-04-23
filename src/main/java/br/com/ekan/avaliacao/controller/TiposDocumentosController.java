package br.com.ekan.avaliacao.controller;

import br.com.ekan.avaliacao.model.enums.TipoDocumento;
import br.com.ekan.avaliacao.service.TiposDocumentosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Tipos Documentos")
@RequestMapping(path = "/v1/tiposDocumentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TiposDocumentosController {

    private TiposDocumentosService service;

    public TiposDocumentosController(TiposDocumentosService service) {
        this.service = service;
    }

    @Operation(summary = "Listar os tipos de documentos")
    @GetMapping
    public ResponseEntity<List<TipoDocumento>>  buscarPeloId() {
        log.info("Request para listar os tipos de documentos");
        List<TipoDocumento> list = service.listar();

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        list.sort(Comparator.comparing(Enum::ordinal));
        return ResponseEntity.ok().body(list);
    }
}
