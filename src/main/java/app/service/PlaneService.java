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

    public String save(Plane plane) {
        if (plane.getOwner() != null && plane.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(plane.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + plane.getOwner().getId()));
            plane.setOwner(ownerFromDb);
        }
        this.planeRepository.save(plane);

        return "Avião criado com sucesso";
    }

    public Plane findById(Long id) {
       return planeRepository.findById(id)
         .orElseThrow(() -> new RuntimeException(ErrorMessages.PLANE_NOT_FOUND + id));
    }

    public List<Plane> findAll() {
        return planeRepository.findAll();
    }

    public String update(Long id, Plane plane) {
        Optional<Plane> planeOptional = planeRepository.findById(id);
        if (planeOptional.isPresent()) {
            Plane existingPlane = planeOptional.get();
            if (plane.getBrand() != null) existingPlane.setBrand(plane.getBrand());
            if (plane.getModel() != null) existingPlane.setModel(plane.getModel());
            if (plane.getYear() != 0) existingPlane.setYear(plane.getYear());
            if (plane.getPrice() != 0) existingPlane.setPrice(plane.getPrice());
            if (plane.getMaxAltitude() != 0) existingPlane.setMaxAltitude(plane.getMaxAltitude());
            if (plane.getEngineCount() != 0) existingPlane.setEngineCount(plane.getEngineCount());
            if (plane.getOwner() != null) {
                if (plane.getOwner().getId() == null) {
                    return "O ID do proprietário não pode ser nulo";
                }

                Optional<Owner> ownerOptional = ownerRepository.findById(plane.getOwner().getId());
                if (ownerOptional.isEmpty()) {
                    return "Proprietário com ID " + plane.getOwner().getId() + " não encontrado";
                }

                existingPlane.setOwner(ownerOptional.get());
            }
            this.planeRepository.save(existingPlane);
            return "Avião atualizado com sucesso";

        } else {
            return "Avião com o ID " + id + " não encontrado";
        }
    }

    public String delete(Long id) {
        Optional <Plane> planeOptional = planeRepository.findById(id);
        if (planeOptional.isPresent()) {
            this.planeRepository.deleteById(id);
            return "Avião deletado com sucesso";
        } else {
            return "Avião com o ID " + id + " não encontrado";
        }
    }

    public List<Plane> findByModel(String model) {
        List <Plane> planeList = planeRepository.findByModel(model);
        if (planeList.isEmpty()) {
            throw new RuntimeException("Nenhum Avião encontrado com o modelo "+ model);
        }
        return this.planeRepository.findByModel(model);
    }

    public List<Plane> findByEngineCount(int engineCount) {
        List <Plane> planeList = planeRepository.findByEngineCount(engineCount);
        if (planeList.isEmpty()) {
            throw new RuntimeException("Nenhum Avião encontrado com " + engineCount + " motores");
        }
        return this.planeRepository.findByEngineCount(engineCount);
    }
}