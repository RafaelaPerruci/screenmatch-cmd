package br.com.rafaelaperruci.screenmatch_cmd_line.dto;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.Category;

public record SerieDTO(Long id,
                       String title,
                       Integer totalSeasssons,
                       Double rate,
                       Category genre,
                       String actors,
                       String poster,
                       String sinopse) {
}
