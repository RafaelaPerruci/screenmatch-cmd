package br.com.rafaelaperruci.screenmatch_cmd_line.models.principal;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.EpisodeData;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodes {
    private Integer season;
    private String title;
    private Integer numEpisode;
    private Double rate;
    private LocalDate date;

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
