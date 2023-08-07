package com.kauanmeira.minhasfinancas.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String email;
    private String Nome;
    private String senha;
}
