package app.service;

import app.entity.Car;
import app.entity.Event;
import app.entity.EventProjection;
import app.messages.ErrorMessages;
import app.repository.CarRepository;
import app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {


    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private final CarRepository carRepository;

    public EventService(EventRepository eventRepository, CarRepository carRepository) {
        this.eventRepository = eventRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    public Event save(Event event) {
        if (event.getCars() == null || event.getCars().isEmpty()) {
            throw new RuntimeException("O evento deve ter pelo menos um carro");
        }
        if (event.getDate() == null ){
            throw new RuntimeException("A data do evento não pode ser nula");
        }
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new RuntimeException("O nome do evento não pode ser nulo ou vazio");
        }
        // Buscar os carros pelo ID antes de salvar
        List<Car> carsFromDb = event.getCars().stream()
                .map(car -> carRepository.findById(car.getId())
                        .orElseThrow(() -> new RuntimeException("Carro não encontrado: " + car.getId())))
                .collect(Collectors.toList());

        event.setCars(carsFromDb);
        return eventRepository.save(event);
    }

    public String delete(Long id) {
        Optional <Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            eventRepository.deleteById(id);
            return "Evento deletado com sucesso";
        } else {
            return ("Evento não encontrado: " + id);
        }
    }

    @Transactional
    public String update(Long id, Event event) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event existingEvent = eventOptional.get();
            if (event.getName() != null) existingEvent.setName(event.getName());
            if (event.getLocation() != null) existingEvent.setLocation(event.getLocation());
            if (event.getDate() != null) existingEvent.setDate(event.getDate());
            if (event.getCars() != null && !event.getCars().isEmpty()) {
                List<Car> carsFromDb = event.getCars().stream()
                        .map(car -> carRepository.findById(car.getId())
                                .orElseThrow(() -> new RuntimeException("Carro não encontrado: " + car.getId())))
                        .collect(Collectors.toList());
                existingEvent.setCars(carsFromDb);
            }
            this.eventRepository.save(existingEvent);

            return "Evento atualizado com sucesso";
        }else {
            return "Evento não encontrado com ID: " + id;
        }
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Evento com o ID " + id + " não foi encontrado"));
    }

    public  List<Event> findByLocation(String location) {
        List <Event> eventList = eventRepository.findByLocation(location);
        if (eventList.isEmpty()) {
            throw new RuntimeException("Nenhum evento encontrado com o local: " + location);
        }
        return eventRepository.findByLocation(location);
    }

    public List<Car> getCarsByEventId(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));

        return event.getCars(); // Retorna a lista de carros do evento
    }

    public List<EventProjection> getEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("As datas de início e fim são obrigatórias");
        }
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("A data de início não pode ser maior que a data de fim");
        }

        List <EventProjection> eventList = eventRepository.findEventsByDateRange(startDate, endDate);
        if (eventList.isEmpty()) {
            throw new RuntimeException("Nenhum evento encontrado entre as datas: " + startDate + " e " + endDate);
        }
        return eventRepository.findEventsByDateRange(startDate, endDate);
    }
}
