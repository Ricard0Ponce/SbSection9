package sb.uam.jpa.s9.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sb.uam.jpa.s9.entities.Person;
import sb.uam.jpa.s9.entities.PersonDto;
import sb.uam.jpa.s9.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;

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

    public List<Object> personaPorEdades(Integer age1, Integer age2) {
        return personRepository.obtenerPersonPorEdades(age1, age2);
    }

    public Person getPersonByName(String name) {
        return personRepository.findByName(name);
    }

    // Cuando los metodos son solo de lectura a la transaccion se le pone readonly = true
    @Transactional(readOnly = true)
    public Optional<Person> obtenerPersonPorPalabra(String name) {
        return personRepository.obtenerPersonPorPalabra(name);
    }

    // Agregamos a este metodo una Transactional para que se ejecute en una transaccion de la base de datos y se pueda hacer un rollback en caso de error
    @Transactional // Esta anotacion es importante para que se ejecute en una transaccion de la base de datos
    public Optional<Person> createPerson(Person person) {
        return Optional.of(personRepository.save(person));
    }

    @Transactional
    public Person updatePerson(Long id, Person updatePerson) {
        // Explicacion de la funcion map
        // Si el id existe, entonces se actualiza el email
        // Si el id no existe, entonces regresa un null
        return personRepository.findById(id) // Buscamos el id
                .map(person -> { // Si lo encuentra, entonces actualizamos el email
                    person.setEmail(updatePerson.getEmail()); // Tomamos los datos de updatePerson y los actualizamos en person
                    return personRepository.save(person); // Almacenamos los datos actualizados
        })
                .orElseGet(() -> { // Si no encuentra el id, entonces regresamos un null
                    //updatePerson.setId(id); // Actualizamos el id
                    return null; // Regresamos un null
                });
    }

    // Implementamos el metood de eliminar
    @Transactional
    public void eliminaPersona(Long id) {
        if (personRepository.findById(id).isEmpty()) {
            throw new IllegalStateException("La persona con el id " + id + " no existe");
        } else {
            personRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public String personalizedQueries(Long id) {
        return personRepository.obtenNombrePorID(id);
    }

    @Transactional(readOnly = true)
    public String obtenTodoElNombrePorID(Long id) {
        return personRepository.obtenTodoElNombrePorID(id);
    }

    @Transactional(readOnly = true)
    public List<PersonDto> obtenListaPersonPorID() {
        return personRepository.obtenPersonDtoPorID();
    }

    @Transactional(readOnly = true)
    public List<String> obtenTodosLosNombresMayusculas() {
        return personRepository.obtenTodosLosNombresMayuscula();
    }

    @Transactional(readOnly = true)
    public List<String> obtenTodosLosNombresMinusculas(){
        return personRepository.obtenTodosLosNombresMinuscula();
    }

    @Transactional(readOnly = true)
    public List<Person> getPersonBetween(Long id1, Long id2) {
        return personRepository.obtenPersonPorRangoDeID(id1, id2);
    }

    @Transactional(readOnly = true)
    public List<Person> obtenPorRangoDeEdad(Integer age1, Integer age2) {
        return personRepository.findByAgeBetween(age1, age2);
    }

    @Transactional(readOnly = true)
    public List<String> obtenNombresOrdenados() {
        return personRepository.obtenNombresOrdenados();
    }

    @Transactional(readOnly = true)
    public List<Person> obtenNombresOrdenadosAsc() {
        return personRepository.findAllByOrderByNameAsc();
    }

    @Transactional(readOnly = true)
    public List<Person> findByIdBetweenOrderByAgeAsc(Long id1, Long id2) {
        return personRepository.findByIdBetweenOrderByAgeAsc(id1, id2);
    }

    @Transactional(readOnly = true)
    public Long countPersons() {
        return personRepository.count();
    }

    @Transactional(readOnly = true)
    public Long minId() {
        return personRepository.idMinimoDeLaTabla();
    }

    @Transactional(readOnly = true)
    public Long maxId() {
        return personRepository.idMaximoDeLaTabla();
    }

    @Transactional(readOnly = true)
    public Long sumEdades() {
        return personRepository.sumEdades();
    }

    @Transactional(readOnly = true)
    public Long promedioEdades() {
        return personRepository.promedioEdades();
    }

    @Transactional(readOnly = true)
    public Person lastRegisterPerson() {
        return personRepository.lastRegisterPerson();
    }


}


