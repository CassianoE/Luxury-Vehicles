package app.repository;

import app.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findByNameIgnoreCaseContaining(String name);
    List<Owner> findByEmailIgnoreCase(String email);

    @Query("SELECT o FROM Owner o WHERE o.cpf = :cpf")
    List<Owner> findByCpf(@Param("cpf") String cpf);

}
