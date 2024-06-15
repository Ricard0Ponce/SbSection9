package sb.uam.jpa.s9.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sb.uam.jpa.s9.entities.Person;
import sb.uam.jpa.s9.repositories.PersonRepository;

import java.util.List;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Person> getAdults() {
        return personRepository.findAdults();
    }

    public List<Object> getPersonData() {
        return personRepository.obtenerPersonData();
    }

}
