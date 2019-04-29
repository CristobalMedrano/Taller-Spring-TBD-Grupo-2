package tbd.group2.spring.backend.sakila.Rest;

import org.hibernate.annotations.SortNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbd.group2.spring.backend.sakila.Entities.Actor;
import tbd.group2.spring.backend.sakila.Entities.Film;
import tbd.group2.spring.backend.sakila.Repository.ActorRepository;
import tbd.group2.spring.backend.sakila.Repository.FilmRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/films")
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ActorRepository actorRepository;

    /* INDEX */
    @GetMapping("")
    public List<Film> index(){
        return filmRepository.findAll();
    }

    /* SHOW (READ) */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Film> show(@PathVariable Integer id)
    {
        Film film = filmRepository.findFilmByFilmId(id);
        if (null == film)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        else
        {
            return ResponseEntity.ok(film);
        }
    }

    /* FIND ACTORS BY FILM */
    @SortNatural
    @RequestMapping(value = "/{id}/actors", method = RequestMethod.GET)
    public ResponseEntity<List> getActorsByFilm(@PathVariable("id") Integer id)
    {
        if (null != filmRepository.findFilmByFilmId(id))
        {
            Set<Actor> actors = filmRepository.findFilmByFilmId(id).getActors();
            List<Actor> sortedActors = new ArrayList<>(actors);
            Collections.sort(sortedActors, (Actor a1, Actor a2) -> a1.getActorId()-a2.getActorId());
            return ResponseEntity.ok(sortedActors);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    /* SAVE A FILM WITH ACTOR */
    @PostMapping("/{idF}/actors/{idA}")
    @ResponseBody
    public ResponseEntity match (@PathVariable("idF") Integer idF, @PathVariable("idA") Integer idA) {

        Actor actor = actorRepository.findActorByActorId(idA);
        Film film = filmRepository.findFilmByFilmId(idF);
        if (actor != null && film != null) {
            actor.getFilms().add(film);
            film.getActors().add(actor);
            actorRepository.save(actor);
            return ResponseEntity.ok("Films id: "+idF+" with Actor id: "+idA+" saved.");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
