package sb.uam.jpa.s9.entities;

// Creamos la clase persona

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons") // Indicamos el nombre de la base de datos
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id // Indicamos que es la clave primaria
    @GeneratedValue // Indicamos que es autoincremental, cada que se inserte un nuevo registro se incrementara
    private Long id;
    // Sí dejamos por defecto el valor de la columna, se tomara el nombre del atributo
    private String name;
    @Column(name = "last_name") // Indicamos el nombre de la columna, Cuando sean dos palabras se recomienda usar guion bajo
    private String lastname;
    private Integer age;
    private String email;


}
