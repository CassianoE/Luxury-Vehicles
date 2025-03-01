package app.service;

import app.entity.Boat;
import app.entity.Car;
import app.entity.Owner;
import app.messages.ErrorMessages;
import app.repository.CarRepository;
import app.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
 public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Car save(Car car) {
        if (car.getOwner() != null && car.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(car.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + car.getOwner().getId()));
            car.setOwner(ownerFromDb);
        }
        return carRepository.save(car);
    }

    public Car findById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        return carOptional.orElseThrow(() -> new RuntimeException(ErrorMessages.CAR_NOT_FOUND + id));
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car update(Long id, Car car) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            if (car.getBrand() != null) existingCar.setBrand(car.getBrand());
            if (car.getModel() != null) existingCar.setModel(car.getModel());
            if (car.getYear() != 0) existingCar.setYear(car.getYear());
            if (car.getPrice() != 0) existingCar.setPrice(car.getPrice());
            if (car.getHorsepower() != 0) existingCar.setHorsepower(car.getHorsepower());
            if (car.getFuelType() != null) existingCar.setFuelType(car.getFuelType());
            if (car.getOwner() != null) existingCar.setOwner(car.getOwner());
            return carRepository.save(existingCar);
        } else {
            throw new RuntimeException(ErrorMessages.CAR_NOT_FOUND + id);
        }
    }

    public void delete(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new RuntimeException(ErrorMessages.CAR_NOT_FOUND + id);
        }
    }

    public List<Car> findByModel(String model) {
        List <Car> carList = carRepository.findByModel(model);
        if (carList.isEmpty()) {
            throw new RuntimeException("Nenhum carro encontrado com o modelo: " + model);
        }
        return this.carRepository.findByModel(model);
    }

    public List<Car> findByFuelType(String fuelType) {
        List<Car> carList = carRepository.findByFuelType(fuelType);

        if (carList.isEmpty()) {
            throw new RuntimeException("Nenhum carro encontrado com o tipo de combustível: " + fuelType);
        }

        return carList;
    }
}
