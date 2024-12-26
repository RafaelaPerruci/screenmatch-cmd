package br.com.rafaelaperruci.screenmatch_cmd_line.controller;

import br.com.rafaelaperruci.screenmatch_cmd_line.dto.SerieDTO;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> getSeries(){
        return service.getAllSeries();

    }
    @GetMapping("/top-five")

    public List<SerieDTO> getTopFiveSeries(){
        return service.getTopFiveSeries();
    }

    @GetMapping("/last-releases")
    public List<SerieDTO> getLastReleases(){
        return service.getLastReleases();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id){
        return service.getById(id);
    }
}
