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
    public ResponseEntity<?> save(@Valid @RequestBody Boat boat) {
        try {
            String newBoat = boatService.save(boat);
            return new ResponseEntity<>(newBoat, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Boat boat = boatService.findById(id);
            return new ResponseEntity<>(boat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
    public ResponseEntity<?> update(@Valid @RequestBody Boat boat, @PathVariable Long id) {
        try {
            String updatedBoat = boatService.update(id, boat);
            return new ResponseEntity<>(updatedBoat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String deleteBoat = this.boatService.delete(id);
            return new ResponseEntity<>(deleteBoat,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByModel")
    public ResponseEntity<?> findByModel(@RequestParam String model) {
        try {
            List<Boat> boatList = boatService.findByModel(model);
            return new ResponseEntity<>(boatList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByYear")
    public ResponseEntity<?> findByYear(@RequestParam int year) {
        try {
            List<Boat> boatList = boatService.findByYear(year);
            return new ResponseEntity<>(boatList, HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByLengthGreaterThanEqual")
    public  ResponseEntity<?> findByLengthGreaterThanEqual(@RequestParam int length) {
        try {
            List<Boat> boatList = this.boatService.findByLengthGreaterThanEqual(length);
            return new ResponseEntity<>(boatList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}