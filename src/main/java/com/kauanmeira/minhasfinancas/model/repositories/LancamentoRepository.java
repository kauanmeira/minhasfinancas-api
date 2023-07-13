package com.kauanmeira.minhasfinancas.model.repositories;

import com.kauanmeira.minhasfinancas.model.entities.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
