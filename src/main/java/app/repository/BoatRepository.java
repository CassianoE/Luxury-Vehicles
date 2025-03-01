package app.repository;

import app.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  BoatRepository extends JpaRepository<Boat, Long> {

   public List<Boat> findByModel(String model);

   public List<Boat> findByLengthGreaterThanEqual(int length);

   @Query("FROM Boat b WHERE b.year = :year")
   List<Boat> findByYear(@Param("year") int year);

}
