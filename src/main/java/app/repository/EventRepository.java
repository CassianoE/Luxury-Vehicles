package app.repository;

import app.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Car;
import java.util.List;

public interface EventRepository extends JpaRepository<Event , Long> {

    List<Event> findByCarsContaining(Car car);

}
