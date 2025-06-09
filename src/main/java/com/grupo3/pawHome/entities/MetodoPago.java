package com.grupo3.pawHome.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity

@Table(name = "metodo_pago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;

    private String alias;

    private String nombreTitular;

    @Column(name = "stripe_payment_method_id")
    private String stripePaymentMethodId;

    @Column(name = "marca_tarjeta")
    private String marcaTarjeta;

    @Column(name = "ultimos_digitos")
    private String ultimosDigitos;

    @Column(name = "exp_mes")
    private Integer expMes;

    @Column(name = "exp_anio")
    private Integer expAnio;

    private boolean activo = true;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "tipo_pago_id")
    private TipoPago tipoPago;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "metodoPago", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pago> pagos;
}
