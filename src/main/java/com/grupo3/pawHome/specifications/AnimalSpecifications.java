package com.grupo3.pawHome.specifications;

import com.grupo3.pawHome.entities.Animal;
import org.springframework.data.jpa.domain.Specification;

public class AnimalSpecifications {

    public static Specification<Animal> animalServicioEsFalse() {
        return (root, query, cb) -> cb.isFalse(root.get("animalServicio"));
    }

    public static Specification<Animal> nombreContiene(String keyword) {
        return (root, query, cb) -> cb.like(
                cb.lower(root.get("nombre")), "%" + keyword.toLowerCase() + "%"
        );
    }

    public static Specification<Animal> adoptadoEs(boolean valor) {
        return (root, query, cb) -> cb.equal(root.get("adoptado"), valor);
    }

    public static Specification<Animal> razaIdEs(Integer razaId) {
        return (root, query, cb) -> cb.equal(root.get("raza").get("id"), razaId);
    }

    public static Specification<Animal> especieIdEs(Integer especieId) {
        return (root, query, cb) -> cb.equal(root.get("raza").get("especie").get("id"), especieId);
    }

    public static Specification<Animal> caracterSocialEs(boolean valor) {
        return (root, query, cb) -> cb.equal(root.get("caracterSocial"), valor);
    }
}

