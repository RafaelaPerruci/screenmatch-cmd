package br.com.rafaelaperruci.screenmatch_cmd_line.principal;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.EpisodeData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeasonData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeriesData;
import br.com.rafaelaperruci.screenmatch_cmd_line.repository.SerieRepository;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.ConsumerAPI;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.DataParser;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.IDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static final String URL = "http://www.omdbapi.com/?apikey=f33cc024&t=";
    private final ConsumerAPI consumerAPI = new ConsumerAPI();
    private DataParser dataParser = new DataParser();
    private Scanner scanner = new Scanner(System.in);
    List<SeriesData> seriesData = new ArrayList<>();

    private SerieRepository serieRepository;

    public Principal(SerieRepository serieRepository){
        this.serieRepository = serieRepository;

    }

    public void displayMenu(){

        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    fetchWebSerie();
                    break;
                case 2:
                    fetchEpisodeSerie();
                    break;

                case 3:
                    listSearchedSeries();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }

        }

    }

    private void listSearchedSeries() {
        List<Serie> series = serieRepository.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getGenre))
                .collect(Collectors.toList());
        series.forEach(s -> System.out.println(s + "\n"));
                //List<Serie> series = new ArrayList<>();
//        series = seriesData.stream().map(sd -> new Serie(sd)).collect(Collectors.toList());
//        series.stream()
//                .sorted(Comparator.comparing(Serie::getGenre))
//                .forEach(System.out::println);

    }

    private void fetchWebSerie(){
        SeriesData serie = getWebSerie();
        //seriesData.add(serie);
        Serie serieClass = new Serie(serie);
        serieRepository.save(serieClass);
        System.out.println(serieClass);

    }

    private SeriesData getWebSerie(){
        System.out.println("Digite o nome da serie que deseja buscar: ");
        var name = scanner.nextLine();
        String encodedName = null;
        try {
            encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding não suportado: " + e.getMessage());
        }
        var json = consumerAPI.getData(URL + encodedName);
        SeriesData serieDto = dataParser.fromObject(json, SeriesData.class);
        return serieDto;

    }
    private void fetchEpisodeSerie(){
        SeriesData seriesData = getWebSerie();
        String title = null;
        try {
            title = URLEncoder.encode(seriesData.title(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding não suportado: " + e.getMessage());
        }
        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i < seriesData.totalSeasons(); i++){
            var json = consumerAPI.getData(URL + title.toLowerCase() + "&season=" + i);
            SeasonData seasonData = dataParser.fromObject(json, SeasonData.class);
            seasons.add(seasonData);

        }
        seasons.forEach(System.out::println);


    }
}
