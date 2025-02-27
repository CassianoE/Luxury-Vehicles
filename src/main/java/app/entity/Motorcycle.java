package app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Motorcycle {
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

    @Min(value = 5000, message = "O preço deve ser maior ou igual a 5.000")
    private double price;

    @Min(value = 50, message = "As cilindradas devem ser maiores ou iguais a 50cc")
    @Max(value = 3000, message = "As cilindradas não podem exceder 3000cc")
    private int engineDisplacement; // Cilindradas (ex.: 600cc, 1000cc)

    @NotBlank(message = "O tipo de moto não pode ser vazio")
    private String bikeType; // Tipo de moto (ex.: Esportiva, Naked, Touring)

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NotNull(message = "O proprietário não pode ser nulo")
    private Owner owner;
}
