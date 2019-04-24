package tbd.group2.spring.backend.sakila.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tbd.group2.spring.backend.sakila.entities.Actor;
import tbd.group2.spring.backend.sakila.repository.ActorRepository;

@RestController
@RequestMapping("/actors")
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Actor> getAllUsers(){
        return actorRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Actor findOne(@PathVariable("id") Integer id){
        return actorRepository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Actor create(@RequestBody Actor resource)
    {
        return actorRepository.save(resource);
    }

}
