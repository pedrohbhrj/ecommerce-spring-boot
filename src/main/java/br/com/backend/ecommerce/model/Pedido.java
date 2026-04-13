package br.com.backend.ecommerce.model;

import br.com.backend.ecommerce.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ItemPedido> itemPedidoList = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal precoTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(nullable = false,updatable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    private void prePersist(){
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }
    @PreUpdate
    private void preUpdate(){
        this.atualizadoEm = LocalDateTime.now();
    }


}
