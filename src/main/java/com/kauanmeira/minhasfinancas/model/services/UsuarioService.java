package com.kauanmeira.minhasfinancas.model.services;

import com.kauanmeira.minhasfinancas.exceptions.RegraNegocioException;
import com.kauanmeira.minhasfinancas.model.entities.Usuario;

public interface UsuarioService {
    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario) throws RegraNegocioException;

    void validarEmail(String email) throws RegraNegocioException;
}
