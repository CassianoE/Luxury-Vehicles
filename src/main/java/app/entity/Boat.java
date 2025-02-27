package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Boat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A marca não pode ser vazia")
    @Size(min = 2, max = 50, message = "A marca deve ter entre 2 e 50 caracteres")
    private String brand;

    @NotBlank(message = "O modelo não pode ser vazio")
    @Size(min = 1, max = 50, message = "O modelo deve ter entre 1 e 50 caracteres")
    private String model;

    @Min(value = 1900, message = "O ano deve ser maior ou igual a 1900")
    @Max(value = 2025, message = "O ano não pode ser maior que 2025")
    private int year;

    @Min(value = 20000, message = "O preço deve ser maior ou igual a 20.000")
    private double price;

    @Min(value = 2, message = "O comprimento deve ser maior ou igual a 2 metros")
    @Max(value = 100, message = "O comprimento não pode exceder 100 metros")
    private double length; // Comprimento em metros (ex.: 12.5)

    @NotBlank(message = "O tipo de casco não pode ser vazio")
    private String hullType; // Tipo de casco (ex.: Monocasco, Catamarã)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @NotNull(message = "O proprietário não pode ser nulo")
    @JsonIgnoreProperties({"cars", "motorcycles", "boats", "planes"}) // Ignorar a relação com a entidade Owner, evitando um loop infinito na serialização JSON
    private Owner owner;

    @NotNull(message = "A data de registro não pode ser nula")
    private LocalDate registrationDate; // Nova coluna para a data de registro
}
