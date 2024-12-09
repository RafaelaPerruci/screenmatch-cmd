package br.com.rafaelaperruci.screenmatch_cmd_line.models;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private Double rate;
    private Category genre;
    private String actors;
    private String poster;
    private String sinopse;

    public Serie(SeriesData serie){
        this.title = serie.title();
        this.totalSeasons = serie.totalSeasons();
        this.rate = OptionalDouble.of(Double.valueOf(serie.rate())).orElse(0);
        this.genre = Category.fromString(serie.genre().split(",")[0].trim());
        this.actors = serie.actors();
        this.poster = serie.poster();
        this.sinopse = serie.sinopse();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    public String toString(){
        return "Título: " + title +"\n" +
                "Categoria: " + genre + "\n" +
                "Total de temporadas : " + totalSeasons + "\n" +
                "Avaliação: " + rate + "\n" +
                "Atores: " + actors + "\n" +
                "Poster: " + poster + "\n" +
                "Sinopse: " + sinopse;
    }
}
