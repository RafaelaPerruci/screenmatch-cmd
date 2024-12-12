package br.com.rafaelaperruci.screenmatch_cmd_line.models;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Table(name = "episodes")
public class Episodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer season;
    private String title;
    private Integer numEpisode;
    private Double rate;
    private LocalDate date;

    @ManyToOne
    private Serie serie;

    public Episodes(){

    }

    public Episodes(Integer season, EpisodeData eps){
        this.season = season;
        this.title = eps.title();
        this.numEpisode = eps.epNumber();

        try {
            this.rate = Double.valueOf(eps.rating());
        }catch (NumberFormatException ex){
            this.rate = 0.0;
        }
        try {
            this.date = LocalDate.parse(eps.yearRelease());
        }catch (DateTimeException e){
            this.date = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumEpisode() {
        return numEpisode;
    }

    public void setNumEpisode(Integer numEpisode) {
        this.numEpisode = numEpisode;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  "  season: " + season + "\n" +
                "  title: " + title + "\n" +
                "  numEpisode: " + numEpisode + "\n" +
                "  rate: " + rate + "\n" +
                "  date: " + date + "\n" ;
    }
}
