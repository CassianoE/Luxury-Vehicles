package app.service;

import app.entity.Owner;
import app.entity.Plane;
import app.messages.ErrorMessages;
import app.repository.OwnerRepository;
import app.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaneService {

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Plane save(Plane plane) {
        if (plane.getOwner() != null && plane.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(plane.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + plane.getOwner().getId()));
            plane.setOwner(ownerFromDb);
        }
        return planeRepository.save(plane);
    }

    public Plane findById(Long id) {
        Optional<Plane> planeOptional = planeRepository.findById(id);
        return planeOptional.orElseThrow(() -> new RuntimeException(ErrorMessages.PLANE_NOT_FOUND + id));
    }

    public List<Plane> findAll() {
        return planeRepository.findAll();
    }

    public Plane update(Long id, Plane plane) {
        Optional<Plane> planeOptional = planeRepository.findById(id);
        if (planeOptional.isPresent()) {
            Plane existingPlane = planeOptional.get();
            if (plane.getBrand() != null) existingPlane.setBrand(plane.getBrand());
            if (plane.getModel() != null) existingPlane.setModel(plane.getModel());
            if (plane.getYear() != 0) existingPlane.setYear(plane.getYear());
            if (plane.getPrice() != 0) existingPlane.setPrice(plane.getPrice());
            if (plane.getMaxAltitude() != 0) existingPlane.setMaxAltitude(plane.getMaxAltitude());
            if (plane.getEngineCount() != 0) existingPlane.setEngineCount(plane.getEngineCount());
            if (plane.getOwner() != null) existingPlane.setOwner(plane.getOwner());
            return planeRepository.save(existingPlane);
        } else {
            throw new RuntimeException(ErrorMessages.PLANE_NOT_FOUND + id);
        }
    }

    public void delete(Long id) {
        if (planeRepository.existsById(id)) {
            planeRepository.deleteById(id);
        } else {
            throw new RuntimeException(ErrorMessages.PLANE_NOT_FOUND + id);
        }
    }

    public List<Plane> findByModel(String model) {
        List <Plane> planeList = planeRepository.findByModel(model);
        if (planeList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.PLANE_NOT_FOUND + model);
        }
        return this.planeRepository.findByModel(model);
    }

    public List<Plane> findByEngineCount(int engineCount) {
        List <Plane> planeList = planeRepository.findByEngineCount(engineCount);
        if (planeList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.PLANE_NOT_FOUND + engineCount);
        }
        return this.planeRepository.findByEngineCount(engineCount);
    }
}