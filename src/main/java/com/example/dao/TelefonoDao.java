package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;



public interface TelefonoDao extends JpaRepository<Telefono, Integer>{
    
    //Se necesita una consulta de sql para poder eliminar los teléfonos. 

    //Primer método

    // @Query (value= "delete from telefonos where estudiante_id = :idEstudiante", nativeQuery = true)
    // long deleteByIdEstudiante (@Param("idEstudiante")Integer idEstudiante); 

    long deleteByEstudiante(Estudiante estudiante); 

    //esto ha salido de poner solo findByEstudiante ctrl+space uuu

    // List<Telefono> findByEstudiante(Object estudiante);

    //No estaba inicialmente, porque teníamos el CRUD repository. Si quieres usar tus propias consultas, 
    //genera la implementación de ese método. 
    //se usa long para saber la cantidad de registros que ha borrado. 
    
}
