package Makhloul.ilyas.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class ChampsDonnees {

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

    private String titreMarche;
    private String typeMarche;
    private String objetComplet;
    private String cpvPrincipal;
    private String lieuExecution;
    private String dureeMois;
    private String trancheNon;
    private String marcheReserveNon;
    private boolean divEnLots;
    private List<Lot> lots;

    private String typeProcedure;
    private String techAchat;
    private String attribSansNegOui;
    private String variantesNon;
    private String categorieAcheteur;
    private String criteresAttribution;

    private String dateReceptionOffres;

    private String participationElectroniqueAutorisee;

    private String critereSelection;
    private String capaciteEco;
    private String capaciteTech;

    private String visiteNon;
    private String renseignementsComplementaires;

    public void extraireDonneesDepuisJson(JsonNode json) {
        JsonNode donnees = json.path("donnees");

        JsonNode identite = donnees.path("IDENTITE");
        this.denomination = identite.path("DENOMINATION").asText();
        this.siret = identite.path("TYPE_IDENT_NATIONAL").path("SIRET").asText();
        this.codeIdentNational = identite.path("CODE_IDENT_NATIONAL").asText();
        this.contact = identite.path("CONTACT").asText();
        this.cp = identite.path("CP").asText();
        this.ville = identite.path("VILLE").asText();
        this.urlProfilAcheteur = identite.path("URL_PROFIL_ACHETEUR").asText();
        this.integraliteDocOui = identite.path("INTEGRALITE_DOC_OUI").asText();
        this.agitPourAutreCompteNon = identite.path("AGIT_POUR_AUTRE_COMPTE_NON").asText();
        this.organismeAcheteurCentralNon = identite.path("ORGANISME_ACHETEUR_CENTRAL_NON").asText();

        JsonNode objet = donnees.path("OBJET");
        this.titreMarche = objet.path("TITRE_MARCHE").asText();
        this.typeMarche = objet.path("TYPE_MARCHE").fieldNames().next();
        this.objetComplet = objet.path("OBJET_COMPLET").asText();
        this.cpvPrincipal = objet.path("CPV").path("PRINCIPAL").asText();
        this.lieuExecution = objet.path("LIEU_EXEC_LIVR").path("ADRESSE").asText();
        this.dureeMois = objet.path("DUREE_DELAI").path("DUREE_MOIS").asText();
        this.trancheNon = objet.path("TRANCHE_NON").asText();
        this.marcheReserveNon = objet.path("MARCHE_RESERVE_NON").asText();
        this.divEnLots = objet.path("DIV_EN_LOTS").has("OUI");

        this.lots = new ArrayList<>();
        JsonNode lotsNode = objet.path("LOTS").path("LOT");
        if (lotsNode.isArray()) {
            for (JsonNode lot : lotsNode) {
                Lot l = new Lot();
                l.description = lot.path("DESCRIPTION").asText();
                l.lieuPrincipal = lot.path("LIEU_PRINCIPAL").asText();
                l.valeur = lot.path("VALEUR").path("#text").asText();
                l.devise = lot.path("VALEUR").path("@DEVISE").asText();
                this.lots.add(l);
            }
        }

        JsonNode procedure = donnees.path("PROCEDURE");
        this.typeProcedure = procedure.path("TYPE_PROCEDURE").fieldNames().next();
        this.techAchat = procedure.path("TECH_ACHAT").fieldNames().next();
        this.attribSansNegOui = procedure.path("ATTRIB_SANS_NEG_OUI").asText();
        this.variantesNon = procedure.path("VARIANTES_NON").asText();
        this.categorieAcheteur = procedure.path("CATEGORIE_ACHETEUR").asText();
        this.criteresAttribution = procedure.path("CRITERES_ATTRIBUTION").path("CRITERES_LIBRE").asText();

        this.dateReceptionOffres = donnees.path("CONDITION_DELAI").path("RECEPT_OFFRES").asText();

        this.participationElectroniqueAutorisee = donnees.path("CONDITION_RELATIVE_MARCHE").path("PARTICIPATION_ELECTRONIQUE_AUTORISEE").asText();

        JsonNode conditionParticipation = donnees.path("CONDITION_PARTICIPATION");
        this.critereSelection = conditionParticipation.path("CRITERE_SELECTION").asText();
        this.capaciteEco = conditionParticipation.path("CAP_ECO").asText();
        this.capaciteTech = conditionParticipation.path("CAP_TECH").asText();

        JsonNode rc = donnees.path("RENSEIGNEMENTS_COMPLEMENTAIRES");
        this.visiteNon = rc.path("VISITE_NON").asText();
        this.renseignementsComplementaires = rc.path("RENS_COMPLEMENT").asText();
    }

    private static class Lot {
        String description;
        String lieuPrincipal;
        String valeur;
        String devise;
    }
}