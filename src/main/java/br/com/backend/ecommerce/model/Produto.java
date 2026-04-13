package br.com.backend.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vendedor")
    private Usuario vendedor;

    @OneToMany(mappedBy = "produto",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ItemPedido> itemPedidoList = new ArrayList<>();


    @Column(nullable = false,length = 50)
    private String nome;

    @Column(length = 300)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    private String imgUrl;

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
