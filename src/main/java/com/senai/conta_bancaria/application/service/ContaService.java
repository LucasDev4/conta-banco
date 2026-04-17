package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ContaRequestDTO;
import com.senai.conta_bancaria.application.dto.ContaResponseDTO;
import com.senai.conta_bancaria.application.dto.SaqueDTO;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.exception.UsuarioNaoEncontradoException;
import com.senai.conta_bancaria.domain.repository.ContaRepository;
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
}