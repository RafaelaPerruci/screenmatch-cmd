package br.com.rafaelaperruci.screenmatch_cmd_line.repository;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.Category;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

   Optional<Serie> findByTitleContainingIgnoreCase(String serieName);

   List<Serie> findByActorsContainingIgnoreCaseAndRateGreaterThanEqual(String name, Double rate);

   List<Serie> findTop5ByOrderByRateDesc();
   List<Serie> findByGenre(Category category);
   List<Serie> findByTotalSeasonsLessThanEqualAndAndRateGreaterThanEqual(Integer total, Double rate);
}
