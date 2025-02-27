package app.repository;


import app.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    public List<Motorcycle> findByModel(String model);

    public List<Motorcycle> findByBikeType(String bikeType);

}
