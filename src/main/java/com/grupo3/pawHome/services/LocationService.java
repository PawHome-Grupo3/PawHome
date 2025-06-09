package com.grupo3.pawHome.services;


import com.grupo3.pawHome.dtos.CountryDTO;
import com.grupo3.pawHome.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<CountryDTO> getAllCountries() throws Exception {
        List<CountryDTO> countries = locationRepository.getCountries();
        System.out.println("Países obtenidos: " + countries);
        return countries;
    }

    public List<String> getCitiesForCountry(String country) throws Exception {
        return locationRepository.getCitiesByCountry(country);
    }
}

