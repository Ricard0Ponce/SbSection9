package sb.uam.jpa.s9.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

// Usaremos esta clase para demostrar el uso de eventos de ciclo de vida, de esta manera
// desacoplamos la logica de la clase Person de la logica de auditoria
@Embeddable // Indicamos que es una clase embebida
public class Audit {

    @Column(name = "created_at")
    private LocalDateTime creatAt; // Campo para saber en que momento se creo el usuario

    @Column(name = "updated_at")
    private LocalDateTime updateAt; // Campo para saber en que momento se actualizo el usuario

    @PrePersist
    public void prePersiste(){
        System.out.println("Antes de persistir");
        this.creatAt = LocalDateTime.now(); // Indicamos que la fecha de creacion es la fecha actual
    }

    @PreUpdate
    public void preUpdate(){
        System.out.println("Antes de actualizar");
        this.updateAt = LocalDateTime.now(); // Indicamos que la fecha de actualizacion es la fecha actual
    }
}
