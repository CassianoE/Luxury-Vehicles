package app.controller;


import app.entity.Boat;
import app.entity.Car;
import app.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Car car) {
      try {
          String newCar = carService.save(car);
          return new ResponseEntity<>(newCar,HttpStatus.CREATED);
      } catch (Exception e) {
          return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
      }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Car car = carService.findById(id);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Car>> findAll() {
        try {
            List<Car> carList = carService.findAll();
            return new ResponseEntity<>(carList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Car car, @PathVariable Long id) {
        try {
            String updatedCar = carService.update(id, car);
            return new ResponseEntity<>(updatedCar, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String deleteCar = this.carService.delete(id);
            return new ResponseEntity<>(deleteCar,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByModel")
    public ResponseEntity<?> findByModel(@RequestParam String model) {
        try {
            List<Car> carList = carService.findByModel(model);
            return new ResponseEntity<>(carList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByFuelType")
    public ResponseEntity<?> findByFuelType(@RequestParam String fuelType) {
        try {
            List<Car> carList = carService.findByFuelType(fuelType);
            return new ResponseEntity<>(carList, HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    }



