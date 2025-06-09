package com.grupo3.pawHome.services;

import com.grupo3.pawHome.config.StripeConfig;
import com.grupo3.pawHome.entities.MetodoPago;
import com.grupo3.pawHome.entities.TipoPago;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.MetodoPagoRepository;
import com.grupo3.pawHome.repositories.TipoPagoRepository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SetupIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {

    private final UsuarioRepository usuarioRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final StripeConfig stripeConfig;

    public MetodoPagoService(UsuarioRepository usuarioRepository,
                             MetodoPagoRepository metodoPagoRepository,
                             TipoPagoRepository tipoPagoRepository,
                             StripeConfig stripeConfig) {
        this.usuarioRepository = usuarioRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.tipoPagoRepository = tipoPagoRepository;
        this.stripeConfig = stripeConfig;
    }

    public String crearSetupIntent(Usuario usuarioAutenticado) throws StripeException {
        Stripe.apiKey = stripeConfig.getSecretKey();

        Usuario usuario = usuarioRepository.findById(usuarioAutenticado.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuario.getStripeCustomerId() == null && usuario.getPerfilDatos() != null) {
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setEmail(usuario.getEmail())
                    .setName(usuario.getPerfilDatos().getNombre() + " " + usuario.getPerfilDatos().getApellidos())
                    .build();

            Customer customer = Customer.create(customerParams);
            usuario.setStripeCustomerId(customer.getId());
            usuarioRepository.save(usuario);
        }

        SetupIntentCreateParams params = SetupIntentCreateParams.builder()
                .setCustomer(usuario.getStripeCustomerId())
                .addPaymentMethodType("card")
                .build();

        SetupIntent setupIntent = SetupIntent.create(params);
        return setupIntent.getClientSecret();
    }

    public void guardarMetodoPago(String paymentMethodId, Usuario usuarioAutenticado, String alias) throws StripeException {
        Stripe.apiKey = stripeConfig.getSecretKey();

        Usuario usuario = usuarioRepository.findById(usuarioAutenticado.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        PaymentMethod method = PaymentMethod.retrieve(paymentMethodId);

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setStripePaymentMethodId(method.getId());
        metodoPago.setMarcaTarjeta(method.getCard().getBrand());
        metodoPago.setUltimosDigitos(method.getCard().getLast4());
        metodoPago.setExpMes(method.getCard().getExpMonth().intValue());
        metodoPago.setExpAnio(method.getCard().getExpYear().intValue());
        metodoPago.setNombreTitular(method.getBillingDetails().getName());
        metodoPago.setUsuario(usuario);
        metodoPago.setAlias(alias);
        metodoPago.setActivo(true);

        Optional<TipoPago> tipoPago = tipoPagoRepository.findByNombreContains("Tarjeta Credito");
        tipoPago.ifPresent(metodoPago::setTipoPago);

        metodoPagoRepository.save(metodoPago);
    }

    public List<MetodoPago> findAllByUsuario(Usuario usuario) { return metodoPagoRepository.findAllByUsuario(usuario); }

    public MetodoPago findByStripePaymentMethodId(String stripePaymentMethodId) {
        return metodoPagoRepository.findByStripePaymentMethodId(stripePaymentMethodId);
    }
}
