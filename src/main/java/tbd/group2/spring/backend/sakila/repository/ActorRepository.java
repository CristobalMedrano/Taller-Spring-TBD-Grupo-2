package tbd.group2.spring.backend.sakila.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import tbd.group2.spring.backend.sakila.entities.Actor;

public interface ActorRepository extends PagingAndSortingRepository<Actor, Integer> {
}
