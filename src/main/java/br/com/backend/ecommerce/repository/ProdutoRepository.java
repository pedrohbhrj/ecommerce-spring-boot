package br.com.backend.ecommerce.repository;

import br.com.backend.ecommerce.model.Pedido;
import br.com.backend.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}
