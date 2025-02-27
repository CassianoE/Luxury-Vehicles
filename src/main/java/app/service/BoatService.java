package app.service;

import app.entity.Boat;
import app.entity.Owner;
import app.messages.ErrorMessages;
import app.repository.BoatRepository;
import app.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

        if (boat.getLength() <= 10) {
            throw new RuntimeException("Não é possível cadastrar barco com comprimento menor ou igual a 10 metros");
        }

        if ("Jorge".equals(boat.getOwner().getName())) {
           throw new RuntimeException("Não é possível cadastrar barco para o proprietário Jorge");
        }

        if ("Catamarã".equals(boat.getHullType()) && (boat.getPrice() < 50000 || boat.getPrice() > 200000)) {
            throw new RuntimeException("Barcos do tipo Catamarã devem ter um preço entre 50.000 e 200.000");
        }

        LocalDate now = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(boat.getRegistrationDate(), now);
        if (daysBetween > 30) {
            throw new RuntimeException("A data de registro do barco não pode ser superior a 30 dias a partir da data atual");
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

    public List<Boat> findByModel(String model) {
       return this.boatRepository.findByModel(model);
    }

    public List<Boat> findByYear(int  year) {
        return this.boatRepository.findByYear(year);
    }

    public List<Boat> findByLengthGreaterThanEqual(int length) {
        return this.boatRepository.findByLengthGreaterThanEqual(length);
    }
}