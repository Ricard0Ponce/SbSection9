package sb.uam.jpa.s9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sb.uam.jpa.s9.entities.Person;
import sb.uam.jpa.s9.entities.PersonDto;
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

    // La url con PathVariable debe ser exactamente igual a la que se esta pasando en el metodo
    // En este caso se esta pasando age1 y age2, por lo que la url debe ser /persons/age/1/2
    // Un ejemplo es: http://localhost:8080/api/v1/persons/age/25/40
    @GetMapping("/persons/age/{age1}/{age2}")
    public List<Object> findPersonByAgeBetween(@PathVariable Integer age1, @PathVariable Integer age2) {
        return personService.personaPorEdades(age1, age2);
    }

    @GetMapping("/persons/name/{name}")
    public Person findPersonByName(@PathVariable String name) {
        return personService.getPersonByName(name);
    }

    //Nota:
    // En repository y en service se usa Optional, sin embargo en el controller no se usa Optional
    // Aqui se usa ResponseEntity para devolver un objeto, en este caso Person
    // Esto me permite devolver un objeto o un null en caso de que no exista el objeto buscado en la base de datos
    // Ademas puedo devolver un status code, por ejemplo 200 si se encontro el objeto o 404 si no se encontro
    // Tambien se puede personalizar el mensaje de respuesta con ResponseEntity y devolver un mensaje personalizado
    @GetMapping("/persons/like/{name}")
    public ResponseEntity<Person> findPersonByLikeName(@PathVariable String name) {
        return ResponseEntity.ok(personService.obtenerPersonPorPalabra(name).orElse(null));
    }


    // Ejemplo de creacion de una persona:
    // {
    //     "name": "Pedro",
    //     "lastname": "Perez",
    //     "age": 25,
    //     "email": "
    // }
    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.createPerson(person).orElse(null));
    }

    // Se usa el endpoint con el id de la persona a actualizar
    @PutMapping("/persons/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person updatePerson) {
        return personService.updatePerson(id, updatePerson); // Nos regresa el JSON con los datos actualizados
    }

    // Con este endpoint eliminamos una persona por id
    @DeleteMapping("/persons/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.eliminaPersona(id);
    }

    // Nota: pasar esto a un Json
    @GetMapping("/persons/name-id/{id}")
    public ResponseEntity<?> personalizedQueries(@PathVariable Long id) {
        return ResponseEntity.ok(personService.personalizedQueries(id));
        // System.out.println(a);
        // return a;
    }

    // Nota: Pasar esto a un Json
    @GetMapping("/persons/all-name/{id}")
    public ResponseEntity<?> obtenTodoElNombrePorID(@PathVariable Long id) {
        return ResponseEntity.ok(personService.obtenTodoElNombrePorID(id));
    }

    @GetMapping("/persons/dto")
    public List<PersonDto> findPersonDto() {
        return personService.obtenListaPersonPorID();
    }

    @GetMapping("/persons/names/upper")
    public List<String> findAllNamesUpper() {
        return personService.obtenTodosLosNombresMayusculas();
    }

    @GetMapping("/persons/names/lower")
    public List<String> findAllNamesLower() {
        return personService.obtenTodosLosNombresMinusculas();
    }

    @GetMapping("/persons/age/{id1}/{id2}")
    public List<Person> getPersonBetween(@PathVariable Long id1, @PathVariable Long id2) {
        return personService.getPersonBetween(id1, id2);
    }

    // Regresa una lista de nombres y lo hace de manera descendente, es decir de la A a la Z
    @GetMapping("/persons/names/order-by-name")
    public List<String> findAllNombresOrdenados() {
        return personService.obtenNombresOrdenados();
    }

    @GetMapping("/persons/names/order-asc")
    public List<Person> findAllNombresOrdenadosAsc() {
        return personService.obtenNombresOrdenadosAsc();
    }

    // Regresa una lista de personas ordenada de menor a mayor en edad y que además esta entre dos id
    @GetMapping("/persons/id/{id1}/{id2}/order-age")
    public List<Person> findByIdBetweenOrderByAgeAsc(@PathVariable Long id1, @PathVariable Long id2) {
        return personService.findByIdBetweenOrderByAgeAsc(id1, id2);
    }

    @GetMapping("/persons/total")
    public Long totalPerson() {
        return personService.countPersons();
    }

    @GetMapping("/persons/min")
    public Long minPerson() {
        return personService.minId();
    }

    @GetMapping("/persons/max")
    public Long maxPerson() {
        return personService.maxId();
    }

    @GetMapping("/persons/suma-age")
    public Long sumEdades() {
        return personService.sumEdades();
    }

    @GetMapping("/persons/promedio-age")
    public Long promedioEdades() {
        return personService.promedioEdades();
    }

    @GetMapping("/persons/last-register")
    public Person lastRegisterPerson() {
        return personService.lastRegisterPerson();
    }



}
