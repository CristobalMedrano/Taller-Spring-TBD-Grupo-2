package tbd.group2.spring.backend.sakila.Rest;

import org.hibernate.annotations.SortNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbd.group2.spring.backend.sakila.Entities.Actor;
import tbd.group2.spring.backend.sakila.Entities.Film;
import tbd.group2.spring.backend.sakila.Repository.ActorRepository;
import tbd.group2.spring.backend.sakila.Repository.FilmRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
@CrossOrigin
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
    public ResponseEntity create(@RequestBody Actor actor)
    {
        String firstName = actor.getFirstName();
        String lastName = actor.getLastName();
        if (firstName == null || lastName == null)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("insufficient actor information.");
        }
        else
            {
                if (null == actorRepository.findActorByFirstNameAndLastName(firstName, lastName))
                {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    actor.setLastUpdate(timestamp);
                    actorRepository.save(actor);
                    System.out.println("Actor "+firstName+" "+lastName+" saved in sakila-db");
                    return ResponseEntity.ok("Actor "+firstName+" "+lastName+" saved in sakila-db");
                }
                else
                {
                    System.out.println("Actor "+firstName+" "+lastName+" already in sakila-db");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Actor "+firstName+" "+lastName+" already in sakila-db");
                }
            }

    }

    /* SHOW (READ) */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Actor> show(@PathVariable Integer id)
    {
        Actor actor = actorRepository.findActorByActorId(id);
        if (null == actor)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        else
            {
                return ResponseEntity.ok(actor);
            }
    }

    /* UPDATE */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable ("id") Integer id, @RequestBody Actor actor)
    {
        if (null == actorRepository.findActorByActorId(id))
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Actor not found.");
        }
        else
            {
                String firstName = actor.getFirstName();
                String lastName = actor.getLastName();
                if (null == actorRepository.findActorByFirstNameAndLastName(firstName, lastName))
                {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Actor updatedActor = actorRepository.findActorByActorId(id);
                    updatedActor.setFirstName(actor.getFirstName());
                    updatedActor.setLastName(actor.getLastName());
                    updatedActor.setLastUpdate(timestamp);
                    System.out.println("Actor "+updatedActor.getFirstName()+" "+updatedActor.getLastName()+" updated");
                    this.actorRepository.save(updatedActor);
                    return ResponseEntity.ok("Actor "+updatedActor.getFirstName()+" "+updatedActor.getLastName()+" updated");
                }
                else
                    {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Actor already added.");
                    }
            }
    }

    /* DELETE */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable ("id") Integer id)
    {
        Actor actor = actorRepository.findActorByActorId(id);
        if (null == actor)
        {
            System.out.println("Actor not found");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Actor not found");
        }
        else
        {
            System.out.println("Actor id: "+id+" deleted");
            actorRepository.delete(actor);
            return ResponseEntity.ok("Actor id: "+id+" deleted");
        }

    }

    /* FIND FILMS BY ACTOR */
    @SortNatural
    @RequestMapping(value = "/{id}/films", method = RequestMethod.GET)
    public ResponseEntity<List> getFilmsByActor(@PathVariable("id") Integer id)
    {
        if (null != actorRepository.findActorByActorId(id))
        {
            Set<Film> films = actorRepository.findActorByActorId(id).getFilms();
            List<Film> sortedFilms = new ArrayList<>(films);
            Collections.sort(sortedFilms, (Film f1, Film f2) -> f1.getFilmId()-f2.getFilmId());
            return ResponseEntity.ok(sortedFilms);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    /* SAVE AN ACTOR WITH FILM */
    @PostMapping("/{idA}/films/{idF}")
    @ResponseBody
    public ResponseEntity match (@PathVariable("idA") Integer idA, @PathVariable("idF") Integer idF) {

        Actor actor = actorRepository.findActorByActorId(idA);
        Film film = filmRepository.findFilmByFilmId(idF);
        if (actor != null && film != null) {
            actor.getFilms().add(film);
            film.getActors().add(actor);
            actorRepository.save(actor);
            return ResponseEntity.ok("Actor id: "+idA+" with Film id: "+idF+" saved.");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
