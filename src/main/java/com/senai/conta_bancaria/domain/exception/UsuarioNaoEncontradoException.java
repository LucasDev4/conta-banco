package com.senai.conta_bancaria.domain.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Long idUsuario) {
        super("Usuario com o id "+idUsuario+ " não foi  encontrado");
    }
}
