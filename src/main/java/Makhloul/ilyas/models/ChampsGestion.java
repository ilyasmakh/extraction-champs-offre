package Makhloul.ilyas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class ChampsGestion {

    private String r2;
    private String r3;
    private String tetier_r3 ;
    private String r4;
    private String tetier_r4 ;
    private String k1 ;
    private String k9 ;
    private String numAnnonce ;
    private LocalDate datePublication ;
    private String nomPublication;
    private Descripteur[] descripteurs;
    private AvisPrecedent avisPrecedent;
    private String resumeObjet;
    private String typeProcedure;
    private String genre ;
    private String rappel;


    public void extraireGestionDepuisJson(JsonNode json) {
        ObjectMapper mapper = new ObjectMapper();

        // Si c'est un texte (chaîne JSON), on le reparse
        if (json.isTextual()) {
            try {
                json = mapper.readTree(json.textValue());
            } catch (IOException e) {
                System.err.println("Erreur lors du parsing du champ 'gestion' : " + e.getMessage());
                return;
            }
        }


        if (json.hasNonNull("R2")) this.r2 = json.get("R2").asText();
        if (json.hasNonNull("R3")) this.r3 = json.get("R3").asText();
        if (json.hasNonNull("TETIER_R3")) this.tetier_r3 = json.get("TETIER_R3").asText();
        if (json.hasNonNull("R4")) this.r4 = json.get("R4").asText();
        if (json.hasNonNull("TETIER_R4")) this.tetier_r4 = json.get("TETIER_R4").asText();
        if (json.hasNonNull("K1")) this.k1 = json.get("K1").asText();
        if (json.hasNonNull("K9")) this.k9 = json.get("K9").asText();
        if (json.hasNonNull("NUM_ANN")) this.numAnnonce = json.get("NUM_ANN").asText();

        JsonNode indexation = json.get("INDEXATION");
        if (indexation != null && indexation.isObject()) {
            if (indexation.hasNonNull("NOMPUBLICATION")) this.nomPublication = indexation.get("NOMPUBLICATION").asText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (indexation.hasNonNull("DATE_PUBLICATION")) {
                this.datePublication = LocalDate.parse(indexation.get("DATE_PUBLICATION").asText(), formatter);
            }  if (indexation.hasNonNull("RESUME_OBJET")) this.resumeObjet = indexation.get("RESUME_OBJET").asText();
            if (indexation.hasNonNull("TYPE_PROCEDURE_V1")) this.typeProcedure = indexation.get("TYPE_PROCEDURE_V1").asText();
            if (indexation.hasNonNull("RAPPEL_V1")) this.rappel = indexation.get("RAPPEL_V1").asText();
            if (indexation.hasNonNull("DESCRIPTEURS")) {
                JsonNode descripteursNode = indexation.get("DESCRIPTEURS").get("DESCRIPTEUR");
                if (descripteursNode != null && descripteursNode.isArray()) {
                    this.descripteurs = mapper.convertValue(descripteursNode, Descripteur[].class);
                }
            }
            if (indexation.hasNonNull("GENRE_V1")) this.genre = indexation.get("GENRE_V1").asText();

            if (indexation.hasNonNull("AVISLIEE")) {
                JsonNode avisLieeNode = indexation.get("AVISLIEE").get("AVIS_PRECEDENT");
                if (avisLieeNode != null) {
                    this.avisPrecedent = mapper.convertValue(avisLieeNode, AvisPrecedent.class);
                }
            }

        }
    }


  @NoArgsConstructor
    public static class  Descripteur {
        @Getter
        @Setter
        @JsonProperty("CODE")
        String code ;
        @Getter
        @Setter
        @JsonProperty("LIBELLE")
        String lebelle ;

      @Override
      public String toString() {
          return "Descripteur{" +
                  "code='" + code + '\'' +
                  ", lebelle='" + lebelle + '\'' +
                  '}';
      }
  }

    @NoArgsConstructor
    public static class  AvisPrecedent {
        @Getter
        @Setter
        @JsonProperty("NUMEROANNONCE")
        String numeroAnnonce ;
        @Getter
        @Setter
        @JsonProperty("NUMEROPARUTION")
        String numeroParution ;

        @Getter
        @Setter
        @JsonProperty("TYPEPARUTION")
        String typeParution ;

        @Getter
        @Setter
        @JsonProperty("DATEPARUTION")
        String dateParution ;

        @Getter
        @Setter
        @JsonProperty("ANNEE")
        String annee;

        @Getter
        @Setter
        @JsonProperty("ANNONCEREF")
        String annonceRef;

        @Override
        public String toString() {
            return "AvisPrecedent{" +
                    "numeroAnnonce='" + numeroAnnonce + '\'' +
                    ", numeroParution='" + numeroParution + '\'' +
                    ", typeParution='" + typeParution + '\'' +
                    ", dateParution='" + dateParution + '\'' +
                    ", annee='" + annee + '\'' +
                    ", annonceRef='" + annonceRef + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChampsGestion{" +
                "r2='" + r2 + '\'' +
                ", r3='" + r3 + '\'' +
                ", tetier_r3='" + tetier_r3 + '\'' +
                ", r4='" + r4 + '\'' +
                ", tetier_r4='" + tetier_r4 + '\'' +
                ", k1='" + k1 + '\'' +
                ", k9='" + k9 + '\'' +
                ", numAnnonce='" + numAnnonce + '\'' +
                ", datePublication=" + datePublication +
                ", nomPublication='" + nomPublication + '\'' +
                ", descripteurs=" + Arrays.toString(descripteurs) +
                ", resumeObjet='" + resumeObjet + '\'' +
                ", typeProcedure='" + typeProcedure + '\'' +
                ", genre='" + genre + '\'' +
                ", rappel='" + rappel + '\'' +
                ", avisPrecedent='" + avisPrecedent + '\'' +
                '}';
    }
}
