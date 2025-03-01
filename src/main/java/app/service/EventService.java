package app.service;

import app.entity.Car;
import app.entity.Event;
import app.messages.ErrorMessages;
import app.repository.CarRepository;
import app.repository.EventRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CarRepository carRepository;

    public EventService(EventRepository eventRepository, CarRepository carRepository) {
        this.eventRepository = eventRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    public Event save(Event event) {
        if (event.getCars() == null || event.getCars().isEmpty()) {
            throw new IllegalArgumentException("O evento deve ter pelo menos um carro");
        }

        List<Car> carsFromDb = event.getCars().stream()
                .map(car -> carRepository.findById(car.getId())
                        .orElseThrow(() -> new RuntimeException("Carro não encontrado: " + car.getId())))
                .collect(Collectors.toList());

        event.setCars(carsFromDb);
        return eventRepository.save(event);
    }


    public void delete(long id) {
        if (eventRepository.existsById(id))
        {
            eventRepository.deleteById(id);
        } else {
            throw new RuntimeException(ErrorMessages.EVENT_NOT_FOUND + id);
        }
    }


    @Transactional
    public Event update(long id, Event event) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado: " + id));

        if (event.getDate() != null) {
            existingEvent.setDate(event.getDate());
        }
        if (event.getName() != null && !event.getName().isEmpty()) {
            existingEvent.setName(event.getName());
        }
        if (event.getLocation() != null && !event.getLocation().isEmpty()) {
            existingEvent.setLocation(event.getLocation());
        }
        if (event.getCars() != null && !event.getCars().isEmpty()) {
            List<Car> carsFromDb = event.getCars().stream()
                    .map(car -> carRepository.findById(car.getId())
                            .orElseThrow(() -> new RuntimeException("Carro não encontrado: " + car.getId())))
                    .collect(Collectors.toList());
            existingEvent.setCars(carsFromDb);
        }

        return eventRepository.save(existingEvent);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        return eventOptional.orElseThrow(() -> new RuntimeException(ErrorMessages.EVENT_NOT_FOUND + id));
    }
}
