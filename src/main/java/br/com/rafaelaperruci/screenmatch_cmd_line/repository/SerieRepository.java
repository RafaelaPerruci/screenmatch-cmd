package br.com.rafaelaperruci.screenmatch_cmd_line.repository;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.Category;
import br.com.rafaelaperruci.screenmatch_cmd_line.models.Episodes;
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

   @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE e.title ILIKE %:excerpt%")
   List<Episodes> findEpisodesByExcerpt(String excerpt);

   @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie ORDER BY e.rate DESC LIMIT 5")
   List<Episodes> findTop5EpisodesBySerie(Serie serie);

   @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie AND YEAR(e.date) >= :year")
   List<Episodes> findEpisodesByYear(Serie serie, Integer year);


   @Query("SELECT s FROM Serie s " + "JOIN s.episodes e " + "GROUP BY s " + "ORDER BY MAX(e.date) DESC LIMIT 5")
   List<Serie> findTop5LastReleases();

   //   List<Serie> findTop5ByOrderByEpisodesDateDesc();

   Optional<Serie> findById(Long id);

   @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s.id = :id AND e.season = :numero")
   List<Episodes> getEpisodesByNum(Long id, Integer numero);

   List<Serie> findByGenre(String genre);
}
