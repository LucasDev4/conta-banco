package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Conta;

public record TransferenciaResponseDTO(
        Long valorTransferencia,
        Long saqueId,
        Long depositoId,
        Long saldoFinalOrigem,
        Long saldoFinalDestino
) {

    public static TransferenciaResponseDTO fromEntity(
            Conta contaOrigem,
            Conta contaDestino,
            Long valorTransferencia
    ) {
        return new TransferenciaResponseDTO(
                valorTransferencia,
                contaOrigem.getId(),
                contaDestino.getId(),
                contaOrigem.getSaldo(),
                contaDestino.getSaldo()
        );
    }
}