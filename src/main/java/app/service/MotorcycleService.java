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

    public Motorcycle save(Motorcycle motorcycle) {
        if (motorcycle.getOwner() != null && motorcycle.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(motorcycle.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + motorcycle.getOwner().getId()));
            motorcycle.setOwner(ownerFromDb);
        }
        return motorcycleRepository.save(motorcycle);
    }

    public Motorcycle findById(Long id) {
        Optional<Motorcycle> motorcycleOptional = motorcycleRepository.findById(id);
        return motorcycleOptional.orElseThrow(() -> new RuntimeException(ErrorMessages.MOTORCYCLE_NOT_FOUND + id));
    }

    public List<Motorcycle> findAll() {
        return motorcycleRepository.findAll();
    }

    public Motorcycle update(Long id, Motorcycle motorcycle) {
        Optional<Motorcycle> motorcycleOptional = motorcycleRepository.findById(id);
        if (motorcycleOptional.isPresent()) {
            Motorcycle existingMotorcycle = motorcycleOptional.get();
            if (motorcycle.getBrand() != null) existingMotorcycle.setBrand(motorcycle.getBrand());
            if (motorcycle.getModel() != null) existingMotorcycle.setModel(motorcycle.getModel());
            if (motorcycle.getYear() != 0) existingMotorcycle.setYear(motorcycle.getYear());
            if (motorcycle.getPrice() != 0) existingMotorcycle.setPrice(motorcycle.getPrice());
            if (motorcycle.getEngineDisplacement() != 0) existingMotorcycle.setEngineDisplacement(motorcycle.getEngineDisplacement());
            if (motorcycle.getBikeType() != null) existingMotorcycle.setBikeType(motorcycle.getBikeType());
            if (motorcycle.getOwner() != null) existingMotorcycle.setOwner(motorcycle.getOwner());
            return motorcycleRepository.save(existingMotorcycle);
        } else {
            throw new RuntimeException(ErrorMessages.MOTORCYCLE_NOT_FOUND + id);
        }
    }

    public void delete(Long id) {
        if (motorcycleRepository.existsById(id)) {
            motorcycleRepository.deleteById(id);
        } else {
            throw new RuntimeException(ErrorMessages.MOTORCYCLE_NOT_FOUND + id);
        }
    }
}