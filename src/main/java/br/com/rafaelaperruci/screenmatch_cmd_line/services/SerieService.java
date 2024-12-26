package br.com.rafaelaperruci.screenmatch_cmd_line.services;

import br.com.rafaelaperruci.screenmatch_cmd_line.dto.SerieDTO;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import br.com.rafaelaperruci.screenmatch_cmd_line.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return parseDataForDto(repository.findTop5ByOrderByEpisodesDateDesc());
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

    private List<SerieDTO> parseDataForDto(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRate(), s.getGenre(),
                        s.getActors(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }


}
