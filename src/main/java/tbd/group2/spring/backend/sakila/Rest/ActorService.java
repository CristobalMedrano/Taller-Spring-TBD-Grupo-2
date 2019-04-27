package tbd.group2.spring.backend.sakila.Rest;

import org.hibernate.annotations.SortNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tbd.group2.spring.backend.sakila.Entities.Actor;
import tbd.group2.spring.backend.sakila.Entities.Film;
import tbd.group2.spring.backend.sakila.Repository.ActorRepository;
import tbd.group2.spring.backend.sakila.Repository.FilmRepository;

import java.util.*;
@CrossOrigin(origins = "Http://localhost:8080")
@RestController
@RequestMapping("/actors")
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private FilmRepository filmRepository;

    /* INDEX */
    @GetMapping("")
    public List<Actor> index(){
        return actorRepository.findAll();
    }

    /* CREATE */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Actor actor)
    {
        String firstName = actor.getFirstName();
        String lastName = actor.getLastName();
        if (null == actorRepository.findActorByFirstNameAndLastName(firstName, lastName))
        {
            System.out.println("Actor "+firstName+" "+lastName+" saved in sakila-db");
            actorRepository.save(actor);
        }
        else
        {
            System.out.println("Actor "+firstName+" "+lastName+" already in sakila-db");
        }
    }

    /* SHOW (READ) */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Actor show(@PathVariable Integer id)
    {
        return this.actorRepository.findActorByActorId(id);
    }

    /* UPDATE */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable ("id") Integer id, @RequestBody Actor actor)
    {
        String firstName = actor.getFirstName();
        String lastName = actor.getLastName();
        if (null == actorRepository.findActorByFirstNameAndLastName(firstName, lastName))
        {

            Actor updatedActor = actorRepository.findActorByActorId(id);
            updatedActor.setFirstName(actor.getFirstName());
            updatedActor.setLastName(actor.getLastName());
            updatedActor.setLastUpdate(actor.getLastUpdate());
            System.out.println("Actor "+updatedActor.getFirstName()+" "+updatedActor.getLastName()+" updated");
            this.actorRepository.save(updatedActor);
        }
    }

    /* DELETE */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ("id") Integer id)
    {
        Actor actor = actorRepository.findActorByActorId(id);
        if (null == actor)
        {
            System.out.println("Actor not found");
        }
        else
        {
            System.out.println("Actor id: "+id+" deleted");
            actorRepository.delete(actor);
        }

    }
    /* ENCONTRAR PELICULAS POR ACTOR */
    @SortNatural
    @RequestMapping(value = "/{id}/films", method = RequestMethod.GET)
    public List<Film> obtenerPeliculasDeActor (@PathVariable("id") Integer id) {

        Set<Film> films = actorRepository.findActorByActorId(id).getFilms();
        List<Film> sortedFilms = new ArrayList<>(films);
        Collections.sort(sortedFilms, (Film f1, Film f2) -> f1.getFilmId()-f2.getFilmId());
        return sortedFilms;
    }

    /* RELACIONAR ACTOR CON PELICULA */
    @PostMapping("/{idA}/films/{idP}")
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

}
