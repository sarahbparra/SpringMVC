package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;

public interface EstudianteService {

    public List<Estudiante> findaAll(); 
    public Estudiante findById(int idEstudiante); 
    public void save(Estudiante estudiante); 
    public void deleteById(int idEstudiante);
    public void delete(Estudiante estudiante);  

    /**
     * No es necesario un método update ya que el save inserta o actualiza, 
     * en dependencia de que el idEstudiante exista o no, es decir, 
     * si no existe lo crea y si existe actualiza la información ^-^
     */
    
}
