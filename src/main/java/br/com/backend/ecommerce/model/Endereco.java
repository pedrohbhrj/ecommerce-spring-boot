package br.com.backend.ecommerce.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {

    private String cep;
    private String rua;
    private String cidade;
    private String estado;
    private String bairro;
    private String complemento;
    private String numeroCasa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Usuario cliente;

}
