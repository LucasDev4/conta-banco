package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.UsuarioReponseDTO;
import com.senai.conta_bancaria.application.dto.UsuarioRequestDTO;
import com.senai.conta_bancaria.application.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioReponseDTO> cadastrarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioReponseDTO usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioRequestDTO);
        return ResponseEntity.created(
                URI.create("/usuario"+usuarioCadastrado.id())
                ).body(usuarioCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioReponseDTO>> listarUsuario(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioReponseDTO> buscarUsuarioPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioReponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){

        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
