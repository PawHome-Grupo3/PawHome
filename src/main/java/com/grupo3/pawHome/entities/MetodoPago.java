package com.grupo3.pawHome.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

@Table(name = "metodo_pago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numero;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "tipo_pago_id")
    private TipoPago tipoPago;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "usuario_id")
    private Usuario usuario;

}
