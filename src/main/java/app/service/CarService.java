package app.service;

import app.entity.Boat;
import app.entity.Car;
import app.entity.Event;
import app.entity.Owner;
import app.exeption.ResourceNotFoundException;
import app.messages.ErrorMessages;
import app.repository.CarRepository;
import app.repository.EventRepository;
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

    @Autowired
    private EventRepository eventRepository;

    public String save(Car car) {
        if (car.getOwner() != null && car.getOwner().getId() != null) {
            Owner ownerFromDb = ownerRepository.findById(car.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + car.getOwner().getId()));
            car.setOwner(ownerFromDb);
        }
        this.carRepository.save(car);
        return "Carro criado com sucesso";
    }

    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carro com o ID " + id + " não foi encontrado"));
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public String update(Long id, Car car) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            if (car.getBrand() != null) existingCar.setBrand(car.getBrand());
            if (car.getModel() != null) existingCar.setModel(car.getModel());
            if (car.getYear() != 0) existingCar.setYear(car.getYear());
            if (car.getPrice() != 0) existingCar.setPrice(car.getPrice());
            if (car.getHorsepower() != 0) existingCar.setHorsepower(car.getHorsepower());
            if (car.getFuelType() != null) existingCar.setFuelType(car.getFuelType());
            if (car.getOwner() != null) {
                if (car.getOwner().getId() == null) {
                    return "O ID do proprietário não pode ser nulo";
                }

                Optional<Owner> ownerOptional = ownerRepository.findById(car.getOwner().getId());
                if (ownerOptional.isEmpty()) {
                    return "Proprietário com ID " + car.getOwner().getId() + " não encontrado";
                }

                existingCar.setOwner(ownerOptional.get());
            }
            this.carRepository.save(existingCar);
            return "Carro atualizado com sucesso";
        } else {
            return "Carro com ID " + id + " não encontrado";
        }
    }

    public String delete(Long id) {
        Optional <Car> carOptional = carRepository.findById(id);
        Car car = carOptional.get();
        List<Event> events = eventRepository.findByCarsContains(car);
        if (!events.isEmpty()) {
            return "O carro está associado ao evento ID: " + events.get(0).getId() +
                    ". Remova-o do evento antes de excluir.";
        }
        if(carOptional.isPresent()){
        this.carRepository.deleteById(id);
        return "Carro deletado com sucesso";
    } else {
            return "Carro com ID " + id + " não encontrado";
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
