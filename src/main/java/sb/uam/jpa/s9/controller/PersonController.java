package sb.uam.jpa.s9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sb.uam.jpa.s9.entities.Person;
import sb.uam.jpa.s9.services.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public List<Person> findAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/persons/adults")
    public List<Person> findAdults() {
        return personService.getAdults();
    }

    @GetMapping("/persons/data")
    public List<Object> findPersonData() {
        return personService.getPersonData();
    }

}
