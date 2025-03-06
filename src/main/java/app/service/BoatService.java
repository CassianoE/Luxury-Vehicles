package app.service;

import app.entity.Boat;
import app.entity.Owner;
import app.exeption.ResourceNotFoundException;
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

    public String save(Boat boat) {
        if (boat.getOwner() != null && boat.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(boat.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + boat.getOwner().getId()));
            boat.setOwner(ownerFromDb);
        }
        this.boatRepository.save(boat);
        return "Barco criado com sucesso";
    }

    public Boat findById(Long id) {
        return boatRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Barco com o ID " + id + " não foi encontrado"));
    }

    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    public String update(Long id, Boat boat) {
        Optional<Boat> boatOptional = boatRepository.findById(id);
        if (boatOptional.isPresent()) {
            Boat existingBoat = boatOptional.get();
            if (boat.getBrand() != null) existingBoat.setBrand(boat.getBrand());
            if (boat.getModel() != null) existingBoat.setModel(boat.getModel());
            if (boat.getYear() != 0) existingBoat.setYear(boat.getYear());
            if (boat.getPrice() != 0) existingBoat.setPrice(boat.getPrice());
            if (boat.getLength() != 0) existingBoat.setLength(boat.getLength());
            if (boat.getHullType() != null) existingBoat.setHullType(boat.getHullType());
            if (boat.getOwner() != null) {
                if (boat.getOwner().getId() == null) {
                    return "O ID do proprietário não pode ser nulo";
                }

                Optional<Owner> ownerOptional = ownerRepository.findById(boat.getOwner().getId());
                if (ownerOptional.isEmpty()) {
                    return "Proprietário com ID " + boat.getOwner().getId() + " não encontrado";
                }

                existingBoat.setOwner(ownerOptional.get());
            }
            this.boatRepository.save(existingBoat);
            return "Barco atualizado com sucesso";

        } else {
            return "Barco com o ID " + id + " não encontrado";
        }
    }

    public String delete(Long id) {
        Optional <Boat> boatOptional = boatRepository.findById(id);
        if (boatOptional.isPresent()) {
            this.boatRepository.deleteById(id);
            return "Barco deletado com sucesso";
        } else {
            return "Barco com o ID " + id + " não encontrado";
        }
    }

    public List<Boat> findByModel(String model) {
    List <Boat> boatList = boatRepository.findByModel(model);
    if (boatList.isEmpty()) {
        throw new RuntimeException("Nenhum barco encontrado com o modelo" + model);
    }
        return this.boatRepository.findByModel(model);
    }

    public List<Boat> findByYear(int year) {
        List<Boat> boatList = boatRepository.findByYear(year);
        if (boatList.isEmpty()) {
            throw new RuntimeException("Nenhum barco encontrado para o ano " + year);
        }
        return this.boatRepository.findByYear(year);
    }

    public List<Boat> findByLengthGreaterThanEqual(int length) {
        List <Boat> boatList = boatRepository.findByLengthGreaterThanEqual(length);
        if (boatList.isEmpty()) {
            throw new RuntimeException("Nenhum barco encontrado com o " + length);
        }
        return this.boatRepository.findByLengthGreaterThanEqual(length);
    }
}