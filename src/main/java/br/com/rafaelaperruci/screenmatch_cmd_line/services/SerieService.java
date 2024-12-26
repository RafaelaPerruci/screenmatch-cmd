package br.com.rafaelaperruci.screenmatch_cmd_line.services;

import br.com.rafaelaperruci.screenmatch_cmd_line.dto.EpisodesDTO;
import br.com.rafaelaperruci.screenmatch_cmd_line.dto.SerieDTO;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Category;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Episodes;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import br.com.rafaelaperruci.screenmatch_cmd_line.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> getAllSeries(){
        return parseDataForDto(repository.findAll());
    }

    public List<SerieDTO> getTopFiveSeries() {
        return parseDataForDto(repository.findTop5ByOrderByRateDesc());

    }

    public List<SerieDTO> getLastReleases() {
        return parseDataForDto(repository.findTop5LastReleases());
    }

    public SerieDTO getById(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRate(), s.getGenre(),
                    s.getActors(), s.getPoster(), s.getSinopse());
        }
        return null;
    }
    public List<EpisodesDTO> getAllSeasons(Long id) {

        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodes().stream()
                    .map(e -> new EpisodesDTO(e.getSeason(), e.getTitle(), e.getNumEpisode()))
                    .collect(Collectors.toList());
        }
        return null;
    }
    public List<EpisodesDTO> getSeasonsByNum(Long id, Integer num) {
        return repository.getEpisodesByNum(id, num)
                .stream()
                .map(e -> new EpisodesDTO(e.getSeason(), e.getTitle(), e.getNumEpisode()))
                .collect(Collectors.toList());


    }

    public List<SerieDTO> getSeriesByGenre(String genre){


        Category c = Category.fromPortuguese(genre.substring(0,1).toUpperCase() + genre.substring(1));

        return parseDataForDto(repository.findByGenre(c));

    }

    private List<SerieDTO> parseDataForDto(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRate(), s.getGenre(),
                        s.getActors(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }



}
