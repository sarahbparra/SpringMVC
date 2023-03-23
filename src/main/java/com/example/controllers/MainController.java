package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

@Controller
@RequestMapping("/")

public class MainController {

    private static final Logger LOG = Logger.getLogger("MainController"); 

    @Autowired
    private EstudianteService estudianteService; 

    //Se acaba de añadir el servicio de Facultades, porque es necesario para dar de alta a un estudiante. 
    //Inyectar el servicio de facultad, sino no hay acceso a los registros de la tabla facultad. 

    @Autowired
    private FacultadService facultadService; 

    //Se inserta TelefonoService para lo que estamos haciendo de insertar teléfonos. 

    @Autowired
    private TelefonoService telefonoService; 

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

   Estudiante estudiante = new Estudiante();
   
    model.addAttribute("estudiante", estudiante); 
    model.addAttribute("facultades", facultades); 

    return "views/formularioAltaEstudiante"; 
}

/**
 * Método que recibe los datos procedentes de los controles del formulario 
 */
@PostMapping("/altaModificacionEstudiante")
//El método altaEstudiante() tiene que recibir un atributo del modelo. 
public String altaEstudiante(@ModelAttribute Estudiante estudiante, 
        @RequestParam(name = "numerosTelefonos") String telefonosRecibidos){

    LOG.info("teléfonos recibidos:  " + telefonosRecibidos);

    estudianteService.save(estudiante);

    List<String> listadoNumerosTelefonos = null; 

    if (telefonosRecibidos != null){
        String[] arrayTelefonos = telefonosRecibidos.split(";"); 

     listadoNumerosTelefonos = Arrays.asList(arrayTelefonos); 
    }
    

    if(listadoNumerosTelefonos != null){

        telefonoService.deleteByEstudiante(estudiante);

        //Borrar todos los teléfonos que tenga el estudiante, si hay que insertar nuevos.

        listadoNumerosTelefonos.stream().forEach(n -> {
            Telefono telefonoObject = Telefono
            .builder()
            .numero(n)
            .estudiante(estudiante)
            .build(); 

            telefonoService.save(telefonoObject);
        });

    }

    return "redirect:/listar"; 

}
    

/**
 * Creo que método para borrar 
 */

 //no puede ser public void porque el controller siempre genera algo (vistas)

 @GetMapping("/borrar/{id}")
 public String borrarEstudiante(@PathVariable(name = "id") int idEstudiante){

    estudianteService.delete(estudianteService.findById(idEstudiante));
    return "redirect:/listar"; 
 }

/**
 * Muestra el formulario para actualizar un estudiante. 

 */
 @GetMapping("/frmActualizar/{id}")
 public String frmActualizarEstudiante(@PathVariable(name = "id") int idEstudiante, Model model){

    //Evita el null pointer exception 
    Estudiante estudiante = estudianteService.findById(idEstudiante); 

    List<Telefono> todosTelefonos = telefonoService.findaAll(); 

    List<Telefono> telefonosDelEstudiante = todosTelefonos.stream()
    .filter(t -> t.getEstudiante().getId() == idEstudiante)
    .collect(Collectors.toList());

    String numerosDeTelefono = telefonosDelEstudiante.stream().map(t -> t.getNumero()).collect(Collectors.joining(";"));

    List<Facultad> facultades = facultadService.findaAll(); 

    model.addAttribute("estudiante", estudiante); 
    model.addAttribute("telefonos", numerosDeTelefono); 
    model.addAttribute("facultades", facultades); 

    return "views/formularioAltaEstudiante"; 
 }


}


