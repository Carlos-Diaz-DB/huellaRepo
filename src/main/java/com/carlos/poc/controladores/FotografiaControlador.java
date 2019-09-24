package com.carlos.poc.controladores;

import com.carlos.poc.entidades.Fotografia;
import com.carlos.poc.servicios.FotografiaServicio;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class FotografiaControlador {

	@Autowired
	@Qualifier("FotografiaServicio")
	FotografiaServicio fotografiaServicio;

	@Value("${file.upload-foto}")
	private String pathFoto;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
			false);

	@RequestMapping(path = "fotoCiudadano", method = RequestMethod.GET)
	public @ResponseBody List<Fotografia> listaById(int id) {
		try {
			return fotografiaServicio.listaById(id);
		} catch (Exception ex) {
			log.error("ERROR" + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(path = "foto", method = RequestMethod.GET)
	public @ResponseBody List<Fotografia> listaTareas() {
		try {
			return fotografiaServicio.listarFotografias();
		} catch (Exception ex) {
			log.error("ERROR" + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(path = "subirFoto", method = RequestMethod.POST)
	public boolean subirFotografia(@RequestBody String foto) {
		log.info("path subri foto");

		try {
			 return fotografiaServicio.escribirFotografia(foto);

		} catch (Exception ex) {
			log.info("Entro exception");
		}
		return false;
	}

	@RequestMapping(path = "foto", method = RequestMethod.POST)
	public @ResponseBody boolean registrarFotografia(@RequestBody String fotoJSON) {
		System.out.println(fotoJSON);
		try {
			Fotografia foto = mapper.readValue(fotoJSON, Fotografia.class);

			if (fotografiaServicio.agregarFotografia(foto)) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			log.error("error: "+ex.getMessage());
			return false;
		}
	}

	@RequestMapping(path = "foto", method = RequestMethod.PUT)
	public @ResponseBody boolean actualizarFotografia(@RequestBody String fotoJSON) {
		try {
			Fotografia foto = new Fotografia();

			foto = mapper.readValue(fotoJSON, Fotografia.class);

			if (fotografiaServicio.actualizarFotografia(foto)) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}

	@RequestMapping(path = "foto", method = RequestMethod.DELETE)
	public @ResponseBody boolean borrarFotografia(@RequestBody String fotoJSON) {
		try {
			if (fotoJSON != null) {
				Fotografia tarea = mapper.readValue(fotoJSON, Fotografia.class);
				return fotografiaServicio.eliminarFotografia(tarea);
			}
			return false;
		} catch (Exception ex) {
			System.out.println("ERROR borrando" + ex.getMessage());
			return false;
		}
	}
}
