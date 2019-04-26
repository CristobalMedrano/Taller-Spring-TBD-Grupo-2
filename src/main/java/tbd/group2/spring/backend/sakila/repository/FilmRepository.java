package tbd.group2.spring.backend.sakila.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tbd.group2.spring.backend.sakila.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    Film findFilmByFilmId(Integer id);
}
