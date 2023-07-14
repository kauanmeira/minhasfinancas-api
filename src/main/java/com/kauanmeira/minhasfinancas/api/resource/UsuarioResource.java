package com.kauanmeira.minhasfinancas.api.resource;

import com.kauanmeira.minhasfinancas.api.dto.UsuarioDTO;
import com.kauanmeira.minhasfinancas.exceptions.RegraNegocioException;
import com.kauanmeira.minhasfinancas.model.entities.Usuario;
import com.kauanmeira.minhasfinancas.model.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

   private UsuarioService service;

   public UsuarioResource(UsuarioService service){
      this.service = service;
   }
   @PostMapping
   public ResponseEntity salvar(@RequestBody UsuarioDTO dto){

      Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

      try{
         Usuario usuarioSalvo = service.salvarUsuario(usuario);
         return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
      }catch (RegraNegocioException e){
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }
}
