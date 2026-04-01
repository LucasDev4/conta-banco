package com.senai.conta_bancaria.domain.exception;

public class notFoundException extends RuntimeException {
    public notFoundException(Long idUsuario) {
        super("Usuario com o id "+idUsuario+" não foi encontrado");
    }
}
