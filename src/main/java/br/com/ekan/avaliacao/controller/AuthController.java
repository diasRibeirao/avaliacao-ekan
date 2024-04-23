package br.com.ekan.avaliacao.controller;

import br.com.ekan.avaliacao.model.converter.UsuarioConverter;
import br.com.ekan.avaliacao.model.dto.entrada.LoginDTO;
import br.com.ekan.avaliacao.model.dto.entrada.UsuarioCadastrarDTO;
import br.com.ekan.avaliacao.model.dto.saida.MensagemDTO;
import br.com.ekan.avaliacao.model.dto.saida.TokenDTO;
import br.com.ekan.avaliacao.model.dto.saida.UsuarioDTO;
import br.com.ekan.avaliacao.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Autenticação")
@RequestMapping(path = "/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private AuthService service;

    private UsuarioConverter converter;

    public AuthController(AuthService service, UsuarioConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza o login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO login) {
        log.info("Request para realizar o login: {}", login.toString());
        final var token = service.login(login.getLogin(), login.getSenha());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/registrar")
    @Operation(summary = "Realiza o registro de um usuário")
    public ResponseEntity<MensagemDTO> registrar(@Valid @RequestBody UsuarioCadastrarDTO cadastrar) {
        log.info("Request para registrar um usuário: {}", cadastrar.toString());
        service.registrar(converter.parseUsuarioCadastrarDTO(cadastrar));

        var mensagem = MensagemDTO.builder()
                .mensagem("Usuário registrado com sucesso!")
                .build();

        return ResponseEntity.ok(mensagem);
    }

    @Operation(summary = "Buscar um usuário pelo seu id")
    @GetMapping(value = "/{id}/usuarios")
    public ResponseEntity<UsuarioDTO> buscarPeloId(@PathVariable Long id) {
        log.info("Request para buscar o usuário de id: {}", id);
        var beneficiario = converter.parse(service.buscarPeloId(id));
        return ResponseEntity.ok().body(beneficiario);
    }
}
