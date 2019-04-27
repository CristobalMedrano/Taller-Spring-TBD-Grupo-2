package tbd.group2.spring.backend.sakila.Rest;

import org.hibernate.annotations.SortNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbd.group2.spring.backend.sakila.Entities.Actor;
import tbd.group2.spring.backend.sakila.Entities.Film;
import tbd.group2.spring.backend.sakila.Repository.ActorRepository;
import tbd.group2.spring.backend.sakila.Repository.FilmRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "Http://localhost:8080")
@RestController
@RequestMapping("/films")
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ActorRepository actorRepository;
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Film> getAllUsers() {
        return filmRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public  Film findOne(@PathVariable("id") Integer id) {
        return filmRepository.findFilmByFilmId(id);
    }

    /* OBTENER ACTORES DESDE PELICULA */
    /* ENCONTRAR PELICULAS POR ACTOR */
    @SortNatural
    @RequestMapping(value = "/{id}/actors", method = RequestMethod.GET)
    public List<Actor> obtenerActoresDePeliculas (@PathVariable("id") Integer id) {

        Set<Actor> actors = filmRepository.findFilmByFilmId(id).getActors();
        List<Actor> sortedActors = new ArrayList<>(actors);
        Collections.sort(sortedActors, (Actor a1, Actor a2) -> a1.getActorId()-a2.getActorId());
        return sortedActors;
    }
    @PostMapping("/{idP}/actors/{idA}")
    @ResponseBody
    public  String match (@PathVariable("idA") Integer idA, @PathVariable("idP") Integer idP) {
        Actor actor = actorRepository.findActorByActorId(idA);
        Film film = filmRepository.findFilmByFilmId(idP);
        System.out.println(":D");
        if ( actor !=  null &&  film != null) {
            actor.getFilms().add(film);
            film.getActors().add(actor);
            actorRepository.save(actor);
            System.out.println(":D");
            return "OK";
        }
        else
            System.out.println("D:");
        return "MALO";
    }
    /*@RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Film findFilm(@PathVariable("id") Integer id){
        return filmRepository.encontrarPelicula(id);
    }



    @RequestMapping(value = "/{id}/actors", method = RequestMethod.GET)
    @ResponseBody
    public List<Actor> getActorsFilm(@PathVariable("id") Integer id){
        Iterable<Integer> actors_ids = filmActorRepository.encontrarActorDesdeIdPelicula(id);
        List<Actor> actors = new ArrayList();
        for (Integer i: actors_ids
        ) {

            actors.add(actorRepository.findOne(i));
        }
        return actors;
    }*/
}
