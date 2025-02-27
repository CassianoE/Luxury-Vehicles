package app.controller;

import app.entity.Boat;
import app.service.BoatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boats")
public class BoatController {

    @Autowired
    private BoatService boatService;

    @PostMapping("/save")
    public ResponseEntity<Boat> save(@Valid @RequestBody Boat boat) {
        try {
            Boat newBoat = boatService.save(boat);
            return new ResponseEntity<>(newBoat, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Boat> findById(@PathVariable Long id) {
        try {
            Boat boat = boatService.findById(id);
            return new ResponseEntity<>(boat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Boat>> findAll() {
        try {
            List<Boat> boatList = boatService.findAll();
            return new ResponseEntity<>(boatList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boat> update(@Valid @RequestBody Boat boat, @PathVariable Long id) {
        try {
            Boat updatedBoat = boatService.update(id, boat);
            return new ResponseEntity<>(updatedBoat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            boatService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findByModel/{model}")
    public ResponseEntity<List<Boat>> findByModel(@PathVariable String model) {
        try {
            List<Boat> boatList = boatService.findByModel(model);
            return new ResponseEntity<>(boatList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findByYear")
    public ResponseEntity<List<Boat>> findByYear(@PathVariable int year) {
        try {
            List<Boat> boatList = this.boatService.findByYear(year);
            return new ResponseEntity<>(boatList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findByLengthGreaterThanEqual")
    public  ResponseEntity<List<Boat>> findByLengthGreaterThanEqual(@PathVariable int length) {
        try {
            List<Boat> boatList = this.boatService.findByLengthGreaterThanEqual(length);
            return new ResponseEntity<>(boatList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}