package br.com.rafaelaperruci.screenmatch_cmd_line.controller;

import br.com.rafaelaperruci.screenmatch_cmd_line.dto.SerieDTO;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping("/series")
    public List<SerieDTO> getSeries(){
        return service.getAllSeries();

    }

    @GetMapping("/inicio")
    public String getStart(){
        return "Bem-vindo ao screen-match application";
    }
}
