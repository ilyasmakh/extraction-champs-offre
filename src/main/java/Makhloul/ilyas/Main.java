package Makhloul.ilyas;

import Makhloul.ilyas.models.Offre;
import Makhloul.ilyas.models.OffreFilter;
import Makhloul.ilyas.services.OffreApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static Makhloul.ilyas.models.Offre.afficherDonnees;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        OffreApiService offreApiService = new OffreApiService(restTemplate);

        List<Offre> offres = offreApiService.getOffreById("23_170859");
        System.out.println("----------------L'offre -------------------------");
        System.out.println(offres.get(0));

        offres.get(0).setChampsGestion();
        offres.get(0).setChampsDonnees();
        System.out.println("---------------- CHAMPS GESTION ------------------------");
        System.out.println(offres.get(0).getChampsGestion().toString());
        System.out.println("---------------- CHAMPS DONNEES ------------------------");
        System.out.println(offres.get(0).getChampsDonnees().toString());
    }


    //  OffreFilter filter = new OffreFilter("pers", null , "FNS" ,"74" ,null ,
    //       null ,null ,null ,null ,"5"  );
/*
        List<Offre> offres2 = offreApiService.getOffreByFilters(filter);
        System.out.println("----------------"+offres2.size()+" results-------------------------");
           for (Offre offre : offres2) {
            System.out.println(offre);
        }*/
}


