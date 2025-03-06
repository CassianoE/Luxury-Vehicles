package app.entity;

import java.time.LocalDate;

public interface EventProjection {
    String getName();
    LocalDate getDate();
}


// Notaçao para poder retornar o Json como esperado, para mostrar apenas o necessario!