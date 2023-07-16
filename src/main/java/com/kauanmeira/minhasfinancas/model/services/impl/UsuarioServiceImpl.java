package com.kauanmeira.minhasfinancas.model.services.impl;

import com.kauanmeira.minhasfinancas.exceptions.ErroAutenticacao;
import com.kauanmeira.minhasfinancas.exceptions.RegraNegocioException;
import com.kauanmeira.minhasfinancas.model.entities.Lancamento;
import com.kauanmeira.minhasfinancas.model.entities.Usuario;
import com.kauanmeira.minhasfinancas.model.repositories.UsuarioRepository;
import com.kauanmeira.minhasfinancas.model.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository repository;


    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha)
    {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if(!usuario.isPresent()){
            throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
        }
        if(!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) throws RegraNegocioException {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) throws RegraNegocioException {
       boolean existe = repository.existsByEmail(email);
       if(existe){
           throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
       }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }

    public void validar(Lancamento lancamento) throws RegraNegocioException {

        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Informe uma descrição válida.");
        }
        if(lancamento.getMes() == null || lancamento.getMes()<1 || lancamento.getMes() > 12){
            throw new RegraNegocioException("Informe um mês válido.");
        }
        if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
            throw new RegraNegocioException("Informe um ano válido.");
        }
        if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw new RegraNegocioException("Informe um Usuário.");
        }
        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) <1){
            throw new RegraNegocioException("Informa um valor válido.");
        }
        if (lancamento.getTipo() == null){
            throw new RegraNegocioException("Informe um tipo de lançamento.");
        }

    }
}
