package com.senai.conta_bancaria.infrastructure.config;

import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.enums.Role;
import com.senai.conta_bancaria.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sistema.admin.email}")
    private String adminEmail;

    @Value("${sistema.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) {
        usuarioRepository.findByEmail(adminEmail).ifPresentOrElse(
                usuario -> {
                    if (!usuario.isAtivo()) {
                        usuario.setAtivo(true);
                        usuarioRepository.save(usuario);
                    }
                },
                () -> {
                    Usuario admin = Usuario.builder()
                            .nome("Administrador Provisório")
                            .email(adminEmail)
                            .cpf("000.000.000-00")
                            .senha(passwordEncoder.encode(adminSenha))
                            .role(Role.ADMIN)
                            .build();
                    usuarioRepository.save(admin);
                    System.out.println("Usuário admin provisório criado: " + adminEmail);
                }
        );
    }
}
