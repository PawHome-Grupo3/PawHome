package com.grupo3.pawHome.services;


import com.grupo3.pawHome.dtos.CountryDTO;
import com.grupo3.pawHome.repositories.LocationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<CountryDTO> getAllCountries() throws Exception {
        List<CountryDTO> countries = locationRepository.getCountries();
        System.out.println("Países obtenidos: " + countries.getFirst().getCodigo() + " " + countries.getFirst().getNombre());
        return countries;
    }

    public List<String> getCitiesForCountry(String country) throws Exception {
        return locationRepository.getCitiesByCountry(country);
    }
}

