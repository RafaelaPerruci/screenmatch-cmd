package br.com.rafaelaperruci.screenmatch_cmd_line.models;

import br.com.rafaelaperruci.screenmatch_cmd_line.services.ChatGPTSearch;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private Integer totalSeasons;

    private Double rate;

    @Enumerated(EnumType.STRING)
    private Category genre;

    private String actors;

    private String poster;

    @Column(length = 500)
    private String sinopse;

    @OneToMany(mappedBy = "serie")
    private List<Episodes> episodes = new ArrayList<>();

    public Serie(SeriesData serie){
        this.title = serie.title();
        this.totalSeasons = serie.totalSeasons();
        this.rate = OptionalDouble.of(Double.valueOf(serie.rate())).orElse(0);
        this.genre = Category.fromString(serie.genre().split(",")[0].trim());
        this.actors = serie.actors();
        this.poster = serie.poster();
        this.sinopse = ChatGPTSearch.translation(serie.sinopse()).trim();

    }

    public Serie() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Episodes> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episodes> episodes) {
        this.episodes = episodes;
    }

    public String toString(){
        return "Título: " + title +"\n" +
                "Categoria: " + genre + "\n" +
                "Total de temporadas: " + totalSeasons + "\n" +
                "Avaliação: " + rate + "\n" +
                "Atores: " + actors + "\n" +
                "Poster: " + poster + "\n" +
                "Sinopse: " + sinopse;
    }
}
