package br.com.rafaelaperruci.screenmatch_cmd_line.principal;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.*;
import br.com.rafaelaperruci.screenmatch_cmd_line.repository.SerieRepository;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.ConsumerAPI;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.DataParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static final String URL = "http://www.omdbapi.com/?apikey=f33cc024&t=";
    private final ConsumerAPI consumerAPI = new ConsumerAPI();
    private DataParser dataParser = new DataParser();
    private Scanner scanner = new Scanner(System.in);
    List<SeriesData> seriesData = new ArrayList<>();

    private SerieRepository serieRepository;

    private List<Serie> listSeriesGlobal = new ArrayList<>();

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
                    4 - Buscar série pelo nome
                    5 - Buscar séries por ator
                    6 - Top 5 séries 
                    7 - Buscar séries por categoria
                    8 - Buscar séries por número de temporadas e bem avaliadas
                                    
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
                case 4:
                    findSerieByTitle();
                    break;
                case 5:
                    findSerieByActor();
                    break;
                case 6:
                    findTopFive();
                    break;
                    
                case 7:
                    findSeriesByCategory();
                    break;
                case 8:
                    findSeriesTotalSeasonsWellRated();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }

        }

    }

    private void findSeriesTotalSeasonsWellRated() {
        System.out.println("Digite o número máximo de total que deseja buscar séries correspondentes: ");
        var total = scanner.nextInt();
        System.out.println("Digite a avaliação mínima que deseja para descobrir séries: ");
        var rate = scanner.nextDouble();
//        List<Serie> series = serieRepository.findByTotalSeasonsLessThanEqualAndAndRateGreaterThanEqual(total, rate);
        List<Serie> series = serieRepository.seriesForSeasonAndEspecificRate(total, rate);
        System.out.println("********** Filtered series " + "*".repeat(10));
        series.forEach(s -> System.out.println(s.getTitle() + " - Avaliação: " + s.getRate()));
        System.out.println();
    }

    private void findSeriesByCategory() {

        System.out.println("Digite o gênero que deseja buscar: ");
        var nameCategory = scanner.nextLine();
        Category category = Category.fromPortuguese(nameCategory);
        List<Serie> serieGenre = serieRepository.findByGenre(category);
        System.out.println("Séries de " + nameCategory + ": \n");
        serieGenre.forEach(s -> System.out.println(s.getTitle()));
    }

    private void findTopFive() {
        List<Serie> seriesTop = serieRepository.findTop5ByOrderByRateDesc();
        seriesTop.forEach(s -> System.out.println(s.getTitle() + " - Avaliação: " + s.getRate()));
    }

    private void findSerieByActor() {
        System.out.println("Qual o nome do ator que deseja buscar? ");
        var name = scanner.nextLine();
//        List<Serie> findSeries = serieRepository.findByActorsContainingIgnoreCase(name);
        List<Serie> findSeries = serieRepository.findByActorsContainingIgnoreCaseAndRateGreaterThanEqual(name, 8.5);
        findSeries.forEach(s -> System.out.println(s.getTitle() + " - Avaliação: " + s.getRate()));

    }

    private void findSerieByTitle() {
        System.out.println("Digite o nome da serie que deseja buscar: ");
        var name = scanner.nextLine();
        Optional<Serie> serieFetched = serieRepository.findByTitleContainingIgnoreCase(name);
        if (serieFetched.isPresent()){
            System.out.println(serieFetched.get());
        }else {
            System.out.println("Série não encontrada.");
        }

    }

    private void listSearchedSeries() {
        listSeriesGlobal = serieRepository.findAll();
        List<Serie> seriesSorted = listSeriesGlobal.stream().sorted(Comparator.comparing(Serie::getGenre))
                .collect(Collectors.toList());
        seriesSorted.forEach(s -> System.out.println(s + "\n"));
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
        listSearchedSeries();
        listSeriesGlobal.forEach(s -> System.out.println(s.getTitle()));
        System.out.println("Escolha uma série pelo nome: ");
        var serieName = scanner.nextLine();

        Optional<Serie> firstSerie = serieRepository.findByTitleContainingIgnoreCase(serieName);

        if (firstSerie.isPresent()) {
            var serieFound = firstSerie.get();
            String title = null;
            try {
                title = URLEncoder.encode(serieFound.getTitle(), StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                System.out.println("Encoding não suportado: " + e.getMessage());
            }
            List<SeasonData> seasons = new ArrayList<>();

            for (int i = 1; i < serieFound.getTotalSeasons(); i++) {
                var json = consumerAPI.getData(URL + title.toLowerCase() + "&season=" + i);
                SeasonData seasonData = dataParser.fromObject(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episodes> episodes = seasons.stream()
                    .flatMap(ds -> ds.episodes().stream().map(e -> new Episodes(ds.number(), e)))
                    .collect(Collectors.toList());

            serieFound.setEpisodes(episodes);
            serieRepository.save(serieFound);


        }else{
            System.out.println("Série não encontrada!");
        }


    }
}
