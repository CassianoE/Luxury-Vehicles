package app.service;

import app.entity.Owner;
import app.messages.ErrorMessages;
import app.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public Owner save(Owner owner) {

        boolean isIncomplete = false;

        if (owner.getPhone() == null || owner.getPhone().isEmpty() ||
            owner.getCpf() == null || owner.getCpf().isEmpty() ||
            owner.getName() == null || owner.getName().isEmpty()) {
            isIncomplete = true;
        }

        owner.setStatusRegister(isIncomplete ? "INCOMPLETO" : "COMPLETO");

        return ownerRepository.save(owner);
    }

    public Owner findById(Long id) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        return ownerOptional.orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + id));
    }

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    public Owner update(Long id, Owner owner) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        if (ownerOptional.isPresent()) {
            Owner existingOwner = ownerOptional.get();
            if (owner.getName() != null) existingOwner.setName(owner.getName());
            if (owner.getCpf() != null) existingOwner.setCpf(owner.getCpf());
            if (owner.getEmail() != null) existingOwner.setEmail(owner.getEmail());
            if (owner.getPhone() != null) existingOwner.setPhone(owner.getPhone());
            return ownerRepository.save(existingOwner);
        } else {
            throw new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + id);
        }
    }

    public void delete(Long id) {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
        } else {
            throw new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + id);
        }
    }
}