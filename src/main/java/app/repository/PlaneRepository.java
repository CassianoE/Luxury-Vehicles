package app.repository;


import app.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaneRepository extends JpaRepository<Plane, Long> {


    public List<Plane> findByModel(String model);

    public List<Plane> findByEngineCount(String engineCount);

}
