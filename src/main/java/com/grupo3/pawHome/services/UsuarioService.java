package com.grupo3.pawHome.services;

import com.grupo3.pawHome.config.StripeConfig;
import com.grupo3.pawHome.dtos.RegistroDTO;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.repositories.RolRepository;
import com.grupo3.pawHome.repositories.UsuarioRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final StripeConfig stripeConfig;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository repository,
                          UsuarioRepository usuarioRepository,
                          StripeConfig stripeConfig, PasswordEncoder passwordEncoder, RolRepository rolRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.stripeConfig = stripeConfig;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return repository.findById(id);
    }

    public void save(Usuario usuario) { usuarioRepository.save(usuario); }

    public Usuario ensureStripeCustomerExists(Usuario usuario) throws StripeException {
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
        return usuario;
    }

    public void registrarUsuario(RegistroDTO request) {
        if (usuarioRepository.existsByEmail((request.getEmail()))) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNickname(request.getNickname());
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevoUsuario.setFechaRegistro(LocalDate.now());
        nuevoUsuario.setRol(rolRepository.findByNombre("USER").orElseThrow(() -> new RuntimeException("Rol USER no encontrado")));
        usuarioRepository.save(nuevoUsuario);
    }
}
