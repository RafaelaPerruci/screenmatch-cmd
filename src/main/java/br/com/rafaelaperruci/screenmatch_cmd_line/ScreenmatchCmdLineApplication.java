package br.com.rafaelaperruci.screenmatch_cmd_line;

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
	}
}
