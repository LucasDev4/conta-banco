package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.UsuarioRequestDTO;
import com.senai.conta_bancaria.application.dto.UsuarioResponseDTO;
import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.exception.UsuarioNaoEncontradoException;
import com.senai.conta_bancaria.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequestDTO) {

        return UsuarioResponseDTO.fromEntity(
                usuarioRepository.save(
                        usuarioRequestDTO.toEntity()
                )
        );
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream().map(
                        UsuarioResponseDTO::fromEntity
                ).toList();
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long id) {

        return UsuarioResponseDTO.fromEntity(usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id))
        );
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioAtualizado = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuarioAtualizado.setNome(usuarioRequestDTO.nome());
        usuarioAtualizado.setEmail(usuarioRequestDTO.email());
        usuarioAtualizado.setSenha(usuarioRequestDTO.senha());

        return UsuarioResponseDTO.fromEntity(usuarioRepository.save(usuarioAtualizado));
    }

    public void deletarUsuario(Long id) {

        if(!usuarioRepository.existsById(id)){
            throw new UsuarioNaoEncontradoException(id);
        }
        usuarioRepository.deleteById(id);
    }
}
