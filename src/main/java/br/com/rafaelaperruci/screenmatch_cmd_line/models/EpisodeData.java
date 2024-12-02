package br.com.rafaelaperruci.screenmatch_cmd_line.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(@JsonAlias("Title") String title,
                          @JsonAlias("Episode") Integer epNumber,
                          @JsonAlias("imdbRating") String rating,
                          @JsonAlias("Released") String yearRelease) {
}
