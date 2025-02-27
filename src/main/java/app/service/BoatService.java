package app.service;

import app.entity.Boat;
import app.entity.Owner;
import app.messages.ErrorMessages;
import app.repository.BoatRepository;
import app.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatService {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Boat save(Boat boat) {
        if (boat.getOwner() != null && boat.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(boat.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + boat.getOwner().getId()));
            boat.setOwner(ownerFromDb);
        }
        return boatRepository.save(boat);
    }

    public Boat findById(Long id) {
        Optional<Boat> boatOptional = boatRepository.findById(id);
        return boatOptional.orElseThrow(() -> new RuntimeException(ErrorMessages.BOAT_NOT_FOUND + id));
    }

    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    public Boat update(Long id, Boat boat) {
        Optional<Boat> boatOptional = boatRepository.findById(id);
        if (boatOptional.isPresent()) {
            Boat existingBoat = boatOptional.get();
            if (boat.getBrand() != null) existingBoat.setBrand(boat.getBrand());
            if (boat.getModel() != null) existingBoat.setModel(boat.getModel());
            if (boat.getYear() != 0) existingBoat.setYear(boat.getYear());
            if (boat.getPrice() != 0) existingBoat.setPrice(boat.getPrice());
            if (boat.getLength() != 0) existingBoat.setLength(boat.getLength());
            if (boat.getHullType() != null) existingBoat.setHullType(boat.getHullType());
            if (boat.getOwner() != null) existingBoat.setOwner(boat.getOwner());
            return boatRepository.save(existingBoat);
        } else {
            throw new RuntimeException(ErrorMessages.BOAT_NOT_FOUND + id);
        }
    }

    public void delete(Long id) {
        if (boatRepository.existsById(id)) {
            boatRepository.deleteById(id);
        } else {
            throw new RuntimeException(ErrorMessages.BOAT_NOT_FOUND + id);
        }
    }
}