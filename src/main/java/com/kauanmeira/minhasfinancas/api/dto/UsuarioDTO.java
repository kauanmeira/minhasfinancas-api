package com.kauanmeira.minhasfinancas.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDTO {
    private String email;
    private String Nome;
    private String senha;
}
