package com.kauanmeira.minhasfinancas.model.entities;

import com.kauanmeira.minhasfinancas.model.enums.StatusLancamento;
import com.kauanmeira.minhasfinancas.model.enums.TipoLancamento;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.convert.Jsr310Converters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name ="descricao")
    private String descricao;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private LocalDate dataCadastro;



    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;
}
