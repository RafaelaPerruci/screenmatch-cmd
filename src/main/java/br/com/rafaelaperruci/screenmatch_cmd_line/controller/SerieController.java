package br.com.rafaelaperruci.screenmatch_cmd_line.controller;

import br.com.rafaelaperruci.screenmatch_cmd_line.dto.SerieDTO;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import br.com.rafaelaperruci.screenmatch_cmd_line.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository repository;


    @GetMapping("/series")
    public List<SerieDTO> getSeries(){
        return repository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRate(), s.getGenre(),
                        s.getActors(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());

    }
}
