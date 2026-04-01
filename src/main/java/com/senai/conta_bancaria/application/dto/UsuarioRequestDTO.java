package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotEmpty
        String nome,
        @Email
        String email,
       @NotEmpty
        String senha) {

    public Usuario toEntity(){

        Usuario usuarioCadastrado = new Usuario();

        usuarioCadastrado.setNome(nome);
        usuarioCadastrado.setEmail(email);
        usuarioCadastrado.setSenha(senha);

        return usuarioCadastrado;
    }

}
