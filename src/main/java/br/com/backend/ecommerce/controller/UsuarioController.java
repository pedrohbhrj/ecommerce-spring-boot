package br.com.backend.ecommerce.controller;

import br.com.backend.ecommerce.dto.request.EnderecoRequest;
import br.com.backend.ecommerce.dto.response.EnderecoResponse;
import br.com.backend.ecommerce.exception.ApiResponse;
import br.com.backend.ecommerce.service.interf.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/usuario")
@RestController
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @PostMapping("/endereco/{idUsuario}")
    public ResponseEntity<ApiResponse<EnderecoResponse>> criarEndereco(@PathVariable UUID idUsuario,@RequestBody @Valid EnderecoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarEndereco(idUsuario,request));
    }

    @PatchMapping("/atualizar-endereco")
    public ResponseEntity<ApiResponse<EnderecoResponse>> atualizarEndereco(@RequestParam("idUsuario") UUID idUsuario,
                                                                           @RequestParam("idEndereco") UUID idEndereco){
        return ResponseEntity.ok(service.atualizarEnderecoComoPrincipal(idUsuario,idEndereco));
    }
}
