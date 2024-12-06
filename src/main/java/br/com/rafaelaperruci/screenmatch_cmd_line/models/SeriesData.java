package br.com.rafaelaperruci.screenmatch_cmd_line.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(@JsonAlias("Title") String title,
                         @JsonAlias("Year") String year,
                         @JsonAlias("Genre") String genre,
                         @JsonAlias("imdbRating") Double rate,
                         Integer totalSeasons,
                         @JsonAlias("Actors") String actors,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String sinopse) {
}
