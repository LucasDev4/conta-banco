package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Conta;


public record ContaResponseDTO(
        Long id,
        String agecia,
        String numero

) {
    public static ContaResponseDTO fromEntity(Conta conta) {

        return new ContaResponseDTO(
                conta.getId(),
                conta.getAgencia(),
                conta.getNumero()

        );
    }

}