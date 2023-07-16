package com.kauanmeira.minhasfinancas.model.services;

import com.kauanmeira.minhasfinancas.exceptions.RegraNegocioException;
import com.kauanmeira.minhasfinancas.model.entities.Usuario;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UsuarioService {
    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario) throws RegraNegocioException;

    void validarEmail(String email) throws RegraNegocioException;

    Optional<Usuario> obterPorId(Long id);
}
