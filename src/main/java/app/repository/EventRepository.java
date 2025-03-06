package app.repository;

import app.entity.Car;
import app.entity.Event;
import app.entity.EventProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event , Long> {

    List<Event> findByLocation (String location);

    @Query("SELECT e.name AS name, e.date AS date FROM Event e WHERE e.date BETWEEN :startDate AND :endDate")
    List<EventProjection> findEventsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Event> findByCarsContains(Car car);
}
