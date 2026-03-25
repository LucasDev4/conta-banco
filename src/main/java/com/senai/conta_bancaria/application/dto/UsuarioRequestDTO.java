package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Usuario;

public record UsuarioRequestDTO(String nome, String email, String senha) {

    public Usuario toEntity(){

        Usuario usuarioCadastrado = new Usuario();

        usuarioCadastrado.setNome(nome);
        usuarioCadastrado.setEmail(email);
        usuarioCadastrado.setSenha(senha);

        return usuarioCadastrado;
    }

}
