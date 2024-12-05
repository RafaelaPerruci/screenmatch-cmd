package br.com.rafaelaperruci.screenmatch_cmd_line.models.principal;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.EpisodeData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeasonData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeriesData;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.ConsumerAPI;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.DataParser;

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

    private Scanner reader = new Scanner(System.in);

    public void displayMenu() throws UnsupportedEncodingException {
        System.out.println("Digite o nome da série para buscar: ");
        var name = reader.nextLine();
        String nameEncode = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        String json = consumerAPI.getData(URL + nameEncode);

        SeriesData serie1 = dataParser.fromObject(json, SeriesData.class);
        System.out.println(serie1);

        List<SeasonData> seasons = new ArrayList<>();
        for (int i = 1; i <= serie1.totalSeasons(); i++){
            json = consumerAPI.getData("http://www.omdbapi.com/?apikey=f33cc024&t=the+walking+dead&season=" + i );
            SeasonData season =  dataParser.fromObject(json, SeasonData.class);
            seasons.add(season);
        }
//        seasons.forEach(System.out::println);
//        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));

        List<EpisodeData> eps = seasons.stream().
                flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("\nTOP 10 EPISÓDIOS: ");
        eps.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(10)
                .map(e -> e.title().toUpperCase())
                .forEach(System.out::println);

        //para cada episódio data, filtra todos em que o rating é diferente de N/A
        //organiza pela pela avaliação(rating), em ordem decrescente,
        //pega os 5 primeiros e exibe na tela


//        List<Episodes> episodes = seasons
//                .stream()
//                .flatMap(t -> t.episodes().stream().map(ed -> new Episodes(t.number(), ed)))
//                .collect(Collectors.toList());
//        episodes.forEach(System.out::println);

        //stream de seasons, para cada temporada pega os episodios e transforma em outra stream e para
        //cada episodio pega o numero da temporada e cria novas instancias da classe de manipulação
        //Episodes e coleta dentro de uma lista


//        List<Episodes> bestRating = episodes.stream()
//                .sorted(Comparator.comparing(Episodes::getRate).reversed())
//                .limit(5)
//                .collect(Collectors.toList());
//        bestRating.forEach(System.out::println);

//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = reader.nextInt();
//        reader.nextLine();
//
//        LocalDate dateSearch = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes.stream().filter(e -> e.getDate() != null && e.getDate().isAfter(dateSearch))
//                .forEach(e -> System.out.println(
//                        "Temporada " + e.getSeason() + " Episódio: " + e.getNumEpisode() +
//                                " Data de Lançamento: " + e.getDate().format(fmt)
//                ));



    }
}
