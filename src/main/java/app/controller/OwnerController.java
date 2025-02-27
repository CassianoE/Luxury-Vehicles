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
    public ResponseEntity<Owner> save(@Valid @RequestBody Owner owner) {
        try {
            Owner newOwner = ownerService.save(owner);
            return new ResponseEntity<>(newOwner, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Owner> findById(@PathVariable Long id) {
        try {
            Owner owner = ownerService.findById(id);
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Owner> update(@Valid @RequestBody Owner owner, @PathVariable Long id) {
        try {
            Owner updatedOwner = ownerService.update(id, owner);
            return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            ownerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByNameIgnoreCaseContaining/{name}")
    public ResponseEntity<List<Owner>> findByNameIgnoreCaseContaining(@PathVariable String name) {
        try {
            List<Owner> ownerList = ownerService.findByNameIgnoreCaseContaining(name);
            return new ResponseEntity<>(ownerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findByEmailIgnoreCase/{email}")
    public ResponseEntity<List<Owner>> findByEmailIgnoreCase(@PathVariable String email) {
        try {
            List<Owner> ownerList = ownerService.findByEmailIgnoreCase(email);
            return new ResponseEntity<>(ownerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}