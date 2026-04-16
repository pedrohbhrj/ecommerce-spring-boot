package br.com.backend.ecommerce.repository;

import br.com.backend.ecommerce.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.HashMap;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
