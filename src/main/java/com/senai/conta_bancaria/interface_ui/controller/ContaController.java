package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.ContaRequestDTO;
import com.senai.conta_bancaria.application.dto.ContaResponseDTO;
import com.senai.conta_bancaria.application.dto.SaqueDTO;
import com.senai.conta_bancaria.application.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {
    @Autowired
    ContaService contaService;

    @Operation(
            summary = "Cadastrar uma nova conta",
            description = "Adiciona uma nova conta à base",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContaRequestDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "agencia": "Lucas",
                                          "numero": "12",
                                          "tipo": "Conta corrente"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Conta cadastrado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "saldo inválido", value = "\"saldo mínimo do serviço deve ser R$ 1,00 R$\""),
                                            @ExampleObject(name = "Conta Inexistente", value = "\"Sua Conta não existe, por favor entrar em contato com o suporte \""),
                                            @ExampleObject(name = "Conta Duplicada", value = "\"Sua foi duplicada\""),
                                            @ExampleObject(name = "", value = "\".\"")
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ContaResponseDTO> cadastrarUsuario(@Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        ContaResponseDTO contaResponseDTO = contaService.cadastrarUsuario(contaRequestDTO);
        return ResponseEntity.created(
                URI.create("/conta/" + contaResponseDTO.id())
        ).body(contaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> listarUsuario() {
        return ResponseEntity.ok(contaService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody ContaRequestDTO contaRequestDTO
    ) {
        return ResponseEntity.ok(contaService.atualizarUsuario(id, contaRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        contaService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/saque")
    public ResponseEntity<ContaResponseDTO> sacar(@RequestBody SaqueDTO saqueDTO) {
        return ResponseEntity.ok(contaService.sacar(saqueDTO));
    }
}