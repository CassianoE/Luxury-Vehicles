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
public class Plane {

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

    @Min(value = 100000, message = "O preço deve ser maior ou igual a 100.000")
    private double price;

    @Min(value = 10000, message = "A altitude máxima deve ser maior ou igual a 10.000 pés")
    @Max(value = 60000, message = "A altitude máxima não pode exceder 60.000 pés")
    private int maxAltitude; // Altitude máxima em pés (ex.: 41000)

    @Min(value = 1, message = "O avião deve ter pelo menos 1 motor")
    @Max(value = 8, message = "O número de motores não pode exceder 8")
    private int engineCount; // Número de motores (ex.: 2, 4)

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NotNull(message = "O proprietário não pode ser nulo")
    private Owner owner;
}
