package br.com.rafaelaperruci.screenmatch_cmd_line.models.principal;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.EpisodeData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeasonData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeriesData;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.ConsumerAPI;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.DataParser;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.IDataParser;

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
    List<SeriesData> series = new ArrayList<>();


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
        series.forEach(System.out::println);
    }

    private void fetchWebSerie(){
        SeriesData serie = getWebSerie();
        series.add(serie);
        System.out.println(serie);

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

        IDataParser iDataParser = new IDataParser() {
            @Override
            public <T> T fromObject(String json, Class<T> obj) {
                return null;
            }
        };
    }


}
