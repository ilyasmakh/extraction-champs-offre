package Makhloul.ilyas.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ToString
public class ChampsDonnees {

    // === IDENTITE ===
    private String denomination;
    private String siret;
    private String codeIdentNational;
    private String contact;
    private String cp;
    private String ville;
    private String urlProfilAcheteur;
    private String integraliteDocOui;
    private String agitPourAutreCompteNon;
    private String organismeAcheteurCentralNon;

    // === OBJET ===
    private String titreMarche;
    private String typeMarche; // Fournitures, Services, etc.
    private String objetComplet;
    private String cpvPrincipal;
    private String lieuExecution;
    private String dureeMois;
    private String trancheNon;
    private String marcheReserveNon;
    private boolean divEnLots;

    // Liste des lots
    private List<Lot> lots;

    // === PROCEDURE ===
    private String typeProcedure;
    private String techAchat;
    private String attribSansNegOui;
    private String variantesNon;
    private String categorieAcheteur;
    private String criteresAttribution;

    // === CONDITION_DELAI ===
    private String dateReceptionOffres;

    // === CONDITION_RELATIVE_MARCHE ===
    private String participationElectroniqueAutorisee;

    // === CONDITION_PARTICIPATION ===
    private String critereSelection;
    private String capaciteEco;
    private String capaciteTech;

    // === RENSEIGNEMENTS_COMPLEMENTAIRES ===
    private String visiteNon;
    private String renseignementsComplementaires;

    // === Classe Lot interne ===
    public static class Lot {
        private String description;
        private String lieuPrincipal;
        private String valeur;
        private String devise;
    }

    // === Méthode d'extraction (à compléter) ===
    public void extraireDonneesDepuisJson(JsonNode json) {
        ObjectMapper mapper = new ObjectMapper();

        if (json.isTextual()) {
            try {
                json = mapper.readTree(json.textValue());
            } catch (IOException e) {
                System.err.println("Erreur lors du parsing du champ 'données' : " + e.getMessage());
                return;
            }
        }
        JsonNode identite = json.get("IDENTITE");
        if (identite != null && identite.isObject()) {
            if (identite.hasNonNull("DENOMINATION")) this.denomination = identite.get("DENOMINATION").asText();
        }
    }
}
