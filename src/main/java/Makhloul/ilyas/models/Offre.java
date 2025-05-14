package Makhloul.ilyas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Offre {

    @JsonProperty("idweb")
    private String idweb;


    @JsonProperty("id")
    private String id;


    @JsonProperty("contractfolderid")
    private String contractfolderid;

    @Getter
    @JsonProperty("objet")
    private String objet;

    @JsonProperty("code_departement")
    private List<String> codeDepartement;

    @JsonProperty("famille")
    private String famille;

    @JsonProperty("etat")
    private String etat;

    @JsonProperty("dateparution")
    private Date dateParution;

    @JsonProperty("datefindiffusion")
    private Date dateFinDiffusion;

    @JsonProperty("datelimitereponse")
    private Date dateLimitReponse;

    @JsonProperty("nomacheteur")
    private String nomAcheteur;

    @JsonProperty("typemarche")
    private String typeMarche;

    @JsonProperty("typecontrat")
    private String typeContrat;

    @JsonProperty("typeavis")
    private String typeAvis;

    @JsonProperty("annoncelie")
    private String[] annonceLie;

    @Getter @Setter
    @JsonProperty("gestion")
    private JsonNode gestion;

    @Getter @Setter
    private ChampsGestion champsGestion = new ChampsGestion() ;

    @Getter
    @JsonProperty("donnees")
    private JsonNode donnees;

    public void setChampsGestion() {
        this.champsGestion.extraireDepuisJson(gestion);
    }

    @JsonProperty("url_avis")
    private String url_avis ;


    // --------- Section 1 - Identification de l'acheteur ---------------------------------

    // String : Nom complet de l'acheteur
    String typeNuméroNationalID  ;
    String nNationalId  ;
    String ville ;
    String cp ;
    // String ; departement de publication

    // --------- Section 2 - Communication ------------------------------------------------
    String urlProfilAcheteur ;
    String idInterneConsultation ;
    String nomContact ;
    String maiContact ;

    // --------- Section 3 - Procédure -----------------------------------------------------
    String typeProcedure;
     //->Conditions de participation :
          String capaciteExercice  ;
          String capaciteEcoFin  ;
          String capaciteTech  ;

    String techniqueAchat  ;
    LocalDateTime dateReceptionOffres ;
    String presOffres  ;
    Boolean nbCandidatReduit  ;
    Boolean attributionSansNegociation  ;
    String variantes  ;
    String criteresAttribution  ;

    // --------- Section 4  :  Identification du marché ---------------------------------------
    String intituleMarche ;
    String codeCPV ;
    // string : type de marché
    String descSuccincteMarche ;
    String lieuExecution  ;
    int duree ;
    String valeurEstime ;
    Boolean consultationTranches ;
    Boolean consultationReservationMarche ;
    Boolean marcheAlloti;

    // --------- Section 5 : Lots -----------------------------------------------------------



    // --------- Section 6 : Informations Complementaires -----------------------------------
    Boolean visiteObligatoir  ;
    String infosComp ;

    @Override
    public String toString() {
        return "Offre{" +
                "idweb='" + idweb + '\'' +
                ", id='" + id + '\'' +
                ", contractfolderid='" + contractfolderid + '\'' +
                ", objet='" + objet + '\'' +
                ", codeDepartement=" + codeDepartement +
                ", famille='" + famille + '\'' +
                ", etat='" + etat + '\'' +
                ", dateParution=" + dateParution +
                ", dateFinDiffusion=" + dateFinDiffusion +
                ", dateLimitReponse=" + dateLimitReponse +
                ", nomAcheteur='" + nomAcheteur + '\'' +
                ", typeMarche='" + typeMarche + '\'' +
                ", typeContrat='" + typeContrat + '\'' +
                ", typeAvis='" + typeAvis + '\'' +
                ", annonceLie=" + Arrays.toString(annonceLie) +
                ", gestion=" + gestion +
                ", donnees=" + donnees +
                ", url_avis='" + url_avis + '\'' +
                '}';
    }

    public static boolean contientObjet(JsonNode node) {
        if (node.isObject()) {
            for (JsonNode child : node) {
                if (child.isObject() || contientObjet(child)) {
                    return true;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode element : node) {
                if (element.isObject() || contientObjet(element)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void afficherDonnees(JsonNode node ,Boolean first , int level ,int nmMe ,String prefix) {
         if(first) {
             ObjectMapper objectMapper = new ObjectMapper();
             try {
                 node = objectMapper.readTree(node.asText());
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }


        AtomicInteger compteurLevel= new AtomicInteger(level);
        AtomicInteger compteurMe= new AtomicInteger(nmMe);
        StringBuilder prefixBuilder = new StringBuilder(prefix);
        if (node.isObject()) {
            if(contientObjet(node))  {
                compteurLevel.incrementAndGet();
                prefixBuilder.append(".1");
                compteurMe.set(1);
            }
            node.fields().forEachRemaining(entry -> {
                JsonNode value = entry.getValue();
                if (!value.isNull() && (!value.isTextual() || !value.asText().isEmpty())) {

                    if(value.isArray()) {
                        System.out.println("\n" + entry.getKey() + " : ");
                        afficherDonnees(value,  false ,compteurLevel.intValue(), compteurMe.intValue() , prefixBuilder.toString());
                    }
                    else if(value.isObject() ) {
                        System.out.println("\n" +  entry.getKey() + " : ");

                       prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
                        prefixBuilder.append(compteurMe.toString());
                        System.out.println("My level is "+ compteurLevel.intValue() +" And I'm "+compteurMe.intValue()+ " And my prefix is "+ prefixBuilder.toString());
                        compteurMe.incrementAndGet();
                        afficherDonnees(value, false ,  compteurLevel.intValue(),compteurMe.intValue(), prefixBuilder.toString());

                    }
                    else System.out.println("\n" +  entry.getKey() + " : " + value.asText());
                }
            });
        } else if (node.isArray()) {
            for (JsonNode element : node) {
                afficherDonnees(element, false , level , nmMe, prefix);
            }
        } else {
            System.out.println("- "+node.asText());
        }
    }




}
