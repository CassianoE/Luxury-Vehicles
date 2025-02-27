package app.repository;

import app.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  BoatRepository extends JpaRepository<Boat, Long> {

    List<Boat> findByBrandIgnoreCaseContaining(String brand);
    List<Boat> findByPriceBetween(double minPrice, double maxPrice);

}
