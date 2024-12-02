package br.com.rafaelaperruci.screenmatch_cmd_line;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.EpisodeData;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeriesData;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.ConsumerAPI;
import br.com.rafaelaperruci.screenmatch_cmd_line.services.DataParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchCmdLineApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchCmdLineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("\nHello World! Overwritten task of main method. Projeto Spring sem Web <3");
		String url = "http://www.omdbapi.com/?apikey=f33cc024&t=the+walking+dead";

		ConsumerAPI consumerAPI = new ConsumerAPI();
		String json = consumerAPI.getData(url);
		System.out.println(json);

		DataParser dataParser = new DataParser();

		SeriesData serie1 = dataParser.fromObject(json, SeriesData.class);
		json = consumerAPI.getData("http://www.omdbapi.com/?apikey=f33cc024&t=the+walking+dead&season=8&episode=7");
		EpisodeData episode1 = dataParser.fromObject(json, EpisodeData.class);

		System.out.println(serie1);
		System.out.println(episode1);
	}
}
