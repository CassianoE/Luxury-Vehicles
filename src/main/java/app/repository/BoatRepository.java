package app.repository;

import app.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  BoatRepository extends JpaRepository<Boat, Long> {

   public List<Boat> findByModel(String model);

   @Query("FROM Boat b WHERE b.year >:year")
   public List<Boat> findByYear(int year);


}
