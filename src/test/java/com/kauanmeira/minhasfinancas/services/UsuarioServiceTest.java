package com.kauanmeira.minhasfinancas.services;

import com.kauanmeira.minhasfinancas.exceptions.RegraNegocioException;
import com.kauanmeira.minhasfinancas.model.entities.Usuario;
import com.kauanmeira.minhasfinancas.model.repositories.UsuarioRepository;
import com.kauanmeira.minhasfinancas.model.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveValidarEmail() {
        assertDoesNotThrow(() -> {
            //cenario
            repository.deleteAll();

            //acao
            service.validarEmail("email@email.com");
        });
    }

    @Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        assertThrows(RegraNegocioException.class, () -> {
            //cenario
            Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
            repository.save(usuario);

            //acao
            service.validarEmail("email@email.com");

        });

    }
}