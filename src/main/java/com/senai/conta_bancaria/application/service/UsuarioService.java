package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.UsuarioReponseDTO;
import com.senai.conta_bancaria.application.dto.UsuarioRequestDTO;
import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioReponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequestDTO) {

        return UsuarioReponseDTO.fromEntity(
                usuarioRepository.save(
                        usuarioRequestDTO.toEntity()));
    }

    public List<UsuarioReponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(
                   UsuarioReponseDTO::fromEntity
        ).toList();
    }

    public UsuarioReponseDTO buscarUsuarioPorId(Long id) {
        return UsuarioReponseDTO.fromEntity(usuarioRepository.findById(id).get());
    }


    public UsuarioReponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioAtualizado = usuarioRepository.findById(id).get();

            usuarioAtualizado.setNome(usuarioRequestDTO.nome());
            usuarioAtualizado.setEmail(usuarioRequestDTO.email());
            usuarioAtualizado.setSenha(usuarioRequestDTO.senha());

            return UsuarioReponseDTO.fromEntity(usuarioRepository.save(usuarioAtualizado));
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
