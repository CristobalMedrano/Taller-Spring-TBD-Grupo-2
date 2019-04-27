package tbd.group2.spring.backend.sakila.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tbd.group2.spring.backend.sakila.Entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    Film findFilmByFilmId(Integer id);
}
