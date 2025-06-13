package com.grupo3.pawHome.repositories;

import com.grupo3.pawHome.dtos.CountryDTO;
import org.springframework.stereotype.Repository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.net.http.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationRepository {

    private static final String USERNAME = "paulokera";

    // Obtiene lista de países desde GeoNames (XML)
    public List<CountryDTO> getCountries() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        String url = "http://api.geonames.org/countryInfo?username=" + USERNAME;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(response.body());
        NodeList countryNodes = doc.getElementsByTagName("country");
        for (int i = 0; i < countryNodes.getLength(); i++) {
            Element countryElement = (Element) countryNodes.item(i);
            String countryName = countryElement.getElementsByTagName("countryName").item(0).getTextContent();
            String countryCode = countryElement.getElementsByTagName("countryCode").item(0).getTextContent();
            countries.add(new CountryDTO(countryCode, countryName));
        }
        return countries;
    }

    // Obtiene lista de ciudades por código de país desde GeoNames (XML)
    public List<String> getCitiesByCountry(String countryCode) throws Exception {
        System.out.println("Consultando ciudades para país: " + countryCode);
        List<String> cities = new ArrayList<>();
        String url = "http://api.geonames.org/search?country=" + countryCode + "&featureClass=P&maxRows=1000&username=" + USERNAME;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(response.body());
        NodeList cityNodes = doc.getElementsByTagName("name");
        for (int i = 0; i < cityNodes.getLength(); i++) {
            cities.add(cityNodes.item(i).getTextContent());
        }

        System.out.println("Total ciudades parseadas: " + cities.size());
        return cities;
    }
}