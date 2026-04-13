package br.com.backend.ecommerce.repository;

import br.com.backend.ecommerce.model.ItemPedido;
import br.com.backend.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {
}
