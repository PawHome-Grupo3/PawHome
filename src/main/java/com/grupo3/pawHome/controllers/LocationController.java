package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.services.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/api/ciudades", produces = "application/xml")
    @ResponseBody
    public String getCitiesByCountry(@RequestParam("pais") String countryCode) throws Exception {
        List<String> ciudades = locationService.getCitiesForCountry(countryCode);
        System.out.println("País recibido en controlador: " + countryCode);
        System.out.println("Ciudades encontradas: " + ciudades);

        StringBuilder xml = new StringBuilder();
        xml.append("<ciudades>");
        for (String ciudad : ciudades) {
            xml.append("<ciudad>").append(ciudad).append("</ciudad>");
        }
        xml.append("</ciudades>");

        return xml.toString();
    }

}
