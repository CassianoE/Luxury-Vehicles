package app.repository;

import app.entity.Boat;
import app.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {


    public List<Car> findByModel(String model);

    public List<Car> findByFuelType(String fuelType);
}
