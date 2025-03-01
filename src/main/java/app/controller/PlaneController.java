package app.controller;

import app.entity.Plane;
import app.service.PlaneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlaneController {

    @Autowired
    private PlaneService planeService;

    @PostMapping("/save")
    public ResponseEntity<Plane> save(@Valid @RequestBody Plane plane) {
        try {
            Plane newPlane = planeService.save(plane);
            return new ResponseEntity<>(newPlane, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Plane> findById(@PathVariable Long id) {
        try {
            Plane plane = planeService.findById(id);
            return new ResponseEntity<>(plane, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Plane>> findAll() {
        try {
            List<Plane> planeList = planeService.findAll();
            return new ResponseEntity<>(planeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Plane> update(@Valid @RequestBody Plane plane, @PathVariable Long id) {
        try {
            Plane updatedPlane = planeService.update(id, plane);
            return new ResponseEntity<>(updatedPlane, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            planeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByModel")
    public ResponseEntity<List<Plane>> findByModel(@RequestParam String model) {
        try {
            List<Plane> planeList = planeService.findByModel(model);
            return new ResponseEntity<>(planeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByEngineCount")
    public ResponseEntity<List<Plane>> findByEngineCount(@RequestParam int engineCount) {
        try {
            List<Plane> planeList = planeService.findByEngineCount(engineCount);
            return new ResponseEntity<>(planeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}