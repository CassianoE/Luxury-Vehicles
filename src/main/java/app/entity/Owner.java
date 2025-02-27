package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String name;

    @NotBlank(message = "O CPF não pode ser vazio")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 caracteres")
    private String cpf;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "O telefone não pode ser vazio")
    @Size(min = 10, max = 15, message = "O telefone deve ter entre 10 e 15 caracteres")
    private String phone;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Car> cars;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Motorcycle> motorcycles;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Plane> planes;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Boat> boats;

    private String statusRegister;


}
