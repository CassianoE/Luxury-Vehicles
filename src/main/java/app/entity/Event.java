package app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do Evento não pode ser vazia")
    @Size(min = 2, max = 50, message = "O nome do Evento deve ter entre 2 e 50 caracteres")
    private String name;

    @NotBlank(message = "O local do evento não pode ser vazio")
    @Size(min = 1, max = 50, message = "O local do evento deve ter entre 1 e 50 caracteres")
    private String location;

    @FutureOrPresent(message = "A data do evento deve ser no presente ou no futuro")
    @NotNull(message = "A data do evento não pode ser vazio")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_participants"
    )
    private List<Car> cars;
}
