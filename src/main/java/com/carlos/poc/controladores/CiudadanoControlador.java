package com.carlos.poc.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.poc.entidades.Ciudadano;
import com.carlos.poc.entidades.Correo;
import com.carlos.poc.servicios.CiudadanoServicio;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/")
public class CiudadanoControlador {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
			false);

	@Autowired
	@Qualifier("CiudadanoServicio")
	private CiudadanoServicio ciudadanoServicio;

	@RequestMapping(path = "Ciudadano", method = RequestMethod.GET)
	public @ResponseBody List<Ciudadano> listarCiudadano() {
		try {
			return ciudadanoServicio.listarCiudadano();
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(path = "busqueda", method = RequestMethod.GET)
	public @ResponseBody Ciudadano busquedaDeCiudadano(@RequestParam String ciudadanoJSON,
			@RequestParam boolean filtro) {
		try {
			// Ciudadano ciudadano = mapper.readValue(ciudadanoJSON, Ciudadano.class);
			return ciudadanoServicio.buscarCiudadano(filtro, ciudadanoJSON);
		} catch (Exception ex) {
			log.error("ERROR CTRL(busquedaDeCiudadano): " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(path = "Ciudadano", method = RequestMethod.POST)
	public @ResponseBody boolean registrarCiudadano(@RequestBody String ciudadanoJSON) {
		try {
			Ciudadano ciudadano = mapper.readValue(ciudadanoJSON, Ciudadano.class);
			log.info("Se recibio del formulario: \n" + ciudadano.toString());

			if (ciudadanoServicio.agregarCiudadano(ciudadano)) {
				log.info("Se agrego el Ciudadano correctamente");
				return true;
			}
			return false;
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getMessage());
			return false;
		}
	}

	@RequestMapping(path = "Ciudadano", method = RequestMethod.PUT)
	public @ResponseBody boolean actualizarCiudadano(@RequestBody String ciudadanoJSON) {
		try {
			Ciudadano ciudadano = new Ciudadano();
			ciudadano = mapper.readValue(ciudadanoJSON, Ciudadano.class);
			log.info("Se recibio del formulario: \n" + ciudadano.toString());
			if (ciudadanoServicio.actualizarCiudadano(ciudadano)) {
				log.info("Se actualizo el Ciudadano correctamente");
				return true;
			}
			return false;
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getMessage());
			return false;
		}
	}

	@RequestMapping(path = "Ciudadano", method = RequestMethod.DELETE)
	public @ResponseBody boolean borrarCiudadano(@RequestBody String ciudadanoJSON) {
		try {
			if (ciudadanoJSON != null) {
				Ciudadano ciudadano = mapper.readValue(ciudadanoJSON, Ciudadano.class);
				return ciudadanoServicio.borrarCiudadano(ciudadano);
			}
			log.warn("El ciudadano llega vacio");
			return false;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
	}

	@RequestMapping(path = "Correo", method = RequestMethod.POST)
	public @ResponseBody void enviarMail(@RequestBody String correoJSON) {
		try {
			Correo correo = mapper.readValue(correoJSON, Correo.class);
			String ciudadanoJSON = correo.getCiudadano();
			Ciudadano ciudadano = mapper.readValue(ciudadanoJSON, Ciudadano.class);
			int veces = correo.getVeces();
			log.info("Ciudadano: " + ciudadano.toString());
			log.info("Veces: " + Integer.toString(veces));
			for (int i = 0; i < veces; i++) {
				ciudadanoServicio.enviarMail(ciudadano, "fotografia", "agregar");
				log.info("Se envio el correo correctamente: " + i);
			}
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getMessage());
		}
	}
}
