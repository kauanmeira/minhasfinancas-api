package com.kauanmeira.minhasfinancas.services;

import com.kauanmeira.minhasfinancas.exceptions.ErroAutenticacao;
import com.kauanmeira.minhasfinancas.exceptions.RegraNegocioException;
import com.kauanmeira.minhasfinancas.model.entities.Usuario;
import com.kauanmeira.minhasfinancas.model.impl.UsuarioServiceImpl;
import com.kauanmeira.minhasfinancas.model.repositories.UsuarioRepository;
import com.kauanmeira.minhasfinancas.model.services.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    public void setUp() {
        service = new UsuarioServiceImpl(repository);
    }

    @Test
    public void deveAutenticarUmUsuarioComSucesso() {
        //cenario
        String email = "email";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //acao
        Usuario result = service.autenticar(email, senha);

        //verificacao
        Assertions.assertNotNull(result);

    }

    @Test
    public void deveLancarErroQuandoSenhaForInvalida() {
        // cenário
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("usuario@gmail.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        // ação e verificação
        ErroAutenticacao exception = assertThrows(ErroAutenticacao.class, () -> service.autenticar("usuario@gmail.com", "123"));
        assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida");
    }


    @Test
    public void deveValidarEmail() {
        // cenário
        when(repository.existsByEmail(any(String.class))).thenReturn(false);

        // ação e verificação
        assertDoesNotThrow(() -> service.validarEmail("email@example.com"));
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
        // cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // ação e verificação
        Throwable exception = assertThrows(ErroAutenticacao.class, () -> service.autenticar("usuario@gmail.com", "senha"));
        assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o email informado.");
    }




    @Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        // cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        // ação e verificação
        assertThrows(RegraNegocioException.class, () -> service.validarEmail("email@example.com"));

    }
}
