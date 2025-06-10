package com.grupo3.pawHome.services;

import com.grupo3.pawHome.config.StripeConfig;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final StripeConfig stripeConfig;

    public UsuarioService(UsuarioRepository repository,
                          UsuarioRepository usuarioRepository,
                          StripeConfig stripeConfig) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.stripeConfig = stripeConfig;
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return repository.findById(id);
    }

    public void save(Usuario usuario) { usuarioRepository.save(usuario); }

    public String ensureStripeCustomerExists(Usuario usuario) throws StripeException {
        if (usuario.getStripeCustomerId() == null) {
            Stripe.apiKey = stripeConfig.getSecretKey();

            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setEmail(usuario.getEmail())
                    .setName(usuario.getPerfilDatos().getNombre() + " " + usuario.getPerfilDatos().getApellidos())
                    .build();

            Customer customer = Customer.create(customerParams);
            usuario.setStripeCustomerId(customer.getId());
            usuarioRepository.save(usuario);
        }
        return usuario.getStripeCustomerId();
    }
}
