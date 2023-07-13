package com.kauanmeira.minhasfinancas.model.repositories;

import com.kauanmeira.minhasfinancas.model.entities.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);
        // ação / execução
        boolean result = repository.existsByEmail("usuario@gmail.com");

        // verificação
        Assertions.assertTrue(result);
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        //cenario

        //ação /execução
        boolean result = repository.existsByEmail("usuario@gmail.com");

        //verificação
        Assertions.assertFalse(result);
    }
    @Test
    public void devePersistirUmUsuarioNaBaseDeDados(){
        //cenario
        Usuario usuario = criarUsuario();

        //acao
        Usuario usuarioSalvo = repository.save(usuario);

        //verificacao
        Assertions.assertNotNull(usuarioSalvo.getId());
    }
    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        //cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@gmail.com");
        Assertions.assertTrue(result.isPresent());

    }
    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase(){
        //cenario

        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@gmail.com");
        Assertions.assertFalse(result.isPresent());

    }
    public static Usuario criarUsuario(){
        return Usuario
                .builder()
                .nome("usuario")
                .email("usuario@gmail.com")
                .senha("senha")
                .build();
    }
}
