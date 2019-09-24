package com.carlos.poc.implementaciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.poc.entidades.HuellaDigital;
import com.carlos.poc.repositorio.HuellaRepositorio;
import com.carlos.poc.servicios.HuellaServicio;

import java.util.List;

@Service("HuellaServicio")
public class HuellaServicioImpl implements HuellaServicio{

    //procesosHuella obj = new procesosHuella();
	private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HuellaRepositorio huellaRepositorio;




    @Override
    public boolean agregarHuella(HuellaDigital huella)
    {
        try{

                if(huella != null)
                {
                    huellaRepositorio.save(huella);
                    log.info("Se agrego la huella digital: "+huella.toString());
                    return true;
                }
                log.warn("No ha sido posible agregar la huella");
                return false;

        //return true;
        }
            catch (Exception ex)
            {
                log.error("ERROR Servicioimpl:  "+ex.getMessage());
                return false;
            }

    }

    @Override
    public List<HuellaDigital> obtenerHuellas(int idCiudadano) {
        try {
            return (List<HuellaDigital>) huellaRepositorio.findAllByCiudadano(idCiudadano);
        }catch(Exception ex){
            log.error(ex.getMessage());
        }
        return null;
    }


}
