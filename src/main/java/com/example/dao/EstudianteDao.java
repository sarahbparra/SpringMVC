package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Estudiante;

@Repository

public interface EstudianteDao extends JpaRepository<Estudiante, Integer> {

    /**
     * Comentario en formato Javadoc 
     */

    
}
