package br.com.rafaelaperruci.screenmatch_cmd_line.repository;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
