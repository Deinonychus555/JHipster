package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Animal entity.
 */
public interface AnimalRepository extends JpaRepository<Animal,Long>{

}
