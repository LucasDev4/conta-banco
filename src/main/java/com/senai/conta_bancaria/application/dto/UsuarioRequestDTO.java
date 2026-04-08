package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotEmpty
        String nome,
        @Email
        @NotEmpty
        String email,
        @NotEmpty
        String senha,
        @NotEmpty
        String cpf
){
    public Usuario toEntity(){
        return Usuario.builder()
                .nome(this.nome)
                .email(this.email)
                .cpf(this.cpf)
                .senha(this.senha)
                .ativo(true)
                .role(Role.USUARIO)
                .build();
    }

}
