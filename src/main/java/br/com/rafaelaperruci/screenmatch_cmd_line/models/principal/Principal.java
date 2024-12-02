package br.com.rafaelaperruci.screenmatch_cmd_line.models.principal;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.EpisodeData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeasonData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeriesData;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.ConsumerAPI;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.DataParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        seasons.forEach(System.out::println);
        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));






    }
}
