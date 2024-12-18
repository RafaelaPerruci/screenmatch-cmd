package br.com.rafaelaperruci.screenmatch_cmd_line.repository;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.Category;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

   Optional<Serie> findByTitleContainingIgnoreCase(String serieName);

   List<Serie> findByActorsContainingIgnoreCaseAndRateGreaterThanEqual(String name, Double rate);

   List<Serie> findTop5ByOrderByRateDesc();
   List<Serie> findByGenre(Category category);
   List<Serie> findByTotalSeasonsLessThanEqualAndAndRateGreaterThanEqual(Integer total, Double rate);

//   @Query(value = "SELECT * FROM series s WHERE s.total_seasons <=4 AND s.rate >= 8.0", nativeQuery = true)
//   List<Serie> seriesForSeasonAndEspecificRate();
   @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :total AND s.rate >= :rate")
   List<Serie> seriesForSeasonAndEspecificRate(Integer total, Double rate);
}
