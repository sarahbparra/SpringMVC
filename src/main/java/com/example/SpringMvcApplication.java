package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.entities.Estudiante.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

@SpringBootApplication
public class SpringMvcApplication implements CommandLineRunner{

	@Autowired
	private FacultadService facultadService; 

	@Autowired
	private EstudianteService estudianteService; 

	@Autowired 
	private TelefonoService telefonoService; 

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	/**
	 * Agregar registros de muestra para Facultad, Estudiante y Teléfono. 
	 * Necesitamos de la capa de servicios. 
	 * Hay que declarar los objetos, los services. 
	 */


	facultadService.save(Facultad.builder().nombre("Informática").build()); 

	facultadService.save(Facultad.builder().nombre("Biología").build());

	estudianteService.save(Estudiante.builder()
	.id(1)
	.nombre("Elisabet")
	.primerApellido("Agulló")
	.segundoApellido("García")
	.fechaAlta(LocalDate.of(2018, Month.SEPTEMBER, 01))
	.fechaNacimiento(LocalDate.of(1997, Month.JULY, 17))
	.genero(Genero.MUJER)
	.beca(6500.00)
	.facultad(facultadService.findById(1))
	.build());

	telefonoService.save(Telefono
	.builder()
	.id(1)
	.numero("666666")
	.estudiante(estudianteService
	.findById(1))
	.build());

	telefonoService.save(Telefono.builder()
	.id(2)
	.numero("9737373737")
	.estudiante(estudianteService.findById(1))
	.build());


	}

}
