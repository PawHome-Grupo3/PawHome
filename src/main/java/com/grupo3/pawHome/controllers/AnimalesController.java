package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.entities.Animales;
import com.grupo3.pawHome.services.AnimalesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AnimalesController {
    private final AnimalesService animalesService;

    public AnimalesController(AnimalesService animalesService) {
        this.animalesService = animalesService;
    }

    @GetMapping("/nuestrosAnimales")
    public String mostrarNuestrosAnimales(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Animales> animalPage = animalesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("animalPage", animalPage);
        model.addAttribute("animales", animalPage.getContent());

        int totalPages = animalPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "nuestrosAnimales";
    }

    @GetMapping("/adopta/{id}")
    public String mostrarPaginaDonacion(@PathVariable("id") int id, Model model) {
        Optional<Animales> animalOpt = animalesService.findById(id); // o animalRepository.findById(id)

        if (animalOpt.isPresent()) {
            Animales animal = animalOpt.get();
            model.addAttribute("animal", animal);
            return "compruebaAdoptaId"; // nombre de la vista Thymeleaf (por ejemplo: templates/dona.html)
        } else {
            return "redirect:/error"; // o cualquier página de error
        }
    }

}
