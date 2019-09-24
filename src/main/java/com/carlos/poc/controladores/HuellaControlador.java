package com.carlos.poc.controladores;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.poc.entidades.HuellaDigital;
import com.carlos.poc.servicios.HuellaServicio;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class HuellaControlador {


	private Logger log = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
    
    @Autowired
    @Qualifier("HuellaServicio")
    private HuellaServicio huellaServicio;

    
    @RequestMapping(path = "huella", method = RequestMethod.POST)
    public @ResponseBody boolean agregarHuella(@RequestBody String huellaJSON)
    {
        try
        {
            HuellaDigital huella = mapper.readValue(huellaJSON, HuellaDigital.class);
            log.info("Se recibio del formulario: \n"+huella.toString());

            if (huellaServicio.agregarHuella(huella))
            {
                log.info("Se registro la huella con exito");
                return true;
            }
            huellaServicio.agregarHuella(huella);
            return true;
        }
        catch (Exception ex)
        {
            log.error("ERROR CONTROLADOR: "+ex.getMessage());
            return false;
        }
    }

    @RequestMapping(path = "huella/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<HuellaDigital> obtenerHuellas(@PathVariable("id") int idCiudadano){
        try{
            return huellaServicio.obtenerHuellas(idCiudadano);
        }catch (Exception ex){
            log.error("ERROR: "+ex.getMessage());
            return null;
        }
    }



    
}
