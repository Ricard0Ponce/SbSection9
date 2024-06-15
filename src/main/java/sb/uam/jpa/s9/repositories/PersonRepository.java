package sb.uam.jpa.s9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sb.uam.jpa.s9.entities.Person;

import java.util.List;

// Importante recordar que el repositorio debe ser una interfaz
// Tambien se puede usar CrudRepository en lugar de JpaRepository, sin embargo
// usar CrudRepository es mas generico y no se recomienda
// JPA Ordena y pagina/ordena los datos
public interface PersonRepository extends JpaRepository<Person, Long> {

    // Agregamos metodos personalizados, es importante que el nombre del metodo sea en ingles, pues jpa lo traduce a sql
    @Override
    List<Person> findAll();
    Person findByName(String name);
    @Query("select p from Person p where p.age >= 18") // Usamos query para hacer una consulta personalizada
    List<Person> findAdults(); // Note que Adults es un metodo personalizado que busca a las personas mayores de 18 años
    // La siguiente consulta devuelve un objeto con los atributos name, lastname y age, por lo que usamos Object
    @Query("select p.name, p.lastname, p.age from Person p where p.age >=25 and p.age <= 40")
    List<Object> obtenerPersonData();
}
