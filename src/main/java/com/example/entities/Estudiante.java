package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estudiantes")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Estudiante implements Serializable{

    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @NotNull(message = "Nombre no puede ser nulo.")
    @Size(max = 24, min = 4)

    private String nombre; 
    private String primerApellido; 
    private String segundoApellido; 

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAlta; 

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento; 
   
    private Genero genero; 
    private double beca; 

    // private int idFacultad; Esto no es necesario ya que con JoinColumn, esta columna se crea de manera automática. 

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "idFacultad")
    private Facultad facultad; 

    // Unión con teléfonos 
    //Se usa el fetchtype a lazy porque 
    //Eager está buscando todo el rato las relaciones. Si quieres borrar estudiante y con ello eléfono. 
    //Y además teléfono estaba en persist y se estaba fastidiando por eso. 
    //LAZY SIEMPRE. No consume recursos permanentemente. 
    //La foreign key solo se pone en el lado de muchos. 
    //Borrar padre y que se borren los hijos, aquí no se puede. Da igual el tipo de cascadetype. 
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "estudiante")
    private List<Telefono> telefonos; 
    

    public enum Genero {
        HOMBRE, MUJER, OTRO
    }
    
}

//Los teléfonos se quieren eliminar por idEstudiante, hay que usar consulta de sql. 