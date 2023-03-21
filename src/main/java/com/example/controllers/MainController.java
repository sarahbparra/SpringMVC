package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;

@Controller
@RequestMapping("/")

public class MainController {

    @Autowired
    private EstudianteService estudianteService; 

    //Se acaba de añadir el servicio de Facultades, porque es necesario para dar de alta a un estudiante. 
    //Inyectar el servicio de facultad, sino no hay acceso a los registros de la tabla facultad. 

    @Autowired
    private FacultadService facultadService; 

//Aquí dentro se encuentran los handler mapping creo. 

/**
 * El método siguiente devuelve un listado de estudiantes. 
 */

 //El GetMapping se utiliza para llamar a la url (http://localhost/8080/listar)
 @GetMapping("/listar")
public ModelAndView listar(){

    List<Estudiante> estudiantes = estudianteService.findaAll(); 

    ModelAndView mav = new ModelAndView("views/listarEstudiantes"); 
    mav.addObject("estudiantes", estudiantes); 
    return mav; 
}

/**
 * Método para mostrar el formulario de alta de estudiante. 
 * 
 * Se tiene que añadir un listado de facultades, para que se tome desde la base de datos. 
 */
@GetMapping("/frmAltaEstudiante")
public String formularioAltaEstudiante(Model model){

   List<Facultad> facultades = facultadService.findaAll(); 
   
    model.addAttribute("estudiante", new Estudiante()); 
    model.addAttribute("facultades", facultades); 

    return "views/formularioAltaEstudiante"; 
}

/**
 * Método que recibe los datos procedentes de los controles del formulario 
 */
@PostMapping("/altaEstudiante")
public void altaEstudiante(){


}
    
}
