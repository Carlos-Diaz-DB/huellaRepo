package com.carlos.poc.servicios;

import java.awt.*;
import java.util.List;

import com.carlos.poc.entidades.HuellaDigital;
import com.digitalpersona.onetouch.DPFPSample;


public interface HuellaServicio {

	public boolean agregarHuella(HuellaDigital huella);
	public List<HuellaDigital> obtenerHuellas(int idCiudadano);

}
