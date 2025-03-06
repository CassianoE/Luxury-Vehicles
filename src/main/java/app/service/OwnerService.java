package app.service;

import app.entity.Owner;
import app.exeption.ResourceNotFoundException;
import app.messages.ErrorMessages;
import app.repository.OwnerRepository;
import app.utils.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public String save(Owner owner) {

        //  Remove caracteres especiais do CPF antes de validar
        String cpf = owner.getCpf().replaceAll("[^0-9]", "");

        //  Valida o CPF antes de salvar
        if (!CpfValidator.isValidCPF(cpf)) {
            return "CPF inválido!";
        }

        //  Verifica se já existe alguém com os mesmos dados
        if (ownerRepository.existsByCpf(cpf)) {
            return "Já existe um proprietário cadastrado com este CPF!";
        }
        if (owner.getEmail() != null && ownerRepository.existsByEmail(owner.getEmail())) {
            return "Já existe um proprietário cadastrado com este E-mail!";
        }
        if (owner.getPhone() != null && ownerRepository.existsByPhone(owner.getPhone())) {
            return "Já existe um proprietário cadastrado com este Telefone!";
        }

        //  Define status de cadastro baseado na presença de telefone e e-mail
        boolean isIncomplete = owner.getPhone() == null || owner.getPhone().isEmpty() ||
                owner.getEmail() == null || owner.getEmail().isEmpty();
        owner.setStatusRegister(isIncomplete ? "INCOMPLETO" : "COMPLETO");


        ownerRepository.save(owner);

        return isIncomplete
                ? "Cadastro incompleto! Quando possível, atualize o cadastro com e-mail ou telefone."
                : "Proprietário criado com sucesso!";
    }

    public Owner findById(Long id) {
    return ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proprietário com ID " + id + " não foi encontrado"));
}

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    public String update(Long id, Owner owner) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);

        if (ownerOptional.isEmpty()) {
            return "Proprietário com ID " + id + " não encontrado";
        }

        Owner existingOwner = ownerOptional.get();

        //  Verifica se está tentando atualizar para um CPF inválido
        if (owner.getCpf() != null) {
            String cpf = owner.getCpf().replaceAll("[^0-9]", ""); // Remove caracteres especiais
            if (!CpfValidator.isValidCPF(cpf)) {
                return "CPF inválido!";
            }

            //  Verifica se o novo CPF já existe em outro registro
            if (!existingOwner.getCpf().equals(cpf) && ownerRepository.existsByCpf(cpf)) {
                return "Já existe um proprietário cadastrado com este CPF!";
            }

            existingOwner.setCpf(cpf);
        }

        //  Verifica se o novo email já está em uso por outro registro
        if (owner.getEmail() != null) {
            if (!owner.getEmail().equals(existingOwner.getEmail()) && ownerRepository.existsByEmail(owner.getEmail())) {
                return "Já existe um proprietário cadastrado com este E-mail!";
            }
            existingOwner.setEmail(owner.getEmail());
        }

        //  Verifica se o novo telefone já está em uso por outro registro
        if (owner.getPhone() != null) {
            if (!owner.getPhone().equals(existingOwner.getPhone()) && ownerRepository.existsByPhone(owner.getPhone())) {
                return "Já existe um proprietário cadastrado com este Telefone!";
            }
            existingOwner.setPhone(owner.getPhone());
        }

        //  Atualiza outros dados
        if (owner.getName() != null) existingOwner.setName(owner.getName());

        //  Atualiza o status do cadastro com base na presença de telefone e e-mail
        boolean isIncomplete = (existingOwner.getPhone() == null || existingOwner.getPhone().isEmpty()) ||
                (existingOwner.getEmail() == null || existingOwner.getEmail().isEmpty());
        existingOwner.setStatusRegister(isIncomplete ? "INCOMPLETO" : "COMPLETO");

        ownerRepository.save(existingOwner);

        return isIncomplete
                ? "Cadastro atualizado, mas incompleto! Quando possível, atualize o cadastro com e-mail ou telefone."
                : "Proprietário atualizado com sucesso!";
    }

    public String delete(Long id) {
    Optional<Owner> ownerOptional = ownerRepository.findById(id);
    if (ownerOptional.isPresent()) {
        this.ownerRepository.deleteById(id);
        return "Propietario deletado com sucesso";
    } else {
        return "Propietario com " + id + " não encontrado";
    }
}

    public List<Owner> findByName(String name) {
        List <Owner> ownerList = ownerRepository.findByName(name);
        if (ownerList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + name + " name");
        }
        return this.ownerRepository.findByName(name);
    }

    public List<Owner> findByEmail(String email) {
        List <Owner> ownerList = ownerRepository.findByEmail(email);
        if (ownerList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + email + " email");
        }
        return this.ownerRepository.findByEmail(email);
    }
    

}