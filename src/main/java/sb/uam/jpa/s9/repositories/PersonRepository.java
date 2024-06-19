package sb.uam.jpa.s9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sb.uam.jpa.s9.entities.Person;
import sb.uam.jpa.s9.entities.PersonDto;

import java.util.List;
import java.util.Optional;

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
    // La siguiente consulta contiene 2 parametros, por lo que usamos ?1 y ?2
    @Query("select p.name, p.lastname, p.age from Person p where p.age >= ?1 and p.age <= ?2")
    List<Object> obtenerPersonPorEdades(Integer age1, Integer age2);

    // Cuando queremos devolver solo un objeto, usamos Object
    @Query("select p from Person p where p.name = ?1")
    Person obtenerPersonPorNombre(String name);
    // El metodo anterior se puede conseguir facilmente con implementaciones de JPA, por lo que no es necesario
    // hacer una consulta personalizada
    // Person findByName(String name);

    // Con like podemos buscar por una palabra en especifico sin que sea exactamente igual, por ejemplo pe podria devolver pedro y todos los nombres que contengan pe
    // Ademas note que se usa % % encerrando la palabra a buscar
    @Query("select p from Person p where p.name like  %?1%")
    Optional <Person> obtenerPersonPorPalabra(String name);

    //Si se creara a partir de un Query Method, entonces usamos Containing para indicar que una parte de la cadena este contenida en el nombre
    // Optional<Person> findByNameContaining(String name);
    // Para saber más visite Query Creation en https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    // Para eliminar usamos:
    void deleteById(Long id); // Eliminamos por id, se usa void porque no se devuelve nada

    /* Usando JPQL*/
    // En el siguiente ejemplo solo devolvemos un campo, en este caso el Nombre
    @Query("select p.name from Person p where p.id = ?1") // Usamos query para hacer una consulta personalizada
    String obtenNombrePorID(Long id);

    // Tambien podemos concatenar usando || en lugar de concat, ejemplo
    // @Query("select p.name || ' ' || p.lastname from Person p ") // Usamos query para hacer una consulta personalizada
    // Cuando devolvemos 2 parametros concatebados:
    @Query("select concat(p.name, ' ',p.lastname) from Person p where p.id = ?1") // Usamos query para hacer una consulta personalizada
    String obtenTodoElNombrePorID(Long id);

    // Añadiendo a un DTO:
    // Nota: Como no lo detectaba puse el paquete en el que se encuentra
    @Query("select new sb.uam.jpa.s9.entities.PersonDto(p.name, p.lastname, p.email) from Person p") // Usamos query para hacer una consulta personalizada
    List<PersonDto> obtenPersonDtoPorID();

    // Ahora vamos a usar Distinct en el ejemplo:
    @Query("select p.name from Person p")
    List<String> findAllNames(); // En este caso devuelve todo los nombres incluso si se repiten

    // A continuacion se usa Distinct para que no se repitan los nombres
    @Query("select distinct(p.name) from Person p") // Se encierra el/los atributos en parentesis
    List<String> findAllDistinctNames(); // En este caso devuelve solo los nombres que no se repiten


    // A continuacion aplicamos concat, upper, lower y like en la consulta
    @Query("select upper(concat(p.name, ' ',p.lastname)) from Person p ") // Usamos query para hacer una consulta personalizada
    List<String> obtenTodosLosNombresMayuscula();

    @Query("select lower(concat(p.name, ' ',p.lastname)) from Person p ") // Usamos query para hacer una consulta personalizada
    List<String> obtenTodosLosNombresMinuscula();

    // Realizando consulta con between. (Por rangos de caracteres)
    @Query("select p from Person p where p.id between ?1 and ?2")
    List<Person> obtenPersonPorRangoDeID(Long name1, Long name2);

    // Realizando lo anterior con Query Methods.
    List<Person> findByAgeBetween(Integer age1, Integer age2);

    // A continuacion se realiza un ejemplo con la palabra clave order by
    // Esto ordena de manera Ascendente, si no se coloca nada se ordena de manera ascendente
    // Si quieres que ordene de manera descendente se agrega desc al final de la consulta
    // Tambien se puede ordenar según el parametro que se indique
    @Query("select p.name from Person p order by p.name") // Note que el order by se coloca al final
    List<String> obtenNombresOrdenados(); // En este caso devuelve todo los nombres incluso si se repiten

    //Usando Query Methods
    // Nota: Si usamos Query Method solo podemos devolver el objeto completo, no podemos devolver solo un campo
    List<Person> findAllByOrderByNameAsc(); // Ordena por nombre de forma ascendente, si fuera descendente se usaria Desc

    // Una consulta usando QueryMethods y juntando 2 campos es:
    List<Person> findByIdBetweenOrderByAgeAsc(Long id1, Long id2); // Ordena por nombre de forma ascendente y por edad de forma descendente

    /*Usando agregacion(Tipo numerico)que resume con:  count, max y min*/

    @Query("select count(p) from Person p")
    Long totalPerson(); // Devuelve el total de personas

    @Query("select min(p.id) from Person p")
    Long idMinimoDeLaTabla(); // Devolvemos el id más pequeño que tengamos registrado

    @Query("select max (p.id) from Person p")
    Long idMaximoDeLaTabla(); // Devolvemos el id más grande que tengamos registrado

    // Ejemplo usando sum con Query
    @Query ("select sum(p.age) from Person p")
    Long sumEdades();

    // Calculando el promedio de edades
    @Query("select avr(sum(p.age)) from Person p")
    Long promedioEdades();

    // Ejemplo de Subquery o Subconsulta
    @Query("select p from Person p where p.id=(select max(p.id) from Person p)")
    Person lastRegisterPerson();

    // Usando Where in:
    @Query("select p from Person p where p.id in ?1")
    public List<Person> getPersonByIds(List<Long> ids); // En este caso se busca por lista de ids
}
