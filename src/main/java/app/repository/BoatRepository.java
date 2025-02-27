package app.repository;

import app.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BoatRepository extends JpaRepository<Boat, Long> {
}
