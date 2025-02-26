package app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {

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

    @Min(value = 10000, message = "O preço deve ser maior ou igual a 10.000")
    private double price;

    @Min(value = 50, message = "A potência deve ser maior ou igual a 50 cavalos")
    private int horsepower; // Potência em cavalos

    @NotBlank(message = "O tipo de combustível não pode ser vazio")
    private String fuelType; // Tipo de combustível (ex.: Gasolina, Elétrico, Híbrido)

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
