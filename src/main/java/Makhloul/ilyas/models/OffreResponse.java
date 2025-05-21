package Makhloul.ilyas.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OffreResponse {

    private int total_count;

    @JsonProperty("results")
    private List<Offre> results;


    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<Offre> getResults() {
        return results;
    }

    public void setResults(List<Offre> results) {
        this.results = results;
    }
}
