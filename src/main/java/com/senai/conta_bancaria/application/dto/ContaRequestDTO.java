package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ContaRequestDTO(
    @NotEmpty
    String agencia,
    @NotEmpty
    String numero,
    @NotEmpty
    String tipo,
    @NotNull
    Long saldo,
    @NotNull
    Boolean ativo
){
    public Conta toEntity(){
        return Conta.builder()
                .agencia(this.agencia)
                .numero(this.numero)
                .saldo(this.saldo)
                .tipo(this.tipo)
                .ativo(true)
                .build();
    }

}
