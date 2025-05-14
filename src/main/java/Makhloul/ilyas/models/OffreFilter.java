package Makhloul.ilyas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
public class OffreFilter {

    @Getter @Setter
    String objet;
    @Getter @Setter
    String typeMarche;
    @Getter @Setter
     String famille;
    @Getter @Setter
    String departement;
    @Getter @Setter
     Date dateFinDiffusion;
    @Getter @Setter
    Date dateLimiteReponse;
    @Getter @Setter
    Date dateParution;
    @Getter @Setter
    String acheteur;
    @Getter @Setter
    String etat;
    @Getter @Setter
    String typeAvis;
    @Getter @Setter
    int offset = 0;
    @Getter @Setter
    int limit = 100;


    public OffreFilter(String objet, String typeMarche, String famille, String departement, Date dateFinDiffusion, Date dateLimiteReponse, Date dateParution, String acheteur, String etat, String typeAvis) {
        this.objet = objet;
        this.typeMarche = typeMarche;
        this.famille = famille;
        this.departement = departement;
        this.dateFinDiffusion = dateFinDiffusion;
        this.dateLimiteReponse = dateLimiteReponse;
        this.dateParution = dateParution;
        this.acheteur = acheteur;
        this.etat = etat;
        this.typeAvis = typeAvis;
    }
}