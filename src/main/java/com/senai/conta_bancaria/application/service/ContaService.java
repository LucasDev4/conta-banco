package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.*;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.exception.UsuarioNaoEncontradoException;
import com.senai.conta_bancaria.domain.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {
    @Autowired
    ContaRepository contaRepository;

    public ContaResponseDTO cadastrarUsuario(ContaRequestDTO contaRequestDTO) {

        return ContaResponseDTO.fromEntity(
                contaRepository.save(
                        contaRequestDTO.toEntity()
                )
        );
    }

    public List<ContaResponseDTO> listarUsuarios() {
        return contaRepository.findAll()
                .stream().map(
                        ContaResponseDTO::fromEntity
                ).toList();
    }

    public ContaResponseDTO buscarUsuarioPorId(Long id) {

        return ContaResponseDTO.fromEntity(contaRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id))
        );
    }

    public ContaResponseDTO atualizarUsuario(Long id, ContaRequestDTO contaRequestDTO) {
        Conta contaAtualizada = contaRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        contaAtualizada.setAgencia(contaRequestDTO.agencia());
        contaAtualizada.setNumero(contaRequestDTO.numero());
        contaAtualizada.setTipo(contaRequestDTO.tipo());
        contaAtualizada.setSaldo(contaRequestDTO.saldo());

        return ContaResponseDTO.fromEntity(contaRepository.save(contaAtualizada));
    }

    public void deletarUsuario(Long id) {

        if(!contaRepository.existsById(id)){
            throw new UsuarioNaoEncontradoException(id);
        }
        contaRepository.deleteById(id);
    }

    public ContaResponseDTO sacar(SaqueDTO saqueDTO) {

        Conta conta = contaRepository.findById(saqueDTO.contaId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(saqueDTO.contaId()));

        if (!conta.isAtivo()) {
            throw new RuntimeException("Conta inativa");
        }


        if (saqueDTO.valor() <= 0) {
            throw new RuntimeException("Valor inválido para saque");
        }

        if (conta.getSaldo() < saqueDTO.valor()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        conta.setSaldo(conta.getSaldo() - saqueDTO.valor());

        return ContaResponseDTO.fromEntity(contaRepository.save(conta));
    }

    public ContaResponseDTO deposito(DepositoDTO depositoDTO){
        Conta conta = contaRepository.findById((depositoDTO.contaId()))
                .orElseThrow(() -> new UsuarioNaoEncontradoException(depositoDTO.contaId()));

        if (!conta.isAtivo()){
            throw  new RuntimeException("Conta não está ativa mano");
        }
        if (conta.getSaldo() < depositoDTO.valor()){
            throw  new RuntimeException("Saldo insuficiente");
        }
        conta.setSaldo(conta.getSaldo() + depositoDTO.valor());

        return ContaResponseDTO.fromEntity(contaRepository.save(conta));
    }

    @Transactional
    public TransferenciaResponseDTO transferencia(TransferenciaDTO transferenciaDTO){
        Conta contaSaque = contaRepository.findById((transferenciaDTO.saqueId()))
                .orElseThrow(() -> new UsuarioNaoEncontradoException(transferenciaDTO.saqueId()));

        Conta contaDeposito = contaRepository.findById((transferenciaDTO.depositoId()))
                .orElseThrow(() -> new UsuarioNaoEncontradoException(transferenciaDTO.depositoId()));

        if (contaSaque.getSaldo() < transferenciaDTO.valorTransferencia()){
            throw new RuntimeException("Saldo insuficiente da conta de origem");
        }

        if (contaSaque.getId().equals(contaDeposito.getId())){
            throw new RuntimeException("Não pode fazer transferencia para a mesma conta");
        }

        if (!contaSaque.isAtivo() || !contaDeposito.isAtivo()){
            throw  new RuntimeException("Conta não está ativa mano");
        }

        contaSaque.setSaldo(contaSaque.getSaldo() - transferenciaDTO.valorTransferencia());
        contaDeposito.setSaldo(contaDeposito.getSaldo() + transferenciaDTO.valorTransferencia());

        return TransferenciaResponseDTO.fromEntity(
                contaSaque,
                contaDeposito,
                transferenciaDTO.valorTransferencia()
        );
    }

}