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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    private UsuarioRepository repository;


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

    @Test
    public void deveSalvarUmUsuario() throws RegraNegocioException {
        // cenário
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder()
                .id(1l)
                .nome("usuario")
                .email("usuario@gmail.com")
                .senha("senha")
                .build();

        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        // ação
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

        // verificação
        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        assertThat(usuarioSalvo.getNome()).isEqualTo("usuario");
        assertThat(usuarioSalvo.getEmail()).isEqualTo("usuario@gmail.com");
        assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() throws RegraNegocioException {
        // cenário
        String email = "usuario@gmail.com";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        // ação
        Assertions.assertThrows(RegraNegocioException.class, () -> service.salvarUsuario(usuario));

        // verificação
        Mockito.verify(repository, Mockito.never()).save(usuario);
    }


}
