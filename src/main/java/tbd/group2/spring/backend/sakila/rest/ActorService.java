package tbd.group2.spring.backend.sakila.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tbd.group2.spring.backend.sakila.Entities.Actor;
import tbd.group2.spring.backend.sakila.Repository.ActorRepository;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

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

}
