package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Usuario;

public record UsuarioReponseDTO(
        Long id,
        String nome,
        String email
) {

    public static UsuarioReponseDTO fromEntity(Usuario usuario) {
        return new UsuarioReponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

}
