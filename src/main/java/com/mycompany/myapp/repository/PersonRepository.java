package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Person entity.
 */
public interface PersonRepository extends JpaRepository<Person,Long>{

    @Query("select person from Person person left join fetch person.pets where person.id =:id")
    Person findOneWithEagerRelationships(@Param("id") Long id);

}
