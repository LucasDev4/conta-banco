package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Usuario;

public record UsuarioResponseDTO(
  Long id,
  String nome,
  String email
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
