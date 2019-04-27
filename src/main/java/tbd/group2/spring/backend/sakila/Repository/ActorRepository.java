package tbd.group2.spring.backend.sakila.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbd.group2.spring.backend.sakila.Entities.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>
{
    Actor findActorByActorId(Integer id);
    Actor findActorByFirstNameAndLastName(String firstName, String lastName);
}
