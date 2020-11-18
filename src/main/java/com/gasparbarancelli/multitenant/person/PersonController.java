package com.gasparbarancelli.multitenant.person;

import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Person persist(@RequestParam("name") String name) {
        return repository.save(new Person(name));
    }

    @PutMapping("{id}")
    public Person update(@PathVariable("id") Long id, @RequestParam("name") String name) {
        var person = repository.findById(id)
                .map(it -> it.setName(name))
                .orElseThrow(NoResultException::new);
        return repository.save(person);
    }

    @GetMapping
    public List<Person> get() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Person> get(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

}
