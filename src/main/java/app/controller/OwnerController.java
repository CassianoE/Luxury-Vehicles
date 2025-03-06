package app.controller;

import app.entity.Owner;
import app.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {


    @Autowired
    private OwnerService ownerService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody Owner owner) {
        try {
            String newOwner = ownerService.save(owner);
            return new ResponseEntity<>(newOwner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Owner owner = ownerService.findById(id);
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Owner>> findAll() {
        try {
            List<Owner> ownerList = ownerService.findAll();
            return new ResponseEntity<>(ownerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody Owner owner, @PathVariable Long id) {
        try {
            String updatedOwner = ownerService.update(id, owner);
            return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String deleteOwner = this.ownerService.delete(id);
            return new ResponseEntity<>(deleteOwner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        try {
            List<Owner> ownerList = ownerService.findByName(name);
            return new ResponseEntity<>(ownerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        try {
            List<Owner> ownerList = ownerService.findByEmail(email);
                return new ResponseEntity<>(ownerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}