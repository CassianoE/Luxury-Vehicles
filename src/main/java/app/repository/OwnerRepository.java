package app.repository;

import app.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {



        boolean existsByCpf(String cpf);

        boolean existsByEmail(String email);

        boolean existsByPhone(String phone);


    List<Owner> findByName(String name);
    List<Owner> findByEmail(String email);

}
