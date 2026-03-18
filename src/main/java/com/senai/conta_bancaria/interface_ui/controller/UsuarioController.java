package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.service.UsuarioService;
import com.senai.conta_bancaria.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario){
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.created(
                URI.create("usuario/"+usuarioCadastrado.getId())
                ).body(usuarioService.cadastrarUsuario(usuarioCadastrado));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuario(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){

        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
