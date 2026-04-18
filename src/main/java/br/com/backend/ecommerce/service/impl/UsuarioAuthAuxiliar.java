package br.com.backend.ecommerce.service.impl;

import br.com.backend.ecommerce.exception.NotFoundException;
import br.com.backend.ecommerce.model.Usuario;
import br.com.backend.ecommerce.repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsuarioAuthAuxiliar {

    public Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) throw new AccessDeniedException("Usuario não está logado, por favor faça login.");
        return (Usuario) authentication.getPrincipal();
    }
    public void validaUsuarioLogadoComEncontrado(UUID idUsuario){
        Usuario usuarioLogado = usuarioLogado();
        if(!idUsuario.equals(usuarioLogado.getId())){
            throw new AccessDeniedException("Acesso não autorizado a informações.");
        }
    }
    public Usuario encontrarUsuario(UsuarioRepository repository,UUID idUsuario){
        return repository.findById(idUsuario).orElseThrow(() -> new NotFoundException("Usuario não encontrado."));
    }
}
