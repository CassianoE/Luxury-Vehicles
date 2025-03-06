package app.service;

import app.entity.Motorcycle;
import app.entity.Owner;
import app.messages.ErrorMessages;
import app.repository.MotorcycleRepository;
import app.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public String save(Motorcycle motorcycle) {
        if (motorcycle.getOwner() != null && motorcycle.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(motorcycle.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + motorcycle.getOwner().getId()));
            motorcycle.setOwner(ownerFromDb);
        }
        this.motorcycleRepository.save(motorcycle);

        return "Moto criada com sucesso";
    }

    public Motorcycle findById(Long id) {
        return motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.MOTORCYCLE_NOT_FOUND + id));
    }

    public List<Motorcycle> findAll() {
        return motorcycleRepository.findAll();
    }

    public String update(Long id, Motorcycle motorcycle) {
        Optional<Motorcycle> motorcycleOptional = motorcycleRepository.findById(id);
        if (motorcycleOptional.isPresent()) {
            Motorcycle existingMotorcycle = motorcycleOptional.get();
            if (motorcycle.getBrand() != null) existingMotorcycle.setBrand(motorcycle.getBrand());
            if (motorcycle.getModel() != null) existingMotorcycle.setModel(motorcycle.getModel());
            if (motorcycle.getYear() != 0) existingMotorcycle.setYear(motorcycle.getYear());
            if (motorcycle.getPrice() != 0) existingMotorcycle.setPrice(motorcycle.getPrice());
            if (motorcycle.getEngineDisplacement() != 0) existingMotorcycle.setEngineDisplacement(motorcycle.getEngineDisplacement());
            if (motorcycle.getBikeType() != null) existingMotorcycle.setBikeType(motorcycle.getBikeType());
            if (motorcycle.getOwner() != null) {
                if (motorcycle.getOwner().getId() == null) {
                    return "O ID do proprietário não pode ser nulo";
                }

                Optional<Owner> ownerOptional = ownerRepository.findById(motorcycle.getOwner().getId());
                if (ownerOptional.isEmpty()) {
                    return "Proprietário com ID " + motorcycle.getOwner().getId() + " não encontrado";
                }

                existingMotorcycle.setOwner(ownerOptional.get());
            }
            this.motorcycleRepository.save(existingMotorcycle);
            return "Moto atualizado com sucesso";
        } else {
            return "Moto com ID " + id + " não encontrado";
        }
    }

    public String delete(Long id) {
       Optional <Motorcycle> motorcycleOptional = motorcycleRepository.findById(id);
         if (motorcycleOptional.isPresent()) {
              this.motorcycleRepository.deleteById(id);
              return "Moto deletada com sucesso";
         } else {
              return "Moto com ID " + id + " não encontrada";
         }
    }

    public List<Motorcycle> findByModel(String model) {
        List <Motorcycle> motorcycleList = motorcycleRepository.findByModel(model);
        if (motorcycleList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.MOTORCYCLE_NOT_FOUND + model);
        }
        return this.motorcycleRepository.findByModel(model);
    }

    public List<Motorcycle> findByBikeType(String bikeType) {
        List <Motorcycle> motorcycleList = motorcycleRepository.findByBikeType(bikeType);
        if (motorcycleList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.MOTORCYCLE_NOT_FOUND + bikeType);
        }
        return this.motorcycleRepository.findByBikeType(bikeType);
    }
}