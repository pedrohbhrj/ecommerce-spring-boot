package br.com.backend.ecommerce.repository;

import br.com.backend.ecommerce.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    @NativeQuery(value = "SELECT e.* FROM endereco e LEFT JOIN usuario u ON u.id =: idCliente")
    List<Endereco> findAllClientAddressById(@Param("idCliente") UUID idCliente);
}
