package com.kauanmeira.minhasfinancas.services;

import com.kauanmeira.minhasfinancas.exceptions.RegraNegocioException;
import com.kauanmeira.minhasfinancas.model.entities.Usuario;
import com.kauanmeira.minhasfinancas.model.impl.UsuarioServiceImpl;
import com.kauanmeira.minhasfinancas.model.repositories.UsuarioRepository;
import com.kauanmeira.minhasfinancas.model.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @MockBean
    private UsuarioRepository repository;

    @BeforeEach
    public void setUp(){
        service = new UsuarioServiceImpl(repository);
    }

    @Test
    public void deveValidarEmail() {
        // cenário
        when(repository.existsByEmail(any(String.class))).thenReturn(false);

        // ação e verificação
        assertDoesNotThrow(() -> service.validarEmail("email@example.com"));
    }

    @Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        // cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        // ação e verificação
        assertThrows(RegraNegocioException.class, () -> service.validarEmail("email@example.com"));
    }
}
