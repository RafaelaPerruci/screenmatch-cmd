package br.com.rafaelaperruci.screenmatch_cmd_line.controller;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import br.com.rafaelaperruci.screenmatch_cmd_line.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository repository;


    @GetMapping("/series")
    public List<Serie> getSeries(){
        return repository.findAll();
    }
}
