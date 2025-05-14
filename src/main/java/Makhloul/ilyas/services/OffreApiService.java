package Makhloul.ilyas.services;



import Makhloul.ilyas.models.Offre;
import Makhloul.ilyas.models.OffreFilter;
import Makhloul.ilyas.models.OffreResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class OffreApiService {
    private final RestTemplate restTemplate;

    public OffreApiService(RestTemplate restTemplate ) {
        this.restTemplate = restTemplate;
    }

    public List<Offre> getOffreById(String id) {
        try {
            String url = "https://boamp-datadila.opendatasoft.com/api/explore/v2.1/catalog/datasets/boamp/records"
                    + "?where=id=\""+id+"\"";

            System.out.println("URL utilisée : " + url);

            OffreResponse response = restTemplate.getForObject(url, OffreResponse.class);

            if (response != null && response.getResults() != null) {
                return response.getResults();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    public List<Offre> getOffreByFilters(OffreFilter filter) {
        try {
            List<String> conditions = new ArrayList<>();

            // Objet : contient (ILIKE pour insensible à la casse)
            if (filter.getObjet() != null && !filter.getObjet().isEmpty()) {
                conditions.add("objet LIKE '%" + filter.getObjet() + "%'");
            }

            // Champs tableaux → utiliser array_contains
            if (filter.getTypeMarche() != null && !filter.getTypeMarche().isEmpty()) {
                conditions.add("type_marche LIKE \"%"+ filter.getTypeMarche() + "%\"");
            }
            if (filter.getDepartement()!= null && !filter.getDepartement().isEmpty()) {
                conditions.add("code_departement LIKE \"%"+ filter.getDepartement() + "%\"");
            }
            if (filter.getTypeAvis() != null && !filter.getTypeAvis() .isEmpty()) {
                conditions.add("type_avis LIKE \"%"+ filter.getTypeAvis() + "%\"");
            }

            if (filter.getFamille() != null && !filter.getFamille() .isEmpty()) {
                conditions.add("famille=\"" + filter.getFamille()  + "\"");

            }

            if (filter.getEtat() != null && !filter.getEtat().isEmpty()) {
                conditions.add("etat=\"" + filter.getEtat() + "\"");
            }

            if (filter.getAcheteur() != null && !filter.getAcheteur() .isEmpty()) {
                conditions.add("nomacheteur=\"" + filter.getAcheteur()  + "\"");
            }

            // Format date yyyy-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (filter.getDateFinDiffusion() != null) {
                conditions.add("datefindiffusion >= \"" + sdf.format(filter.getDateFinDiffusion()) + "\"");
            }
            if (filter.getDateLimiteReponse() != null) {
                conditions.add("datelimitereponse >= \"" + sdf.format(filter.getDateLimiteReponse() ) + "\"");
            }
            if (filter.getDateParution()!= null) {
                conditions.add("dateparution >= \"" + sdf.format(filter.getDateParution()) + "\"");
            }

            // Construction de la clause WHERE
            String whereClause = String.join(" and ", conditions);
            String baseUrl = "https://boamp-datadila.opendatasoft.com/api/explore/v2.1/catalog/datasets/boamp/records";


            String url;
            if (!whereClause.isEmpty()) {
                url = baseUrl + "?where=" + whereClause
                        + "&limit=" + filter.getLimit() + "&offset=" + filter.getOffset();
            } else {
                url = baseUrl + "?limit=" + filter.getLimit() + "&offset=" + filter.getOffset();
            }

            System.out.println("URL utilisée : " + url);

            OffreResponse response = restTemplate.getForObject(url, OffreResponse.class);

            if (response != null && response.getResults() != null) {
                return response.getResults();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}
