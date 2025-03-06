package app.controller;

import app.entity.Car;
import app.entity.Event;
import app.entity.EventProjection;
import app.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Event event) {
        try {
            Event newEvent = eventService.save(event);
            return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Event event = eventService.findById(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Event>> findAll() {
        try {
            List<Event> EventList = eventService.findAll();
            return new ResponseEntity<>(EventList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody Event event, @PathVariable Long id) {
        try {
            String updatedEvent = eventService.update(id, event);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String deleteEvent = this.eventService.delete(id);
            return new ResponseEntity<>(deleteEvent,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByLocation")
    public ResponseEntity <?> findByLocation(@RequestParam String location) {
        try {
            List<Event> eventList = eventService.findByLocation(location);
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/cars") // Rota mais clara e evita conflitos
    public ResponseEntity<?> getCarsByEventId(@PathVariable Long id) {
        try {
            List<Car> cars = eventService.getCarsByEventId(id);
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-date")
    public ResponseEntity<?> getEventsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    try {
        List<EventProjection> events = eventService.getEventsByDateRange(startDate, endDate);
        return ResponseEntity.ok(events);
    }catch (Exception e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}}