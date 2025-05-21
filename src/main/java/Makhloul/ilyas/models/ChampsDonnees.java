package Makhloul.ilyas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;

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
    private Lot[] lots;

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

    @ToString
    public static class Valeur {
        @Getter
        @Setter
        @JsonProperty("@DEVISE")
        private String devise;
        @Getter
        @Setter
        @JsonProperty("#text")
        private String text;
    }

    @ToString
    public static class Lot {
        @Getter
        @Setter
        @JsonProperty("DESCRIPTION")
        private String description;
        @Getter
        @Setter
        @JsonProperty("LIEU_PRINCIPAL")
        private String lieuPrincipal;
        @Getter
        @Setter
        @JsonProperty("VALEUR")
        private Valeur valeur;
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
            if (identite.hasNonNull("DENOMINATION")) this.denomination =
                    identite.get("DENOMINATION").asText();
            JsonNode typeIdentNational = identite.get("TYPE_IDENT_NATIONAL");
            if (typeIdentNational != null && typeIdentNational.isObject()) {
                if (typeIdentNational.hasNonNull("SIRET")) this.siret = typeIdentNational.get("SIRET").asText();
            }
            if (identite.hasNonNull("CODE_IDENT_NATIONAL"))
                this.codeIdentNational = identite.get("CODE_IDENT_NATIONAL").asText();
            if (identite.hasNonNull("CONTACT")) this.contact = identite.get("CONTACT").asText();
            if (identite.hasNonNull("CP")) this.cp = identite.get("CP").asText();
            if (identite.hasNonNull("VILLE")) this.ville = identite.get("VILLE").asText();
            if (identite.hasNonNull("URL_PROFIL_ACHETEUR"))
                this.urlProfilAcheteur = identite.get("URL_PROFIL_ACHETEUR").asText();
            if (identite.hasNonNull("INTEGRALITE_DOC_OUI"))
                this.integraliteDocOui = identite.get("INTEGRALITE_DOC_OUI").asText();
            if (identite.hasNonNull("AGIT_POUR_AUTRE_COMPTE_NON"))
                this.agitPourAutreCompteNon = identite.get("AGIT_POUR_AUTRE_COMPTE_NON").asText();
            if (identite.hasNonNull("ORGANISME_ACHETEUR_CENTRAL_NON"))
                this.organismeAcheteurCentralNon = identite.get("ORGANISME_ACHETEUR_CENTRAL_NON").asText();
        }
        JsonNode objet = json.get("OBJET");
        if (objet != null && objet.isObject()) {
            if (objet.hasNonNull("TITRE_MARCHE")) this.titreMarche =
                    objet.get("TITRE_MARCHE").asText();
            if (objet.hasNonNull("OBJET_COMPLET"))
                this.objetComplet = objet.get("OBJET_COMPLET").asText();

            JsonNode cpv = objet.get("CPV");
            if (cpv != null && cpv.isObject()) {
                if (cpv.hasNonNull("PRINCIPAL"))
                    this.cpvPrincipal = cpv.get("PRINCIPAL").asText();
            }
            JsonNode lieuExecLiv = objet.get("LIEU_EXEC_LIVR");
            if (lieuExecLiv != null && lieuExecLiv.isObject()) {
                if (lieuExecLiv.hasNonNull("ADRESSE"))
                    this.lieuExecution = lieuExecLiv.get("ADRESSE").asText();
            }

            JsonNode dureeDelai = objet.get("DUREE_DELAI");
            if (dureeDelai != null && dureeDelai.isObject()) {
                if (dureeDelai.hasNonNull("DUREE_MOIS"))
                    this.dureeMois = dureeDelai.get("DUREE_MOIS").asText();
            }

            if (objet.hasNonNull("TRANCHE_NON"))
                this.trancheNon = objet.get("TRANCHE_NON").asText();

            if (objet.hasNonNull("TRANCHE_NON"))
                this.trancheNon = objet.get("TRANCHE_NON").asText();

            if (objet.hasNonNull("MARCHE_RESERVE_NON"))
                this.marcheReserveNon = objet.get("MARCHE_RESERVE_NON").asText();

            if (objet.hasNonNull("LOTS")) {
                JsonNode lotNode = objet.get("LOTS").get("LOT");
                if (lotNode != null && lotNode.isArray()) {
                    this.lots = mapper.convertValue(lotNode, ChampsDonnees.Lot[].class);
                }
            }


        }
        JsonNode procedure = json.get("PROCEDURE");
        if (procedure != null && procedure.isObject()) {
            JsonNode typeProcedure = procedure.get("TYPE_PROCEDURE");
            if (typeProcedure != null && typeProcedure.hasNonNull("OUVERT"))
                this.typeProcedure = typeProcedure.get("OUVERT").asText();

            JsonNode techAchat = procedure.get("TECH_ACHAT");
            if (techAchat != null && techAchat.hasNonNull("ACCORD_CADRE")) {
                this.techAchat = techAchat.get("ACCORD_CADRE").
                        asText();
            }

            if (techAchat != null && techAchat.hasNonNull("VARIANTES_NON"))
                this.variantesNon = techAchat.get("VARIANTES_NON").
                        asText();

            if (techAchat != null && techAchat.hasNonNull("CATEGORIE_ACHETEUR"))
                this.categorieAcheteur = techAchat.get("CATEGORIE_ACHETEUR").
                        asText();

            JsonNode criteresAttribution = procedure.get("CRITERES_ATTRIBUTION");
            if (criteresAttribution != null && criteresAttribution.hasNonNull("CRITERES_LIBRE"))
                this.criteresAttribution = criteresAttribution.get("CRITERES_LIBRE").
                        asText();

            if (procedure.hasNonNull("ATTRIB_SANS_NEG_OUI"))
                this.attribSansNegOui = procedure.get("ATTRIB_SANS_NEG_OUI").
                        asText();

            if (procedure.hasNonNull("VARIANTES_NON"))
                this.variantesNon = procedure.get("VARIANTES_NON").
                        asText();

            if (procedure.hasNonNull("CATEGORIE_ACHETEUR"))
                this.categorieAcheteur = procedure.get("CATEGORIE_ACHETEUR").
                        asText();
        }
        JsonNode conditionDelai = json.get("CONDITION_DELAI");
        if (conditionDelai != null && conditionDelai.isObject()) {
            if (conditionDelai.hasNonNull("RECEPT_OFFRES"))
                this.dateReceptionOffres = conditionDelai.get("RECEPT_OFFRES").
                        asText();
        }
        JsonNode conditionParticipation = json.get("CONDITION_PARTICIPATION");
        if (conditionParticipation != null && conditionParticipation.isObject()) {
            if (conditionParticipation.hasNonNull("CRITERE_SELECTION"))
                this.critereSelection = conditionParticipation.get("CRITERE_SELECTION").
                        asText();

            if (conditionParticipation.hasNonNull("CAP_ECO"))
                this.capaciteEco = conditionParticipation.get("CAP_ECO").
                        asText();

            if (conditionParticipation.hasNonNull("CAP_TECH"))
                this.capaciteTech = conditionParticipation.get("CAP_TECH").
                        asText();
        }
        JsonNode renseignementsComp = json.get("RENSEIGNEMENTS_COMPLEMENTAIRES");
        if (renseignementsComp != null && renseignementsComp.isObject()) {
            if (renseignementsComp.hasNonNull("VISITE_NON"))
                this.visiteNon = renseignementsComp.get("VISITE_NON").
                        asText();

            if (renseignementsComp.hasNonNull("RENS_COMPLEMENT"))
                this.renseignementsComplementaires = renseignementsComp.get("RENS_COMPLEMENT").
                        asText();
        }

        JsonNode conditionRelativeMarche = json.get("CONDITION_RELATIVE_MARCHE");
        if (conditionRelativeMarche != null && conditionRelativeMarche.isObject()) {
            if (conditionRelativeMarche.hasNonNull("PARTICIPATION_ELECTRONIQUE_AUTORISEE"))
                this.participationElectroniqueAutorisee =
                        conditionRelativeMarche.get("PARTICIPATION_ELECTRONIQUE_AUTORISEE").
                                asText();
        }
    }
}
