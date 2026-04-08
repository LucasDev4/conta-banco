package com.senai.conta_bancaria.interface_ui.controller;


import com.senai.conta_bancaria.application.dto.UsuarioRequestDTO;
import com.senai.conta_bancaria.application.dto.UsuarioResponseDTO;
import com.senai.conta_bancaria.application.service.UsuarioService;
import com.senai.conta_bancaria.domain.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;



@Tag(name = "Usuarios", description = "Cadastro de Usuários")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;


    @Operation(
            summary = "Cadastrar um novo Usuário",
            description = "Adiciona um novo Usuário à base",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UsuarioRequestDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "nome": "Rafael Pinheiro Costa",
                                          "email": "rafael@email.com",
                                          "senha": "xxxxx"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
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
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioRequestDTO);
        return ResponseEntity.created(
                URI.create("/usuario/" + usuarioCadastrado.id())
        ).body(usuarioCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuario() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
