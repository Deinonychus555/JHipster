package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Engine entity.
 */
public interface EngineRepository extends JpaRepository<Engine,Long>{

}
