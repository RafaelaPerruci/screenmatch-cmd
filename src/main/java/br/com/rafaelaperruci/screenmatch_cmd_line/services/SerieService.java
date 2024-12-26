package br.com.rafaelaperruci.screenmatch_cmd_line.services;

import br.com.rafaelaperruci.screenmatch_cmd_line.dto.SerieDTO;
import br.com.rafaelaperruci.screenmatch_cmd_line.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> getAllSeries(){
        return repository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRate(), s.getGenre(),
                        s.getActors(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }
}
