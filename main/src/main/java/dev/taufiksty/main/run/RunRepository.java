package dev.taufiksty.main.run;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RunRepository extends ListCrudRepository<Run, Integer> {

//    @Query("SELECT * FROM run WHERE location = :location") // Custom query
    List<Run> findAllByLocation(Location location);

}
