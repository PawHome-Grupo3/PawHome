package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.dtos.AnimalDto;
import com.grupo3.pawHome.entities.Animal;
import com.grupo3.pawHome.entities.Especie;
import com.grupo3.pawHome.entities.Raza;
import com.grupo3.pawHome.services.AnimalMapperService;
import com.grupo3.pawHome.services.AnimalService;
import com.grupo3.pawHome.services.EspecieService;
import com.grupo3.pawHome.services.RazaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AnimalesController {
    private final AnimalService animalService;
    private final AnimalMapperService animalMapperService;
    private final RazaService razaService;
    private final EspecieService especieService;

    public AnimalesController(AnimalService animalService,
                              AnimalMapperService animalMapperService,
                              RazaService razaService, EspecieService especieService) {
        this.animalService = animalService;
        this.animalMapperService = animalMapperService;
        this.razaService = razaService;
        this.especieService = especieService;
    }

    @GetMapping("/nuestrosAnimales")
    public String mostrarNuestrosAnimales(Model model,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String adoptado,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "5") int size,
                                          @RequestParam(required = false) Integer razaId,
                                          @RequestParam(required = false) Integer especieId,
                                          @RequestParam(defaultValue = "id,asc") String[] sort){
        try {
            List<Raza> razas = razaService.findAll();
            model.addAttribute("razas", razas);

            if (razaId != null) {
                List<Especie> especies = especieService.findByRazaId(razaId);
                model.addAttribute("especies", especies);
                model.addAttribute("razaId", razaId);
            }
            model.addAttribute("especieId", especieId);

            List<Animal> animales;

            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortField);

            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

            Page<Animal> pageAns = animalService.buscarAnimales(keyword, adoptado, razaId, especieId, pageable);

            animales = pageAns.getContent();
            AnimalDto animalDto;
            List<AnimalDto> animalDtos = new ArrayList<>();

            for (Animal animal : animales) {
                animalDto = animalMapperService.toDto(animal);
                animalDtos.add(animalDto);
            }

            model.addAttribute("animales", animalDtos);
            model.addAttribute("currentPage", pageAns.getNumber() + 1);
            model.addAttribute("totalItems", pageAns.getTotalElements());
            model.addAttribute("totalPages", pageAns.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("adoptado", adoptado);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "nuestrosAnimales";
    }

    @GetMapping("/adopta/{id}")
    public String mostrarPaginaDonacion(@PathVariable("id") int id, Model model) {
        Optional<Animal> animalOpt = animalService.findById(id);

        if (animalOpt.isPresent()) {
            Animal animal = animalOpt.get();
            model.addAttribute("animal", animal);

            return "compruebaAdoptaId";
        } else {
            return "redirect:/error";
        }
    }
}
